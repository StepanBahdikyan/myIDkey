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
public class Tag extends GenericDataObject implements Parcelable {

	public static final String NAME = "name";
	public static final String KEY_CARD_ID = "keyCardId";
	public static final String TABLE_NAME = "tag";
	public static final String[] columns = { ID, NAME, KEY_CARD_ID };
	public static final String CREATE = "CREATE TABLE " + TABLE_NAME + " ( " +
	//
			Tag.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			//
			+ Tag.NAME + " TEXT NOT NULL, "
			//
			+ GenericDataObject.CREATE_DATE + " INTEGER, "
			//
			+ GenericDataObject.MODIFY_DATE + " INTEGER, "
			//
			+ Tag.KEY_CARD_ID + " INTEGER) ";

	private String name;

	/**
	 * 
	 */
	public Tag() {

	}

	public static Tag create(Cursor cursor) {
		int idColon = cursor.getColumnIndex(ID);
		int nameColon = cursor.getColumnIndex(NAME);
		int createDateColumn = cursor.getColumnIndex(CREATE_DATE);
		int modifyDateColumn = cursor.getColumnIndex(MODIFY_DATE);

		Tag tag = new Tag();

		if ((idColon > -1) && (cursor.getColumnCount() > idColon)) {
			tag.setId((cursor.getLong(idColon)));
		}

		if ((nameColon > -1) && (cursor.getColumnCount() > nameColon)) {
			tag.setName(cursor.getString(nameColon));
		}
		if ((createDateColumn > -1)
				&& (cursor.getColumnCount() > createDateColumn)) {
			tag.setCreateDate(cursor.getLong(createDateColumn));
		}
		if ((modifyDateColumn > -1)
				&& (cursor.getColumnCount() > modifyDateColumn)) {
			tag.setModifyDate(cursor.getLong(modifyDateColumn));
		}
		if (cursor.isLast()) {
			cursor.close();
		}
		return tag;
	}

	/**
	 * @param cursor
	 * @return
	 */
	public static List<Tag> createTags(Cursor cursor) {
		List<Tag> tags = new ArrayList<Tag>();
		if (cursor.moveToFirst()) {
			do {
				Tag tag = Tag.create(cursor);
				tags.add(tag);
			} while (cursor.moveToNext());
		}
		return tags;
	}

	/**
	 * @return content values with all the data in this object
	 */
	public ContentValues getValues() {
		ContentValues contentValues = new ContentValues();
		if (this.getCreateDate() != null) {
			contentValues.put(CREATE_DATE, this.getCreateDate());
		}
		if (this.getId() != null) {
			contentValues.put(ID, this.getId());
		}
		if (this.getModifyDate() != null) {
			contentValues.put(MODIFY_DATE, this.getModifyDate());
		}
		if (this.name != null) {
			contentValues.put(NAME, this.name);
		}
		return contentValues;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeLong(this.getId());
		out.writeString(this.name);
		out.writeLong(this.getCreateDate());
		out.writeLong(this.getModifyDate());
	}

	/**
	 * @param in
	 */
	public Tag(Parcel in) {
		this.setId(in.readLong());
		this.setName(in.readString());
		this.setCreateDate(in.readLong());
		this.setModifyDate(in.readLong());
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
