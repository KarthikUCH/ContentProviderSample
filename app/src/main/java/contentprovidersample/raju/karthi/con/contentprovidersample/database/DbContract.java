package contentprovidersample.raju.karthi.con.contentprovidersample.database;


import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * The contract between clients and this 'sample app' content provider.
 * <p>
 * Created by karthikeyan on 27/1/17.
 */

public final class DbContract {

    /**
     * The authority of this 'sample app' provider.
     */
    public static final String AUTHORITY = "contentprovidersample.raju.karthi.con.contentprovidersample.database.DbContentProvider";
    /**
     * The content URI for the top-level 'sample app' authority.
     */
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);




    /**
     * Constants for Item table on the provider
     */
    public static final class Items implements BaseColumns {
        /**
         * The content URI for this table.
         */
        public static final Uri CONTENT_URI =  Uri.withAppendedPath(DbContract.CONTENT_URI, DbConstants.TBL_ITEMS);


        public static final String COL_ITEM_NAME = "item_name";

        public static final String COL_ITEM_BORROWER = "item_borrower";

        /**
         * The mime type of a directory of items.
         */
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd." + AUTHORITY + ".item";

        /**
         * The mime type of a single items.
         */
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd." + AUTHORITY + ".item";

        /**
         * A projection of all columns in the items table.
         */
        public static final String[] PROJECTION_ALL = {_ID, COL_ITEM_NAME, COL_ITEM_BORROWER};
        /**
         * The default sort order for queries containing NAME fields.
         */
        public static final String SORT_ORDER_DEFAULT = COL_ITEM_NAME + " ASC";
    }




    /**
     * Constants for Item Photos on the provider
     */
    public static final class Photos implements BaseColumns {

        /**
         * The content URI for this table.
         */
        public static final Uri CONTENT_URI =  Uri.withAppendedPath(DbContract.CONTENT_URI, DbConstants.TBL_ITEMS);


        public static  final String COL_PHOTOS_DATA = "photo_data";

        public static  final String COL_PHOTOS_ITEMS_ID = "items_id";

        /**
         * The mime type of a directory of items.
         */
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd." + AUTHORITY + ".photo";

        /**
         * The mime type of a single items.
         */
        public static final String CONTENT_PHOTO_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd." + AUTHORITY + ".photo";

        /**
         * A projection of all columns in the items table.
         */
        public static final String[] PROJECTION_ALL = {_ID, COL_PHOTOS_DATA, COL_PHOTOS_ITEMS_ID};
        /**
         * The default sort order for queries containing NAME fields.
         */
        public static final String SORT_ORDER_DEFAULT = COL_PHOTOS_ITEMS_ID + " ASC";
    }


}
