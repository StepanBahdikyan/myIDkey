/**
 * 
 */
package com.arkami.myidkey.activity.customuicomponents.predifined;

import com.arkami.myidkey.activity.customuicomponents.FieldNamesEnum;

/**
 * @author sbahdikyan
 * 
 */
public class CreditCardNew extends PredifinedCard {

	@Override
	void setFields() {
		fields = new FieldNamesEnum[7];
		fields[0] = FieldNamesEnum.NAME_ON_CARD;
		fields[1] = FieldNamesEnum.TYPE;
		fields[2] = FieldNamesEnum.NUMBER;
		fields[3] = FieldNamesEnum.SECURITY_CODE;
		fields[4] = FieldNamesEnum.START_DATE;
		fields[5] = FieldNamesEnum.EXPIRATION_DATE;
		fields[6] = FieldNamesEnum.NOTE;
	}
}
