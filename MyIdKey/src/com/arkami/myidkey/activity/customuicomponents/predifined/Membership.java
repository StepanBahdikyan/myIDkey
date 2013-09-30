/**
 * 
 */
package com.arkami.myidkey.activity.customuicomponents.predifined;

import com.arkami.myidkey.activity.customuicomponents.FieldNamesEnum;

/**
 * @author sbahdikyan
 * 
 */
public class Membership extends PredifinedCard {

	@Override
	void setFields() {
		fields = new FieldNamesEnum[9];
		fields[0] = FieldNamesEnum.ORGANIZATION;
		fields[1] = FieldNamesEnum.MEMBERSHIP_NUMBER;
		fields[2] = FieldNamesEnum.MEMBER_NAME;
		fields[3] = FieldNamesEnum.START_DATE;
		fields[4] = FieldNamesEnum.EXPIRATION_DATE;
		fields[5] = FieldNamesEnum.WEBSITE;
		fields[6] = FieldNamesEnum.TELEPHONE;
		fields[7] = FieldNamesEnum.PASSWORD;
		fields[8] = FieldNamesEnum.NOTE;
	}
}