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

import com.jkiel.simzonestk.R;
import com.jkiel.simzonestk.Transactions;

import res.DatabaseQueries;
import res.UsableConstants;

/**
 * Created by Ragos Bros on 2/9/2017.
 */
public class TransactionListAdapter extends CursorAdapter {

    public static final String TAG = TransactionListAdapter.class.getSimpleName();

    private Context context;
    private final FragmentManager appFragMngr;
    private final SQLiteDatabase db;

    private String WHERE_COND_ID = "%s = %s";

    public TransactionListAdapter(Context context, FragmentManager appFragMngr, Cursor transactionsCursor, SQLiteDatabase db) {
        super(context, transactionsCursor, 0);
        this.context = context;
        this.appFragMngr = appFragMngr;
        this.db = db;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.frag_transaction, viewGroup, false);
    }

    @Override
    public void bindView(View transactionView, Context context, Cursor cursor) {
        // Get transaction fragment properties
        TextView transName = (TextView) transactionView.findViewById(R.id.transaction_name),
                transMsg = (TextView) transactionView.findViewById(R.id.transaction_msg),
                transPhoneDtl = (TextView) transactionView.findViewById(R.id.transaction_phonedtl);

        // Get transaction fragment properties
        long id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseQueries.COL_TBLTRANS_ID));
        String sender = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseQueries.COL_TBLTRANS_SENDER)),
               message = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseQueries.COL_TBLTRANS_MESSAGE)),
               carrierName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseQueries.COL_TBLTRANS_CARRIERNAME)),
               phoneNum = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseQueries.COL_TBLTRANS_PHONENUM));

        transName.setText(String.format(UsableConstants.TRANS_SENDER_FORMAT, sender));
        transMsg.setText(message);
        transPhoneDtl.setText(String.format(UsableConstants.TRANS_SOURCE_FORMAT, carrierName, phoneNum));

        transactionView.setOnLongClickListener(new TransactionLongClickListener(id));
    }

    private class TransactionLongClickListener implements View.OnLongClickListener {
        public final String TAG = TransactionLongClickListener.class.getSimpleName();
        private long id;

        public TransactionLongClickListener(long id) {
            super();
            this.id = id;
        }

        @Override
        public boolean onLongClick(View view) {
            deleteTransaction();
            return false;
        }

        private void deleteTransaction() {
            new DeleteTransactionDialogFragment().show(appFragMngr, TAG);
        }

        private class DeleteTransactionDialogFragment extends DialogFragment {

            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                // Use the Builder class for convenient dialog construction
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(UsableConstants.DELETE_TRANS_LABEL)
                        .setMessage(UsableConstants.DELETE_TRANS_MSG)
                        .setPositiveButton(UsableConstants.OK, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                deleteTransaction();
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

            private void deleteTransaction() {
                db.delete(DatabaseQueries.TABLE_TRANSACTIONS,
                        String.format(WHERE_COND_ID, DatabaseQueries.COL_TBLTRANS_ID, id), null);

                changeCursor(Transactions.getTransactionsCursor(db));
                notifyDataSetChanged();

                Toast.makeText(context, UsableConstants.DELETED_TRANS_MSG,
                        Toast.LENGTH_SHORT).show();
            }

        }
    };



}
