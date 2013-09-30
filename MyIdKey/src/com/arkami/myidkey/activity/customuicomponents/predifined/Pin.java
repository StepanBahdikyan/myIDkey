/**
 * 
 */
package com.arkami.myidkey.activity.customuicomponents.predifined;

import java.util.Date;

import android.content.Context;

import com.arkami.myidkey.activity.customuicomponents.CustomComponentHolder;
import com.arkami.myidkey.activity.customuicomponents.CustomViewComponent;
import com.arkami.myidkey.activity.customuicomponents.FieldNamesEnum;
import com.arkami.myidkey.database.datasources.FieldDataSource;
import com.arkami.myidkey.database.tables.Value;

/**
 * @author sbahdikyan
 * 
 */
public class Pin {
	private CustomComponentHolder componentHolder;

	public Pin(Context context, Long keyCardId) throws IllegalAccessException {
		this.componentHolder = new CustomComponentHolder(context);
		FieldDataSource fieldDataSource = new FieldDataSource(context);
		long fieldId = fieldDataSource.get(FieldNamesEnum.NOTE.getName()).getId();
		Value value = new Value();
		value.setCreateDate(new Date().getTime());
		value.setModifyDate(value.getCreateDate());
		value.setData(String.valueOf(""));
		value.setFieldId(fieldId);
		value.setKeyCardId(keyCardId);
		addView(new CustomViewComponent(context, value));
	}

	/**
	 * @param component
	 *            to be added;
	 */
	private void addView(CustomViewComponent component) {
		this.componentHolder.addCustomComponent(component);
	}

	/**
	 * @return the componentHolder
	 */
	public CustomComponentHolder getComponentHolder() {
		return this.componentHolder;
	}
}
