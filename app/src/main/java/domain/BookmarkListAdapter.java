package domain;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.jkiel.simzonestk.Bookmarks;
import com.jkiel.simzonestk.R;

import controller.UsableFunctions;
import fragments.PromoSubscribeDialogFragment;
import res.DatabaseQueries;
import res.UsableConstants;

/**
 * Created by Ragos Bros on 2/9/2017.
 */
public class BookmarkListAdapter extends CursorAdapter {

    public static final String TAG = BookmarkListAdapter.class.getSimpleName();
    private final int carrierColor;

    private Context context;
    private final FragmentManager appFragMngr;
    private final SQLiteDatabase db;

    private String WHERE_COND_ID = "%s = '%s'";

    public BookmarkListAdapter(Context context, FragmentManager appFragMngr, Cursor bookmarksCursor, SQLiteDatabase db, int carrierColor) {
        super(context, bookmarksCursor, 0);
        this.context = context;
        this.appFragMngr = appFragMngr;
        this.db = db;
        this.carrierColor = carrierColor;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.frag_promo_bookmark, viewGroup, false);
    }

    @Override
    public void bindView(View bookmarkView, Context context, Cursor cursor) {
        // Get bookmark fragment properties
        TextView bmarkPromoName = (TextView) bookmarkView.findViewById(R.id.promo_bmark_name),
                 bmarkPromoDesc = (TextView) bookmarkView.findViewById(R.id.promo_bmark_desc),
                 bmarkPromoCost = (TextView) bookmarkView.findViewById(R.id.promo_bmark_cost),
                 bmarkPromoCarrier = (TextView) bookmarkView.findViewById(R.id.promo_bmark_carrier);

        // Get bookmark fragment properties
        String promoName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseQueries.COL_TBLBMARK_KEYWORDID)),
               description = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseQueries.COL_TBLBMARK_DESC)),
               sendTo = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseQueries.COL_TBLBMARK_RNUM)),
               carrierName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseQueries.COL_TBLBMARK_CARRIERNAME));
        double cost = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseQueries.COL_TBLBMARK_COST));
        int color = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseQueries.COL_TBLBMARK_COLOR));

        bmarkPromoName.setText(promoName);
        bmarkPromoDesc.setText(description);
        bmarkPromoCost.setText(UsableFunctions.formatAmountToWhole(cost));
        bmarkPromoCarrier.setText(carrierName);
        bmarkPromoCarrier.setTextColor(color);

        bookmarkView.setOnClickListener(new BookmarkClickListener(new Promo(promoName, description, sendTo, cost)));
        bookmarkView.setOnLongClickListener(new BookmarkLongClickListener(promoName));
    }

    private class BookmarkClickListener implements View.OnClickListener {
        private Promo promo;

        public BookmarkClickListener(Promo promo) {
            super();
            this.promo = promo;
        }

        @Override
        public void onClick(View v) {
            showPromoDialogConfirmation();
        }

        private void showPromoDialogConfirmation() {
            new PromoSubscribeDialogFragment(context, promo.getKeyword(), promo.getSendTo(), promo.getCost())
                    .show(appFragMngr, PromoSubscribeDialogFragment.TAG);
        }
    }

    private class BookmarkLongClickListener implements View.OnLongClickListener {
        public final String TAG = BookmarkLongClickListener.class.getSimpleName();
        private String promoId;

        public BookmarkLongClickListener(String promoId) {
            super();
            this.promoId = promoId;
        }

        @Override
        public boolean onLongClick(View view) {
            deleteBookmark();
            return false;
        }

        private void deleteBookmark() {
            new DeleteBookmarkDialogFragment().show(appFragMngr, TAG);
        }

        private class DeleteBookmarkDialogFragment extends DialogFragment {

            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                // Use the Builder class for convenient dialog construction
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(UsableConstants.DELETE_BMARK_LABEL)
                        .setMessage(String.format(UsableConstants.DELETE_BMARK_MSG, promoId))
                        .setPositiveButton(UsableConstants.OK, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                deleteBookmark();
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

            private void deleteBookmark() {
                db.delete(DatabaseQueries.TABLE_BOOKMARKS,
                        String.format(WHERE_COND_ID, DatabaseQueries.COL_TBLBMARK_KEYWORDID, promoId), null);

                changeCursor(Bookmarks.getBookmarksCursor(db));
                notifyDataSetChanged();

                Toast.makeText(context, UsableConstants.DELETED_BMARK_MSG,
                        Toast.LENGTH_SHORT).show();
            }

        }
    };



}
