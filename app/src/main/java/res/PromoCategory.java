package res;

import android.graphics.Color;

import com.jkiel.simzonestk.R;

/**
 * Created by Ragos Bros on 1/24/2017.
 */
public enum PromoCategory {

    Call ("Call", R.drawable.promo_icon_call),
    Text ("Text", R.drawable.promo_icon_text),
    Internet ("Internet", R.drawable.promo_icon_internet),
    Others ("Others", R.drawable.promo_icon_others);

    private final String name;
    private final int iconResource;

    private PromoCategory(String name, int iconResource) {
        this.name = name;
        this.iconResource = iconResource;
    }

    public String getName() {
        return name;
    }

    public int getIconResource() {
        return iconResource;
    }

}
