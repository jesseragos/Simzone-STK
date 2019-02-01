package fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;

import res.UsableConstants;

/**
 * Created by Ragos Bros on 1/18/2017.
 */
public class InvalidCarrierDialogFragment extends DialogFragment {

    public static final String TAG = InvalidCarrierDialogFragment.class.getSimpleName();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(UsableConstants.TECH_ERR_LABEL)
               .setMessage(UsableConstants.INVALID_CARRIER_MSG)
               .setPositiveButton(UsableConstants.OK, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       exitActivity();
                   }
               }).setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
                        if( keyCode == KeyEvent.KEYCODE_BACK ) {
                            exitActivity();
                            return true;
                        }
                        return false;
                    }
                });

        // Create the AlertDialog object and return it
        return builder.create();
    }

    private void exitActivity() {
        getActivity().finish();
        System.exit(0);
    }

}

