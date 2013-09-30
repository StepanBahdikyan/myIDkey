/**
 * 
 */
package com.arkami.myidkey.database.datasources;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.arkami.myidkey.database.tables.FileObject;
import com.arkami.myidkey.database.tables.GenericDataObject;

/**
 * @author sbahdikyan
 * 
 */
public class FileDataSource extends BaseAdapter {

	/**
	 * number of bytes to read/write at one time
	 */
	public static final int BYTE_BUFFER_SIZE = 1024;

	public FileDataSource(Context context) {
		super(context);
	}

	/**
	 * @param file
	 * @return
	 */
	public Long insert(FileObject file) {
		return insert(FileObject.TABLE_NAME, null, file.getValues());
	}

	/**
	 * Inserts file as a byte array blob in the database
	 * 
	 * @param file
	 *            to be inserted in blob in the database.
	 * @return id of the record
	 */
	public Long insertBlob(File file) {
		// check file exists/length
		// if it's larger than buffer - chunk it
		// create record and get its' id
		// iterate chunks and insert them using insert() method
		// put your hand in the air like you just don't care :D

		ContentValues contentValues = new ContentValues();
		contentValues.put(FileObject.CREATE_DATE, new Date().getTime());
		contentValues.put(FileObject.NAME, file.getName());
		contentValues.put(FileObject.SIZE, file.length());

		return insert(FileObject.TABLE_NAME, null, contentValues);
	}

	// /**
	// * Gets binary data from database and creates a temporary file.
	// *
	// * @param id
	// * of the file object
	// * @return temporary file with data from database
	// * @throws IOException
	// */
	// public File getFileObject(Long id) throws IOException {
	//
	// Long startByte = 1L;
	// String query = "SELECT substr( " + FileObject.DATA + ", "
	// + startByte.toString() + ", " + BYTE_BUFFER_SIZE
	// + ") as data FROM " + FileObject.TABLE_NAME + " WHERE "
	// + FileObject.ID + " = " + id;
	// Cursor data = rawQuery(query, null);
	// File file = File.createTempFile("temp", "mp3");
	// FileOutputStream outputStream = new FileOutputStream(file);
	//
	// byte[] chunk = data.getBlob(0);
	// outputStream.write(chunk);
	// outputStream.close();
	// data.close();
	// Log.w("first data chunk ", chunk + "");
	// return file;
	// }
	public FileObject get(Long id) {
		if ((id != null) && (id > -1)) {
			String query = "SELECT * FROM " + FileObject.TABLE_NAME + " WHERE "
					+ GenericDataObject.ID + " = " + id;
			Cursor cursor = rawQuery(query, null);
			if (cursor.moveToFirst()) {
				return FileObject.create(cursor);
			}
		}
		return null;
	}

	/**
	 * @param fileIds
	 *            id of the files we want
	 * @return file objects
	 */
	public List<FileObject> getFiles(long[] fileIds) {
		List<FileObject> result = new ArrayList<FileObject>();
		if ((fileIds != null) && (fileIds.length > 0)) {
			for (long id : fileIds) {
				result.add(get(id));
			}
		}
		return result;
	}

    /**
     * @return file objects
     */
    public List<FileObject> getFiles() {
        List<FileObject> result = new ArrayList<FileObject>();
        String query = "SELECT * FROM " + FileObject.TABLE_NAME ;
        Cursor cursor = rawQuery(query, null);
        if (cursor.moveToFirst()) {
            return FileObject.createFileObjects(cursor);
        }
        return result;
    }

    /**
     * Gets all children of a file object
     *
     * @param parentId
     * @return
     */
    public  List<FileObject> getChildren(Long parentId) {
        List<FileObject> result = new ArrayList<FileObject>();
        String query = "SELECT * FROM " + FileObject.TABLE_NAME
                + " where " + FileObject.IS_KEYCARD_ATTRIBUTE + " = 0"
                + " AND " + FileObject.PARENT_ID + " = " + parentId ;
        Cursor cursor = rawQuery(query, null);
        if (cursor.moveToFirst()) {
            return FileObject.createFileObjects(cursor);
        }
        return result;
    }

    /**
     * Returns all files that are not part of a key card.
     * @return file objects
     */
    public List<FileObject> getNonKeyCardFiles(boolean inRootDirectory) {
        List<FileObject> result = new ArrayList<FileObject>();
        String query = "SELECT * FROM " + FileObject.TABLE_NAME + " where " + FileObject.IS_KEYCARD_ATTRIBUTE + " = 0" ;
        if(inRootDirectory){
            query += " AND " + FileObject.PARENT_ID + " is NULL";
        }
        Cursor cursor = rawQuery(query, null);
        if (cursor.moveToFirst()) {
            return FileObject.createFileObjects(cursor);
        }
        return result;
    }



    /**
     * Deletes file with specific id
     * @param id  of the itenm to delete
     * @return
     */
    public long delete( long id) {
        return super.delete(FileObject.TABLE_NAME, id);
    }

    public long update(FileObject fileObject){
        return super.update(FileObject.TABLE_NAME,fileObject.getValues(),FileObject.ID +" = " + fileObject.getId(),null);
    }
}