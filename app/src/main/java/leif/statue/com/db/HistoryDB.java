package leif.statue.com.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import leif.statue.com.consts.DBConsts;
import leif.statue.com.model.CountsItem;
import leif.statue.com.util.DBHelper;
import leif.statue.com.util.DateUtil;

public class HistoryDB extends DBHelper {
    private static final Object[] DB_LOCK 		= new Object[0];

    public HistoryDB(Context context) {
        super(context);
    }

    public ArrayList<CountsItem> fetchLastHistory() {
        ArrayList<CountsItem> ret = null;
        try {

            synchronized (DB_LOCK) {
                SQLiteDatabase db = getReadableDatabase();
                Cursor cursor = db.query(DBConsts.TABLE_COUNT_HISTORY, null, null, null, null, null, "date DESC", "10");
                ret = createHistoryBeans(cursor);
                db.close();
            }
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }

        return ret;
    }

    public ArrayList<CountsItem> fetchItemByDate(String date) {
        ArrayList<CountsItem> ret = null;
        try {

            synchronized (DB_LOCK) {
                SQLiteDatabase db = getReadableDatabase();

                String szWhere = DBConsts.HISTORY_FIELD_DATE + " = '" + date + "'";

                Cursor cursor = db.query(DBConsts.TABLE_COUNT_HISTORY, null, szWhere, null, null, null, "date DESC");
                ret = createHistoryBeans(cursor);

                if(ret.size() == 0) {
                    CountsItem item = new CountsItem(DateUtil.getCurDate(), 0);
                    addItem(item);

                    ret.add(item);
                }

                db.close();
            }
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }

        return ret;
    }

    public void removeAllDatas() {
        try {
            synchronized (DB_LOCK) {
                SQLiteDatabase db = getReadableDatabase();
                db.delete(DBConsts.TABLE_COUNT_HISTORY, null, null);
                db.close();
            }
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }
    }

    public long addItem(CountsItem bean) {
        long ret = -1;
        try {
            ContentValues value = new ContentValues();
            value.put(DBConsts.HISTORY_FIELD_COUNT, bean.getCounts());
            value.put(DBConsts.HISTORY_FIELD_DATE, bean.getDate());
            synchronized (DB_LOCK) {
                SQLiteDatabase db = getWritableDatabase();
                ret = db.insert(DBConsts.TABLE_COUNT_HISTORY, null, value);
                db.close();
            }
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }

        return ret;
    }

    public long updateItem(CountsItem bean) {
        long ret = -1;
        try {
            String szWhere = DBConsts.HISTORY_FIELD_DATE + " = '" + bean.getDate() + "'";
            ContentValues value = new ContentValues();
            value.put(DBConsts.HISTORY_FIELD_COUNT, bean.getCounts());

            synchronized (DB_LOCK) {
                SQLiteDatabase db = getWritableDatabase();
                db.update(DBConsts.TABLE_COUNT_HISTORY, value, szWhere, null);
                db.close();
            }
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }

        return ret;
    }

    private ArrayList<CountsItem> createHistoryBeans(Cursor c) {
        ArrayList<CountsItem> ret = null;
        try {
            ret = new ArrayList();

            final int COL_ID	            = c.getColumnIndexOrThrow(DBConsts.HISTORY_FIELD_ID),
                    COL_COUNT            = c.getColumnIndexOrThrow(DBConsts.HISTORY_FIELD_COUNT),
                    COL_DATE   	    = c.getColumnIndexOrThrow(DBConsts.HISTORY_FIELD_DATE);

            while (c.moveToNext()) {
                CountsItem bean = new CountsItem();
                bean.setCounts(c.getInt(COL_COUNT));
                bean.setDate(c.getString(COL_DATE));
                ret.add(bean);
            }

            c.close();
            getReadableDatabase().close();
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }

        return ret;
    }
}
