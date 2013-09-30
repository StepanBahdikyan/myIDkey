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
public class Field extends GenericDataObject implements Parcelable {

	public static final String TABLE_NAME = "field";
	public static final String FIELD_TYPE_ID = "fieldTypeId";
	public static final String NAME = "name";
	public static final String POSSIBLE_VALUES = "possibleValues";
	public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
			+ " ( " +
			//
			GenericDataObject.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			//
			+ Field.NAME + " TEXT NOT NULL, "
			//
			+ GenericDataObject.CREATE_DATE + " INTEGER, "
			//
			+ Field.POSSIBLE_VALUES + " TEXT, "
			//
			+ GenericDataObject.MODIFY_DATE + " INTEGER, "
			//
			+ Field.FIELD_TYPE_ID + " INTEGER) ";
	private String name;
	private long fieldTypeId;
	private String possibleValues;

	/**
	 * default
	 */
	public Field() {
	}

	/**
	 * @param in
	 */
	public Field(Parcel in) {
		this.setId(in.readLong());
		this.setFieldTypeId(in.readLong());
		this.setName(in.readString());
		this.setCreateDate(in.readLong());
		this.setModifyDate(in.readLong());
		this.setPossibleValues(in.readString());
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeLong(this.getId());
		out.writeLong(this.getFieldTypeId());
		out.writeString(this.getName());
		out.writeLong(this.getCreateDate());
		out.writeLong(this.getModifyDate());
		out.writeString(this.getPossibleValues());
	}

	/**
	 * creates object from a cursor.
	 * 
	 * @param cursor
	 * @return field object with data from the cursor.
	 */
	public static Field create(Cursor cursor) {
		int idColon = cursor.getColumnIndex(ID);
		int nameColon = cursor.getColumnIndex(NAME);
		int createDateColumn = cursor.getColumnIndex(CREATE_DATE);
		int modifyDateColumn = cursor.getColumnIndex(MODIFY_DATE);
		int fieldTypeIdColumn = cursor.getColumnIndex(FIELD_TYPE_ID);
		int possibleValuesColumn = cursor.getColumnIndex(POSSIBLE_VALUES);
		Field field = new Field();

		if ((possibleValuesColumn > -1)
				&& (cursor.getColumnCount() > possibleValuesColumn)) {
			field.setPossibleValues((cursor.getString(possibleValuesColumn)));
		}

		if ((idColon > -1) && (cursor.getColumnCount() > idColon)) {
			field.setId((cursor.getLong(idColon)));
		}

		if ((fieldTypeIdColumn > -1)
				&& (cursor.getColumnCount() > fieldTypeIdColumn)) {
			field.setFieldTypeId(cursor.getLong(fieldTypeIdColumn));
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
	public static List<Field> createFields(Cursor cursor) {
		List<Field> fields = new ArrayList<Field>();
		cursor.moveToFirst();
		do {
			Field field = Field.create(cursor);
			fields.add(field);
		} while (cursor.moveToNext());
		return fields;
	}

	/**
	 * @return
	 */
	public ContentValues getValues() {
		ContentValues contentValues = new ContentValues();
		if (fieldTypeId > 0) {
			contentValues.put(FIELD_TYPE_ID, this.getFieldTypeId());
		}
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
		if (this.getPossibleValues() != null) {
			contentValues.put(POSSIBLE_VALUES, this.getPossibleValues());
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

	/**
	 * @return the fieldTypeId
	 */
	public long getFieldTypeId() {
		return fieldTypeId;
	}

	/**
	 * @param fieldTypeId
	 *            the fieldTypeId to set
	 */
	public void setFieldTypeId(long fieldTypeId) {
		this.fieldTypeId = fieldTypeId;
	}

	/**
	 * @return the possibleValues
	 */
	public String getPossibleValues() {
		return possibleValues;
	}

	/**
	 * @param possibleValues
	 *            the possibleValues to set
	 */
	public void setPossibleValues(String possibleValues) {
		this.possibleValues = possibleValues;
	}

}
