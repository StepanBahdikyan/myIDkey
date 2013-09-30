/**
 * 
 */
package com.arkami.myidkey.activity.customuicomponents.predifined;

import com.arkami.myidkey.activity.customuicomponents.FieldNamesEnum;

/**
 * @author sbahdikyan
 * 
 */
public class Site extends PredifinedCard {

	@Override
	void setFields() {
		fields = new FieldNamesEnum[4];
		fields[0] = FieldNamesEnum.URL;
		fields[1] = FieldNamesEnum.USER_NAME;
		fields[2] = FieldNamesEnum.PASSWORD;
		fields[3] = FieldNamesEnum.NOTE;
	}

}
