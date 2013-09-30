/**
 * 
 */
package com.arkami.myidkey.activity.customuicomponents.predifined;

import com.arkami.myidkey.activity.customuicomponents.FieldNamesEnum;

/**
 * @author sbahdikyan
 * 
 */
public class Server extends PredifinedCard {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.arkami.myidkey.activity.customuicomponents.predifined.PredifinedCard
	 * #setFields()
	 */
	@Override
	void setFields() {
		super.fields = new FieldNamesEnum[4];
		super.fields[0] = FieldNamesEnum.HOSTNAME;
		super.fields[1] = FieldNamesEnum.USER_NAME;
		super.fields[2] = FieldNamesEnum.PASSWORD;
		super.fields[3] = FieldNamesEnum.NOTE;
	}
}
