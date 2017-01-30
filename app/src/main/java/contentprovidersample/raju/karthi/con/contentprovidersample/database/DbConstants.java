package contentprovidersample.raju.karthi.con.contentprovidersample.database;

import android.provider.BaseColumns;

/**
 * A helper interface which defines constants for work with the DB.
 *
 * Created by karthikeyan on 27/1/17.
 *
 */

public class DbConstants {

    public static  final String DB_NAME = "lentitems.db";
    public static  final int DB_VERSION = 1;

    public static  final String TBL_ITEMS = "items";
    public static  final String TBL_PHOTOS = "photos";

    public static  final String COL_ITEM_ID = BaseColumns._ID;
    public static  final String COL_ITEM_NAME = "item_name";
    public static  final String COL_ITEM_BORROWER = "item_borrower";

    public static  final String COL_PHOTOS_ID = BaseColumns._ID;
    public static  final String COL_PHOTOS_DATA = "photo_data";
    public static  final String COL_PHOTOS_ITEMS_ID = "items_id";


    // BE AWARE: Normally you would store the LOOKUP_KEY
    // of a contact from the device. But this would
    // have needless complicated the sample. Thus I
    // omitted it.
    public static  final String DDL_CREATE_TBL_ITEMS =
            "CREATE TABLE items (" +
                    COL_ITEM_ID+"               INTEGER  PRIMARY KEY AUTOINCREMENT, \n" +
                    COL_ITEM_NAME+"             TEXT,\n" +
                    COL_ITEM_BORROWER+"         TEXT \n" +
                    ")";

    // BE AWARE: old sqlite versions didn't support referential
    // integrity; for this reasons I do _not_ use foreign keys!
    // I use triggers instead (see the sample trigger below).
    //
    // If you only target newer Android versions you could
    // of course use proper foreign keys instead.
    public static  final String DDL_CREATE_TBL_PHOTOS =
            "CREATE TABLE photos (" +
                    COL_PHOTOS_ID+"               INTEGER  PRIMARY KEY AUTOINCREMENT, \n" +
                    COL_PHOTOS_DATA+"             TEXT,\n" +
                    COL_PHOTOS_ITEMS_ID+"         INTEGER  NOT NULL  UNIQUE \n" +
                    ")";

    // The following trigger is here to show you how to
    // achieve referential integrity without foreign keys.
    public static  final String DDL_CREATE_TRIGGER_DEL_ITEMS =
            "CREATE TRIGGER delete_items DELETE ON items \n"
                    + "begin\n"
                    + "  delete from photos where items_id = old._id;\n"
                    + "end\n";

    public static  final String DDL_DROP_TBL_ITEMS =
            "DROP TABLE IF EXISTS "+TBL_ITEMS;

    public static  final String DDL_DROP_TBL_PHOTOS =
            "DROP TABLE IF EXISTS "+TBL_PHOTOS;

    public static  final String DDL_DROP_TRIGGER_DEL_ITEMS =
            "DROP TRIGGER IF EXISTS delete_items";

    public static  final String DML_WHERE_ID_CLAUSE = "_id = ?";

    public static  final String DEFAULT_TBL_ITEMS_SORT_ORDER = "name ASC";

    public static  final String LEFT_OUTER_JOIN_STATEMENT = TBL_ITEMS + " LEFT OUTER JOIN " + TBL_PHOTOS + " ON(items._id = photos.items_id)";


}
