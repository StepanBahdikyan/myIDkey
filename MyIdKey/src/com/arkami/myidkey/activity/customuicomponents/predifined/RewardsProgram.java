/**
 * 
 */
package com.arkami.myidkey.activity.customuicomponents.predifined;

import com.arkami.myidkey.activity.customuicomponents.FieldNamesEnum;

/**
 * @author sbahdikyan
 * 
 */
public class RewardsProgram extends PredifinedCard {

	@Override
	void setFields() {
		fields= new FieldNamesEnum[9];
		fields[0] = FieldNamesEnum.COMPANY_NAME;
		fields[1] = FieldNamesEnum.MEMBER_NAME;
		fields[2] = FieldNamesEnum.MEMBERSHIP_NUMBER;
		fields[3] = FieldNamesEnum.PIN;
		fields[4] = FieldNamesEnum.MEMBERSHIP_NUMBER;
		fields[5] = FieldNamesEnum.MEMBER_SINCE;
		fields[6] = FieldNamesEnum.COSTOMER_SERVICE_PHONE_NUMBER;
		fields[7] = FieldNamesEnum.RESERVATIONS_PHONE_NUMBER;
		fields[8] = FieldNamesEnum.WEBSITE;
	}
}