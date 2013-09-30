/**
 * 
 */
package com.arkami.myidkey.activity.customuicomponents.predifined;

import com.arkami.myidkey.activity.customuicomponents.FieldNamesEnum;

/**
 * @author sbahdikyan
 * 
 */
public class WiFi extends PredifinedCard {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.arkami.myidkey.activity.customuicomponents.predifined.PredifinedCard
	 * #setFields()
	 */
	@Override
	void setFields() {
		super.fields = new FieldNamesEnum[12];
		super.fields[0] = FieldNamesEnum.SSID;
		super.fields[1] = FieldNamesEnum.PASSWORD;
		super.fields[2] = FieldNamesEnum.CONNECTION_TYPE;
		super.fields[3] = FieldNamesEnum.CONNECTION_MODE;
		super.fields[4] = FieldNamesEnum.AUTHENTICATION;
		super.fields[5] = FieldNamesEnum.ENCRIPTION;
		super.fields[6] = FieldNamesEnum.USE_802_1X;
		super.fields[7] = FieldNamesEnum.FIPS_MODE;
		super.fields[8] = FieldNamesEnum.KEY_TYPE;
		super.fields[9] = FieldNamesEnum.PROTECTED;
		super.fields[10] = FieldNamesEnum.KEY_INDEX;
		super.fields[11] = FieldNamesEnum.NOTE;
	}

}
