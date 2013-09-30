/**
 * 
 */
package com.arkami.myidkey.activity.customuicomponents.predifined;

import com.arkami.myidkey.activity.customuicomponents.FieldNamesEnum;

/**
 * @author sbahdikyan
 * 
 */
public class Insurance extends PredifinedCard {

	@Override
	void setFields() {
		fields = new FieldNamesEnum[6];
		fields[0] = FieldNamesEnum.COMPANY;
		fields[1] = FieldNamesEnum.EXPIRATION;
		fields[2] = FieldNamesEnum.AGENT_NAME;
		fields[3] = FieldNamesEnum.AGENT_PHONE;
		fields[4] = FieldNamesEnum.URL;
		fields[5] = FieldNamesEnum.NOTE;
	}
}