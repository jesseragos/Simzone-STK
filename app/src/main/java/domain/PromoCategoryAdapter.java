package domain;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jkiel.simzonestk.PromoList;
import com.jkiel.simzonestk.R;

import res.Carrier;
import res.PromoCategory;
import res.UsableConstants;
import controller.UsableFunctions;

/**
 * Created by Ragos Bros on 2/9/2017.
 */
public class PromoCategoryAdapter extends BaseAdapter {

    private final View.OnClickListener clickListener;
    private final Carrier deviceCarrier;
    PromoCategory[] promoCategories = PromoCategory.values();
    Context context;
    private static LayoutInflater inflater = null;

    public PromoCategoryAdapter(Context context, Carrier deviceCarrier) {
        this.context = context;
        this.deviceCarrier = deviceCarrier;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        clickListener = getPromoCatBtnListener();
    }

    @Override
    public int getCount() {
        return promoCategories.length;
    }

    @Override
    public Object getItem(int position) {
        return promoCategories[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // See if the view needs to be inflated
        View promoCatView = convertView;
        if (promoCatView == null) {
            promoCatView = inflater.inflate(R.layout.frag_promo_cat, null);
        }
        // Extract the desired views
        ImageButton promoCatIcon = (ImageButton) promoCatView.findViewById(R.id.promo_cat_icon);
        TextView promoCatText = (TextView) promoCatView.findViewById(R.id.promo_cat_name);

        // Get the promo cat
        PromoCategory promoCategory = promoCategories[position];

        String promoCatName = promoCategory.getName();

        // Display the promo cat's properties
        promoCatIcon.setTag(promoCatName);
        promoCatIcon.setOnClickListener(clickListener);
        promoCatIcon.setBackgroundResource(promoCategory.getIconResource());

        promoCatText.setTypeface(UsableFunctions.getLabelTypeFace(context));
        promoCatText.setText(promoCatName);

        return promoCatView;
    }

    private View.OnClickListener getPromoCatBtnListener() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String promoCatName = (String)((ImageButton) v).getTag();
                verifyPromoCatCall(promoCatName);
            }

            private void verifyPromoCatCall(String promoCatName) {

                Intent intent = new Intent(context, PromoList.class);
                intent.putExtra(UsableConstants.SELECTEDPROMOCAT_ID, promoCatName);
                intent.putExtra(UsableConstants.DEVCAR_ID, deviceCarrier);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        };
    }

}
