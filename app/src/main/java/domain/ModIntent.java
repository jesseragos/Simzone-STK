package domain;

import android.content.Context;
import android.content.Intent;

import java.util.Hashtable;

/**
 * Created by Ragos Bros on 2/10/2017.
 */
public class ModIntent extends Intent {

    private final Hashtable objects;

    public ModIntent(Context context, Class appClass) {
        super(context, appClass);
        this.objects = new Hashtable();
    }

    public Intent putExtra(String key, Object object) {
        objects.put(key, object);
        return this;
    }

    public Object getObjectExtra(String key) {
        return objects.get(key);
    }

}
