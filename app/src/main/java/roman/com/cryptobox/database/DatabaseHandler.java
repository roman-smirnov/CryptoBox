package roman.com.cryptobox.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import roman.com.cryptobox.utils.MyApplication;
import roman.com.cryptobox.dataobjects.DBNote;
import roman.com.cryptobox.dataobjects.KeyWrapper;
import roman.com.cryptobox.dataobjects.Note;
import roman.com.cryptobox.encryption.CryptoManager;
import roman.com.cryptobox.utils.PasswordHandler;

/**
 * Created by roman on 9/17/16.
 */

public class DatabaseHandler extends SQLiteOpenHelper {


    /**
     * main constructor
     * @param context
     */
    public DatabaseHandler(Context context) {
        super(context, DatabaseContract.DATABASE_NAME, null, DatabaseContract.DATABASE_VERSION);
    }


    /**
     * initialize database on first use of app
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaseContract.TableFiles.CREATE_TABLE);
        db.execSQL(DatabaseContract.TableKeys.CREATE_TABLE);
    }

    /**
     * on database upgrade - drop all tables and re-create the database
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL(DatabaseContract.TableFiles.DELETE_TABLE);
        db.execSQL(DatabaseContract.TableKeys.DELETE_TABLE);

        // Create tables again
        onCreate(db);
    }

    /**
     * add a note to the database
     * @param title
     * @param lastModified
     * @param content
     */
    public Note addNote(String title, String lastModified, String content) {
        SQLiteDatabase db = this.getWritableDatabase();

        //generate new symmetric key
        KeyWrapper wrapper = generateNewKeyToDB(db);

        //encrypt title and content with the new generated key
        DBNote DbNote = new DBNote(title, lastModified, content, wrapper.decryptedKey);

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.TableFiles.COLUMN_TITLE, DbNote.getEncryptedTitle());
        values.put(DatabaseContract.TableFiles.COLUMN_LAST_UPDATED, DbNote.getEncryptedLastModified());
        values.put(DatabaseContract.TableFiles.COLUMN_CONTENT, DbNote.getEncryptedContent());
        values.put(DatabaseContract.TableFiles.COLUMN_KEY_ID, wrapper.keyId);

        // Inserting Row
        long rowId = db.insert(DatabaseContract.TableFiles.TABLE_NAME, null, values);

        //update table keys to make that id marked as used.
        updateNewKeyToUsedKey(wrapper.keyId, db);

        //close the connection
        db.close();

        //create Note and return it.
        Note note = new Note(title, lastModified, rowId);
        return note;
    }

    private Boolean updateNewKeyToUsedKey(long id, SQLiteDatabase db)
    {
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.TableKeys.COLUMN_KEY_IS_USED, "1");

        int rowsAffected = db.update(DatabaseContract.TableKeys.TABLE_NAME, values, "id = " + id, null);

        return (rowsAffected == 1)? true : false;
    }

    /**
     * Generate random key and save it as a new row in KEYS table
     */
    private KeyWrapper generateNewKeyToDB(SQLiteDatabase db){


        //generate new symmetric key
        String key = CryptoManager.Symmetric.AES.generateKey();

        //encrypt the key with user password.
        String encryptedKey = CryptoManager.Symmetric.AES.encryptText(key, PasswordHandler.getSessionPassword());

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.TableKeys.COLUMN_KEY_DATA, encryptedKey);
        values.put(DatabaseContract.TableKeys.COLUMN_KEY_DATA_BACKUP, encryptedKey);
        values.put(DatabaseContract.TableKeys.COLUMN_KEY_IS_USED, "0");

        // Inserting Row
        long rowId = db.insert(DatabaseContract.TableKeys.TABLE_NAME, null,  values);

        //wrap all needed data for creation of a new file.
        KeyWrapper wrapper = new KeyWrapper();
        wrapper.keyId = rowId;
        wrapper.encryptedKey = encryptedKey;
        wrapper.decryptedKey = key;

        return wrapper;
    }

    /**
     * get Note's content from the database by note id.
     * @param id
     * @return
     */
    public static String getContentById(Long id) {

        DatabaseHandler handler = new DatabaseHandler(MyApplication.getContext());
        SQLiteDatabase db =  handler.getReadableDatabase();

        //Get Encrypted key from DB

        //With password, decrypt the key.

        //with the decrypted key, decrypt the content from DB

        Cursor cursor = db.query(DatabaseContract.TableFiles.TABLE_NAME,
                new String [] {DatabaseContract.TableFiles.COLUMN_CONTENT,
                        DatabaseContract.TableFiles.COLUMN_KEY_ID},
                "id = " + id, null, "" ,"", "" );

        String encryptedContent;
        String decryptedContent = "";

        if(cursor.moveToFirst())
        {
            int contentIndex = cursor.getColumnIndex(DatabaseContract.TableFiles.COLUMN_CONTENT);
            int KeyIdIndex = cursor.getColumnIndex(DatabaseContract.TableFiles.COLUMN_KEY_ID);

            byte[] arr = cursor.getBlob(contentIndex);
            Long keyId = cursor.getLong(KeyIdIndex);

            if(arr != null)
            {
                String encryptionKey = getEncryptionKeyByKeyId(keyId, db);
                encryptedContent = new String(arr, Charset.defaultCharset());

                decryptedContent = CryptoManager.Symmetric.AES.decryptText(encryptedContent, encryptionKey);
            }
        }

        db.close();

        // return content
        return decryptedContent;
    }

    private static String getEncryptionKeyByNoteId(long NoteId, SQLiteDatabase db)
    {
        String result = "";

        Cursor cursor = db.query(DatabaseContract.TableFiles.TABLE_NAME,
                new String [] {DatabaseContract.TableFiles.COLUMN_KEY_ID},
                "id = " + NoteId , null, "" ,"", "" );

        if(cursor.moveToFirst())
        {
            int keyDataIndex = cursor.getColumnIndex(DatabaseContract.TableFiles.COLUMN_KEY_ID);
            long keyId = cursor.getLong(keyDataIndex);

            result = getEncryptionKeyByKeyId(keyId, db);
        }

        return result;
    }


    /**
     * get the encrypted encryption key by its id
     * @param KeyId
     * @param db
     * @return EncryptionKey_Decrypted
     */
    private static String getEncryptionKeyByKeyId(long KeyId, SQLiteDatabase db)
    {
        String EncryptionKey_Decrypted = "";

        Cursor cursor = db.query(DatabaseContract.TableKeys.TABLE_NAME,
                new String [] {DatabaseContract.TableKeys.COLUMN_KEY_DATA},
                "id = " + KeyId, null, "" ,"", "" );

        if(cursor.moveToFirst())
        {
            int keyDataIndex = cursor.getColumnIndex(DatabaseContract.TableKeys.COLUMN_KEY_DATA);

            String EncryptionKey_Encrypted = cursor.getString(keyDataIndex);
            EncryptionKey_Decrypted = CryptoManager.Symmetric.AES.decryptText(EncryptionKey_Encrypted, PasswordHandler.getSessionPassword());
        }

        return EncryptionKey_Decrypted;
    }

    /**
     * Save all changes made on that note to DB.
     * @return Boolean if the data was saved
     */
    public static Boolean saveNoteToDB(String title, String content, String LastUpdated, long noteId)
    {
        DatabaseHandler handler = new DatabaseHandler(MyApplication.getContext());
        SQLiteDatabase db =  handler.getReadableDatabase();

        //get symmetric key by note id
        String EncryptionKey = getEncryptionKeyByNoteId(noteId, db);

        DBNote DbNote = new DBNote(title, LastUpdated, content, EncryptionKey);

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.TableFiles.COLUMN_TITLE, DbNote.getEncryptedTitle());
        values.put(DatabaseContract.TableFiles.COLUMN_LAST_UPDATED, DbNote.getEncryptedLastModified());
        values.put(DatabaseContract.TableFiles.COLUMN_CONTENT, DbNote.getEncryptedContent());

        int rowsAffected = db.update(DatabaseContract.TableFiles.TABLE_NAME, values, "id = " + noteId, null);

        return (rowsAffected == 0)? false : true;
    }

    /**
     * Fetch all note from DB.
     *
     * @return List<Note>
     */

    public static List<Note> getAllNotes()
    {
        DatabaseHandler handler = new DatabaseHandler(MyApplication.getContext());
        SQLiteDatabase db =  handler.getReadableDatabase();

        List<Note> noteList = new ArrayList<>();

        Cursor cursor = db.rawQuery(DatabaseContract.GET_ALL_DATA_QUERY, null);

        /*Cursor cursor = db.query(DatabaseContract.TableFiles.TABLE_NAME,
                new String [] {DatabaseContract.TableFiles.COLUMN_ID,
                        DatabaseContract.TableFiles.COLUMN_LAST_UPDATED,
                        DatabaseContract.TableFiles.COLUMN_TITLE,
                        DatabaseContract.TableFiles.COLUMN_KEY_ID},
                "" , null, "" ,"", "" );*/

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {

                    int idNoteIndex = cursor.getColumnIndex("n_id");
                    int lastModifiedIndex = cursor.getColumnIndex(DatabaseContract.TableFiles.COLUMN_LAST_UPDATED);
                    int titleIndex = cursor.getColumnIndex(DatabaseContract.TableFiles.COLUMN_TITLE);
                    int keyDataIndex = cursor.getColumnIndex(DatabaseContract.TableKeys.COLUMN_KEY_DATA);

                    String encrypted_KeyData = cursor.getString(keyDataIndex);
                    String decrypted_key = CryptoManager.Symmetric.AES.decryptText(encrypted_KeyData, PasswordHandler.getSessionPassword());

                    long id = cursor.getLong(idNoteIndex);
                    String lastModified_Encrypted = cursor.getString(lastModifiedIndex);
                    String title_Encrypted = cursor.getString(titleIndex);

                    String lastModified_Decrypted = CryptoManager.Symmetric.AES.decryptText(lastModified_Encrypted, decrypted_key);
                    String title_Decrypted = CryptoManager.Symmetric.AES.decryptText(title_Encrypted, decrypted_key);

                    Note temp = new Note(title_Decrypted, lastModified_Decrypted, id);

                    noteList.add(temp);

                } while (cursor.moveToNext());
            }
        }

        db.close();

        return noteList;
    }


    /**
     * get a list of all the users from the database
     * @return
     */
    /*public List<File> getAllUsers() {
        List<File> userList = new ArrayList<File>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DatabaseContract.TableUsers.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                File user = new File();
                user.setId(Integer.parseInt(cursor.getString(0)));
                user.setUserName(cursor.getString(1));
                user.setPassword(cursor.getString(2));
                // Adding contact to list
                userList.add(user);
            } while (cursor.moveToNext());
        }

        // return user list
        return userList;
    }*/

    /**
     * update a single user listing
     * @param contact
     * @return
     */
    /*public int updateUser(File contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.TableUsers.COLUMN_USERNAME, contact.getUserName());
        values.put(DatabaseContract.TableUsers.COLUMN_PASSWORD, contact.getPassword());

        // updating row
        return db.update(DatabaseContract.TableUsers.COLUMN_ID, values, DatabaseContract.TableUsers.COLUMN_ID + " = ?",
                new String[] { String.valueOf(contact.getId()) });
    }*/

    /**
     * delete a single user from the database
     * @param contact
     */
    /*public void deleteUser(File contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DatabaseContract.TableUsers.TABLE_NAME, DatabaseContract.TableUsers.COLUMN_ID + " = ?",
                new String[] { String.valueOf(contact.getId()) });
        db.close();
    }*/


    /**
     * get the number of users in the database
     * @return
     */
    /*public int getUsersCount() {
        String countQuery = "SELECT  * FROM " + DatabaseContract.TableUsers.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }*/
}
