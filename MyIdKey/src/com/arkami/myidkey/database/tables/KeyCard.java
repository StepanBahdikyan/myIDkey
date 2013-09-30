/**
 * 
 */
package com.arkami.myidkey.database.tables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author sbahdikyan
 * 
 */
public class KeyCard extends GenericDataObject implements Parcelable {

	public static final String TABLE_NAME = "keyCard";
	// field names
	public static final String NAME = "name";
	public static final String TAG_IDS = "tagIds";// tag.userId
	public static final String IS_FAVOURITE = "isFavourite";
	public static final String TYPE_ID = "typeId";
	public static final String PARENT_ID = "parentId";
	public static final String FILE_IDS = "fileIds";
	public static final String VALUE_IDS = "valueIds";

	public static final String[] columns = { ID, NAME, TAG_IDS, IS_FAVOURITE,
			TYPE_ID, PARENT_ID, FILE_IDS };

	private String name;
	private long[] tagIds;
	private boolean isFavourite;
	private Long keyCardTypeId;
	private Long parentId;
	private long[] fileIds;
	private long[] valueIds;
    /**
     * has values in it ONLY when searching
     * through items in the key cards screen,
     * it's needed to show the user results
     * from the search
     */
    private String tag;

    // public static final String CHILDREN_IDS = "childrenIds";//in child parent
	// id == this.id

	public static final String CREATE = "CREATE TABLE " + KeyCard.TABLE_NAME
			+ " ( "
			//
			+ KeyCard.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			//
			+ KeyCard.IS_FAVOURITE + " INTEGER, "
			//
			+ GenericDataObject.CREATE_DATE + " INTEGER, "
			//
			+ GenericDataObject.MODIFY_DATE + " INTEGER, "
			//
			+ KeyCard.NAME + " TEXT NOT NULL ,"
			//
			+ KeyCard.VALUE_IDS + " TEXT ,"
			//
			+ KeyCard.PARENT_ID + " INTEGER , "
			//
			+ KeyCard.TAG_IDS + " TEXT, "
			//
			+ KeyCard.FILE_IDS + " TEXT, "
			//
			+ KeyCard.TYPE_ID + " INTEGER )";

	@Override
	public int describeContents() {
		return 0;
	}

	/**
	 * @param in
	 */
	public KeyCard(Parcel in) {
		this.setId(in.readLong());
		this.setName(in.readString());
		this.setParentId(in.readLong());
		this.setCreateDate(in.readLong());
		in.readLongArray(this.fileIds);
		in.readLongArray(this.tagIds);
		in.readLongArray(this.getValueIds());
	}

	/**
	 * default
	 */
	public KeyCard() {
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeLong(this.getId());
		out.writeString(this.name);
		out.writeLong(this.parentId);
		out.writeLong(this.getCreateDate());
		out.writeLongArray(this.fileIds);
		out.writeLongArray(this.tagIds);
		out.writeLongArray(this.getValueIds());
	}

	/**
	 * Creates a keyCard object from given cursor with data
	 * 
	 * @param cursor
	 * @return {@link KeyCard} objects
	 */
	public static KeyCard createKeyCard(Cursor cursor) {
		int idColon = cursor.getColumnIndex(ID);
		int valueIdsColumn = cursor.getColumnIndex(VALUE_IDS);
		int isFavouriteColon = cursor.getColumnIndex(IS_FAVOURITE);
		int typeIdColon = cursor.getColumnIndex(TYPE_ID);
		int parentIdColon = cursor.getColumnIndex(PARENT_ID);
		int tagIdsColon = cursor.getColumnIndex(TAG_IDS);
		int nameColon = cursor.getColumnIndex(NAME);
		int createDateColumn = cursor.getColumnIndex(CREATE_DATE);
		int modifyDateColumn = cursor.getColumnIndex(MODIFY_DATE);
		int fileIdsColumn = cursor.getColumnIndex(FILE_IDS);

		KeyCard keyCard = new KeyCard();

		if ((valueIdsColumn > -1) && (cursor.getColumnCount() > valueIdsColumn)) {
			cursor.isNull(valueIdsColumn);
			String stringIds = cursor.getString(valueIdsColumn);
			long[] valueIds = getIDs(stringIds);
			keyCard.setValueIds(valueIds);
		}

		if ((fileIdsColumn > -1) && (cursor.getColumnCount() > fileIdsColumn)) {
			cursor.isNull(fileIdsColumn);

			String stringIds = cursor.getString(fileIdsColumn);
			long[] fileIds = getIDs(stringIds);
			keyCard.setFileIds(fileIds);
		}

		if ((idColon > -1) && (cursor.getColumnCount() > idColon)) {
			keyCard.setId((cursor.getLong(idColon)));
		}
		if ((isFavouriteColon > -1)
				&& (cursor.getColumnCount() > isFavouriteColon)) {
			keyCard.setFavourite((cursor.getInt(isFavouriteColon)) != 0);
		}
		if ((nameColon > -1) && (cursor.getColumnCount() > nameColon)) {
			keyCard.setName(cursor.getString(nameColon));
		}
		if ((parentIdColon > -1) && (cursor.getColumnCount() > parentIdColon)) {
			keyCard.setParentId(cursor.getLong(parentIdColon));
		}
		if ((typeIdColon > -1) && (cursor.getColumnCount() > typeIdColon)) {
			keyCard.setKeyCardTypeId(cursor.getLong(typeIdColon));
		}
		if ((tagIdsColon > -1) && (cursor.getColumnCount() > tagIdsColon)) {
			keyCard.setTagIds(getIDs(cursor.getString(tagIdsColon)));
		}
		if ((createDateColumn > -1)
				&& (cursor.getColumnCount() > createDateColumn)) {
			keyCard.setCreateDate(cursor.getLong(createDateColumn));
		}
		if ((modifyDateColumn > -1)
				&& (cursor.getColumnCount() > modifyDateColumn)) {
			keyCard.setModifyDate(cursor.getLong(modifyDateColumn));
		}
		if (cursor.isLast()) {
			cursor.close();
		}
		return keyCard;
	}

    /**
     *
     * @return  tag names found from search in the key cards screen.
     */
    public String getTag(){
        return this.tag;
    }

    /**
     *
     * @param tag
     */
    public void setTag(String tag){
        this.tag = tag;
    }

	/**
	 * @param cursor
	 * @return
	 */
	public static List<KeyCard> createKeyCards(Cursor cursor) {
		List<KeyCard> keyCards = new ArrayList<KeyCard>();
		if (cursor.moveToFirst()) {
			do {
				KeyCard keyCard = KeyCard.createKeyCard(cursor);
				keyCards.add(keyCard);
			} while (cursor.moveToNext());
			// cursor.close();
		}
		return keyCards;
	}

	/**
	 * @return content values with all the data in this object
	 */
	public ContentValues getValues() {
		ContentValues contentValues = new ContentValues();
		if (this.getCreateDate() != null) {
			contentValues.put(CREATE_DATE, this.getCreateDate());
		}
		if (this.valueIds != null) {
			contentValues.put(VALUE_IDS, concatIds(this.getValueIds()));
		}
		if (this.getId() != null) {
			contentValues.put(ID, this.getId());
		}
		if (this.name != null) {
			contentValues.put(NAME, this.name);
		}

		if (this.parentId != null) {
			contentValues.put(PARENT_ID, this.parentId);
		}

		if (this.keyCardTypeId != null) {
			contentValues.put(TYPE_ID, this.keyCardTypeId);
		}

		if (this.tagIds != null) {
			contentValues.put(TAG_IDS, concatIds(this.tagIds));
		}
		contentValues.put(IS_FAVOURITE, this.isFavourite);
		if (this.fileIds != null) {
			contentValues.put(FILE_IDS, concatIds(fileIds));
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
	 * @return the tagIds
	 */
	public long[] getTagIds() {
		return tagIds;
	}

	/**
	 * @param tagIds
	 *            the tagIds to set
	 */
	public void setTagIds(long[] tagIds) {
		this.tagIds = tagIds;
	}

	/**
	 * @return the isFavourite
	 */
	public boolean isFavourite() {
		return isFavourite;
	}

	/**
	 * @param isFavourite
	 *            the isFavourite to set
	 */
	public void setFavourite(boolean isFavourite) {
		this.isFavourite = isFavourite;
	}

	/**
	 * @return the typeId
	 */
	public Long getKeyCardTypeId() {
		return this.keyCardTypeId;
	}

	/**
	 * @param keyCardTypeId
	 *            the typeId to set
	 */
	public void setKeyCardTypeId(Long keyCardTypeId) {
		this.keyCardTypeId = keyCardTypeId;
	}

	/**
	 * @return the parentId
	 */
	public Long getParentId() {
		return parentId;
	}

	/**
	 * @param parentId
	 *            the parentId to set
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return the fileIds
	 */
	public long[] getFileIds() {
        if(fileIds == null){
          return new long[0];
        }
		return fileIds;
	}

	/**
	 * @param fileIds
	 *            the fileIds to set
	 */
	public void setFileIds(long[] fileIds) {
		this.fileIds = fileIds;
	}

	/**
	 * @return the fieldIds
	 */
	public long[] getValueIds() {
		return valueIds;
	}

	/**
	 * @param valueIds
	 *            the valueIds to set
	 */
	public void setValueIds(long[] valueIds) {
		this.valueIds = valueIds;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "KeyCard ["
				+ (name != null ? "name=" + name + ", " : "")
				+ (tagIds != null ? "tagIds=" + Arrays.toString(tagIds) + ", "
						: "")
				+ "isFavourite="
				+ isFavourite
				+ ", "
				+ (keyCardTypeId != null ? "keyCardTypeId=" + keyCardTypeId
						+ ", " : "")
				+ (parentId != null ? "parentId=" + parentId + ", " : "")
				+ (fileIds != null ? "fileIds=" + Arrays.toString(fileIds)
						+ ", " : "")
				+ (valueIds != null ? "fieldIds=" + Arrays.toString(valueIds)
						: "") + "]";
	}

}
