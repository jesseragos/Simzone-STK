package fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.jkiel.simzonestk.R;

import controller.MainMenuFunctions;
import controller.UsableFunctions;
import res.UsableConstants;

/**
 * Created by Ragos Bros on 1/18/2017.
 */
public class CarrierUrlPromptDialogFragment extends DialogFragment {

    public static final String TAG = CarrierUrlPromptDialogFragment.class.getSimpleName();
    private Context context;
    private String url;

    public CarrierUrlPromptDialogFragment(Context context, String url) {
        this.context = context;
        this.url = url;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        setRetainInstance(true);

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dialog_carrier_site_prompt, null))
        // Add action buttons
                .setMessage(UsableConstants.CARRIER_URL_MSG)
                .setPositiveButton(UsableConstants.CONFIRM, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        UsableFunctions.goToUrl(context, url);
                    }
                })
                .setNegativeButton(UsableConstants.CANCEL, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getDialog().cancel();
                    }
                });

        return builder.create();
    }

}

