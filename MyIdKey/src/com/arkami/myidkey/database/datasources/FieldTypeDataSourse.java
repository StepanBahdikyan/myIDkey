/**
 * 
 */
package com.arkami.myidkey.database.datasources;

import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.arkami.myidkey.database.tables.FieldType;
import com.arkami.myidkey.database.tables.GenericDataObject;

/**
 * @author sbahdikyan
 * 
 */
public class FieldTypeDataSourse extends BaseAdapter {

	public FieldTypeDataSourse(Context context) {
		super(context);
	}

	/**
	 * Inserts keyCard to the database
	 * 
	 * @param keyCard
	 *            to be inserted
	 * @return id of the inserted key card
	 */
	public long insert(FieldType fieldType) {
		return insert(FieldType.TABLE_NAME, null, fieldType.getValues());
	}

	/**
	 * @param id
	 *            of the key card
	 * @return the key card from database
	 * @throws IllegalAccessException
	 */
	public FieldType get(Long id) throws IllegalAccessException {
		Cursor result = fetchRow(FieldType.TABLE_NAME, null,
				GenericDataObject.ID + " = " + id, null, null, null, null);
		if (result.moveToFirst()) {
			return FieldType.create(result);
		}
		throw new IllegalAccessException("no item fond with id " + id);
	}

	/**
	 * @return all key cards from data base
	 */
	public List<FieldType> getFieldTypes() {
		return FieldType.createFieldTypes(fetchTableData(FieldType.TABLE_NAME,
				null, null));
	}

	/**
	 * Updates key card record in database, rewrites the data.
	 * 
	 * @param keyCard
	 *            to be updated
	 */
	public long update(FieldType fieldType) {
		return update(FieldType.TABLE_NAME, fieldType.getValues(),
				GenericDataObject.ID + " = " + fieldType.getId(), null);
	}

	/**
	 * @param name
	 * @return
	 * @throws IllegalAccessException
	 */
	public long get(String name) throws IllegalAccessException {
		Cursor result = fetchRow(FieldType.TABLE_NAME, null, FieldType.NAME
				+ " = '" + name + "'", null, null, null, null);
		if (result.moveToFirst()) {
			return FieldType.create(result).getId();
		}
		throw new IllegalAccessException("no item fond with name " + name);
	}

}
