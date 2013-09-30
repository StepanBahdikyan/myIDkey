/**
 * 
 */
package com.arkami.myidkey.activity.customuicomponents.predifined;

import com.arkami.myidkey.activity.customuicomponents.FieldNamesEnum;

/**
 * @author sbahdikyan
 * 
 */
public class Clothing extends PredifinedCard {

	@Override
	void setFields() {
		fields = new FieldNamesEnum[11];
		fields[0] = FieldNamesEnum.SIZES_FOR;
		fields[1] = FieldNamesEnum.BRAND;
		fields[2] = FieldNamesEnum.SUIT;
		fields[3] = FieldNamesEnum.WAIST;
		fields[4] = FieldNamesEnum.BUST;
		fields[5] = FieldNamesEnum.INSEAM;
		fields[6] = FieldNamesEnum.SHOE;
		fields[7] = FieldNamesEnum.PANTS;
		fields[8] = FieldNamesEnum.SKIRT;
		fields[9] = FieldNamesEnum.NECK;
		fields[10] = FieldNamesEnum.GLOVE;

	}

}