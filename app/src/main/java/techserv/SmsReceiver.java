package techserv;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.jkiel.simzonestk.ApplicationMonitor;
import com.jkiel.simzonestk.MainMenu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import controller.MainMenuFunctions;
import res.Carrier;
import res.UsableConstants;
import res.UsableDebugMsgs;

/**
 * Created by Ragos Bros on 1/26/2017.
 */
public class SmsReceiver extends BroadcastReceiver {

    private String TAG = SmsReceiver.class.getSimpleName();
    private String currentBalStr = UsableConstants.BAL_VALUE_BLANK;
    private Pattern smsBalPattern = Pattern.compile(UsableConstants.SMS_BAL_PATTERN);
    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
//        Restrict external sms events to call this receiver
        if(!ApplicationMonitor.isActivityVisible()) return;

        this.context = context;

        Log.d(TAG + " - onReceive", String.format(UsableDebugMsgs.SMS_RECEIVER_ENTERED, intent));

        // Get the data (SMS data) bound to intent
        Bundle bundle = intent.getExtras();

        SmsMessage[] msgs = null;

        String str = "", sender = "", message = "";

        if (bundle != null) {
            // Retrieve the SMS Messages received
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];

            // First SMS message received
                // Convert Object array
                msgs[0] = SmsMessage.createFromPdu((byte[]) pdus[0]);

                sender = msgs[0].getOriginatingAddress();
                message = msgs[0].getMessageBody().toString();

                // Sender's phone number
                str += "SMS from " + sender + " : ";
                // Fetch the text message
                str += message;

            // For every SMS message received
//            for (int i=0; i < msgs.length; i++) {
//                // Convert Object array
//                msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
//
//                sender = msgs[i].getOriginatingAddress();
//                message = msgs[i].getMessageBody().toString();
//
//                // Sender's phone number
//                str += "SMS from " + sender + " : ";
//                // Fetch the text message
//                str += message;
//                // Newline <img draggable="false" class="emoji" alt="🙂" src="https://s.w.org/images/core/emoji/72x72/1f642.png">
//                str += "\n";
//            }

            // Display the entire SMS Message
            Log.d(TAG + " - onReceive(...)", str);

            try {
                String carrierName = MainMenu.carrierName;
                String phoneNum = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getLine1Number();

                if(!validateSender(sender)) return;
                //  Verify if sms received implies bal inquiry reply else save random transaction committed
                updateBalInfo(sender, message, carrierName);
                MainMenuFunctions.saveTransactionToDB(context, sender, message, carrierName, phoneNum);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean validateSender(String sender) {
//        for(Carrier carrier: Carrier.values())
//            if(sender.toLowerCase().contains(carrier.getName().toLowerCase()))
//                return true;

        return sender.toLowerCase().contains(MainMenu.dataStorage.getCarrierName().toLowerCase());

//        return false;
    }

    private void updateBalInfo(String sender, String message, String carrierName) {
        boolean isCarrierBalConfirmation = false;

        try {
            isCarrierBalConfirmation = sender.toLowerCase().contains(carrierName.toLowerCase());
        } catch (Exception e) {
            Log.d(TAG + " - updateBalInfo(...)", UsableDebugMsgs.CARRIER_NAME_PROCESS_ERR);
            e.printStackTrace();
        }

        if(!isCarrierBalConfirmation) return;

        Matcher smsBalMatcher = smsBalPattern.matcher(message);

        if(smsBalMatcher.find()
           && message.toLowerCase().contains(UsableConstants.BAL_KEYWORD)
           && isCarrierBalConfirmation) {
            currentBalStr = smsBalMatcher.group(0);
            Log.d(TAG + " - updateBalInfo(...)", String.format(UsableDebugMsgs.CARRIER_NAME_MATCH, carrierName, currentBalStr));

        } else return;

        try {
            float currentBal = Float.valueOf(String.format(UsableConstants.STR_TO_FLOAT_FORMAT, currentBalStr));
//            String balValue = UsableFunctions.formatBalToCurrency(currentBal);

            MainMenu.dataStorage.storeBalInfo(currentBal, formatBalInfoToStorage(sender, message, carrierName));
            MainMenu.initBalanceFromStorage();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String formatBalInfoToStorage(String sender, String message, String carrierName) {
        return sender + UsableConstants.NEWLINEREPLACE + message + UsableConstants.NEWLINEREPLACE + carrierName;
    }
}
