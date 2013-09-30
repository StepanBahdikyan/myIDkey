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
public class Value extends GenericDataObject implements Parcelable {

	public static final String TABLE_NAME = "valuesTable";
	public static final String KEY_CARD_ID = "keyCardId";
	public static final String FIELD_ID = "fieldId";
	public static final String DATA = "data";
	public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
			+ " ( " +
			//
			GenericDataObject.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			//
			+ Value.KEY_CARD_ID + " INTEGER  NOT NULL, "
			//
			+ Value.DATA + " TEXT, "

			+ Value.FIELD_ID + " INTEGER  NOT NULL, "
			//
			+ GenericDataObject.CREATE_DATE + " INTEGER, "
			//
			+ GenericDataObject.MODIFY_DATE + " INTEGER) ";

	private Long fieldId;
	private Long keyCardId;
	private String data;

	/**
	 * default
	 */
	public Value() {
	}

	/**
	 * @param in
	 */
	public Value(Parcel in) {
		this.setId(in.readLong());
		this.setFieldId(in.readLong());
		this.setKeyCardId(in.readLong());
		this.setCreateDate(in.readLong());
		this.setModifyDate(in.readLong());
		this.setData(in.readString());
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeLong(this.getId());
		out.writeLong(this.getFieldId());
		out.writeLong(this.getKeyCardId());
		out.writeLong(this.getCreateDate());
		out.writeLong(this.getModifyDate());
		out.writeString(this.getData());
	}

	/**
	 * creates object from a cursor.
	 * 
	 * @param cursor
	 * @return field object with data from the cursor.
	 */
	public static Value create(Cursor cursor) {
		int idColon = cursor.getColumnIndex(ID);
		int fieldIdColumn = cursor.getColumnIndex(FIELD_ID);
		int keyCardIdColumn = cursor.getColumnIndex(KEY_CARD_ID);
		int createDateColumn = cursor.getColumnIndex(CREATE_DATE);
		int modifyDateColumn = cursor.getColumnIndex(MODIFY_DATE);
		int dataColumn = cursor.getColumnIndex(DATA);

		Value field = new Value();
		if ((idColon > -1) && (cursor.getColumnCount() > idColon)) {
			field.setId((cursor.getLong(idColon)));
		}

		if ((dataColumn > -1) && (cursor.getColumnCount() > dataColumn)) {
			field.setData((cursor.getString(dataColumn)));
		}

		if ((fieldIdColumn > -1) && (cursor.getColumnCount() > fieldIdColumn)) {
			field.setFieldId(cursor.getLong(fieldIdColumn));
		}
		if ((keyCardIdColumn > -1)
				&& (cursor.getColumnCount() > keyCardIdColumn)) {
			field.setKeyCardId(cursor.getLong(keyCardIdColumn));
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
	public static List<Value> createValues(Cursor cursor) {
		List<Value> fields = new ArrayList<Value>();
		cursor.moveToFirst();
		do {
			Value field = Value.create(cursor);
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
		if (this.getFieldId() != null) {
			contentValues.put(FIELD_ID, this.getFieldId());
		}
		if (this.getId() != null) {
			contentValues.put(ID, this.getId());
		}
		if (this.getKeyCardId() != null) {
			contentValues.put(KEY_CARD_ID, this.getKeyCardId());
		}
		if (this.getData() != null) {
			contentValues.put(DATA, this.getData());
		}
		return contentValues;
	}

	/**
	 * @return the fieldId
	 */
	public Long getFieldId() {
		return fieldId;
	}

	/**
	 * @param fieldId
	 *            the fieldId to set
	 */
	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
	}

	/**
	 * @return the keyCardId
	 */
	public Long getKeyCardId() {
		return keyCardId;
	}

	/**
	 * @param keyCardId
	 *            the keyCardId to set
	 */
	public void setKeyCardId(Long keyCardId) {
		this.keyCardId = keyCardId;
	}

	/**
	 * @return the data
	 */
	public String getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}
}