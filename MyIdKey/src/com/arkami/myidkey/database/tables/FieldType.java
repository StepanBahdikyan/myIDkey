/**
 * 
 */
package com.arkami.myidkey.database.tables;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author sbahdikyan
 * 
 */
public class FieldType extends GenericDataObject implements Parcelable {

	public static final String TABLE_NAME = "fieldType";
	public static final String NAME = "name";
	public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
			+ " ( " +
			//
			GenericDataObject.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			//
			+ FieldType.NAME + " TEXT NOT NULL, "
			//
			+ GenericDataObject.CREATE_DATE + " INTEGER, "
			//
			+ GenericDataObject.MODIFY_DATE + " INTEGER) ";
	private String name;

	/**
	 * default
	 */
	public FieldType() {
	}

	/**
	 * @param in
	 */
	public FieldType(Parcel in) {
		this.setId(in.readLong());
		this.setName(in.readString());
		this.setCreateDate(in.readLong());
		this.setModifyDate(in.readLong());
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeLong(this.getId());
		out.writeString(this.getName());
		out.writeLong(this.getCreateDate());
		out.writeLong(this.getModifyDate());
	}

	/**
	 * creates object from a cursor.
	 * 
	 * @param cursor
	 * @return field object with data from the cursor.
	 */
	public static FieldType create(Cursor cursor) {
		int idColon = cursor.getColumnIndex(ID);
		int nameColon = cursor.getColumnIndex(NAME);
		int createDateColumn = cursor.getColumnIndex(CREATE_DATE);
		int modifyDateColumn = cursor.getColumnIndex(MODIFY_DATE);
		FieldType field = new FieldType();

		if ((idColon > -1) && (cursor.getColumnCount() > idColon)) {
			field.setId((cursor.getLong(idColon)));
		}

		if ((nameColon > -1) && (cursor.getColumnCount() > nameColon)) {
			field.setName(cursor.getString(nameColon));
		}
		if ((createDateColumn > -1)
				&& (cursor.getColumnCount() > createDateColumn)) {
			field.setCreateDate(cursor.getLong(createDateColumn));
		}
		if ((modifyDateColumn > -1)
				&& (cursor.getColumnCount() > modifyDateColumn)) {
			field.setModifyDate(cursor.getLong(modifyDateColumn));
		}
		if (cursor.isLast()) {
			cursor.close();
		}
		return field;
	}

	/**
	 * @param cursor
	 * @return
	 */
	public static List<FieldType> createFieldTypes(Cursor cursor) {
		List<FieldType> fields = new ArrayList<FieldType>();
		cursor.moveToFirst();
		do {
			FieldType field = FieldType.create(cursor);
			fields.add(field);
		} while (cursor.moveToNext());
		return fields;
	}

	/**
	 * @return
	 */
	public ContentValues getValues() {
		ContentValues contentValues = new ContentValues();
		if (this.getCreateDate() != null) {
			contentValues.put(CREATE_DATE, this.getCreateDate());
		}
		if (this.getModifyDate() != null) {
			contentValues.put(MODIFY_DATE, this.getModifyDate());
		}
		if (this.getName() != null) {
			contentValues.put(NAME, this.getName());
		}
		if (this.getId() != null) {
			contentValues.put(ID, this.getId());
		}
		if (this.name != null) {
			contentValues.put(NAME, this.name);
		}
		return contentValues;
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