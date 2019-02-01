package com.jkiel.simzonestk;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;

import controller.DataStorage;
import controller.MainMenuFunctions;
import fragments.CarrierUrlPromptDialogFragment;
import res.DatabaseQueries;
import res.UsableDebugMsgs;
import techserv.SmsSender;
import domain.MenuButtonAdapter;
import fragments.InvalidCarrierDialogFragment;
import res.Carrier;
import res.UsableConstants;
import controller.UsableFunctions;


public class MainMenu extends Activity {

    public static final String TAG = MainMenu.class.getSimpleName();

    final String[] menuBtnNames = UsableConstants.APP_FUNCTION_NAMES;
    public Carrier deviceCarrier;
    public static String carrierName;
    public static TextView currentBalView;

    public static Context appContext;
    public FragmentManager appFragmentManager;
    public SharedPreferences appPreferences;
    public static SmsSender smsSender;
    public MainMenuFunctions mainMenuFunctions;
    public static DataStorage dataStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!verifyCarrier()) return;
        setContentView(R.layout.activity_main_menu);

//        _testDatabase();
        initComponents();
        setComponentStyles();
        setMainMenuBtns();
    }

//    private void _testDatabase() {
//        deleteDatabase(DatabaseQueries.DB_FILENAME);
//        MainMenuFunctions.saveTransactionToDB(getApplicationContext(), "Smart", "The quick brown fox jumped the cable van",
//                "Smart", "092173821973");
//        MainMenuFunctions.saveTransactionToDB(getApplicationContext(), "Globe", "Lorem ipsum damyeon kureom sarang opseo seul.",
//                "Globe", "092173821973");
//    }

    private void initComponents() {
        setCarrierPreferences();
        populateGlobalVars();
        initBalanceFromStorage();
        getOverflowMenu();
    }

    private void populateGlobalVars() {
        appContext = getApplicationContext();
        appFragmentManager = getFragmentManager();
        appPreferences = getPreferences(MODE_PRIVATE);
        smsSender = new SmsSender(appContext);
        mainMenuFunctions = new MainMenuFunctions(appContext, appFragmentManager, deviceCarrier);
        dataStorage = new DataStorage(appPreferences);
        carrierName = deviceCarrier.getName();

        currentBalView = ((TextView) findViewById(R.id.current_bal_val));

        //  Save carrier name
        dataStorage.storeCarrierName(deviceCarrier.getName());
    }

    private boolean verifyCarrier() {
        //  Get carrier name
        TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String deviceCarrierName = manager.getNetworkOperatorName();
        boolean isCarrierMatch = false;

        //  Verify device carrier with system carriers
        for(Carrier carrier:Carrier.values())
            if(deviceCarrierName.toLowerCase().contains(carrier.getName().toLowerCase())) {
                deviceCarrier = carrier;
                isCarrierMatch = true;
                break;
            }

        //  Verify device carrier with system provided carriers
        if(!isCarrierMatch) {
            //  create dialog saying device carrier not supported then exit activity
            Log.d(TAG + " - verifyCarrier()", UsableDebugMsgs.CARRIER_UNMATCH);

            new InvalidCarrierDialogFragment().show(getFragmentManager(), InvalidCarrierDialogFragment.TAG);
        }

        return isCarrierMatch;
    }

    private void setCarrierPreferences() {
        //  Set carrier logo banner
        ImageView banner = (ImageView) findViewById(R.id.carrier_banner);
        banner.setBackgroundResource(deviceCarrier.getLogoResource());
        banner.setClickable(true);
//        banner.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                goToCarrierSite(view);
//            }
//        });

        //  Set carrier background color
        findViewById(R.id.main_layout).setBackgroundColor(deviceCarrier.getBgColorValue());

    }

    private void setComponentStyles() {
        Typeface currentBalCompsFont = UsableFunctions.getLabelTypeFace(this);

        ((TextView) findViewById(R.id.current_bal_lbl)).setTypeface(currentBalCompsFont);
        ((TextView) findViewById(R.id.current_bal_val)).setTypeface(currentBalCompsFont);
    }

    private void setMainMenuBtns() {
        MenuButtonAdapter buttonAdapter = new MenuButtonAdapter(this, mainMenuFunctions, menuBtnNames, deviceCarrier.getButtonXmlResource());

        GridView gridView = (GridView) findViewById(R.id.menu_btns_layout);
        gridView.setAdapter(buttonAdapter);
    }


//      Usable methods ---->
    public void updateBalance(View view) {
        mainMenuFunctions.updateBalance(view);
    }

    public void goToCarrierSite(View view) {
        Log.d("Sample test logo", "Logo banner clicked");
//        Toast.makeText(getApplicationContext(), "Sample test logo", Toast.LENGTH_LONG);
        new CarrierUrlPromptDialogFragment(getApplicationContext(), deviceCarrier.getWebSite())
                .show(getFragmentManager(), CarrierUrlPromptDialogFragment.TAG);
    }

    public static void updateCurrentBalStat(String balValue) {
        currentBalView.setText(balValue);
    }

    public static void initBalanceFromStorage() {
        float balValue = dataStorage.getBal();

        updateCurrentBalStat(balValue == UsableConstants.NO_BAL_VAL ?
                UsableConstants.BAL_VALUE_BLANK : UsableFunctions.formatBalToCurrency(balValue));
    }

    private void getOverflowMenu() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if(menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//      ----> End Usable methods


//      Default class methods ---->
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_help:
                mainMenuFunctions.showHelp();
                return true;
            case R.id.action_about:
                mainMenuFunctions.showAbout();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void registerReceiver() {
        if(!verifyCarrier()) return;
        smsSender.registerReceiver();
    }

    @Override
    protected void onStart()
    {
        registerReceiver();
        super.onStart();
        ApplicationMonitor.activityVisible();
    }

    @Override
    protected void onResume()
    {
        registerReceiver();
        super.onResume();
        ApplicationMonitor.activityVisible();
    }

    @Override
    protected void onDestroy()
    {
        Log.d(TAG + " - onDestroy()", UsableDebugMsgs.MAINMENU_DESTROYED);
        smsSender.unregisterReceiver();
        super.onDestroy();
        ApplicationMonitor.activityInvisible();
    }

    @Override
    protected void onPause()
    {
        smsSender.unregisterReceiver();
        super.onPause();
        ApplicationMonitor.activityInvisible();
    }

    @Override
    protected void onStop()
    {
        smsSender.unregisterReceiver();
        super.onStop();
        ApplicationMonitor.activityInvisible();
    }

//      ----> End Default class methods

}
