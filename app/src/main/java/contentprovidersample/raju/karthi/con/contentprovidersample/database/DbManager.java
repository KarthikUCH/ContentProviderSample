package contentprovidersample.raju.karthi.con.contentprovidersample.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by karthikeyan on 27/1/17.
 */

public class DbManager {

    private static final String TAG = DbManager.class.getName();

    private static DbManager dbInstance = null;

    private Context mContext;

    private SQLiteDatabase mDbHelper;

    private DbManager() {

    }

    private DbManager(Context context) {
        this.mContext = context;
        mDbHelper = new DatabaseHelper(context).getWritableDatabase();
    }

    public SQLiteDatabase getDbHelper()
    {
        return mDbHelper;
    }

    public static DbManager getInstance(Context context) {

        if (dbInstance == null) {
            dbInstance = new DbManager(context);
        }

        return dbInstance;
    }


    private class DatabaseHelper extends SQLiteOpenHelper {

        private DatabaseHelper(Context context) {
            super(mContext, DbConstants.DB_NAME, null, DbConstants.DB_VERSION);
            mContext = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.i(TAG, DbConstants.DDL_CREATE_TBL_ITEMS);
            db.execSQL(DbConstants.DDL_CREATE_TBL_ITEMS);

            Log.i(TAG, DbConstants.DDL_CREATE_TBL_PHOTOS);
            db.execSQL(DbConstants.DDL_CREATE_TBL_PHOTOS);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL(DbConstants.DDL_DROP_TBL_ITEMS);
            db.execSQL(DbConstants.DDL_DROP_TBL_PHOTOS);

            // CREATE TABLE
            onCreate(db);
        }
    }

    /**
     *
     * @param item
     * @return the umber of affected rows
     */
    public void insertItems(String item) throws NullPointerException
    {
        ContentValues cv= new ContentValues();

        cv.put(DbConstants.COL_ITEM_NAME, item);
        cv.put(DbConstants.COL_ITEM_BORROWER, item+" borrower");

        mDbHelper.insert(DbConstants.TBL_ITEMS, null, cv);
    }


    public ArrayList<String> retriveItems()
    {
        Cursor c = mDbHelper.query(DbConstants.TBL_ITEMS, null, null, null, null, null, null);
        ArrayList<String> items = new ArrayList<>();
        while (c.moveToNext())
        {
            items.add(c.getString(c.getColumnIndex(DbConstants.COL_ITEM_NAME)));
        }

        c.close();
        return items;
    }

    @Override
    protected void finalize() throws Throwable {
        if(null != mDbHelper)
            mDbHelper.close();
        super.finalize();
    }
}
