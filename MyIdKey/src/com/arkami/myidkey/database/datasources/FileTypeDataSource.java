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
import com.arkami.myidkey.database.tables.FileType;

/**
 * @author sbahdikyan
 * 
 */
public class FileTypeDataSource {

	// Database fields
	private SQLiteDatabase database;
	private DataBaseHelper dbHelper;

	public FileTypeDataSource(Context context) {
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

	public FileType createType(String name) throws Exception {
		ContentValues values = new ContentValues();
		values.put(FileType.NAME, name);
		long insertId = database.insert(FileType.TABLE_NAME, null, values);
		if (insertId > 0) {
			Cursor cursor = database.query(FileType.TABLE_NAME, FileType.COLUMNS,
					FileType.ID + " = " + insertId, null, null, null, null);
			cursor.moveToFirst();
			FileType newType = cursorToType(cursor);
			cursor.close();
			return newType;
		}
		throw new Exception("type not inserted");
	}

	/**
	 * @param type
	 *            to be deleted
	 */
	public void deleteType(FileType type) {
		long id = type.getId();
		database.delete(FileType.TABLE_NAME, FileType.ID + " = " + id, null);
		// XXX delete all accuranses of this type id in type_ids fields in other
		// tables(key card)
	}

	public List<FileType> getAllTypes() {
		List<FileType> types = new ArrayList<FileType>();

		Cursor cursor = database.query(FileType.TABLE_NAME, FileType.COLUMNS, null,
				null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			FileType type = cursorToType(cursor);
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
	private FileType cursorToType(Cursor cursor) {
		int idColumn = cursor.getColumnIndex(FileType.ID);
		int nameColumn = cursor.getColumnIndex(FileType.NAME);
		int createDateColumn = cursor.getColumnIndex(FileType.CREATE_DATE);
		int modifyDateColumn = cursor.getColumnIndex(FileType.MODIFY_DATE);
		FileType type = new FileType();
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

	public FileType getTypeById(Long id) {
		if ((id != null) && (id > -1)) {

			Cursor cursor = database.query(FileType.TABLE_NAME, FileType.COLUMNS,
					FileType.ID + " = " + id, null, null, null, null);
			cursor.moveToFirst();
			FileType type = cursorToType(cursor);
			// Make sure to close the cursor
			cursor.close();
			return type;
		}
		return null;
	}
}
