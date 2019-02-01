package controller;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;

import com.jkiel.simzonestk.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;

import res.Carrier;
import res.UsableConstants;

/**
 * Created by Ragos Bros on 1/28/2017.
 */
public class UsableFunctions {

    private final static Intent callIntent = new Intent(Intent.ACTION_CALL);

    public static String formatBalToCurrency(float balValue) {
        return String.format(UsableConstants.CURRENCY_2DP_FORMAT, balValue);
    }

    public static String formatAmountToWhole(double value) {
        return String.format(UsableConstants.CURRENCY_0DP_FORMAT, value);
    }

    public static String getJsonFileContent(Object object, Context appContext) {
        String jsonData, dir = "";

        try {
            if(object instanceof Carrier) dir = ((Carrier) object).getServicesDir();
            else if(object instanceof String) dir = (String) object;
            else return dir;

            InputStream is = appContext.getAssets().open(String.format(UsableConstants.FILE_JSON_DIR_FORMAT, dir));
            int size = is.available();

            byte[] buffer = new byte[size];
            is.read(buffer);

            is.close();

            jsonData = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return jsonData;
    }

    public static Typeface getButtonTypeFace(Context context) {
        return Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.button_font));
    }

    public static Typeface getLabelTypeFace(Context context) {
        return Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.main_font));
    }

    public static void associateBackButton(ActionBar actionBar) {
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public static void initiatePhoneCallEvent(Context context, String dialNum) {
        //  Call specified dial number
        callIntent.setData(Uri.parse(String.format(UsableConstants.CALL_TELEPHONY_NUM_FORMAT, dialNum)));
        callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(callIntent);
    }

    public static String formattedPhoneNum(String phoneNum) {
        Matcher matcher = UsableConstants.PTRN_PHONE_NUM.matcher(phoneNum);

        if(matcher.find()) return String.format(UsableConstants.PHONE_NUM_FORMAT, matcher.group(2));

        return "";
    }

    public static void goToUrl(Context context, String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        launchBrowser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(launchBrowser);
    }

}
