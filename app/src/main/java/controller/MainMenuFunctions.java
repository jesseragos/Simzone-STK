package controller;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.jkiel.simzonestk.About;
import com.jkiel.simzonestk.Bookmarks;
import com.jkiel.simzonestk.Help;
import com.jkiel.simzonestk.MainMenu;
import com.jkiel.simzonestk.PromoCategories;
import com.jkiel.simzonestk.Transactions;

import org.json.*;

import domain.Promo;
import fragments.CheckBalInfoDialogFragment;
import fragments.LoadCardPinDialogFragment;
import fragments.LoadCardPromptDialogFragment;
import fragments.ShareLoadDialogFragment;
import res.Carrier;
import res.DatabaseQueries;
import res.UsableConstants;
import res.UsableJsonConstants;
import techserv.DatabaseOpenHelper;

/**
 * Created by Ragos Bros on 2/6/2017.
 */
public class MainMenuFunctions {

    public static final String TAG = MainMenuFunctions.class.getSimpleName();

    private final Context appContext;
    private final FragmentManager appFragmentManager;
    private static String carrierName;
    private static int bgColor;
    private Carrier deviceCarrier;
    private JSONObject carrierServPrefs;
    private JSONObject cardLoadPrefs;

    public MainMenuFunctions(Context appContext, FragmentManager appFragmentManager, Carrier carrier) {
        this.appContext = appContext;
        this.appFragmentManager = appFragmentManager;
        this.deviceCarrier = carrier;
        carrierName = deviceCarrier.getName();
        bgColor = deviceCarrier.getBgColorValue();

        try {
            carrierServPrefs = new JSONObject(UsableFunctions.getJsonFileContent(this.deviceCarrier, this.appContext));
            cardLoadPrefs = carrierServPrefs.getJSONObject(UsableJsonConstants.CARDLOAD);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void loadCard() {
        try {
            if(cardLoadPrefs.getBoolean(UsableJsonConstants.VOICEPROMPT))
                loadCardPromptDialog();
            else loadCardInDialog();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void loadCardInDialog() {
        FragmentManager manager = appFragmentManager;
        FragmentTransaction ft = manager.beginTransaction();

        Fragment prev = manager.findFragmentByTag(LoadCardPinDialogFragment.TAG);
        if (prev != null) {
            ft.remove(prev);
        }

        // Create and show the dialog.
        LoadCardPinDialogFragment loadCardPinDialogFragment = LoadCardPinDialogFragment.newInstance(1, carrierServPrefs,
                                                                                                    2, this);

        loadCardPinDialogFragment.show(appFragmentManager, LoadCardPinDialogFragment.TAG);
    }

    public void loadCardPromptDialog() throws JSONException {
        new LoadCardPromptDialogFragment(appContext, cardLoadPrefs.getString(UsableJsonConstants.DIALNUMBER))
                .show(appFragmentManager, LoadCardPromptDialogFragment.TAG);
    }

    public void loadCard(String pin) {
//        String servicesDir = deviceCarrier.getServicesDir();  //  <-- Do something

        try {
            String dialKeyword = cardLoadPrefs.getString(UsableJsonConstants.DIALNUMBER);

            //  Notify of load card pin is being processed to load sim
            Toast.makeText(appContext, UsableConstants.LOADING_PIN_MSG, Toast.LENGTH_SHORT).show();
            UsableFunctions.initiatePhoneCallEvent(appContext, dialKeyword.replace(UsableJsonConstants.PINREPLACE, pin));

            updateBalance();
        } catch (JSONException e) {
            Toast.makeText(appContext, UsableConstants.SERV_PREFS_ERR, Toast.LENGTH_LONG).show();
            Log.d(TAG + " - loadCard(...)", UsableConstants.SERV_PREFS_ERR);
//            e.printStackTrace();
        }
    }

    public void updateBalance() {
        updateBalance(null);
    }

    public void updateBalance(View view) {
        try {
            JSONObject balInquiryPrefs = carrierServPrefs.getJSONObject(UsableJsonConstants.BALINQUIRY);
            String receiverKeyword = balInquiryPrefs.getString(UsableJsonConstants.RECEIVERKEYWORD),
                    message = balInquiryPrefs.getString(UsableJsonConstants.MESSAGE);

            Toast.makeText(appContext, UsableConstants.UPDATING_BAL_MSG, Toast.LENGTH_SHORT).show();
            if(receiverKeyword.equals(UsableJsonConstants.CALL)) {
                UsableFunctions.initiatePhoneCallEvent(appContext, message);
            } else {
                MainMenu.smsSender.sendSMS(receiverKeyword, message);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void shareLoadInDialog() {
        FragmentManager manager = appFragmentManager;
        FragmentTransaction ft = manager.beginTransaction();

        Fragment prev = manager.findFragmentByTag(ShareLoadDialogFragment.TAG);
        if (prev != null) {
            ft.remove(prev);
        }

// Create and show the dialog.
        ShareLoadDialogFragment shareLoadDialogFragment = ShareLoadDialogFragment.newInstance(1, this);

        shareLoadDialogFragment.show(appFragmentManager, ShareLoadDialogFragment.TAG);
    }

    public void shareLoad(String receiverNum, int amount) {
        //  Validate phone number's value then proceed if successful else inform user invalid number
        if((receiverNum = UsableFunctions.formattedPhoneNum(receiverNum)).isEmpty()) {
            Toast.makeText(appContext, UsableConstants.INVALID_PHONE_NUM,
                    Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            JSONObject shareLoadPrefs = carrierServPrefs.getJSONObject(UsableJsonConstants.SHARELOAD);
            String receiverKeyword = shareLoadPrefs.getString(UsableJsonConstants.RECEIVERKEYWORD);
            String message = shareLoadPrefs.getString(UsableJsonConstants.MESSAGE);

            message = message.replace(UsableJsonConstants.AMOUNTREPLACE, String.valueOf(amount));
            if(shareLoadPrefs.getBoolean(UsableJsonConstants.ISNUMREVEIVER)){
                receiverKeyword = receiverKeyword.replace(UsableJsonConstants.RECEIVERNUMREPLACE, receiverNum);
            } else {
                message = message.replace(UsableJsonConstants.RECEIVERNUMREPLACE, receiverNum);
            }

            MainMenu.smsSender.sendSMS(receiverKeyword, message);
            Toast.makeText(appContext, UsableConstants.SHARING_LOAD_MSG,
                    Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void checkBalInfoInDialog() {
        FragmentManager manager = appFragmentManager;
        FragmentTransaction ft = manager.beginTransaction();

        Fragment prev = manager.findFragmentByTag(CheckBalInfoDialogFragment.TAG);
        if (prev != null) {
            ft.remove(prev);
        }

// Create and show the dialog.
        CheckBalInfoDialogFragment checkBalInfoDialogFragment = CheckBalInfoDialogFragment.newInstance(1);
        checkBalInfoDialogFragment.show(ft, CheckBalInfoDialogFragment.TAG);
    }

    public void showPromoCatagories() {
        showNewActivity(PromoCategories.class);
    }

    public void showBookmarks() {
        showNewActivity(Bookmarks.class);
    }

    public void showTransactions() {
        showNewActivity(Transactions.class);
    }

    //  Menu options
    public void showHelp() {
        showNewActivity(Help.class);
    }

    public void showAbout() {
        showNewActivity(About.class);
    }

    public void showNewActivity(Class activityClass) {
        Intent intent = new Intent(appContext, activityClass);
        intent.putExtra(UsableConstants.DEVCAR_ID, deviceCarrier);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        appContext.startActivity(intent);
    }

    public static void saveTransactionToDB(Context context, String sender, String message, String carrierName, String phoneNum) {
        // Store sender, message, and carrierName in a ContentValues object.
        ContentValues values = new ContentValues();
        values.put(DatabaseQueries.COL_TBLTRANS_SENDER, sender);
        values.put(DatabaseQueries.COL_TBLTRANS_MESSAGE, message);
        values.put(DatabaseQueries.COL_TBLTRANS_CARRIERNAME, carrierName);
        values.put(DatabaseQueries.COL_TBLTRANS_PHONENUM,
                (phoneNum = UsableFunctions.formattedPhoneNum(phoneNum)).isEmpty()? UsableConstants.PHONE_VALUE_BLANK:phoneNum);

        saveValuesToDB(context, values, DatabaseQueries.TABLE_TRANSACTIONS);
    }

    public static void saveBookmarkedPromoToDB(Context context, Promo promo) {
        // Store sender, message, and carrierName in a ContentValues object.
        ContentValues values = new ContentValues();
        values.put(DatabaseQueries.COL_TBLBMARK_KEYWORDID, promo.getKeyword());
        values.put(DatabaseQueries.COL_TBLBMARK_DESC, promo.getDescription());
        values.put(DatabaseQueries.COL_TBLBMARK_RNUM, promo.getSendTo());
        values.put(DatabaseQueries.COL_TBLBMARK_COST, promo.getCost());
        values.put(DatabaseQueries.COL_TBLBMARK_CARRIERNAME, carrierName);
        values.put(DatabaseQueries.COL_TBLBMARK_COLOR, bgColor);

        saveValuesToDB(context, values, DatabaseQueries.TABLE_BOOKMARKS);
    }

    private static void saveValuesToDB(Context context, ContentValues values, String dbTable) {
        // Record that ContentValues in a SQLite database
        DatabaseOpenHelper dbHelper = new DatabaseOpenHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long id = db.insertOrThrow(dbTable, null, values);

        dbHelper.close();

        Log.d(TAG + " - saveTransactionToDB(...)", String.format("Saved new record to table %s with ID: %d", dbTable, id));
    }

}
