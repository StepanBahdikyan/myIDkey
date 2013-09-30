/**
 *
 */
package com.arkami.myidkey.activity.customuicomponents.predifined;

import java.util.Date;

import android.content.Context;

import com.arkami.myidkey.activity.customuicomponents.FieldNamesEnum;
import com.arkami.myidkey.database.datasources.FieldDataSource;
import com.arkami.myidkey.database.tables.Value;

/**
 * @author sbahdikyan
 */
public abstract class PredifinedCard {

    private static Value[] values;
    protected FieldNamesEnum[] fields;

    /**
     * @param context
     * @return
     */
    public Value[] createValues(Context context) {
        setFields();
        FieldDataSource fieldDataSource = new FieldDataSource(context);
        long date = new Date().getTime();
        int i = 0;
        values = new Value[fields.length];
        try {
            for (FieldNamesEnum field : fields) {
                long id = fieldDataSource.get(field.getName()).getId();
                Value value = createValue(id, date);
                values[i++] = value;
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return values;
    }

    abstract void setFields();

    /**
     * @param fieldId
     * @param date
     * @return
     * @throws IllegalAccessException
     */
    private static Value createValue(long fieldId, long date) {
        Value value = new Value();
        value.setCreateDate(date);
        value.setModifyDate(date);
        value.setData(String.valueOf(""));
        value.setFieldId(fieldId);
        return value;
    }
}
