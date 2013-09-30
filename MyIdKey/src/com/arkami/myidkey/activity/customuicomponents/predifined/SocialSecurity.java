/**
 * 
 */
package com.arkami.myidkey.activity.customuicomponents.predifined;

import com.arkami.myidkey.activity.customuicomponents.FieldNamesEnum;

/**
 * @author sbahdikyan
 * 
 */
public class SocialSecurity extends PredifinedCard {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.arkami.myidkey.activity.customuicomponents.predifined.PredifinedCard
	 * #setFields()
	 */
	@Override
	void setFields() {
		super.fields = new FieldNamesEnum[3];
		super.fields[0] = FieldNamesEnum.NAME;
		super.fields[1] = FieldNamesEnum.NUMBER;
		super.fields[2] = FieldNamesEnum.NOTE;
	}

}
