/**
 * 
 */
package com.arkami.myidkey.database.datasources;

import java.util.Date;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.arkami.myidkey.activity.customuicomponents.FieldNamesEnum;
import com.arkami.myidkey.database.tables.Field;
import com.arkami.myidkey.database.tables.GenericDataObject;

/**
 * @author sbahdikyan
 * 
 */
public class FieldDataSource extends BaseAdapter {

	/**
	 * @param context
	 */
	public FieldDataSource(Context context) {
		super(context);
		if (this.isEmpty(Field.TABLE_NAME)) {
			try {
				initializeFields(context);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	/**

	 * @throws IllegalAccessException
	 */
	private void initializeFields(Context context)
			throws IllegalAccessException {
		for (FieldNamesEnum name : FieldNamesEnum.values()) {
			Field field = new Field();
			field.setCreateDate(new Date().getTime());
			field.setModifyDate(field.getCreateDate());
			field.setName(name.getName());
			if(name.getPossibleSelections()!=null){
				field.setPossibleValues(name.getPossibleSelections());
			}
			FieldTypeDataSourse fieldTypeDataSourse = new FieldTypeDataSourse(
					context);
			field.setFieldTypeId(fieldTypeDataSourse.get(name.getFieldType()
					.name()));
			insert(Field.TABLE_NAME, null, field.getValues());
		}

	}

	/**
	 * Inserts keyCard to the database
	 * 

	 *            to be inserted
	 * @return id of the inserted key card
	 */
	public long insert(Field field) {
		return insert(Field.TABLE_NAME, null, field.getValues());
	}

	/**
	 * @param id
	 *            of the key card
	 * @return the key card from database
	 * @throws IllegalAccessException
	 */
	public Field get(Long id) throws IllegalAccessException {
		Cursor result = fetchRow(Field.TABLE_NAME, null, GenericDataObject.ID
				+ " = " + id, null, null, null, null);
		if (result.moveToFirst()) {
			return Field.create(result);
		}
		throw new IllegalAccessException("no item fond with id " + id);
	}

	/**
	 * @return all key cards from data base
	 */
	public List<Field> getFileds() {
		return Field.createFields(fetchTableData(Field.TABLE_NAME, null, null));
	}

	/**
	 * Updates key card record in database, rewrites the data.
	 * 

	 *            to be updated
	 */
	public long update(Field field) {
		return update(Field.TABLE_NAME, field.getValues(), GenericDataObject.ID
				+ " = " + field.getId(), null);
	}

	/**
	 * 
	 * @param name
	 *            name of the field we want
	 * @return  the field
	 * @throws IllegalAccessException
	 *             if cannot find an item with the input name.
	 */
	public Field get(String name) throws IllegalAccessException {
		Cursor result = fetchRow(Field.TABLE_NAME, null, Field.NAME + " = '"
				+ name + "'", null, null, null, null);
		if (result.moveToFirst()) {
			return Field.create(result);
		}
		throw new IllegalAccessException("no item fond with name " + name);
	}
	
}
