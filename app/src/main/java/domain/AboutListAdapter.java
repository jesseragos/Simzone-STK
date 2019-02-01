package domain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.jkiel.simzonestk.R;

/**
 * Created by Ragos Bros on 2/9/2017.
 */
public class AboutListAdapter extends BaseAdapter {

    public static final String TAG = AboutListAdapter.class.getSimpleName();

    private final StandardInfo[] aboutInfo;
    Context context;
    private static LayoutInflater inflater = null;

    public AboutListAdapter(Context context, StandardInfo[] aboutInfo) {
        this.context = context;
        this.aboutInfo = aboutInfo;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return aboutInfo.length;
    }

    @Override
    public Object getItem(int position) {
        return aboutInfo[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // See if the view needs to be inflated
        View aboutView = convertView;
        if (aboutView == null) {
            aboutView = inflater.inflate(R.layout.frag_std_info, null);
        }

        TextView header = (TextView) aboutView.findViewById(R.id.std_info_header_textview),
                 content = (TextView) aboutView.findViewById(R.id.std_info_content_textview);

        // Get the about item
        header.setText(aboutInfo[position].getHeader());
        content.setText(aboutInfo[position].getTextContent());

        return aboutView;
    }

}
