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
 */
public class FileObject extends GenericDataObject implements Parcelable {

    public static final String TABLE_NAME = "fileTable";
    public static final String FILE_TYPE = "fileType";
    public static final String IS_KEYCARD_ATTRIBUTE = "isKeyCardAttribute";
    public static final String SIZE = "size";
    public static final String NAME = "name";
    public static final String PARENT_ID = "parentId";
    public static final String PATH_TO_FILE = "pathToFile";
    public static final String CREATE_TABLE = "create table " + TABLE_NAME
            + " ( "
            //
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            //
            + GenericDataObject.CREATE_DATE + " INTEGER, "
            //
            + GenericDataObject.MODIFY_DATE + " INTEGER, "
            //
            + FileObject.FILE_TYPE + " INTEGER, "
            //
            + FileObject.NAME + " TEXT NOT NULL ,"
            //
            +PARENT_ID + " INTEGER, "
            + FileObject.IS_KEYCARD_ATTRIBUTE + " INTEGER, "
            //
            + FileObject.PATH_TO_FILE + " TEXT NOT NULL,"
            //
            + FileObject.SIZE + " INTEGER )";

    private Long fileTypeId;
    private String name;
    private Long size;
    private String pathToFile;
    private boolean isKeyCardAttribute;
    private int parentId;
    
    
    public static final Parcelable.Creator<FileObject> CREATOR = new Parcelable.Creator<FileObject>() {
        public FileObject createFromParcel(Parcel in) {
            return new FileObject(in);
        }

        public FileObject[] newArray(int size) {
            return new FileObject[size];
        }
    };

    /**
     * default
     */
    public FileObject() {
    }

    /**
     * @param in
     */
    public FileObject(Parcel in) {
//        this.setCreateDate(in.readLong());
//        this.setFileType(in.readLong());
        this.setId(in.readLong());
//        this.setModifyDate(in.readLong());
        this.setName(in.readString());
        this.setPathToFile(in.readString());
        this.setSize(in.readLong());
//        this.setKeyCardAttribute(in.readInt());
    }

    /**
     * @return
     */
    public ContentValues getValues() {
        ContentValues contentValues = new ContentValues();
        if (this.getCreateDate() != null) {
            contentValues.put(CREATE_DATE, this.getCreateDate());
        }
        contentValues.put(IS_KEYCARD_ATTRIBUTE, isKeyCardAttribute);

        if (this.getModifyDate() != null) {
            contentValues.put(MODIFY_DATE, this.getModifyDate());
        }
        if (this.getId() != null) {
            contentValues.put(ID, this.getId());
        }
        if (this.name != null) {
            contentValues.put(NAME, this.name);
        }
        if(this.parentId > 0){
            contentValues.put(PARENT_ID, parentId);
        }
        if (this.size != null) {
            contentValues.put(SIZE, this.size);
        }
        if (this.fileTypeId != null) {
            contentValues.put(FILE_TYPE, this.fileTypeId);
        }
        if (this.getPathToFile() != null) {
            contentValues.put(PATH_TO_FILE, this.getPathToFile());
        }

        return contentValues;
    }

    /**
     * Creates a keyCard object from given cursor with data
     *
     * @param cursor
     * @return {@link KeyCard} objects
     */
    public static FileObject create(Cursor cursor) {
        int idColumn = cursor.getColumnIndex(ID);
        int fileTypeIdColumn = cursor.getColumnIndex(FILE_TYPE);
        int nameColumn = cursor.getColumnIndex(NAME);
        int sizeColumn = cursor.getColumnIndex(SIZE);
        int createDateColumn = cursor.getColumnIndex(CREATE_DATE);
        int modifyDateColumn = cursor.getColumnIndex(MODIFY_DATE);
        int pathToFileColumn = cursor.getColumnIndex(PATH_TO_FILE);
        int isKeyCardAttributeColumn = cursor.getColumnIndex(IS_KEYCARD_ATTRIBUTE);
        int parentIdColumn = cursor. getColumnIndex(PARENT_ID);

        FileObject fileRecord = new FileObject();

        if ((cursor.getColumnCount() > pathToFileColumn)
                && (pathToFileColumn > -1)) {
            fileRecord.setPathToFile((cursor.getString(pathToFileColumn)));
        }

        if ((cursor.getColumnCount() > parentIdColumn)
                && (parentIdColumn > -1)) {
            fileRecord.setParentId((cursor.getInt(parentIdColumn)));
        }

        if ((cursor.getColumnCount() > isKeyCardAttributeColumn)
                && (isKeyCardAttributeColumn > -1)) {
            fileRecord.setKeyCardAttribute((cursor.getInt(isKeyCardAttributeColumn)));
        }

        if ((cursor.getColumnCount() > idColumn) && (idColumn > -1)) {
            fileRecord.setId((cursor.getLong(idColumn)));
        }
        if ((cursor.getColumnCount() > fileTypeIdColumn)
                && (fileTypeIdColumn > -1)) {
            fileRecord.setFileType(cursor.getLong(fileTypeIdColumn));
        }
        if ((cursor.getColumnCount() > nameColumn) && (nameColumn > -1)) {
            fileRecord.setName(cursor.getString(nameColumn));
        }
        if ((cursor.getColumnCount() > sizeColumn) && (sizeColumn > -1)) {
            fileRecord.setSize(cursor.getLong(sizeColumn));
        }
        if ((cursor.getColumnCount() > createDateColumn)
                && (createDateColumn > -1)) {
            fileRecord.setCreateDate(cursor.getLong(createDateColumn));
        }
        if ((cursor.getColumnCount() > modifyDateColumn)
                && (modifyDateColumn > -1)) {
            fileRecord.setModifyDate(cursor.getLong(modifyDateColumn));
        }
        if (cursor.isLast()) {
            cursor.close();
        }
        return fileRecord;
    }

    /**
     * @param cursor
     * @return
     */
    public static List<FileObject> createFileObjects(Cursor cursor) {
        List<FileObject> fileObjects = new ArrayList<FileObject>();
        cursor.moveToFirst();
        do {
            FileObject file = FileObject.create(cursor);
            fileObjects.add(file);
        } while (cursor.moveToNext());
        // cursor.close();
        return fileObjects;
    }

    /**
     * @return the fileType
     */
    public Long getFileType() {
        return fileTypeId;
    }

    /**
     * @param fileTypeId the fileTypeId to set
     */
    public void setFileType(Long fileTypeId) {
        this.fileTypeId = fileTypeId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeLong(this.getCreateDate());
//        dest.writeLong(this.getFileType());
    	if(getId() != null){
    		dest.writeLong(this.getId());
    	}else{
    		setId(-1L);
    	}
        
//        dest.writeLong(this.getModifyDate());
        dest.writeString(this.getName());
        dest.writeString(this.getPathToFile());
        dest.writeLong(this.getSize());
//        dest.writeInt(isKeyCardAttribute ? 1 : 0);
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    
   
    
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the size
     */
    public Long getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(Long size) {
        this.size = size;
    }

    /**
     * @return the pathToFile
     */
    public String getPathToFile() {
        return pathToFile;
    }

    /**
     * @param pathToFile the pathToFile to set
     */
    public void setPathToFile(String pathToFile) {
        this.pathToFile = pathToFile;
    }

    /**
     * Shows if the file is a key card attribute or not.
     * Non keycard attributes are shown in the files screen,
     * others a part of a key card.
     *
     * @return
     */
    public boolean isKeyCardAttribute() {
        return isKeyCardAttribute;
    }

    public void setKeyCardAttribute(boolean keyCardAttribute) {
        isKeyCardAttribute = keyCardAttribute;
    }

    public void setKeyCardAttribute(int keyCardAttribute) {
        isKeyCardAttribute = keyCardAttribute == 1;
    }

    public void setParentId(int parentId){
        this.parentId = parentId;
    }

    public int getParentId(){
        return parentId;
    }
}
