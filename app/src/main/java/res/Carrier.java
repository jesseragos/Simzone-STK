package res;

import android.graphics.Color;

import com.jkiel.simzonestk.R;

/**
 * Created by Ragos Bros on 1/24/2017.
 */
public enum Carrier {

    Globe ("Globe", R.drawable.logo_globe, Color.parseColor("#ff2c3b99"), R.drawable.carrierbtn_globe, "http://www.globe.com.ph/prepaid", "services_globe"),
    Smart ("Smart", R.drawable.logo_smart, Color.parseColor("#ff009540"), R.drawable.carrierbtn_smart, "http://www1.smart.com.ph/prepaid/offers/", "services_smart"),
    Sun ("Sun", R.drawable.logo_smart, Color.parseColor("#ffffcb13"), R.drawable.carrierbtn_sun, "http://suncellular.com.ph/prepaid/loads-and-cards", "services_sun");

//    TM ("TM", R.drawable.logo_globe, Color.parseColor("#ffe80109"), R.drawable.carrierbtn_tm, "services_tm"),
//    TalkNText ("Talk n Text", R.drawable.logo_talkntext, Color.parseColor("#ffff7700"), R.drawable.carrierbtn_talkntext, "services_tnt");


    private final String name;
    private final int logoResource;
    private final int bgColorValue;
    private final int buttonXmlResource;
    private final String webSite;
    private final String servicesDir;

    private Carrier(String name, int logoResource, int bgColorValue, int buttonXmlResource, String webSite, String servicesDir) {
        this.name = name;
        this.logoResource = logoResource;
        this.bgColorValue = bgColorValue;
        this.buttonXmlResource = buttonXmlResource;
        this.webSite = webSite;
        this.servicesDir = servicesDir;
    }

    public String getName() {
        return name;
    }

    public int getLogoResource() {
        return logoResource;
    }

    public int getBgColorValue() {
        return bgColorValue;
    }

    public int getButtonXmlResource() {
        return buttonXmlResource;
    }

    public String getWebSite() {
        return webSite;
    }

    public String getServicesDir() {
        return servicesDir;
    }

}
