/**
 * 
 */
package com.arkami.myidkey.activity.customuicomponents.predifined;

import com.arkami.myidkey.activity.customuicomponents.FieldNamesEnum;

/**
 * @author sbahdikyan
 * 
 */
public class Note extends PredifinedCard {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.arkami.myidkey.activity.customuicomponents.predifined.PredifinedCard
	 * #setFields()
	 */
	@Override
	void setFields() {
		super.fields = new FieldNamesEnum[1];
		fields[0] = FieldNamesEnum.NOTE;

	}

}
