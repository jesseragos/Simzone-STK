package domain;

import android.app.FragmentManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jkiel.simzonestk.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import res.UsableJsonConstants;

/**
 * Created by Ragos Bros on 2/9/2017.
 */
public class HelpListAdapter extends BaseAdapter {

    public static final String TAG = HelpListAdapter.class.getSimpleName();

    private final JSONArray helpList;
    Context context;
    private static LayoutInflater inflater = null;

    public HelpListAdapter(Context context, JSONArray helpList) {
        this.context = context;
        this.helpList = helpList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return helpList.length();
    }

    @Override
    public Object getItem(int position) {
        try {
            return helpList.get(position);
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
        View helpView = convertView;
        if (helpView == null) {
            helpView = inflater.inflate(R.layout.frag_std_info, null);
        }

        TextView header = (TextView) helpView.findViewById(R.id.std_info_header_textview),
                 content = (TextView) helpView.findViewById(R.id.std_info_content_textview);

        // Get the promo cat
        try {
            JSONObject helpItem = helpList.getJSONObject(position);

            header.setText(helpItem.getString(UsableJsonConstants.TITLE));
            content.setText(helpItem.getString(UsableJsonConstants.INSTRUCTIONS));

        } catch (JSONException e) {
            header.setText("empty");
        }

        return helpView;
    }

}
