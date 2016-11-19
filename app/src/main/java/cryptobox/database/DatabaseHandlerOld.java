package cryptobox.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import cryptobox.dataobjects.ContentValueWrapper;
import cryptobox.dataobjects.CursorWrapper;
import cryptobox.dataobjects.RawNote;
import cryptobox.utils.MyApplication;
import cryptobox.dataobjects.DBNote;
import cryptobox.dataobjects.KeyWrapper;
import cryptobox.dataobjects.Note;
import cryptobox.encryption.CryptoManager;
import cryptobox.utils.PasswordHandler;

/**
 * Created by roman on 9/17/16.
 */

public class DatabaseHandlerOld extends SQLiteOpenHelper {


    /**
     * main constructor
     *
     * @param context
     */
    public DatabaseHandlerOld(Context context) {
        super(context, DatabaseContract.DATABASE_NAME, null, DatabaseContract.DATABASE_VERSION);
    }


    /**
     * initialize database on first use of app
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaseContract.TableNotes.CREATE_TABLE);
        db.execSQL(DatabaseContract.TableKeys.CREATE_TABLE);
    }

    /**
     * on database upgrade - drop all tables and re-create the database
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL(DatabaseContract.TableNotes.DELETE_TABLE);
        db.execSQL(DatabaseContract.TableKeys.DELETE_TABLE);

        // Create tables again
        onCreate(db);
    }

    private static SQLiteDatabase getDatabase(Boolean isWritable) {
        DatabaseHandler dbHandler = new DatabaseHandler(MyApplication.getContext());

        if (isWritable)
            return dbHandler.getWritableDatabase();
        else
            return dbHandler.getReadableDatabase();
    }

    /**
     * add a note to the database
     *
     * @param title
     * @param lastModified
     * @param content
     */

    //can be deleted
    public Note addNote(String title, String lastModified, String content) {
        SQLiteDatabase db = this.getWritableDatabase();

        //generate new symmetric key
        KeyWrapper wrapper = generateNewKeyToDB(db);

        //encrypt title and content with the new generated key
        DBNote DbNote = new DBNote(title, lastModified, content, wrapper.decryptedKey);

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.TableNotes.COLUMN_TITLE, DbNote.getEncryptedTitle());
        values.put(DatabaseContract.TableNotes.COLUMN_LAST_UPDATED, DbNote.getEncryptedLastModified());
        values.put(DatabaseContract.TableNotes.COLUMN_CONTENT, DbNote.getEncryptedContent());
        values.put(DatabaseContract.TableNotes.COLUMN_KEY_ID, wrapper.keyId);

        // Inserting Row
        long rowId = db.insert(DatabaseContract.TableNotes.TABLE_NAME, null, values);

        //update table keys to make that id marked as used.
        updateNewKeyToUsedKey(wrapper.keyId, db);

        //close the connection
        db.close();

        //create Note and return it.
        Note note = new Note(title, lastModified, rowId, wrapper.keyId);
        return note;
    }

    public static long insertToDB(ContentValueWrapper cvw) {
        SQLiteDatabase db = getDatabase(true);

        long rowId = db.insert(
                cvw.tableName,
                null,
                cvw.getContentValue());

        db.close();

        return rowId;
    }

    public static Boolean updateDB(ContentValueWrapper cvw) {
        SQLiteDatabase db = getDatabase(true);

        int rowsAffected = db.update(
                cvw.tableName,
                cvw.getContentValue(),
                cvw.whereClause,
                null);

        db.close();

        return (rowsAffected == 1) ? true : false;
    }

    public static Boolean deleteFromDB(ContentValueWrapper cvw) {
        SQLiteDatabase db = getDatabase(true);

        int rowsAffected = db.delete(
                cvw.tableName,
                cvw.whereClause,
                null);

        db.close();

        return (rowsAffected == 1) ? true : false;
    }

    public static String readOneFromDB(CursorWrapper cw) {
        SQLiteDatabase db = getDatabase(false);

        String[] arr = cw.getColumns();
        String result = "";

        Cursor cursor = db.query(
                cw.tableName,
                arr,
                cw.whereClause,
                null, "", "", "");

        if (cursor.moveToFirst()) {
            int colIndex = cursor.getColumnIndex(arr[0]);
            result = cursor.getString(colIndex);
        }

        db.close();
        return result;
    }

    public static ArrayList<RawNote> readManyFromDB(CursorWrapper cw) {
        SQLiteDatabase db = getDatabase(false);

        ArrayList<RawNote> lstRawNotes = new ArrayList<>();
        Cursor cursor = db.rawQuery(cw.sqlQuery, null);

        String[] columnsArr = cw.getColumns();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {

                    RawNote rowData = new RawNote();

                    for (int i = 0; i < columnsArr.length; i++) {

                        int index = cursor.getColumnIndex(columnsArr[i]);
                        String data = cursor.getString(index);

                        rowData.addValue(columnsArr[i], data);
                    }
                    lstRawNotes.add(rowData);

                } while (cursor.moveToNext());
            }
        }
        db.close();
        return lstRawNotes;
    }

    public static String getBlobAsString(CursorWrapper cw) {
        SQLiteDatabase db = getDatabase(false);

        String[] columnsArr = cw.getColumns();
        String resultStr = "";

        Cursor cursor = db.query(
                cw.tableName,
                columnsArr,
                cw.whereClause,
                null, "", "", "");


        if (cursor.moveToFirst()) {
            int colIndex = cursor.getColumnIndex(columnsArr[0]);
            byte[] tmpByteArr = cursor.getBlob(colIndex);
            resultStr = new String(tmpByteArr, Charset.defaultCharset());
        }

        db.close();
        return resultStr;
    }


    /**
     * Fetch all note from DB.
     *
     * @return List<Note>
     */
    //can be deleted
    public static List<Note> getAllNotes() {
        DatabaseHandler handler = new DatabaseHandler(MyApplication.getContext());
        SQLiteDatabase db = handler.getReadableDatabase();

        List<Note> noteList = new ArrayList<>();

        Cursor cursor = db.rawQuery(DatabaseContract.GET_ALL_DATA_QUERY, null);

        /*Cursor cursor = db.query(DatabaseContract.TableNotes.TABLE_NAME,
                new String [] {DatabaseContract.TableNotes.COLUMN_ID,
                        DatabaseContract.TableNotes.COLUMN_LAST_UPDATED,
                        DatabaseContract.TableNotes.COLUMN_TITLE,
                        DatabaseContract.TableNotes.COLUMN_KEY_ID},
                "" , null, "" ,"", "" );*/

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {

                    int idNoteIndex = cursor.getColumnIndex("n_id");
                    int lastModifiedIndex = cursor.getColumnIndex(DatabaseContract.TableNotes.COLUMN_LAST_UPDATED);
                    int titleIndex = cursor.getColumnIndex(DatabaseContract.TableNotes.COLUMN_TITLE);
                    int noteKeyId = cursor.getColumnIndex(DatabaseContract.TableNotes.COLUMN_KEY_ID);
                    int keyDataIndex = cursor.getColumnIndex(DatabaseContract.TableKeys.COLUMN_KEY_DATA);

                    long keyId = cursor.getLong(noteKeyId);

                    String encrypted_KeyData = cursor.getString(keyDataIndex);
                    String decrypted_key = CryptoManager.Symmetric.AES.decryptText(encrypted_KeyData, PasswordHandler.getSessionPassword());

                    long id = cursor.getLong(idNoteIndex);
                    String lastModified_Encrypted = cursor.getString(lastModifiedIndex);
                    String title_Encrypted = cursor.getString(titleIndex);

                    String lastModified_Decrypted = CryptoManager.Symmetric.AES.decryptText(lastModified_Encrypted, decrypted_key);
                    String title_Decrypted = CryptoManager.Symmetric.AES.decryptText(title_Encrypted, decrypted_key);

                    Note temp = new Note(title_Decrypted, lastModified_Decrypted, id, keyId);

                    noteList.add(temp);

                } while (cursor.moveToNext());
            }
        }

        db.close();

        return noteList;
    }

    /**
     * get Note's content from the database by note id.
     *
     * @param id
     * @return
     */
    public static String getContentById(Long id) {

        DatabaseHandler handler = new DatabaseHandler(MyApplication.getContext());
        SQLiteDatabase db = handler.getReadableDatabase();

        Cursor cursor = db.query(DatabaseContract.TableNotes.TABLE_NAME,
                new String[]{DatabaseContract.TableNotes.COLUMN_CONTENT,
                        DatabaseContract.TableNotes.COLUMN_KEY_ID},
                "id = " + id, null, "", "", "");

        String encryptedContent;
        String decryptedContent = "";

        if (cursor.moveToFirst()) {
            int contentIndex = cursor.getColumnIndex(DatabaseContract.TableNotes.COLUMN_CONTENT);
            int KeyIdIndex = cursor.getColumnIndex(DatabaseContract.TableNotes.COLUMN_KEY_ID);

            byte[] arr = cursor.getBlob(contentIndex);
            Long keyId = cursor.getLong(KeyIdIndex);

            if (arr != null) {
                //Get Encrypted key from DB
                String encryptionKey = getEncryptionKeyByKeyId(keyId, db);
                encryptedContent = new String(arr, Charset.defaultCharset());

                //with the encryption key, decrypt the content from DB
                decryptedContent = CryptoManager.Symmetric.AES.decryptText(encryptedContent, encryptionKey);
            }
        }

        db.close();

        // return content
        return decryptedContent;
    }

    /**
     * get the encrypted encryption key by its id
     *
     * @param KeyId
     * @param db
     * @return EncryptionKey_Decrypted
     */
    //can be deleted
    private static String getEncryptionKeyByKeyId(long KeyId, SQLiteDatabase db) {
        String EncryptionKey_Decrypted = "";

        Cursor cursor = db.query(DatabaseContract.TableKeys.TABLE_NAME,
                new String[]{DatabaseContract.TableKeys.COLUMN_KEY_DATA},
                "id = " + KeyId, null, "", "", "");

        if (cursor.moveToFirst()) {
            int keyDataIndex = cursor.getColumnIndex(DatabaseContract.TableKeys.COLUMN_KEY_DATA);

            String EncryptionKey_Encrypted = cursor.getString(keyDataIndex);
            EncryptionKey_Decrypted = CryptoManager.Symmetric.AES.decryptText(EncryptionKey_Encrypted, PasswordHandler.getSessionPassword());
        }

        return EncryptionKey_Decrypted;
    }


    /**
     * delete note bi its id.
     *
     * @return
     */

    //can be deleted
    public Boolean deleteNote(Note n) {
        return deleteNoteById(n.getId(), n.getKeyId());
    }

    //can be deleted
    public Boolean deleteNoteById(long NoteId, long keyId) {
        DatabaseHandler handler = new DatabaseHandler(MyApplication.getContext());
        SQLiteDatabase db = handler.getWritableDatabase();


        //need to be two
        int noteRowsAffected = db.delete(DatabaseContract.TableNotes.TABLE_NAME, "id = " + NoteId, null);
        int keyRowsAffected = db.delete(DatabaseContract.TableKeys.TABLE_NAME, "id = " + keyId, null);


        Boolean result = (noteRowsAffected == 1 && keyRowsAffected == 1) ? true : false;

        return result;
    }


    //can be deleted
    private Boolean updateNewKeyToUsedKey(long id, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.TableKeys.COLUMN_KEY_IS_USED, "1");

        int rowsAffected = db.update(DatabaseContract.TableKeys.TABLE_NAME, values, "id = " + id, null);

        return (rowsAffected == 1) ? true : false;
    }


    /**
     * Generate random key and save it as a new row in KEYS table
     */
    //can be deleted
    private KeyWrapper generateNewKeyToDB(SQLiteDatabase db) {


        //generate new symmetric key
        String key = CryptoManager.Symmetric.AES.generateKey();

        //encrypt the key with user password.
        String encryptedKey = CryptoManager.Symmetric.AES.encryptText(key, PasswordHandler.getSessionPassword());

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.TableKeys.COLUMN_KEY_DATA, encryptedKey);
        values.put(DatabaseContract.TableKeys.COLUMN_KEY_DATA_BACKUP, encryptedKey);
        values.put(DatabaseContract.TableKeys.COLUMN_KEY_IS_USED, "0");

        // Inserting Row
        long rowId = db.insert(DatabaseContract.TableKeys.TABLE_NAME, null, values);

        //wrap all needed data for creation of a new file.
        KeyWrapper wrapper = new KeyWrapper();
        wrapper.keyId = rowId;
        wrapper.encryptedKey = encryptedKey;
        wrapper.decryptedKey = key;

        return wrapper;
    }

    //can be deleted
    private static String getEncryptionKeyByNoteId(long NoteId, SQLiteDatabase db) {
        String result = "";

        Cursor cursor = db.query(DatabaseContract.TableNotes.TABLE_NAME,
                new String[]{DatabaseContract.TableNotes.COLUMN_KEY_ID},
                "id = " + NoteId, null, "", "", "");

        if (cursor.moveToFirst()) {
            int keyDataIndex = cursor.getColumnIndex(DatabaseContract.TableNotes.COLUMN_KEY_ID);
            long keyId = cursor.getLong(keyDataIndex);

            result = getEncryptionKeyByKeyId(keyId, db);
        }

        return result;
    }

    /**
     * Save all changes made on that note to DB.
     *
     * @return Boolean if the data was saved
     */
    //can be deleted
    public static Boolean saveNoteToDB(String title, String content, String LastUpdated, long noteId) {
        DatabaseHandler handler = new DatabaseHandler(MyApplication.getContext());
        SQLiteDatabase db = handler.getReadableDatabase();

        //get symmetric key by note id
        String EncryptionKey = getEncryptionKeyByNoteId(noteId, db);

        DBNote DbNote = new DBNote(title, LastUpdated, content, EncryptionKey);

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.TableNotes.COLUMN_TITLE, DbNote.getEncryptedTitle());
        values.put(DatabaseContract.TableNotes.COLUMN_LAST_UPDATED, DbNote.getEncryptedLastModified());
        values.put(DatabaseContract.TableNotes.COLUMN_CONTENT, DbNote.getEncryptedContent());

        int rowsAffected = db.update(DatabaseContract.TableNotes.TABLE_NAME, values, "id = " + noteId, null);

        return (rowsAffected == 0) ? false : true;
    }
}
