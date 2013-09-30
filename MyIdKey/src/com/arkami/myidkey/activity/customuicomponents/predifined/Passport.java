/**
 * 
 */
package com.arkami.myidkey.activity.customuicomponents.predifined;

import com.arkami.myidkey.activity.customuicomponents.FieldNamesEnum;

/**
 * @author sbahdikyan
 * 
 */
public class Passport extends PredifinedCard {

	@Override
	void setFields() {
		fields = new FieldNamesEnum[11];
		fields[0] = FieldNamesEnum.TYPE_NO_VALUES;
		fields[1] = FieldNamesEnum.NAME;
		fields[2] = FieldNamesEnum.COUNTRY;
		fields[3] = FieldNamesEnum.NUMBER;
		fields[4] = FieldNamesEnum.SEX;
		fields[5] = FieldNamesEnum.NATIONALITY;
		fields[6] = FieldNamesEnum.ISSUING_AUTHORITY;
		fields[7] = FieldNamesEnum.DATE_OF_BIRTH;
		fields[8] = FieldNamesEnum.ISSUED_DATE;
		fields[9] = FieldNamesEnum.EXPIRATION_DATE;
		fields[10] = FieldNamesEnum.NOTE;
	}
}