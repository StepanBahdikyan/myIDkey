/**
 * 
 */
package com.arkami.myidkey.activity.customuicomponents;

/**
 * @author sbahdikyan
 * 
 */
public class FieldType<dataType> {
	private FieldTypeEnum fieldtype;
	private String title;
	private dataType data;

	public FieldTypeEnum getFieldType() {
		return this.fieldtype;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public dataType getData() {
		return this.data;
	}

	public void setData(dataType data) {
		this.data = data;
	}

}
