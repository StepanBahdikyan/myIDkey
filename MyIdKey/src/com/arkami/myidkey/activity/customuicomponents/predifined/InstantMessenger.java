/**
 * 
 */
package com.arkami.myidkey.activity.customuicomponents.predifined;

import com.arkami.myidkey.activity.customuicomponents.FieldNamesEnum;

/**
 * @author sbahdikyan
 * 
 */
public class InstantMessenger extends PredifinedCard {

	@Override
	void setFields() {
		fields = new FieldNamesEnum[6];
		fields[0] = FieldNamesEnum.TYPE_NO_VALUES;
		fields[1] = FieldNamesEnum.USER_NAME;
		fields[2] = FieldNamesEnum.PASSWORD;
		fields[3] = FieldNamesEnum.SERVER;
		fields[4] = FieldNamesEnum.PORT;
		fields[5] = FieldNamesEnum.NOTE;
	}
}