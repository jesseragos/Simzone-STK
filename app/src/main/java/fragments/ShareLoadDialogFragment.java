package fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.TextView;

import com.jkiel.simzonestk.R;

import controller.MainMenuFunctions;
import controller.UsableFunctions;
import res.UsableConstants;

/**
 * Created by Ragos Bros on 1/18/2017.
 */
public class ShareLoadDialogFragment extends DialogFragment {

    public static final String TAG = ShareLoadDialogFragment.class.getSimpleName();

    private MainMenuFunctions mainMenuFunctions;

    public static ShareLoadDialogFragment newInstance(int arg, MainMenuFunctions mainMenuFunctions) {
        ShareLoadDialogFragment frag = new ShareLoadDialogFragment();
        Bundle args = new Bundle();
        args.putInt(UsableConstants.KEY_SHARELDDF_MENUFUNCS, arg);
        frag.setArguments(args);

        frag.setMainMenuFunctions(mainMenuFunctions);

        return frag;
    }

    public void setMainMenuFunctions(MainMenuFunctions mainMenuFunctions) {
        this.mainMenuFunctions = mainMenuFunctions;
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
        builder.setView(inflater.inflate(R.layout.dialog_share_load, null))
        // Add action buttons
                .setTitle(UsableConstants.SHARE_LOAD_LABEL)
                .setPositiveButton(UsableConstants.CONFIRM, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        TextView receiverNumTextBar = (TextView) ShareLoadDialogFragment.this.getDialog().findViewById(R.id.receiver_num_textbar);
                        TextView amountTextBar = (TextView) ShareLoadDialogFragment.this.getDialog().findViewById(R.id.amount_textBar);

                        mainMenuFunctions.shareLoad(
                                UsableFunctions.formattedPhoneNum(receiverNumTextBar.getText().toString()),
                                Integer.valueOf(amountTextBar.getText().toString()));
                    }
                })
                .setNegativeButton(UsableConstants.CANCEL, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getDialog().cancel();
                    }
                });

        return builder.create();
    }

    public void onStart() {
        super.onStart();
        ((EditText) getDialog().findViewById(R.id.receiver_num_textbar)).setFilters(
                new InputFilter[] {new InputFilter.LengthFilter(UsableConstants.PHONE_NUM_LENGTH)});
    }

}

