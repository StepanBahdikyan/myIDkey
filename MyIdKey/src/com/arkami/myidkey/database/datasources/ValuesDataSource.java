/**
 * 
 */
package com.arkami.myidkey.database.datasources;

import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.arkami.myidkey.database.tables.GenericDataObject;
import com.arkami.myidkey.database.tables.Value;

/**
 * @author sbahdikyan
 *
 */
public class ValuesDataSource extends BaseAdapter {

	/**
	 * @param context
	 */
	public ValuesDataSource(Context context) {
		super(context);
	}

	/**
	 * Inserts keyCard to the database
	 * 
	 * @return id of the inserted key card
	 */
	public long insert(Value value) {
		return insert(Value.TABLE_NAME, null, value.getValues());
	}

	/**
	 * @param id
	 *            of the key card
	 * @return the key card from database
	 * @throws IllegalAccessException
	 */
	public Value get(Long id) throws IllegalAccessException {
		Cursor result = fetchRow(Value.TABLE_NAME, null, GenericDataObject.ID
				+ " = " + id, null, null, null, null);
		if (result.moveToFirst()) {
			return Value.create(result);
		}
		throw new IllegalAccessException("no item fond with id " + id);
	}

	/**
	 * @return all key cards from data base
	 */
	public List<Value> getValues() {
		return Value.createValues(fetchTableData(Value.TABLE_NAME, null, null));
	}

	/**
	 * Updates key card record in database, rewrites the data.
	 * 
	 * @param values
	 *            to be updated
	 */
	public long update(Value values) {
		return update(Value.TABLE_NAME, values.getValues(), GenericDataObject.ID
				+ " = " + values.getId(), null);
	}

    public long delete(long id){
        return delete(Value.TABLE_NAME, id);
    }

}