package com.jkiel.simzonestk;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import controller.UsableFunctions;
import domain.AboutListAdapter;
import domain.StandardInfo;
import res.UsableConstants;


public class About extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_std_info);

        //  Add back button to action bar
        UsableFunctions.associateBackButton(getActionBar());
        setAbout();
    }

    private void setAbout() {
        ListView listView = (ListView) findViewById(R.id.std_info_listview);
        listView.setAdapter(new AboutListAdapter(getApplicationContext(), getAboutInfo()));
    }

    private StandardInfo[] getAboutInfo() {
        return new StandardInfo[]{
            new StandardInfo(UsableConstants.APP_VERSION_LABEL, BuildConfig.VERSION_NAME),
            new StandardInfo(UsableConstants.DEVELOPER_LABEL, UsableConstants.DEVELOPER_NAME_LABEL),
            new StandardInfo(UsableConstants.DISCLAIMER_LABEL, UsableConstants.DISCLAIMER_MSG)
        };
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

//    public String getAppVersionCode() {
//        try {
//            return getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
//        } catch (PackageManager.NameNotFoundException e) {
//            return UsableConstants.BAL_INFO_BLANK;
//        }
//    }

}
