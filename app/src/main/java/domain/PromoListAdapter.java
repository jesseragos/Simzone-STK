package domain;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.jkiel.simzonestk.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import controller.MainMenuFunctions;
import fragments.PromoSubscribeDialogFragment;
import controller.UsableFunctions;
import res.UsableConstants;
import res.UsableJsonConstants;

/**
 * Created by Ragos Bros on 2/9/2017.
 */
public class PromoListAdapter extends BaseAdapter {

    public static final String TAG = PromoListAdapter.class.getSimpleName();

    private final JSONArray promoList;
    private final FragmentManager fragMngr;
    Context context;
    private static LayoutInflater inflater = null;

    public PromoListAdapter(Context context, FragmentManager fragMngr, JSONArray promoList) {
        this.context = context;
        this.promoList = promoList;
        this.fragMngr = fragMngr;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return promoList.length();
    }

    @Override
    public Object getItem(int position) {
        try {
            return promoList.get(position);
        } catch (JSONException e) {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // See if the view needs to be inflated
        View promoView = convertView;
        if (promoView == null) {
            promoView = inflater.inflate(R.layout.frag_promo_info, null);
        }

        TextView promoName = (TextView) promoView.findViewById(R.id.promo_name),
                 promoDesc = (TextView) promoView.findViewById(R.id.promo_desc),
                 promoCost = (TextView) promoView.findViewById(R.id.promo_cost);

        // Get the promo cat
        try {
            Promo promo = getPromoItemFromJsonFile(position);

            promoName.setText(promo.getKeyword());
            promoDesc.setText(promo.getDescription());
            promoCost.setText(UsableFunctions.formatAmountToWhole(promo.getCost()));

            promoView.setOnClickListener(new PromoItemClickListener(position));
            promoView.setOnLongClickListener(new PromoItemLongClickListener(promo));

        } catch (JSONException e) {
            promoName.setText("empty");
        }

        return promoView;
    }

    private Promo getPromoItemFromJsonFile(int position) throws JSONException {
        JSONObject promoItem = promoList.getJSONObject(position);

        String promoKey = promoItem.getString(UsableJsonConstants.PROMOKEY);
        String description = promoItem.getString(UsableJsonConstants.DESCRIPTION);
        String sendTo = promoItem.getString(UsableJsonConstants.SENDTO);
        double cost = promoItem.getDouble(UsableJsonConstants.COST);

        return new Promo(promoKey, description, sendTo, cost);
    }

    private class PromoItemClickListener implements View.OnClickListener {
        private int position;

        public PromoItemClickListener(int position) {
            super();
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            showPromoDialogConfirmation();
        }

        private void showPromoDialogConfirmation() {
            try {
                Promo promo = getPromoItemFromJsonFile(position);

                new PromoSubscribeDialogFragment(context, promo.getKeyword(), promo.getSendTo(), promo.getCost())
                        .show(fragMngr, PromoSubscribeDialogFragment.TAG);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    };

    private class PromoItemLongClickListener implements View.OnLongClickListener {
        private Promo promo;

        public PromoItemLongClickListener(Promo promo) {
            super();
            this.promo = promo;
        }

        @Override
        public boolean onLongClick(View view) {
            addBookmark();
            return false;
        }

        private void addBookmark() {
            new addBookmarkDialogFragment().show(fragMngr, TAG);
        }

        private class addBookmarkDialogFragment extends DialogFragment {

            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                // Use the Builder class for convenient dialog construction
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(UsableConstants.ADD_BMARK_LABEL)
                        .setMessage(UsableConstants.ADD_BMARK_MSG)
                        .setPositiveButton(UsableConstants.OK, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                addBookmark();
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

            private void addBookmark() {
                String msg = "";
                try {
                    MainMenuFunctions.saveBookmarkedPromoToDB(context, promo);
                    msg = UsableConstants.ADDED_BMARK_MSG;

                } catch (SQLiteConstraintException e) {
                    msg = UsableConstants.ADDED_EXISTING_BMARK_MSG;
                } finally {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }
            }

        }

    }

}
