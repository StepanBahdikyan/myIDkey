/**
 * 
 */
package com.arkami.myidkey.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import android.content.Context;
import android.util.Log;

import com.arkami.myidkey.database.datasources.FileTypeDataSource;
import com.arkami.myidkey.database.datasources.KeyCardTypesDataSource;
import com.arkami.myidkey.database.tables.FileType;
import com.arkami.myidkey.database.tables.KeyCardType;
import com.arkami.myidkey.model.FileTypeEnum;
import com.arkami.myidkey.model.KeyCardTypeEnum;

/**
 * Chashed data from database.
 * 
 * @author sbahdikyan XXX make static !
 */
public class Cache {
	private static List<KeyCardType> keyCardTypes;
	private static Map<KeyCardTypeEnum, Long> keyCardTypesMap;

	private static List<FileType> fileTypes;
	private static Map<FileTypeEnum, Long> fileTypesMap;
	private static Cache cache;

	/**
	 * singleton
	 */
	private Cache() {
	}

	/**
	 * @param context
	 * @return
	 */
	public static Cache getInstance(Context context) {
		if (cache == null) {
			cache = new Cache();
			initializeFileTypes(context);
			initializeKeyCardTypes(context);
		}
		return cache;
	}

	private static void initializeKeyCardTypes(Context context) {
		KeyCardTypesDataSource keyCardTypesDataSource = new KeyCardTypesDataSource(
				context);
		keyCardTypesDataSource.open();
		keyCardTypesMap = new HashMap<KeyCardTypeEnum, Long>();
		keyCardTypes = keyCardTypesDataSource.getAllTypes();
		if (keyCardTypes.isEmpty()) {
			Log.w("keyCardTypes ", "empy..");
		}
		int folder = -1;
		int photo = -1;
		for (KeyCardType keyCardType : keyCardTypes) {
			if ((keyCardType.getName().equals("photo"))) {
				photo = keyCardTypes.indexOf(keyCardType);
				continue;
			}
			if ((keyCardType.getName().equals("Folder"))) {
				folder = keyCardTypes.indexOf(keyCardType);
				continue;
			}

			keyCardTypesMap.put(
					KeyCardTypeEnum.getValueByTitle(keyCardType.getName()),
					keyCardType.getId());
		}
		keyCardTypes.remove(photo);
		keyCardTypes.remove(folder);
		// keyCardTypesMap.remove(KeyCardTypeEnum.folder);
		// keyCardTypesMap.remove(KeyCardTypeEnum.photo);
	}

	/**
	 * Gets all needed data from data base and caches it for better performance.
	 */
	private static void initializeFileTypes(Context context) {
		FileTypeDataSource typeDataSource = new FileTypeDataSource(context);
		typeDataSource.open();
		fileTypesMap = new HashMap<FileTypeEnum, Long>();
		fileTypes = typeDataSource.getAllTypes();
		if (fileTypes.isEmpty()) {
			Log.w("types ", "empy..");
		}
		for (FileType type : fileTypes) {
			fileTypesMap
					.put(FileTypeEnum.valueOf(type.getName()), type.getId());
		}
	}

	/**
	 * @return all types from database
	 */
	public List<FileType> getTypes() {
		if (fileTypesMap == null) {
			throw new NoSuchElementException(
					"cache not initialized, must call Cache.initialize(context) first.");
		}
		return fileTypes;
	}

	/**
	 * @param type
	 *            to b checked
	 * @return id of the given type
	 */
	public Long getTypeId(FileType type) {
		for (FileType typeFromDB : fileTypes) {
			if (typeFromDB.getName().equals(type.getName())) {
				return typeFromDB.getId();
			}
		}
		throw new NoSuchElementException("no element with name "
				+ type.getName());
	}

	/**
	 * @param type
	 * @return
	 */
	public Long getFileTypeId(FileTypeEnum type) {
		return fileTypesMap.get(type);
	}

	/**
	 * @param typeId
	 *            id of the type in the database, if there is no id set for this
	 *            type it returns android png.
	 * @return drawable id
	 */
	public FileTypeEnum getFileTypeEnum(Long typeId) {
		for (FileTypeEnum type : fileTypesMap.keySet()) {

			if (fileTypesMap.get(type) == typeId) {
				return type;
			}
		}
		throw new NoSuchElementException();
	}

	/**
	 * @return all types from database
	 */
	public List<KeyCardType> getKeyCardTypes() {
		if (keyCardTypesMap == null) {
			throw new NoSuchElementException(
					"cache not initialized, must call Cache.initialize(context) first.");
		}
		return keyCardTypes;
	}

	/**
	 * @param type
	 *            to b checked
	 * @return id of the given type
	 */
	public Long getKeyCardTypeId(KeyCardType type) {
		for (KeyCardType typeFromDB : keyCardTypes) {
			if (typeFromDB.getName().trim().equals(type.getName().trim())) {
				return typeFromDB.getId();
			}
		}
		throw new NoSuchElementException("no element with name "
				+ type.getName());
	}

	/**
	 * @param type
	 *            to b checked
	 * @return id of the given type
	 */
	public Long getKeyCardTypeId(KeyCardTypeEnum type) {
		if (keyCardTypesMap.containsKey(type)) {
			return keyCardTypesMap.get(type);
		}
		throw new NoSuchElementException("no element with name "
				+ type.getTitle());
	}

	/**
	 * @param type
	 * @return
	 */
	public Long getFileTypeId(KeyCardTypeEnum type) {
		return fileTypesMap.get(type);
	}

	/**
	 * @param typeId
	 *            id of the type in the database, if there is no id set for this
	 *            type it returns android png.
	 * @return drawable id
	 */
	public KeyCardTypeEnum getKeyCardTypeEnum(Long typeId) {
		for (KeyCardTypeEnum type : keyCardTypesMap.keySet()) {

			if (keyCardTypesMap.get(type) == typeId) {
				return type;
			}
		}
		throw new NoSuchElementException();
	}

}
