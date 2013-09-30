package com.arkami.myidkey.database.tables;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created with IntelliJ IDEA.
 * User: sbahdikyan
 * Date: 13-8-15
 * Time: 15:11
 * To change this template use File | Settings | File Templates.
 */
public class PassPhrase extends GenericDataObject implements Parcelable {
    public static final String TABLE_NAME = "passPhrase";
    public static final String PASS_PHRASE = "passPhrase";

    public static final String[] columns = {ID, PASS_PHRASE};
    public static final String CREATE = "CREATE TABLE " + TABLE_NAME + " ( " +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            //
            GenericDataObject.CREATE_DATE + " INTEGER, " +
            //
            GenericDataObject.MODIFY_DATE + " INTEGER, " +
            PASS_PHRASE + " TEXT NOT NULL) ";

    private String passPhrase;

    /**
     * default constructor
     */
    public PassPhrase() {

    }

    public static PassPhrase create(Cursor cursor) {

            int idColon = cursor.getColumnIndex(ID);
            int passPhraseColon = cursor.getColumnIndex(PASS_PHRASE);
            int createDateColumn = cursor.getColumnIndex(CREATE_DATE);
            int modifyDateColumn = cursor.getColumnIndex(MODIFY_DATE);

            PassPhrase passPhraseObject = new PassPhrase();

            if ((idColon > -1) && (cursor.getColumnCount() > idColon)) {
                passPhraseObject.setId((cursor.getLong(idColon)));
            }

            if ((passPhraseColon > -1) && (cursor.getColumnCount() > passPhraseColon)) {
                passPhraseObject.setPassPhrase(cursor.getString(passPhraseColon));
            }
            if ((createDateColumn > -1)
                    && (cursor.getColumnCount() > createDateColumn)) {
                passPhraseObject.setCreateDate(cursor.getLong(createDateColumn));
            }
            if ((modifyDateColumn > -1)
                    && (cursor.getColumnCount() > modifyDateColumn)) {
                passPhraseObject.setModifyDate(cursor.getLong(modifyDateColumn));
            }
            if (cursor.isLast()) {
                cursor.close();
            }
            return passPhraseObject;

    }

    /**
     * @param cursor
     * @return
     */
    public static List<PassPhrase> createPassPhrases(Cursor cursor) {
        List<PassPhrase> passPhrases = new ArrayList<PassPhrase>();
        if (cursor.moveToFirst()) {
            do {
                PassPhrase passPhraseObject = PassPhrase.create(cursor);
                passPhrases.add(passPhraseObject);
            } while (cursor.moveToNext());
        }
        return passPhrases;
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
        if (this.passPhrase != null) {
            contentValues.put(PASS_PHRASE, this.passPhrase);
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
        out.writeString(this.getPassPhrase());
        out.writeLong(this.getCreateDate());
        out.writeLong(this.getModifyDate());
    }

    /**
     * @param in
     */
    public PassPhrase(Parcel in) {
        this.setId(in.readLong());
        this.setPassPhrase(in.readString());
        this.setCreateDate(in.readLong());
        this.setModifyDate(in.readLong());
    }

    //getter
    public String getPassPhrase() {
        return this.passPhrase;
    }

    //setter
    public void setPassPhrase(String passPhrase) {
        this.passPhrase = passPhrase;
    }
}
