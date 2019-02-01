package domain;

import android.content.Context;
import android.widget.Button;

import controller.UsableFunctions;

/**
 * Created by Ragos Bros on 1/23/2017.
 */
public class ModButton extends Button {

    public ModButton(Context context) {
        super(context);

        setTypeface(UsableFunctions.getButtonTypeFace(context));
    }

}
