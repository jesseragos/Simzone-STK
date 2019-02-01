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

import org.json.JSONObject;

import controller.MainMenuFunctions;
import res.UsableConstants;
import res.UsableJsonConstants;

/**
 * Created by Ragos Bros on 1/18/2017.
 */
public class LoadCardPinDialogFragment extends DialogFragment {

    public static final String TAG = LoadCardPinDialogFragment.class.getSimpleName();

    private String instructions;
    private int pinLength;
    private JSONObject carrierServPrefs;
    private MainMenuFunctions mainMenuFunctions;

    public static LoadCardPinDialogFragment newInstance(int arg1, JSONObject carrierServPrefs,
                                                         int arg2, MainMenuFunctions mainMenuFunctions) {
        LoadCardPinDialogFragment frag = new LoadCardPinDialogFragment();
        Bundle args = new Bundle();
        args.putInt(UsableConstants.KEY_LDCARDDF_CSERVPREFS, arg1);
        args.putInt(UsableConstants.KEY_LDCARDDF_MENUFUNCS, arg2);
        frag.setArguments(args);

        frag.setCarrierServPrefs(carrierServPrefs);
        frag.setMainMenuFunctions(mainMenuFunctions);

        return frag;
    }

    public void setCarrierServPrefs(JSONObject carrierServPrefs) {
        this.carrierServPrefs = carrierServPrefs;
    }

    public void setMainMenuFunctions(MainMenuFunctions mainMenuFunctions) {
        this.mainMenuFunctions = mainMenuFunctions;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        setRetainInstance(true);

        try {
            JSONObject carrierServPref = carrierServPrefs.getJSONObject(UsableJsonConstants.CARDLOAD);
            instructions = carrierServPref.getString(UsableJsonConstants.INSTRUCTIONS);
            pinLength = carrierServPref.getInt(UsableJsonConstants.PINLENGTH);
        } catch (Exception e) {
            e.printStackTrace();
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dialog_load_card_pin, null))
        // Add action buttons
                .setTitle(UsableConstants.LOAD_CARD_LABEL)
                .setPositiveButton(UsableConstants.CONFIRM, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        TextView loadCardPinText = (TextView) getDialog().findViewById(R.id.load_card_pin_edittext);
                        mainMenuFunctions.loadCard(loadCardPinText.getText().toString());

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
        ((TextView) getDialog().findViewById(R.id.load_card_instructions_textview)).setText(instructions);
        ((EditText) getDialog().findViewById(R.id.load_card_pin_edittext)).setFilters(
                new InputFilter[] {new InputFilter.LengthFilter(pinLength)});
    }

}

