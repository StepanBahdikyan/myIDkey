/**
 * 
 */
package com.arkami.myidkey.activity.customuicomponents.predifined;

import com.arkami.myidkey.activity.customuicomponents.FieldNamesEnum;


/**
 * @author sbahdikyan
 * 
 */
public class DrivingLicense extends PredifinedCard {

	@Override
	void setFields() {
		fields = new FieldNamesEnum[11];
		fields[0] = FieldNamesEnum.NUMBER;
		fields[1] = FieldNamesEnum.EXPIRATION_DATE;
		fields[2] = FieldNamesEnum.LICENSE_CLASS;
		fields[3] = FieldNamesEnum.NAME;
		fields[4] = FieldNamesEnum.ADDRESS;
		fields[5] = FieldNamesEnum.STATE;
		fields[6] = FieldNamesEnum.COUNTRY;
		fields[7] = FieldNamesEnum.DATE_OF_BIRTH;
		fields[8] = FieldNamesEnum.SEX;
		fields[9] = FieldNamesEnum.HEIGHT;
		fields[10] =FieldNamesEnum.NOTE ;
	}
}
