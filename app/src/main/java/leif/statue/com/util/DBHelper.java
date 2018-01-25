package leif.statue.com.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import leif.statue.com.consts.DBConsts;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME_PREFIX = "BUTSUGU_DB";
    private static final int DB_VERSION = 2;

    protected static String DESPATCH_TABLE_CREATE_SQL =
            "CREATE TABLE IF NOT EXISTS " + DBConsts.TABLE_COUNT_HISTORY + " (" +
                    DBConsts.HISTORY_FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DBConsts.HISTORY_FIELD_COUNT + " INTEGER," +
                    DBConsts.HISTORY_FIELD_DATE + " TEXT);";

    public DBHelper(Context context) {
        super(context, DB_NAME_PREFIX, null, DB_VERSION);
        this.getWritableDatabase().close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL(DESPATCH_TABLE_CREATE_SQL);
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            onCreate(db);
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }
    }
}
