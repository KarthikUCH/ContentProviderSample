package contentprovidersample.raju.karthi.con.contentprovidersample.database;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.compat.BuildConfig;
import android.util.Log;

/**
 * Created by karthikeyan on 27/1/17.
 */

public class DbContentProvider extends ContentProvider {


    private static final int ITEM_LIST = 1;
    private static final int ITEM_ID = 2;
    private static final int PHOTO_LIST = 3;
    private static final int PHOTO_ID = 4;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private SQLiteDatabase mDB;

    static {
        // TABLE ITEMS
        sUriMatcher.addURI(DbContract.AUTHORITY, DbConstants.TBL_ITEMS, ITEM_LIST);
        sUriMatcher.addURI(DbContract.AUTHORITY, DbConstants.TBL_ITEMS + "/#", ITEM_ID);

        // TABLE PHOTOS
        sUriMatcher.addURI(DbContract.AUTHORITY, DbConstants.TBL_PHOTOS, PHOTO_LIST);
        sUriMatcher.addURI(DbContract.AUTHORITY, DbConstants.TBL_PHOTOS + "/#", PHOTO_ID);


    }


    @Override
    public boolean onCreate() {
        mDB = DbManager.getInstance(getContext()).getDbHelper();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder builder = getQueryBuilder(uri);

        Cursor cursor = builder.query(mDB,projection, selection, selectionArgs, null, null, sortOrder);
        return cursor;
    }

    private SQLiteQueryBuilder getQueryBuilder(Uri uri) {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        switch (sUriMatcher.match(uri)) {
            case ITEM_LIST:
                builder.setTables(DbConstants.TBL_ITEMS);
                break;
            case ITEM_ID:
                builder.setTables(DbConstants.TBL_ITEMS);
                builder.appendWhere(DbConstants.COL_ITEM_ID + " = " + uri.getLastPathSegment());
                break;
            case PHOTO_LIST:
                builder.setTables(DbConstants.TBL_PHOTOS);
                break;
            case PHOTO_ID:
                builder.setTables(DbConstants.TBL_ITEMS);
                builder.appendWhere(DbConstants.COL_PHOTOS_ID + " = " + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        return builder;

    }

    @Nullable
    @Override
    public String getType(Uri uri) {

        switch (sUriMatcher.match(uri)) {
            case ITEM_LIST:
                return DbContract.Items.CONTENT_TYPE;
            case ITEM_ID:
                return DbContract.Items.CONTENT_ITEM_TYPE;
            case PHOTO_LIST:
                return DbContract.Photos.CONTENT_TYPE;
            case PHOTO_ID:
                return DbContract.Photos.CONTENT_PHOTO_TYPE;
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void logQuery(SQLiteQueryBuilder builder, String[] projection, String selection, String sortOrder) {
        if (BuildConfig.DEBUG) {
            Log.v("cpsample", "query: " + builder.buildQuery(projection, selection, null, null, sortOrder, null));
        }
    }

    @SuppressWarnings("deprecation")
    private void logQueryDeprecated(SQLiteQueryBuilder builder, String[] projection, String selection, String sortOrder) {
        if (BuildConfig.DEBUG) {
            Log.v("cpsample", "query: " + builder.buildQuery(projection, selection, null, null, null, sortOrder, null));
        }
    }
}
