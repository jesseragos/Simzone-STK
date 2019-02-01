package com.jkiel.simzonestk;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.RelativeLayout;

import domain.PromoCategoryAdapter;
import res.Carrier;
import res.UsableConstants;
import controller.UsableFunctions;


public class PromoCategories extends Activity {

    private Carrier deviceCarrier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo_categories);

        deviceCarrier = (Carrier) getIntent().getSerializableExtra(UsableConstants.DEVCAR_ID);

        setPreferences();
        setPromoCategories();
    }

    private void setPromoCategories() {
        PromoCategoryAdapter promoCatAdapter = new PromoCategoryAdapter(this, deviceCarrier);

        GridView gridView = (GridView) findViewById(R.id.promo_cat_gridview);
        gridView.setAdapter(promoCatAdapter);
    }

    private void setPreferences() {
        //  Add back button to action bar
        UsableFunctions.associateBackButton(getActionBar());

        ((RelativeLayout) findViewById(R.id.promo_cat_mainlayout)).setBackgroundColor(
                deviceCarrier.getBgColorValue());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.promo_categories, menu);
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
