/**
 * 
 */
package com.arkami.myidkey.database.datasources;

import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.arkami.myidkey.database.tables.GenericDataObject;
import com.arkami.myidkey.database.tables.Tag;

/**
 * @author sbahdikyan
 * 
 */
public class TagDataSource extends BaseAdapter {

	public TagDataSource(Context context) {
		super(context);
	}

	/**
	 * Adds new tag to database.
	 * 
	 * @param tag
	 *            tag to be inserted in the database, duplicate values are not
	 *            permitted.
	 * @return id of the new tag
	 * @throws Exception
	 */
	public long insert(Tag tag) throws Exception {
		long insertId = insert(Tag.TABLE_NAME, null, tag.getValues());
		if (insertId > 0) {
			return insertId;
		}
		throw new Exception("tag not inserted");
	}

    public List<Tag> getTagLike(CharSequence tagNameLike) throws IllegalAccessException {
        String query = "select * from tag where name like '%"+tagNameLike+"%'";
        Cursor result = rawQuery(query,null);
        if (result.moveToFirst()) {
            return Tag.createTags(result);
        }
        throw new IllegalAccessException("no items fond with name" + tagNameLike);
    }

	/**
	 * @param id
	 *            of the key card
	 * @return the key card from database
	 * @throws IllegalAccessException
	 */
	public Tag get(Long id) throws IllegalAccessException {
		Cursor result = fetchRow(Tag.TABLE_NAME, null, GenericDataObject.ID
				+ " = " + id, null, null, null, null);
		if (result.moveToFirst()) {
			return Tag.create(result);
		}
		throw new IllegalAccessException("no item fond with id " + id);
	}

	/**
	 * @return all key cards from data base
	 */
	public List<Tag> getTags() {
		return Tag.createTags(fetchTableData(Tag.TABLE_NAME, null, Tag.NAME));
	}

	/**
	 * Updates key card record in database, rewrites the data.
	 * 
	 * @param tag
	 *            to be updated
	 */
	public long update(Tag tag) {
		return update(Tag.TABLE_NAME, tag.getValues(), GenericDataObject.ID
				+ " = " + tag.getId(), null);
	}

    /**
     *  inserts new tag in data base if
     *  it is not existing, returns the id of the tag
     * @param tag
     * @return   id of the tag
     */
    public long insertIfNotExisting(Tag tag) throws Exception {
        if(super.contains(Tag.TABLE_NAME,Tag.NAME,tag.getName())){
            return getTagByName(tag.getName());
        }
        return insert(tag);
    }

    /**
     *
     * @return      id of the tag in the database
     */
    private long getTagByName(String tagName) throws IllegalAccessException {
        if (tagName.contains("'")) {
            int indexOfApostrophe = tagName.indexOf("'");
            tagName = tagName.substring(0, indexOfApostrophe) + "'"
                    + tagName.substring(indexOfApostrophe, tagName.length() - 1);
        }
        Cursor result = fetchRow(Tag.TABLE_NAME, null, Tag.TABLE_NAME + "." + Tag.NAME
                + " = '" + tagName + "'", null, null, null, null);
        if (result.moveToFirst()) {
            return Tag.create(result).getId();
        }
        throw new IllegalAccessException("could not get the tag with name " + tagName);
    }

}
