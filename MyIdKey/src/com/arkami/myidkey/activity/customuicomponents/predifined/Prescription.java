/**
 * 
 */
package com.arkami.myidkey.activity.customuicomponents.predifined;

import com.arkami.myidkey.activity.customuicomponents.FieldNamesEnum;

/**
 * @author sbahdikyan
 * 
 */
public class Prescription extends PredifinedCard {

	@Override
	void setFields() {
		fields = new FieldNamesEnum[13];
		fields[0] = FieldNamesEnum.DRUG_NAME;
		fields[1] = FieldNamesEnum.AMOUNT;
		fields[2] = FieldNamesEnum.GENERIC_FOR;
		fields[3] = FieldNamesEnum.WHEN_TO_TAKE;
		fields[4] = FieldNamesEnum.BRAND;
		fields[5] = FieldNamesEnum.PURCHASE_DATE;
		fields[6] = FieldNamesEnum.Pharmacy;
		fields[7] = FieldNamesEnum.DOCTOR;
		fields[8] = FieldNamesEnum.DOCTOR_PHONE;
		fields[9] = FieldNamesEnum.EXPIRATION;
		fields[10] = FieldNamesEnum.RX_NUMBER;
		fields[11] = FieldNamesEnum.NUMBER_OF_REFILLS;
		fields[12] = FieldNamesEnum.NOTE;
	}
}