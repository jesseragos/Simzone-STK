package techserv;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.widget.Toast;

/**
 * Created by Ragos Bros on 2/6/2017.
 */
public class SmsSender {

    final String SENT = "SMS_SENT";
//    final String DELIVERED = "SMS_DELIVERED";
    private Context context;
    private BroadcastReceiver broadcastReceiver;

    public SmsSender(Context context) {
        this.context = context;
        setBroadCastReceiver();
    }

    private void setBroadCastReceiver() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {

                String msg = "Sending request failed";
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        msg = "Waiting to receive notification";
                        break;
//                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
//                        Toast.makeText(context, "Generic failure",
//                                Toast.LENGTH_SHORT).show();
//                        break;
//                    case SmsManager.RESULT_ERROR_NO_SERVICE:
//                        Toast.makeText(context, "No service",
//                                Toast.LENGTH_SHORT).show();
//                        break;
//                    case SmsManager.RESULT_ERROR_NULL_PDU:
//                        Toast.makeText(context, "Null PDU",
//                                Toast.LENGTH_SHORT).show();
//                        break;
//                    case SmsManager.RESULT_ERROR_RADIO_OFF:
//                        Toast.makeText(context, "Radio off",
//                                Toast.LENGTH_SHORT).show();
//                        break;
                }

                Toast.makeText(context, msg,
                        Toast.LENGTH_SHORT).show();
            }
        };
    }

    public void sendSMS(String phoneNumber, String message) {
        PendingIntent sentPI = PendingIntent.getBroadcast(context, 0, new Intent(
                SENT), 0);

//        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
//                new Intent(DELIVERED), 0);

        // ---when the SMS has been sent---
        registerReceiver();

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, null);
    }

    public void registerReceiver() {
        context.registerReceiver(broadcastReceiver, new IntentFilter(SENT));
    }

    public void unregisterReceiver() {
        registerReceiver();
        context.unregisterReceiver(broadcastReceiver);
    }
}
