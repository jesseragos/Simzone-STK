package controller;

import android.content.SharedPreferences;

import res.UsableConstants;

/**
 * Created by Ragos Bros on 2/6/2017.
 */
public class DataStorage {

    private final SharedPreferences prefs;

    public DataStorage(SharedPreferences prefs) {
        this.prefs = prefs;
    }

    public void storeBalInfo(float bal, String balInfo) {
        // Get the SharedPreferences editor.
        SharedPreferences.Editor editor = prefs.edit();

        // Save the bal value and info.
        editor.putFloat(UsableConstants.KEY_DATASTORAGE_BAL, bal);
        editor.putString(UsableConstants.KEY_DATASTORAGE_BALINFO, balInfo);

        // Commit the changes.
        editor.commit();
    }

    public float getBal() {
        return prefs.getFloat(UsableConstants.KEY_DATASTORAGE_BAL, UsableConstants.NO_BAL_VAL);
    }

    public String getBalInfo() {
        return prefs.getString(UsableConstants.KEY_DATASTORAGE_BALINFO, UsableConstants.BAL_INFO_BLANK);
    }

    public void storeCarrierName(String carrierName) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(UsableConstants.KEY_DATASTORAGE_CARRNAME, carrierName);
        editor.commit();
    }

    public String getCarrierName() {
        return prefs.getString(UsableConstants.KEY_DATASTORAGE_CARRNAME, "");
    }

}
