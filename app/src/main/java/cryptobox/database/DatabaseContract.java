package cryptobox.database;

/**
 * Created by roman on 9/17/16.
 */
public class DatabaseContract {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "crypto_database";
    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INTEGER";
    private static final String BLOB_TYPE = " BLOB ";
    private static final String NOT_NULL = " NOT NULL ";
    private static final String COMMA_SEP = ",";


    public static final String GET_ALL_DATA_QUERY =
            " select notes.id as n_id, notes.title, notes.last_updated, notes.key_id, " +
                    " keys.id as k_id, keys.key_data from " +
                    TableNotes.TABLE_NAME + " inner join " + TableKeys.TABLE_NAME +
                    " on notes.key_id = keys.id ";

    public static final String GET_ALL_KEYS_WITH_ID =
            " select id, key_data, key_data_backup from " + TableKeys.TABLE_NAME;

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private DatabaseContract() {
    }

    public static abstract class Tables {

        public static String TABLE_NAME;
        public static String COLUMN_ID;
        public static String CREATE_TABLE;
        public static String DELETE_TABLE;
    }

    /**
     * Table files.
     */
    public static class TableNotes extends Tables {

        public static final String TABLE_NAME = "notes";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_LAST_UPDATED = "last_updated";
        public static final String COLUMN_CONTENT = "content";
        public static final String COLUMN_KEY_ID = "key_id";

        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME + " (" +
                COLUMN_ID + INT_TYPE + " PRIMARY KEY" + COMMA_SEP +
                COLUMN_TITLE + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                COLUMN_LAST_UPDATED + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                COLUMN_CONTENT + BLOB_TYPE + NOT_NULL + COMMA_SEP +
                COLUMN_KEY_ID + INT_TYPE + " )";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

        public static final String READ_TABLE = "SELECT * FROM " + TABLE_NAME;
    }

    /**
     * Table keys.
     */
    public static class TableKeys extends Tables {

        public static final String TABLE_NAME = "keys";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_KEY_DATA = "key_data";
        public static final String COLUMN_KEY_DATA_BACKUP = "key_data_backup";
        public static final String COLUMN_KEY_IS_USED = "is_used";

        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME + " (" +
                COLUMN_ID + INT_TYPE + " PRIMARY KEY " + COMMA_SEP +
                COLUMN_KEY_DATA + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                COLUMN_KEY_IS_USED + INT_TYPE + NOT_NULL + COMMA_SEP +
                COLUMN_KEY_DATA_BACKUP + TEXT_TYPE + ")";
        // " FOREIGN KEY(id) REFERENCES notes(id) ON DELETE CASCADE  );";

        //Deprecated
        public static String GetInsertKeyQuery(String key) {
            String query = "INSERT INTO KEYS " +
                    "( " + COLUMN_KEY_DATA + COMMA_SEP + COLUMN_KEY_DATA_BACKUP + COMMA_SEP + COLUMN_KEY_IS_USED + ") " +
                    "VALUES " + "( " +
                    key + COMMA_SEP + key + COMMA_SEP + "0" +
                    " ); ";

            return query;
        }

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

}
