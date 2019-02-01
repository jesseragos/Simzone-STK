package domain;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import com.jkiel.simzonestk.MainMenu;

import controller.MainMenuFunctions;
import controller.UsableFunctions;
import res.UsableConstants;

/**
 * Created by Ragos Bros on 2/8/2017.
 */
public class MenuButtonAdapter extends BaseAdapter {
    private final Context mContext;
    private final MainMenuFunctions mainMenuFunctions;
    private final String[] btnNames;
    private final View.OnClickListener clickListener;
    private final int bgResource;

    // Gets the context so it can be used later
    public MenuButtonAdapter(Context c, MainMenuFunctions mainMenuFunctions,String[] btnNames, int bgResource) {
        mContext = c;
        this.mainMenuFunctions = mainMenuFunctions;
        this.btnNames = btnNames;
        this.bgResource = bgResource;
        clickListener = getMenuBtnListener();
    }

    // Total number of things contained within the adapter
    public int getCount() {
        return btnNames.length;
    }

    // Require for structure, not really used in my code.
    public Object getItem(int position) {
        return null;
    }

    // Require for structure, not really used in my code. Can
    // be used to get the id of an item in the adapter for
    // manual control.
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ModButton btn;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            btn = new ModButton(mContext);
            btn.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT, 150));
        } else {
            btn = (ModButton) convertView;
        }

        btn.setText(btnNames[position]);
        // btnNames is an array of strings
        btn.setTextColor(Color.WHITE);
        btn.setBackgroundResource(bgResource);
        btn.setId(position);
        btn.setOnClickListener(clickListener);

        return btn;
    }

    private View.OnClickListener getMenuBtnListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String btnName = (String)((Button) v).getText();
                verifyMenuButtonCall(btnName);
            }

            private void verifyMenuButtonCall(String btnName) {

                if (btnName.equals(UsableConstants.LOAD_CARD)) {
                    mainMenuFunctions.loadCard();
                } else if (btnName.equals(UsableConstants.CHECK_BAL_INFO)) {
                    mainMenuFunctions.checkBalInfoInDialog();
                } else if (btnName.equals(UsableConstants.PROMOS)) {
                    mainMenuFunctions.showPromoCatagories();
                } else if (btnName.equals(UsableConstants.BOOKMARKS)) {
                    mainMenuFunctions.showBookmarks();
                } else if (btnName.equals(UsableConstants.SHARE_LOAD)) {
                    mainMenuFunctions.shareLoadInDialog();
                } else if (btnName.equals(UsableConstants.TRANSACTIONS)) {
                    mainMenuFunctions.showTransactions();
                }


            }
        };
    }

}
