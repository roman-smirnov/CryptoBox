package cryptobox.database;

import com.apkfuns.logutils.LogUtils;

import java.util.ArrayList;

import cryptobox.contracts.DataManagerContract;
import cryptobox.dataobjects.ContentValueCryptWrapper;
import cryptobox.dataobjects.ContentValueWrapper;
import cryptobox.dataobjects.CursorWrapper;
import cryptobox.dataobjects.KeyWrapper;
import cryptobox.dataobjects.Note;
import cryptobox.dataobjects.RawNote;
import cryptobox.encryption.CryptoManager;
import cryptobox.utils.PasswordHandler;

/**
 * Created by avishai on 05/11/2016.
 */

public class DataManager implements DataManagerContract {

    private static DataManager dataManagerInstance = new DataManager();

    public static DataManager getInstance() {
        //TODO
        //Make it thread safe
        return dataManagerInstance;
    }

    private DataManager() {
    }

    @Override
    public Note addNote(String title, String lastModified, String content) {

        //generate new symmetric key.
        KeyWrapper wrapper = generateNewKeyToDB();

        //Load information and encrypt it.
        ContentValueCryptWrapper cvcw = new ContentValueCryptWrapper(wrapper.keyToEncryptWith);
        cvcw.tableName = DatabaseContract.TableNotes.TABLE_NAME;
        cvcw.addEncryptedStringValue(DatabaseContract.TableNotes.COLUMN_TITLE, title);
        cvcw.addEncryptedStringValue(DatabaseContract.TableNotes.COLUMN_LAST_UPDATED, lastModified);
        cvcw.addEncryptedStringValue(DatabaseContract.TableNotes.COLUMN_CONTENT, content);
        cvcw.addLongValue(DatabaseContract.TableNotes.COLUMN_KEY_ID, wrapper.keyId);

        // sending data to data access point
        long rowId = DatabaseHandler.insertToDB(cvcw);

        //update table keys to make that id marked as used.
        ContentValueWrapper cvw = new ContentValueWrapper();
        cvw.tableName = DatabaseContract.TableKeys.TABLE_NAME;
        cvw.whereClause = "id = " + wrapper.keyId;
        cvw.addStringValue(DatabaseContract.TableKeys.COLUMN_KEY_IS_USED, "1");

        // sending the data to be updated to data access point
        DatabaseHandler.updateDB(cvw);

        Note note = new Note(title, lastModified, rowId, wrapper.keyId);
        return note;
    }

    @Override
    public Note getNoteById(int id) {

        Note res = null;
        ArrayList<Note> noteList = getAllNotes(" where n_id = " + id);

        //Could not found any note with that id
        if (noteList.size() == 0)
            return res;

        if (noteList.size() == 1) {
            res = noteList.get(0);
        }

        return res;
    }

    /**
     * Fetch all note from DB.
     *
     * @return List<Note>
     */
    @Override
    public ArrayList<Note> getAllNotes() {
        return getAllNotes("");
    }

    public ArrayList<Note> getAllNotes(String filter) {

        CursorWrapper cw = new CursorWrapper();
        cw.sqlQuery = DatabaseContract.GET_ALL_DATA_QUERY + filter;
        cw.addValue("n_id");
        cw.addValue(DatabaseContract.TableNotes.COLUMN_LAST_UPDATED);
        cw.addValue(DatabaseContract.TableNotes.COLUMN_TITLE);
        cw.addValue(DatabaseContract.TableNotes.COLUMN_KEY_ID);
        cw.addValue(DatabaseContract.TableKeys.COLUMN_KEY_DATA);

        ArrayList<RawNote> rawNotesList = DatabaseHandler.readManyFromDB(cw);
        ArrayList<Note> notesList = new ArrayList<>();

        for (int i = 0; i < rawNotesList.size(); i++) {

            RawNote rawNote = rawNotesList.get(i);

            //get all data, parts of it are encrypted
            String title = rawNote.getValue(DatabaseContract.TableNotes.COLUMN_TITLE);
            String lastUpdated = rawNote.getValue(DatabaseContract.TableNotes.COLUMN_LAST_UPDATED);
            String idAsString = rawNote.getValue("n_id");
            String keyIdAsString = rawNote.getValue(DatabaseContract.TableNotes.COLUMN_KEY_ID);

            String encryptedSymmetricKey = rawNote.getValue(DatabaseContract.TableKeys.COLUMN_KEY_DATA);

            //decrypt symmetric key with user's password
            String SymmetricKey = CryptoManager.Symmetric.AES.decryptText(encryptedSymmetricKey, PasswordHandler.getSessionPassword());

            //decrypt data with the symmetric key
            String decryptedTitle = CryptoManager.Symmetric.AES.decryptText(title, SymmetricKey);
            String decryptedLastUpdated = CryptoManager.Symmetric.AES.decryptText(lastUpdated, SymmetricKey);
            Long id = Long.parseLong(idAsString);
            Long keyId = Long.parseLong(keyIdAsString);

            Note tmpNote = new Note(
                    decryptedTitle,
                    decryptedLastUpdated,
                    id,
                    keyId);

            notesList.add(tmpNote);
        }

        return notesList;
    }

    @Override
    public String getContent(Note n) {

        Long keyId = n.getKeyId();
        Long noteId = n.getId();

        //get encrypted symmetric key
        CursorWrapper cw1 = new CursorWrapper();
        cw1.tableName = DatabaseContract.TableKeys.TABLE_NAME;
        cw1.whereClause = "id = " + keyId.toString();
        cw1.addValue(DatabaseContract.TableKeys.COLUMN_KEY_DATA);

        String encryptedSymmetricKey = DatabaseHandler.readOneFromDB(cw1);

        //decrypt the symmetric key, so we can decrypt the note content
        String symmetricKey = CryptoManager.Symmetric.AES.decryptText(encryptedSymmetricKey, PasswordHandler.getSessionPassword());

        //Get the note content
        CursorWrapper cw2 = new CursorWrapper();
        cw2.tableName = DatabaseContract.TableNotes.TABLE_NAME;
        cw2.whereClause = "id = " + noteId.toString();
        cw2.addValue(DatabaseContract.TableNotes.COLUMN_CONTENT);

        //Get the encrypted content and decrypt it.
        String encryptedContent = DatabaseHandler.getBlobAsString(cw2);
        String decryptedContent = CryptoManager.Symmetric.AES.decryptText(encryptedContent, symmetricKey);

        return decryptedContent;
    }

    @Override
    public Boolean UpdateNote(Note n) {

        //get the symmetric key by key id
        String EncryptionKey = getEncryptionKeyByKeyId(n.getKeyId());

        //Wrap data for Content value
        ContentValueCryptWrapper cvcw = new ContentValueCryptWrapper(EncryptionKey);
        cvcw.tableName = DatabaseContract.TableNotes.TABLE_NAME;
        cvcw.whereClause = "id = " + n.getId();
        cvcw.addEncryptedStringValue(DatabaseContract.TableNotes.COLUMN_TITLE, n.getTitle());
        cvcw.addEncryptedStringValue(DatabaseContract.TableNotes.COLUMN_LAST_UPDATED, n.getLastModified());
        cvcw.addEncryptedStringValue(DatabaseContract.TableNotes.COLUMN_CONTENT, n.getContent());

        // sending the data to be updated to data access point
        Boolean res = DatabaseHandler.updateDB(cvcw);

        return res;
    }

    @Override
    public Boolean deleteNote(Note n) {
        return deleteNoteById(n.getId(), n.getKeyId());
    }

    @Override
    public Boolean deleteNoteById(long NoteId, long keyId) {

        Boolean Answer;

        ContentValueWrapper cvwNotes = new ContentValueWrapper();
        cvwNotes.tableName = DatabaseContract.TableNotes.TABLE_NAME;
        cvwNotes.whereClause = "id = " + NoteId;

        ContentValueWrapper cvwKeys = new ContentValueWrapper();
        cvwKeys.tableName = DatabaseContract.TableKeys.TABLE_NAME;
        cvwKeys.whereClause = "id = " + keyId;

        //TODO
        //Change the way the answer is calculated, to check if one of them fails
        Answer = DatabaseHandler.deleteFromDB(cvwNotes) && DatabaseHandler.deleteFromDB(cvwKeys);

        return Answer;
    }

    private KeyWrapper generateNewKeyToDB() {
        //generate new symmetric key
        String key = CryptoManager.Symmetric.AES.generateKey();

        //encrypt the key with user password.
        String encryptedKey = CryptoManager.Symmetric.AES.encryptText(key, PasswordHandler.getSessionPassword());

        ContentValueWrapper cvw = new ContentValueWrapper();

        cvw.tableName = DatabaseContract.TableKeys.TABLE_NAME;
        cvw.addStringValue(DatabaseContract.TableKeys.COLUMN_KEY_DATA, encryptedKey);
        cvw.addStringValue(DatabaseContract.TableKeys.COLUMN_KEY_DATA_BACKUP, encryptedKey);
        cvw.addStringValue(DatabaseContract.TableKeys.COLUMN_KEY_IS_USED, "0");

        // sending data to data access point
        long rowId = DatabaseHandler.insertToDB(cvw);

        //wrap all needed data for creation of a new file.
        KeyWrapper wrapper = new KeyWrapper();
        wrapper.keyId = rowId;
        wrapper.encryptedKeyToEncrypt = encryptedKey;
        wrapper.keyToEncryptWith = key;

        return wrapper;
    }


    /**
     * get the encrypted encryption key by its id
     *
     * @param KeyId
     * @return EncryptionKey_Decrypted
     */
    private String getEncryptionKeyByKeyId(long KeyId) {

        CursorWrapper wrapper = new CursorWrapper();

        wrapper.tableName = DatabaseContract.TableKeys.TABLE_NAME;
        wrapper.whereClause = "id = " + KeyId;
        wrapper.addValue(DatabaseContract.TableKeys.COLUMN_KEY_DATA);

        //get the key - it is encrypted
        String EncryptedKey = DatabaseHandler.readOneFromDB(wrapper);

        //decrypt the key
        String EncryptionKey = CryptoManager.Symmetric.AES.decryptText(
                EncryptedKey,
                PasswordHandler.getSessionPassword());

        return EncryptionKey;
    }
}
