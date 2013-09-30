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
public class CreditCard {
	private CustomComponentHolder componentHolder;
	private FieldDataSource fieldDataSource;

	
	
	
	public CreditCard(Context context, Long keyCardId)
			throws IllegalAccessException {
		this.componentHolder = new CustomComponentHolder(context);
		long createDate = new Date().getTime();

		this.fieldDataSource = new FieldDataSource(context);
		
		Value nameOnCardValue = createValue(FieldNamesEnum.NAME_ON_CARD,
				keyCardId, createDate);
		addView(new CustomViewComponent(context, nameOnCardValue));

		Value typeValue = createValue(FieldNamesEnum.TYPE, keyCardId,
				createDate);
		addView(new CustomViewComponent(context, typeValue));

		Value numberValue = createValue(FieldNamesEnum.NUMBER, keyCardId,
				createDate);
		addView(new CustomViewComponent(context, numberValue));

		Value securityCodeValue = createValue(FieldNamesEnum.SECURITY_CODE,
				keyCardId, createDate);
		addView(new CustomViewComponent(context, securityCodeValue));

		Value startDateValue = createValue(FieldNamesEnum.START_DATE,
				keyCardId, createDate);
		addView(new CustomViewComponent(context, startDateValue));

		Value expirationDateValue = createValue(FieldNamesEnum.EXPIRATION_DATE,
				keyCardId, createDate);
		addView(new CustomViewComponent(context, expirationDateValue));

		Value noteValue = createValue(FieldNamesEnum.NOTE, keyCardId,
				createDate);
		addView(new CustomViewComponent(context, noteValue));

		// Value photoValue = createValue(FieldNamesEnum.PHOTO_ATTACHMENTS,
		// keyCardId, createDate);
		// addView(new CustomViewComponent(context, photoValue));
		//
		// Value audioValue = createValue(FieldNamesEnum.AUDIO_ATTACHMENTS,
		// keyCardId, createDate);
		// addView(new CustomViewComponent(context, audioValue));

		// XXX add photos
		// XXX add audios
		// XXX ask about autofill values

	}

	/**
	 * @param fieldNamesEnum
	 * @return
	 * @throws IllegalAccessException
	 */
	private Value createValue(FieldNamesEnum fieldNamesEnum, long id, long date)
			throws IllegalAccessException {
		Value value = new Value();
		value.setCreateDate(date);
		value.setModifyDate(date);
		value.setData(String.valueOf(""));
		Long valueID = fieldDataSource.get(fieldNamesEnum.getName()).getId();
		value.setFieldId(valueID);
		value.setKeyCardId(id);

		return value;
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
