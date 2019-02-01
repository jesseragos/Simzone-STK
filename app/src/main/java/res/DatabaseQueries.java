package res;

/**
 * Created by Ragos Bros on 2/12/2017.
 */
public class DatabaseQueries {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "simzone_stk",
                               DB_FILENAME = String.format("%s.db", DB_NAME),

    //  NOTE: All tables refer an id column name as '_id' for CursorAdapter to function
    //  Transactions table and column names
                               TABLE_TRANSACTIONS = "transactions",
                               COL_TBLTRANS_ID = "_id",
                               COL_TBLTRANS_SENDER = "sender",
                               COL_TBLTRANS_MESSAGE = "message",
                               COL_TBLTRANS_CARRIERNAME = "carriername",
                               COL_TBLTRANS_PHONENUM = "phonenum",

    //  Transactions table and column names
                               TABLE_BOOKMARKS = "bookmarks",
                               COL_TBLBMARK_KEYWORDID = "_id",
                               COL_TBLBMARK_RNUM = "receivernum",
                               COL_TBLBMARK_DESC = "description",
                               COL_TBLBMARK_COST = "cost",
                               COL_TBLBMARK_COLOR = "color",
                               COL_TBLBMARK_CARRIERNAME = "carriername";

}
