/**
 * 
 */
package com.arkami.myidkey.database.tables;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author sbahdikyan
 * 
 */
public class KeyCardType extends GenericDataObject implements Parcelable {

	public static final String NAME = "name";
	public static final String TABLE_NAME = "keyCardType";
	public static final String[] COLUMNS = { ID, NAME };

	public static final String CREATE = "CREATE TABLE "
			+ KeyCardType.TABLE_NAME + "(" + KeyCardType.ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KeyCardType.NAME
			+ " TEXT NOT NULL) ";

	private String name;

	/**
	 * Default.
	 */
	public KeyCardType() {
	}

	/**
	 * @param in
	 */
	public KeyCardType(Parcel in) {
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

	public static KeyCardType create(Cursor cursor) {
		int idColon = cursor.getColumnIndex(ID);
		int nameColon = cursor.getColumnIndex(NAME);
		int createDateColumn = cursor.getColumnIndex(CREATE_DATE);
		int modifyDateColumn = cursor.getColumnIndex(MODIFY_DATE);
		KeyCardType keyCardType = new KeyCardType();

		if ((idColon > -1) && (cursor.getColumnCount() > idColon)) {
			keyCardType.setId((cursor.getLong(idColon)));
		}

		if ((nameColon > -1) && (cursor.getColumnCount() > nameColon)) {
			keyCardType.setName(cursor.getString(nameColon));
		}
		if ((createDateColumn > -1)
				&& (cursor.getColumnCount() > createDateColumn)) {
			keyCardType.setCreateDate(cursor.getLong(createDateColumn));
		}
		if ((modifyDateColumn > -1)
				&& (cursor.getColumnCount() > modifyDateColumn)) {
			keyCardType.setModifyDate(cursor.getLong(modifyDateColumn));
		}
		if (cursor.isLast()) {
			cursor.close();
		}
		return keyCardType;
	}
}
