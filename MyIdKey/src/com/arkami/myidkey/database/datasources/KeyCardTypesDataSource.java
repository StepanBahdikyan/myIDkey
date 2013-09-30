/**
 * 
 */
package com.arkami.myidkey.database.datasources;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.arkami.myidkey.database.DataBaseHelper;
import com.arkami.myidkey.database.tables.KeyCardType;

/**
 * @author sbahdikyan
 * 
 */
public class KeyCardTypesDataSource {

	// Database fields
	private SQLiteDatabase database;
	private DataBaseHelper dbHelper;

	public KeyCardTypesDataSource(Context context) {
		dbHelper = DataBaseHelper.getInstance(context);
	}

	/**
	 * Opens connection to database.
	 */
	public void open() {
		database = dbHelper.getWritableDatabase();
	}

	/**
	 * closes the connection
	 */
	public void close() {
		dbHelper.close();
	}

	public KeyCardType createKeyCardType(String name) throws Exception {
		ContentValues values = new ContentValues();
		values.put(KeyCardType.NAME, name);
		long insertId = database.insert(KeyCardType.TABLE_NAME, null, values);
		if (insertId > 0) {
			Cursor cursor = database.query(KeyCardType.TABLE_NAME,
					KeyCardType.COLUMNS, KeyCardType.ID + " = " + insertId,
					null, null, null, null);
			cursor.moveToFirst();
			KeyCardType newtype = cursorToType(cursor);
			cursor.close();
			return newtype;
		}
		throw new Exception("type not inserted");
	}

	/**
	 * @param type
	 *            to be deleted
	 */
	public void deleteKeyCardType(KeyCardType type) {
		long id = type.getId();
		database.delete(KeyCardType.TABLE_NAME, KeyCardType.ID + " = " + id,
				null);
		// XXX delete all accuranses of this type id in type_ids fields in other
		// tables(key card)
	}

	public List<KeyCardType> getAllTypes() {
		List<KeyCardType> types = new ArrayList<KeyCardType>();

		Cursor cursor = database.query(KeyCardType.TABLE_NAME,
				KeyCardType.COLUMNS, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			KeyCardType type = cursorToType(cursor);
			types.add(type);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return types;
	}

	/**
	 * @param cursor
	 * @return Type from cursor
	 */
	private KeyCardType cursorToType(Cursor cursor) {
		int idColumn = cursor.getColumnIndex(KeyCardType.ID);
		int nameColumn = cursor.getColumnIndex(KeyCardType.NAME);
		int createDateColumn = cursor.getColumnIndex(KeyCardType.CREATE_DATE);
		int modifyDateColumn = cursor.getColumnIndex(KeyCardType.MODIFY_DATE);
		KeyCardType type = new KeyCardType();
		if ((idColumn > -1) && (cursor.getColumnCount() > idColumn)) {
			type.setId(cursor.getLong(idColumn));
		}
		if ((nameColumn > -1) && (cursor.getColumnCount() > nameColumn)) {
			type.setName(cursor.getString(nameColumn));
		}
		if ((createDateColumn > -1)
				&& (cursor.getColumnCount() > createDateColumn)) {
			type.setCreateDate(cursor.getLong(createDateColumn));
		}
		if ((modifyDateColumn > -1)
				&& (cursor.getColumnCount() > modifyDateColumn)) {
			type.setModifyDate(cursor.getLong(modifyDateColumn));
		}
		return type;
	}

	/**
	 * @param id
	 * @return
	 */
	public KeyCardType getTypeById(Long id) {
		if ((id != null) && (id > -1)) {

			Cursor cursor = database.query(KeyCardType.TABLE_NAME,
					KeyCardType.COLUMNS, KeyCardType.ID + " = " + id, null,
					null, null, null);
			cursor.moveToFirst();
			KeyCardType type = cursorToType(cursor);
			// Make sure to close the cursor
			cursor.close();
			return type;
		}
		return null;
	}

	public long getByName(String name) throws IllegalAccessException {
		// fix for apostrophe bug
		if (name.contains("'")) {
			int indexOfApostrophe = name.indexOf("'");
			name = name.substring(0, indexOfApostrophe) + "'"
					+ name.substring(indexOfApostrophe, name.length() - 1);
		}
		Cursor result = fetchRow(KeyCardType.TABLE_NAME, null, KeyCardType.NAME
				+ " = '" + name + "'", null, null, null, null);
		if (result.moveToFirst()) {
			return KeyCardType.create(result).getId();
		}
		throw new IllegalAccessException("no item fond with name " + name);
	}

	/**
	 * 
	 * XXX XXX XXX XXX XXX XXX XXX
	 * 
	 * @param table
	 * @param columns
	 * @param selection
	 * @param selectionArgs
	 * @param groupBy
	 * @param having
	 * @param orderBy
	 * @return
	 */
	public Cursor fetchRow(String table, String[] columns, String selection,
			String[] selectionArgs, String groupBy, String having,
			String orderBy) {
		return this.database.query(table, columns, selection, selectionArgs,
				groupBy, having, orderBy);
	}
}
