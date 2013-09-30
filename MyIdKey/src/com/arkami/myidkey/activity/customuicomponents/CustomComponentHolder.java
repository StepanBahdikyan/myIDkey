/**
 *
 */
package com.arkami.myidkey.activity.customuicomponents;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.widget.LinearLayout;

import com.arkami.myidkey.activity.customuicomponents.predifined.PredifinedCard;
import com.arkami.myidkey.database.tables.Value;

/**
 * @author sbahdikyan
 */
public class CustomComponentHolder extends LinearLayout {

    private List<CustomViewComponent> customComponents;

    /**
     * @param context
     */
    public CustomComponentHolder(Context context) {
        super(context);
        // CustomComponentHolder.inflate(context,
        // R.layout.custom_component_free_text, null);
        this.setOrientation(LinearLayout.VERTICAL);
        customComponents = new ArrayList<CustomViewComponent>();
    }

    public void addCustomComponent(CustomViewComponent view) {
        this.addView(view);
        customComponents.add(view);
    }

    /**
     * @return the customComponents
     */
    public List<CustomViewComponent> getCustomComponents() {
        return customComponents;
    }

    /**
     * @return list of value ids in holder
     */
    public long[] getValueIds() {
        long[] values = new long[customComponents.size()];
        int i = 0;
        for (CustomViewComponent component : customComponents) {
            values[i++] = component.getValue().getId();
        }
        return values;
    }

    /**
     * @return list of value in holder
     */
    public Value[] getValues() {
        Value[] values = new Value[customComponents.size()];
        int i = 0;
        for (CustomViewComponent component : customComponents) {
            values[i++] = component.getValue();
        }
        return values;
    }

    /**
     * @param card predifined card with all values in it
     * @throws IllegalAccessException
     */
    public void addValues(PredifinedCard card) throws IllegalAccessException {
        for (Value value : card.createValues(this.getContext())) {
            this.addCustomComponent(new CustomViewComponent(this.getContext(),
                    value));
        }
    }

    /**
     * deletes all custom components
     */
    public void clear() {
        this.customComponents = new ArrayList<CustomViewComponent>();
    }

//    /**
//     * @param values values to be set in components
//     */
//
//    public void setValues(Value[] values) {
//        if ((values == null)
//                || (this.customComponents == null)
//                || (values.length != customComponents.size())) {
//            return;
//        } else {
//            for (int i = 0; i < this.customComponents.size(); i++) {
//                       customComponents.get(i).setValue(values[i]);
//            }
//        }
//    }

    /**
     *
     * @param components    new custom components.
     */

    public void setCustomComponents(List<CustomViewComponent> components){
        this.customComponents = components;
    }
}
