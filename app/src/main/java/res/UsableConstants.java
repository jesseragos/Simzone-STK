package res;

import java.util.regex.Pattern;

/**
 * Created by Ragos Bros on 1/28/2017.
 */
public class UsableConstants {

    //  App Function names
    public final static String LOAD_CARD = "Load Card",
                               CHECK_BAL_INFO = "Check Balance Info",
                               PROMOS = "Promos",
                               BOOKMARKS = "Bookmarks",
                               SHARE_LOAD = "Share Load",
                               TRANSACTIONS = "Transactions";
    public final static String[] APP_FUNCTION_NAMES = { LOAD_CARD,
                                                        CHECK_BAL_INFO,
                                                        PROMOS,
                                                        BOOKMARKS,
                                                        SHARE_LOAD,
                                                        TRANSACTIONS };
    //  String messages
    public final static String LOADING_PIN_MSG = "Load Card PIN is being processed",
                               LOADING_PIN_VOICEPROMPT_MSG = "A voice prompt transaction will handle your Load Card PIN. Do you you want to initiate the call transaction?",
                               UPDATING_BAL_MSG = "Updating balance...",
                               CARRIER_URL_MSG = "Go to carrier services site?",
                               SHARING_LOAD_MSG = "Sharing load amount...",
                               PROMO_SENT_MSG = "Your promo request has been sent. Please wait for your confirmation reply.",
                               INVALID_CARRIER_MSG = "Your current network operator may be unsupported/invalid or you are in airplane mode.",
                               ADD_BMARK_MSG = "Do you want to add promo to bookmarks?",
                               ADDED_BMARK_MSG = "Selected bookmark added",
                               ADDED_EXISTING_BMARK_MSG = "Promo was already bookmarked",
                               DELETE_TRANS_MSG = "Are you sure you want to remove transaction?",
                               DELETE_BMARK_MSG = "Are you sure you want to remove %s bookmark?",
                               DELETED_TRANS_MSG = "Selected transaction deleted",
                               DELETED_BMARK_MSG = "Selected bookmark deleted",
                               INVALID_PHONE_NUM = "Invalid phone number",
                               DISCLAIMER_MSG = "All transactions and network requests are not set responsible to the system and developers if any issues occur like unwanted charges. By then, please be cautious and aware of selection of value added services",
                               SERV_PREFS_ERR = "SERVICE PREFS ERROR OCCURRED";

    //  Other constants
    public final static String SMS_BAL_PATTERN = "\\d+\\.\\d{2}",
                               BAL_KEYWORD = "bal",
                               BAL_VALUE_BLANK = "-",
                               PHONE_VALUE_BLANK = "-",
                               BAL_INFO_BLANK = "No balance info%n%-",
                               BACK = "Back",
                               OK = "Ok",
                               CONFIRM = "Confirm",
                               CANCEL = "Cancel";
    public final static float NO_BAL_VAL = -1.0f;
    public final static int PHONE_NUM_LENGTH = 11;

    //  Intent IDs
    public final static String DEVCAR_ID = "deviceCarrierID",
                               SELECTEDPROMOCAT_ID = "selectedPromoCategoryID";

    //  UI Labels
    public final static String PROMO_LIST_LABEL = "Promos: %s",
                               BAL_INFO_LABEL = "Balance Info",
                               LOAD_CARD_LABEL = "Load Card PIN",
                               SHARE_LOAD_LABEL = "Share Load",
                               ADD_BMARK_LABEL = "Add bookmark",
                               DELETE_TRANS_LABEL = "Delete Transaction",
                               DELETE_BMARK_LABEL = "Delete Bookmark",
                               APP_VERSION_LABEL = "Version",
                               DEVELOPER_LABEL = "Developer",
                               DISCLAIMER_LABEL = "Disclaimer",
                               DEVELOPER_NAME_LABEL = "Jessekiel N. Ragos",
                               TECH_ERR_LABEL = "Technical Error";

    //  Key names
    public final static String KEY_DATASTORAGE_BAL = "bal",
                               KEY_DATASTORAGE_BALINFO = "balInfo",
                               KEY_DATASTORAGE_CARRNAME = "carrierName",
                               KEY_CHKBALDF_DEVCAR = "devcar",
                               KEY_LDCARDDF_CSERVPREFS = "carservprefs",
                               KEY_LDCARDDF_MENUFUNCS = "menufuncs",
                               KEY_SHARELDDF_MENUFUNCS = KEY_LDCARDDF_MENUFUNCS;

    //  File names and directories
    public final static String FILE_BAL_INFO = "bal_info",
                               FILE_JSON_HELP = "help_app",
                               FILE_JSON_ABOUT = "about_app",
                               FILE_JSON_DIR_FORMAT = "files/%s.json";

    //  String formats
    public final static String CALL_TELEPHONY_NUM_FORMAT = "tel:%s",
                               BAL_INFO_FORMAT = "From: %s | Carrier Info: %s",
                               SUBSCRIBING_PROMO_FORMAT = "Subscribing to %s",
                               PROMO_AVAIL_COST_FORMAT = "Are you sure to avail the promo for %s";

    //  Custom format specifiers
    public final static String NEWLINEREPLACE = "%n%",
                               PHONE_NUM_PATTERN = "^(0|63)?(\\d{10})$",
                               PHONE_NUM_FORMAT = "0%s",
                               STR_TO_FLOAT_FORMAT = "%sf",
                               CURRENCY_2DP_FORMAT = "₱%.2f",
                               CURRENCY_0DP_FORMAT = "₱%.0f",
                               TRANS_SENDER_FORMAT = "From: %s",
                               TRANS_SOURCE_FORMAT = "%s (%s)";

    //  Patterns
    public final static Pattern PTRN_PHONE_NUM = Pattern.compile(PHONE_NUM_PATTERN);

    //  App/Credentials info

}
