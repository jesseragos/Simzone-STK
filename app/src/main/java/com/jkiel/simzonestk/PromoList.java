package com.jkiel.simzonestk;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import domain.PromoListAdapter;
import res.Carrier;
import res.DatabaseQueries;
import res.UsableConstants;
import controller.UsableFunctions;
import res.UsableJsonConstants;
import techserv.DatabaseOpenHelper;

public class PromoList extends Activity {

    public static final String TAG = PromoList.class.getSimpleName();

    private JSONObject promoOffers;
    private String selectedPromoCat;
    private Carrier deviceCarrier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo_list);

        deviceCarrier = (Carrier) getIntent().getSerializableExtra(UsableConstants.DEVCAR_ID);

        initFields();
        setPreferences();
        setPromoList();
    }

    private void initFields() {
        try {
            JSONObject carrierServPrefs = new JSONObject(UsableFunctions.getJsonFileContent(this.deviceCarrier, getApplicationContext()));
            promoOffers = carrierServPrefs.getJSONObject(UsableJsonConstants.PROMOOFFERS);
        } catch (JSONException e) {
            promoOffers = new JSONObject();
            e.printStackTrace();
        }

        selectedPromoCat = getIntent().getStringExtra(UsableConstants.SELECTEDPROMOCAT_ID);
    }

    private void setPreferences() {
        //  Add back button to action bar
        UsableFunctions.associateBackButton(getActionBar());

        findViewById(R.id.promo_list_mainlayout).setBackgroundColor(
                deviceCarrier.getBgColorValue());

        ((TextView) findViewById(R.id.promo_cat_lbl_textview)).setText(String.format(UsableConstants.PROMO_LIST_LABEL, selectedPromoCat));
    }

    private void setPromoList() {
        try {
            JSONArray promoList = promoOffers.getJSONArray(selectedPromoCat);
            PromoListAdapter promoListAdapter = new PromoListAdapter(this, getFragmentManager(), promoList);

            ListView listView = (ListView) findViewById(R.id.promo_listview);
            listView.setAdapter(promoListAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.promo_list, menu);
        return false;
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
