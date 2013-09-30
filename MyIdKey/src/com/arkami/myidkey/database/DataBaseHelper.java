/**
 * 
 */
package com.arkami.myidkey.database;

import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.arkami.myidkey.activity.customuicomponents.FieldTypeEnum;
import com.arkami.myidkey.database.tables.Field;
import com.arkami.myidkey.database.tables.FieldType;
import com.arkami.myidkey.database.tables.FileObject;
import com.arkami.myidkey.database.tables.FileType;
import com.arkami.myidkey.database.tables.KeyCard;
import com.arkami.myidkey.database.tables.KeyCardType;
import com.arkami.myidkey.database.tables.PassPhrase;
import com.arkami.myidkey.database.tables.PasswordSettings;
import com.arkami.myidkey.database.tables.Tag;
import com.arkami.myidkey.database.tables.Value;
import com.arkami.myidkey.model.FileTypeEnum;
import com.arkami.myidkey.model.KeyCardTypeEnum;
import com.arkami.myidkey.util.Common;

/**
 * @author sbahdikyan
 * 
 */
public class DataBaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "myIDkey.db";
	private static final int DATABASE_VERSION = 8;
	private static DataBaseHelper helperInstance = null;
	private static Context context;

	private DataBaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}

	/**
	 * @param context
	 * @return
	 */
	public static DataBaseHelper getInstance(Context context) {
		if (helperInstance == null) {
			helperInstance = new DataBaseHelper(context);
		}
		return helperInstance;
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(Tag.CREATE);
		database.execSQL(FileType.CREATE);
		database.execSQL(KeyCardType.CREATE);
		database.execSQL(KeyCard.CREATE);
		database.execSQL(FileObject.CREATE_TABLE);
		database.execSQL(Field.CREATE_TABLE);
		database.execSQL(FieldType.CREATE_TABLE);
		database.execSQL(Value.CREATE_TABLE);
        database.execSQL(PassPhrase.CREATE);
        database.execSQL(PasswordSettings.CREATE);
		// initializeTags(database);
        initializePassword(database);
		initializeKeyCardTypes(database);
		initializeFileTypes(database);
		initializeFieldTypes(database);
	}

    /**
     * default settings for passwords
     */
    private void initializePassword(SQLiteDatabase database) {
        PasswordSettings passwordSettings = new PasswordSettings();
        passwordSettings.setHasNumbers(false);
        passwordSettings.setHexadecimal(false);
        passwordSettings.setPasswordLength(12);
        passwordSettings.setHasUppercaseLetters(false);
        passwordSettings.setHasSpecialCharacters(false);
        passwordSettings.setHasSmallLetters(true);
        database.insert(PasswordSettings.TABLE_NAME, null, passwordSettings.getValues());
    }

    /**
	 * fills up the field type table with predifined data.
	 * 
	 * @param database
	 */
	private static void initializeFieldTypes(SQLiteDatabase database) {
		for (FieldTypeEnum type : FieldTypeEnum.values()) {
			FieldType fieldType = new FieldType();
			fieldType.setCreateDate(new Date().getTime());
			fieldType.setModifyDate(fieldType.getCreateDate());
			fieldType.setName(type.name());
			database.insert(FieldType.TABLE_NAME, null, fieldType.getValues());
		}

	}

	/**
	 * Fills up database with initial data.
	 */
	private static void initializeTags(SQLiteDatabase database) {

		for (Tag tag : Common.getPredifinedTags(context)) {
			ContentValues values = new ContentValues();
			values.put(Tag.NAME, tag.getName());
			Long date = new Date().getTime();
			values.put(Tag.CREATE_DATE, date);
			values.put(Tag.MODIFY_DATE, date);
			database.insert(Tag.TABLE_NAME, null, values);
		}
	}

	/**
	 * Fills up database with initial data.
	 */
	private static void initializeFileTypes(SQLiteDatabase database) {

		for (FileTypeEnum typeEnum : FileTypeEnum.values()) {
			ContentValues values = new ContentValues();
			values.put(com.arkami.myidkey.database.tables.FileType.NAME,
					typeEnum.name());
			database.insert(
					com.arkami.myidkey.database.tables.FileType.TABLE_NAME,
					null, values);
		}
	}

	/**
	 * Fills up database with initial data.
	 */
	private static void initializeKeyCardTypes(SQLiteDatabase database) {
		for (KeyCardTypeEnum typeEnum : KeyCardTypeEnum.values()) {
			ContentValues values = new ContentValues();
			values.put(com.arkami.myidkey.database.tables.KeyCardType.NAME,
					typeEnum.getTitle());
			database.insert(
					com.arkami.myidkey.database.tables.KeyCardType.TABLE_NAME,
					null, values);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		database.execSQL("DROP TABLE IF EXISTS " + Tag.TABLE_NAME);
		database.execSQL("DROP TABLE IF EXISTS " + FileType.TABLE_NAME);
		database.execSQL("DROP TABLE IF EXISTS " + KeyCard.TABLE_NAME);
        database.execSQL("DROP TABLE IF EXISTS " + Field.TABLE_NAME);
        database.execSQL("DROP TABLE IF EXISTS " + FieldType.TABLE_NAME);
        database.execSQL("DROP TABLE IF EXISTS " + FileObject.TABLE_NAME);
        database.execSQL("DROP TABLE IF EXISTS " + KeyCardType.TABLE_NAME);
        database.execSQL("DROP TABLE IF EXISTS " + Value.TABLE_NAME);
        database.execSQL("DROP TABLE IF EXISTS " + PassPhrase.TABLE_NAME);
        database.execSQL("DROP TABLE IF EXISTS " + PasswordSettings.TABLE_NAME);
		onCreate(database);
	}

}
