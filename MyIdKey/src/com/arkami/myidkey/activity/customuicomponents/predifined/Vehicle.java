/**
 * 
 */
package com.arkami.myidkey.activity.customuicomponents.predifined;

import com.arkami.myidkey.activity.customuicomponents.FieldNamesEnum;

/**
 * @author sbahdikyan
 *
 */
public class Vehicle extends PredifinedCard {

	/* (non-Javadoc)
	 * @see com.arkami.myidkey.activity.customuicomponents.predifined.PredifinedCard#setFields()
	 */
	@Override
	void setFields() {
		super.fields = new FieldNamesEnum[6];
		super.fields[0] = FieldNamesEnum.PLATE_NUMBER;
		super.fields[1] = FieldNamesEnum.STATE;
		super.fields[2] = FieldNamesEnum.MAKE;
		super.fields[3] = FieldNamesEnum.MODEL;
		super.fields[4] = FieldNamesEnum.YEAR;
		super.fields[5] = FieldNamesEnum.VIN;
	}

}
