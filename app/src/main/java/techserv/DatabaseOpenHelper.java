package techserv;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import res.DatabaseQueries;

/**
 * Created by Ragos Bros on 2/12/2017.
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper {

    private static final String CREATE_TBL_TRANSACTIONS = "create table %s (%s integer primary key autoincrement, " +
                                                          "%s text not null, " +
                                                          "%s text not null, " +
                                                          "%s text not null, " +
                                                          "%s text not null)",
                                CREATE_TBL_BOOKMARKS = "create table %s (%s text primary key," +
                                        "%s text not null, " +
                                        "%s text not null, " +
                                        "%s double not null, " +
                                        "%s text not null," +
                                        "%s integer not null)";

    public DatabaseOpenHelper(Context context) {
        super(context, DatabaseQueries.DB_FILENAME, null, DatabaseQueries.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //  Create Transactions table
        db.execSQL(String.format(CREATE_TBL_TRANSACTIONS, DatabaseQueries.TABLE_TRANSACTIONS,
                                                          DatabaseQueries.COL_TBLTRANS_ID,
                                                          DatabaseQueries.COL_TBLTRANS_SENDER,
                                                          DatabaseQueries.COL_TBLTRANS_MESSAGE,
                                                          DatabaseQueries.COL_TBLTRANS_CARRIERNAME,
                                                          DatabaseQueries.COL_TBLTRANS_PHONENUM));

        //  Create Bookmarks table
        db.execSQL(String.format(CREATE_TBL_BOOKMARKS, DatabaseQueries.TABLE_BOOKMARKS,
                                                       DatabaseQueries.COL_TBLBMARK_KEYWORDID,
                                                       DatabaseQueries.COL_TBLBMARK_RNUM,
                                                       DatabaseQueries.COL_TBLBMARK_DESC,
                                                       DatabaseQueries.COL_TBLBMARK_COST,
                                                       DatabaseQueries.COL_TBLBMARK_CARRIERNAME,
                                                       DatabaseQueries.COL_TBLBMARK_COLOR));
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i1, int i2) {}

}
