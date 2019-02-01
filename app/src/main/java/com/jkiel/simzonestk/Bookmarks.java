package com.jkiel.simzonestk;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import controller.UsableFunctions;
import domain.BookmarkListAdapter;
import res.Carrier;
import res.DatabaseQueries;
import res.UsableConstants;
import techserv.DatabaseOpenHelper;


public class Bookmarks extends Activity {

    public static final String TAG = Bookmarks.class.getSimpleName();

    private static final String SELECT_BOOKMARKS_ID = "select * from %s order by %s";

    private Carrier deviceCarrier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);

        deviceCarrier = (Carrier) getIntent().getSerializableExtra(UsableConstants.DEVCAR_ID);

        setPreferences();
        setBookmarksList();
    }

    private void setPreferences() {
        //  Add back button to action bar
        UsableFunctions.associateBackButton(getActionBar());

        findViewById(R.id.bookmarks_mainlayout).setBackgroundColor(deviceCarrier.getBgColorValue());
    }

    public static Cursor getBookmarksCursor(SQLiteDatabase db) {
        return db.rawQuery(String.format(SELECT_BOOKMARKS_ID,
                DatabaseQueries.TABLE_BOOKMARKS, DatabaseQueries.COL_TBLBMARK_KEYWORDID), null);
    }

    private void setBookmarksList() {
        // Load the most recent record from the SQLite database.
        DatabaseOpenHelper dbHelper = new DatabaseOpenHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        BookmarkListAdapter bookmarkListAdapter = new BookmarkListAdapter(this, getFragmentManager(),
                getBookmarksCursor(db), db, deviceCarrier.getBgColorValue());

        TextView bmarkView = (TextView) findViewById(R.id.bookmarks_empty_textview);
        ListView listView = (ListView) findViewById(R.id.bookmarks_listview);
        listView.setEmptyView(bmarkView);
        listView.setAdapter(bookmarkListAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
