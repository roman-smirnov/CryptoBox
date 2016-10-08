package roman.com.cryptobox.database;

import android.provider.BaseColumns;

import org.spongycastle.jcajce.provider.symmetric.ARC4;

/**
 * Created by roman on 9/17/16.
 */
public class DatabaseContract {

    public static final  int    DATABASE_VERSION   = 1;
    public static final  String DATABASE_NAME      = "crypto_database";
    private static final String TEXT_TYPE          = " TEXT";
    private static final String INT_TYPE          = " INTEGER";
    private static final String COMMA_SEP          = ",";

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private DatabaseContract() {}

    public static abstract class Tables{

        public static String TABLE_NAME;
        public static String COLUMN_ID;
        public static String CREATE_TABLE;
        public static String DELETE_TABLE;
    }

    /**
     * Table files.
     */
    public static class TableFiles extends Tables{

        public static final String TABLE_NAME = "files";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_LAST_UPDATED = "last_updated";
        public static final String COLUMN_CONTENT = "content";
        public static final String COLUMN_KEY_ID = "key_id";

        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                COLUMN_ID + INT_TYPE + " PRIMARY KEY" + COMMA_SEP +
                COLUMN_TITLE + TEXT_TYPE + COMMA_SEP +
                COLUMN_LAST_UPDATED + TEXT_TYPE + COMMA_SEP +
                COLUMN_CONTENT + " BLOB " + COMMA_SEP +
                COLUMN_KEY_ID + INT_TYPE + " )";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    /**
     * Table keys.
     */
    public static class TableKeys extends Tables{

        public static final String TABLE_NAME = "keys";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_KEY_DATA = "key_data";
        public static final String COLUMN_KEY_DATA_BACKUP = "key_data_backup";
        public static final String COLUMN_KEY_IS_USED = "is_used";

        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                COLUMN_ID + "INTEGER PRIMARY KEY," +
                COLUMN_KEY_DATA + TEXT_TYPE + COMMA_SEP +
                COLUMN_KEY_IS_USED + INT_TYPE + COMMA_SEP +
                COLUMN_KEY_DATA_BACKUP + TEXT_TYPE  + " )";

        //Deprecated
        public static String GetInsertKeyQuery(String key)
        {
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
