/**
 * 
 */
package com.arkami.myidkey.activity.customuicomponents.predifined;

import com.arkami.myidkey.activity.customuicomponents.FieldNamesEnum;

/**
 * @author sbahdikyan
 * 
 */
public class SoftwareLicense extends PredifinedCard {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.arkami.myidkey.activity.customuicomponents.predifined.PredifinedCard
	 * #setFields()
	 */
	@Override
	void setFields() {
		super.fields = new FieldNamesEnum[11];
		super.fields[0] = FieldNamesEnum.LICENSE_KEY;
		super.fields[1] = FieldNamesEnum.VERSION;
		super.fields[2] = FieldNamesEnum.PUBLISHER;
		super.fields[3] = FieldNamesEnum.SUPPORT_EMAIL;
		super.fields[4] = FieldNamesEnum.WEBSITE;
		super.fields[5] = FieldNamesEnum.PRICE;
		super.fields[6] = FieldNamesEnum.PURCHASE_DATE;
		super.fields[7] = FieldNamesEnum.ORDER_NUMBER;
		super.fields[8] = FieldNamesEnum.NUMBER_OF_LICENSES;
		super.fields[9] = FieldNamesEnum.ORDER_TOTAL;
		super.fields[10] = FieldNamesEnum.NOTE;

	}

}
