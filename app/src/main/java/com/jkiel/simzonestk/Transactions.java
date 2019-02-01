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
import domain.TransactionListAdapter;
import res.Carrier;
import res.DatabaseQueries;
import res.UsableConstants;
import techserv.DatabaseOpenHelper;


public class Transactions extends Activity {

    public static final String TAG = Transactions.class.getSimpleName();

    private static final String SELECT_TRANSACTIONS_ID = "select * from %s order by %s desc";

    private Carrier deviceCarrier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        deviceCarrier = (Carrier) getIntent().getSerializableExtra(UsableConstants.DEVCAR_ID);

        setPreferences();
        setTransactionsList();
    }

    private void setPreferences() {
        //  Add back button to action bar
        UsableFunctions.associateBackButton(getActionBar());

        findViewById(R.id.transactions_mainlayout).setBackgroundColor(deviceCarrier.getBgColorValue());
    }

    public static Cursor getTransactionsCursor(SQLiteDatabase db) {
        return db.rawQuery(String.format(SELECT_TRANSACTIONS_ID,
                DatabaseQueries.TABLE_TRANSACTIONS, DatabaseQueries.COL_TBLTRANS_ID), null);
    }

    private void setTransactionsList() {
        // Load the most recent record from the SQLite database.
        DatabaseOpenHelper dbHelper = new DatabaseOpenHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        TransactionListAdapter transactionListAdapter = new TransactionListAdapter(this, getFragmentManager(),
                getTransactionsCursor(db), db);

        TextView transView = (TextView) findViewById(R.id.transactions_empty_textview);
        ListView listView = (ListView) findViewById(R.id.transactions_listview);
        listView.setEmptyView(transView);
        listView.setAdapter(transactionListAdapter);
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
