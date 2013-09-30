/**
 * 
 */
package com.arkami.myidkey.database.tables;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author sbahdikyan
 * 
 */
public class FileType extends GenericDataObject implements Parcelable {

	public static final String NAME = "name";
	public static final String TABLE_NAME = "fileType";
	public static final String[] COLUMNS = { ID, NAME };

	public static final String CREATE = "CREATE TABLE " + FileType.TABLE_NAME
			+ "(" + FileType.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ FileType.NAME + " TEXT NOT NULL) ";

	private String name;

	/**
	 * Default.
	 */
	public FileType() {
	}

	/**
	 * @param in
	 */
	public FileType(Parcel in) {
		this.setCreateDate(in.readLong());
		this.setModifyDate(in.readLong());
		this.setId(in.readLong());
		this.setName(in.readString());
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.getCreateDate());
		dest.writeLong(this.getModifyDate());
		dest.writeLong(this.getId());
		dest.writeString(this.getName());
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}
