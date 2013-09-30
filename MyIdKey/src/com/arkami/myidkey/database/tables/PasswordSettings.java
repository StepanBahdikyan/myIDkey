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
 * Time: 17:47
 * To change this template use File | Settings | File Templates.
 */
public class PasswordSettings extends GenericDataObject implements Parcelable {
    public static final String TABLE_NAME = "passwordSettings";
    public static final String PASSWORD_LENGTH = "passwordLength";
    public static final String HAS_SMALL_LETTERS = "hasSmallLetters";
    public static final String HAS_UPPERCASE_LETTERS = "hasUppercaseLetters";
    public static final String HAS_SPECIAL_CHARACTERS = "hasSpecialCharacters";
    public static final String HAS_NUMBERS = "hasNumbers";
    public static final String IS_HEXADECIMAL = "isHexadecimal";

    public static final String[] columns = {
            ID,
            PASSWORD_LENGTH,
            HAS_SMALL_LETTERS,
            HAS_UPPERCASE_LETTERS,
            HAS_SPECIAL_CHARACTERS,
            HAS_NUMBERS,
            IS_HEXADECIMAL};
    public static final String CREATE = "CREATE TABLE " + TABLE_NAME + " ( " +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            //
            GenericDataObject.CREATE_DATE + " INTEGER, " +
            //
            GenericDataObject.MODIFY_DATE + " INTEGER, " +
            //
            HAS_SMALL_LETTERS + " INTEGER, " +
            //
            HAS_NUMBERS + " INTEGER, " +
            //
            HAS_SPECIAL_CHARACTERS + " INTEGER, " +
            //
            IS_HEXADECIMAL + " INTEGER, " +
            //
            HAS_UPPERCASE_LETTERS + " INTEGER, " +
            //
            PASSWORD_LENGTH + " INTEGER NOT NULL) ";


    private int passwordLength;
    private boolean hasSmallLetters;
    private boolean hasUppercaseLetters;
    private boolean isHexadecimal;
    private boolean hasSpecialCharacters;


    private boolean hasNumbers;

    /**
     * default constructor
     */
    public PasswordSettings() {

    }

    public static PasswordSettings create(Cursor cursor) {

        int idColomn = cursor.getColumnIndex(ID);
        int passwordLengthColomn = cursor.getColumnIndex(PASSWORD_LENGTH);
        int passwordHasUpperCaseLettersColumn = cursor.getColumnIndex(HAS_UPPERCASE_LETTERS);
        int passwordHasSmallLettersColumn = cursor.getColumnIndex(HAS_SMALL_LETTERS);
        int passwordHasSpecialCharactersColumn = cursor.getColumnIndex(HAS_SPECIAL_CHARACTERS);
        int passwordIsHexadecimal = cursor.getColumnIndex(IS_HEXADECIMAL);
        int createDateColumn = cursor.getColumnIndex(CREATE_DATE);
        int modifyDateColumn = cursor.getColumnIndex(MODIFY_DATE);
        int hasnumbersColomn = cursor.getColumnIndex(HAS_NUMBERS);
        PasswordSettings passwordSettings = new PasswordSettings();

        if ((hasnumbersColomn > -1) && (cursor.getColumnCount() > hasnumbersColomn)) {
            passwordSettings.setHasNumbers((cursor.getLong(hasnumbersColomn) == 1));
        }

        if ((idColomn > -1) && (cursor.getColumnCount() > idColomn)) {
            passwordSettings.setId((cursor.getLong(idColomn)));
        }

        if ((passwordIsHexadecimal > -1) && (cursor.getColumnCount() > passwordIsHexadecimal)) {
            passwordSettings.setHexadecimal((cursor.getLong(passwordIsHexadecimal)) == 1);
        }

        if ((passwordHasSpecialCharactersColumn > -1) && (cursor.getColumnCount() > passwordHasSpecialCharactersColumn)) {
            passwordSettings.setHasSpecialCharacters((cursor.getLong(passwordHasSpecialCharactersColumn)) == 1);
        }

        if ((passwordHasUpperCaseLettersColumn > -1)
                && (cursor.getColumnCount() > passwordHasUpperCaseLettersColumn)) {
            passwordSettings.setHasUppercaseLetters(cursor.getInt(passwordHasUpperCaseLettersColumn) == 1);
        }

        if ((passwordHasSmallLettersColumn > -1)
                && (cursor.getColumnCount() > passwordHasSmallLettersColumn)) {
            passwordSettings.setHasSmallLetters(cursor.getInt(passwordHasSmallLettersColumn) == 1);
        }

        if ((passwordLengthColomn > -1) && (cursor.getColumnCount() > passwordLengthColomn)) {
            passwordSettings.setPasswordLength(cursor.getInt(passwordLengthColomn));
        }
        if ((createDateColumn > -1)
                && (cursor.getColumnCount() > createDateColumn)) {
            passwordSettings.setCreateDate(cursor.getLong(createDateColumn));
        }
        if ((modifyDateColumn > -1)
                && (cursor.getColumnCount() > modifyDateColumn)) {
            passwordSettings.setModifyDate(cursor.getLong(modifyDateColumn));
        }
        if (cursor.isLast()) {
            cursor.close();
        }
        return passwordSettings;

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
        if (this.passwordLength > 0) {
            contentValues.put(PASSWORD_LENGTH, this.passwordLength);
        }
        if(this.hasNumbers){
           contentValues.put(HAS_NUMBERS,1);
        }else{
           contentValues.put(HAS_NUMBERS,0);
        }

        if(this.hasSmallLetters){
            contentValues.put(HAS_SMALL_LETTERS,1);
        }else{
            contentValues.put(HAS_SMALL_LETTERS,0);
        }

        if(this.hasSpecialCharacters){
            contentValues.put(HAS_SPECIAL_CHARACTERS,1);
        }else{
            contentValues.put(HAS_SPECIAL_CHARACTERS,0);
        }

        if(this.hasUppercaseLetters){
            contentValues.put(HAS_UPPERCASE_LETTERS,1);
        }else{
            contentValues.put(HAS_UPPERCASE_LETTERS,0);
        }

        if(this.isHexadecimal){
            contentValues.put(IS_HEXADECIMAL,1);
        }else{
            contentValues.put(IS_HEXADECIMAL,0);
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
        out.writeLong(this.getCreateDate());
        out.writeLong(this.getModifyDate());
    }

    /**
     * @param in
     */
    public PasswordSettings(Parcel in) {
        this.setId(in.readLong());
        this.setCreateDate(in.readLong());
        this.setModifyDate(in.readLong());
    }

    public boolean hasSmallLetters() {
        return hasSmallLetters;
    }

    public void setHasSmallLetters(boolean hasSmallLetters) {
        this.hasSmallLetters = hasSmallLetters;
    }

    public int getPasswordLength() {
        return passwordLength;
    }

    public void setPasswordLength(int passwordLength) {
        this.passwordLength = passwordLength;
    }

    public boolean hasUppercaseLetters() {
        return hasUppercaseLetters;
    }

    public void setHasUppercaseLetters(boolean hasUppercaseLetters) {
        this.hasUppercaseLetters = hasUppercaseLetters;
    }

    public boolean isHexadecimal() {
        return isHexadecimal;
    }

    public void setHexadecimal(boolean hexadecimal) {
        isHexadecimal = hexadecimal;
    }

    public boolean hasSpecialCharacters() {
        return hasSpecialCharacters;
    }

    public void setHasSpecialCharacters(boolean hasSpecialCharacters) {
        this.hasSpecialCharacters = hasSpecialCharacters;
    }

    public boolean hasNumbers() {
        return hasNumbers;
    }

    public void setHasNumbers(boolean hasNumbers) {
        this.hasNumbers = hasNumbers;
    }

}

