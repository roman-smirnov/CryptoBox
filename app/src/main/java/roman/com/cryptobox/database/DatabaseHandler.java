package roman.com.cryptobox.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.nio.charset.Charset;
import java.util.ArrayList;

import roman.com.cryptobox.dataobjects.ContentValueWrapper;
import roman.com.cryptobox.dataobjects.CursorWrapper;
import roman.com.cryptobox.dataobjects.RawNote;
import roman.com.cryptobox.utils.MyApplication;

/**
 * Created by roman on 9/17/16.
 */

public class DatabaseHandler extends SQLiteOpenHelper {


    /**
     * main constructor
     *
     * @param context
     */
    public DatabaseHandler(Context context) {
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

        String [] columnsArr = cw.getColumns();

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

                }while (cursor.moveToNext());
            }
        }
        db.close();
        return lstRawNotes;
    }

    public static String getBlobAsString(CursorWrapper cw){
        SQLiteDatabase db = getDatabase(false);

        String [] columnsArr = cw.getColumns();
        String resultStr = "";

        Cursor cursor = db.query(
                cw.tableName,
                columnsArr,
                cw.whereClause,
                null, "", "", "");


        if (cursor.moveToFirst()) {
            int colIndex = cursor.getColumnIndex(columnsArr[0]);
            byte [] tmpByteArr = cursor.getBlob(colIndex);
            resultStr = new String(tmpByteArr, Charset.defaultCharset());
        }

        db.close();
        return resultStr;
    }
}
