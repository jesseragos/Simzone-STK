package com.jkiel.simzonestk;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONException;
import controller.UsableFunctions;
import domain.HelpListAdapter;
import res.UsableConstants;


public class Help extends Activity {

    private final String dir = UsableConstants.FILE_JSON_HELP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_std_info);

        //  Add back button to action bar
        UsableFunctions.associateBackButton(getActionBar());
        setHelpList();
    }

    private void setHelpList() {
        try {
            JSONArray helpList = new JSONArray(UsableFunctions.getJsonFileContent(dir, getApplicationContext()));;
            HelpListAdapter helpListAdapter = new HelpListAdapter(this, helpList);

            ListView listView = (ListView) findViewById(R.id.std_info_listview);
            listView.setAdapter(helpListAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.help, menu);
//        return true;
//    }
//

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
