/**
 * 
 */
package com.arkami.myidkey.activity.customuicomponents.predifined;

import com.arkami.myidkey.activity.customuicomponents.FieldNamesEnum;

/**
 * @author sbahdikyan
 * 
 */
public class Database extends PredifinedCard {

	@Override
	void setFields() {
		fields = new FieldNamesEnum[8];
		fields[0] = FieldNamesEnum.HOSTNAME;
		fields[1] = FieldNamesEnum.PORT;
		fields[2] = FieldNamesEnum.DATABASE;
		fields[3] = FieldNamesEnum.USER_NAME;
		fields[4] = FieldNamesEnum.PASSWORD;
		fields[5] = FieldNamesEnum.SID;
		fields[6] = FieldNamesEnum.ALIAS;
		fields[7] = FieldNamesEnum.NOTE;
	}
}