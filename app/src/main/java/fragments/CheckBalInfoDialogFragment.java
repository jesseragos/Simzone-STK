package fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.jkiel.simzonestk.MainMenu;
import com.jkiel.simzonestk.R;

import res.Carrier;
import res.UsableConstants;

/**
 * Created by Ragos Bros on 1/18/2017.
 */
public class CheckBalInfoDialogFragment extends DialogFragment {

    public static final String TAG = CheckBalInfoDialogFragment.class.getSimpleName();
    private String sender, message;

    public static CheckBalInfoDialogFragment newInstance(int arg) {
        CheckBalInfoDialogFragment frag = new CheckBalInfoDialogFragment();
        Bundle args = new Bundle();
        args.putInt(UsableConstants.KEY_CHKBALDF_DEVCAR, arg);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        setRetainInstance(true);

        setBalInfo();

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dialog_check_bal_info, null))
        // Add action buttons
                .setTitle(UsableConstants.BAL_INFO_LABEL)
                .setPositiveButton(UsableConstants.BACK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        getDialog().cancel();
                    }
                });

        return builder.create();
    }

    private void setBalInfo() {
        String[] balInfo;
        try {
            balInfo = MainMenu.dataStorage.getBalInfo().split(UsableConstants.NEWLINEREPLACE);

            sender = String.format(UsableConstants.BAL_INFO_FORMAT, balInfo[0], balInfo[2]);
            message = balInfo[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            balInfo = UsableConstants.BAL_INFO_BLANK.split(UsableConstants.NEWLINEREPLACE);

            sender = balInfo[0];
            message = balInfo[1];
        }
    }

    public void onStart() {
        super.onStart();
        ((TextView) getDialog().findViewById(R.id.sender_text_view)).setText(sender);
        ((TextView) getDialog().findViewById(R.id.message_text_view)).setText(message);
    }

}

