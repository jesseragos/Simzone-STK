package fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import com.jkiel.simzonestk.MainMenu;

import controller.MainMenuFunctions;
import res.UsableConstants;
import controller.UsableFunctions;

/**
 * Created by Ragos Bros on 1/18/2017.
 */
public class PromoSubscribeDialogFragment extends DialogFragment {

    public static final String TAG = PromoSubscribeDialogFragment.class.getSimpleName();
    private final Context context;

    private String keyWord, sendTo;
    private double cost;

    public PromoSubscribeDialogFragment(Context context, String keyWord, String sendTo, double cost) {
        super();

        this.context = context;
        this.keyWord = keyWord;
        this.sendTo =  sendTo;
        this.cost = cost;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(String.format(UsableConstants.SUBSCRIBING_PROMO_FORMAT, keyWord))
               .setMessage(String.format(UsableConstants.PROMO_AVAIL_COST_FORMAT, UsableFunctions.formatAmountToWhole(cost)))
               .setPositiveButton(UsableConstants.CONFIRM, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       subscribePromo();
                   }
               })
               .setNegativeButton(UsableConstants.CANCEL, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       getDialog().cancel();
                   }
               });

        // Create the AlertDialog object and return it
        return builder.create();
    }

    private void subscribePromo() {
        MainMenu.smsSender.sendSMS(sendTo, keyWord);
        Toast.makeText(context, UsableConstants.PROMO_SENT_MSG,
                Toast.LENGTH_SHORT).show();
    }

}

