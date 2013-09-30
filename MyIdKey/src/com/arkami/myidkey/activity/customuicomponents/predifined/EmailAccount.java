/**
 * 
 */
package com.arkami.myidkey.activity.customuicomponents.predifined;

import com.arkami.myidkey.activity.customuicomponents.FieldNamesEnum;

/**
 * @author sbahdikyan
 * 
 */
public class EmailAccount extends PredifinedCard {

	@Override
	void setFields() {
		fields = new FieldNamesEnum[8];
		fields[0] = FieldNamesEnum.USER_NAME;
		fields[1] = FieldNamesEnum.PASSWORD;
		fields[2] = FieldNamesEnum.SERVER;
		fields[3] = FieldNamesEnum.PORT;
		fields[4] = FieldNamesEnum.TYPE_NO_VALUES;
		fields[5] = FieldNamesEnum.SMTP_SERVER;
		fields[6] = FieldNamesEnum.SMTP_PORT;
		fields[7] = FieldNamesEnum.NOTE;
	}
}