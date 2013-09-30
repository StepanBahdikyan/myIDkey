/**
 * 
 */
package com.arkami.myidkey.activity.customuicomponents.predifined;

import com.arkami.myidkey.activity.customuicomponents.FieldNamesEnum;

/**
 * @author sbahdikyan
 * 
 */
public class BankAccount extends PredifinedCard {

	@Override
	void setFields() {
		fields = new FieldNamesEnum[10];
		fields[0] = FieldNamesEnum.BANK_NAME;
		fields[1] = FieldNamesEnum.ACCOUNT_TYPE;
		fields[2] = FieldNamesEnum.ACCOUNT_NUMBER;
		fields[3] = FieldNamesEnum.ROUTING_NUMBER;
		fields[4] = FieldNamesEnum.SWIFT_CODE;
		fields[5] = FieldNamesEnum.IBAN_NUMBER;
		fields[6] = FieldNamesEnum.PIN;
		fields[7] = FieldNamesEnum.BRANCH_ADDRESS;
		fields[8] = FieldNamesEnum.BRANCH_PHONE;
		fields[9] = FieldNamesEnum.NOTE;
	}
}
