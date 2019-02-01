package fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import com.jkiel.simzonestk.MainMenu;

import controller.UsableFunctions;
import res.UsableConstants;
import res.UsableJsonConstants;

/**
 * Created by Ragos Bros on 1/18/2017.
 */
public class LoadCardPromptDialogFragment extends DialogFragment {

    public static final String TAG = LoadCardPromptDialogFragment.class.getSimpleName();

    private final Context context;
    private final String callNum;

    public LoadCardPromptDialogFragment(Context context, String callNum) {
        super();

        this.context = context;
        this.callNum = callNum;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(UsableConstants.LOAD_CARD_LABEL)
               .setMessage(UsableConstants.LOADING_PIN_VOICEPROMPT_MSG)
               .setPositiveButton(UsableConstants.CONFIRM, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       UsableFunctions.initiatePhoneCallEvent(context, callNum);
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

}

