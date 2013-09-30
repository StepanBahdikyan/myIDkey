/**
 *
 */
package com.arkami.myidkey.database.datasources;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.arkami.myidkey.database.tables.Field;
import com.arkami.myidkey.database.tables.GenericDataObject;
import com.arkami.myidkey.database.tables.KeyCard;
import com.arkami.myidkey.database.tables.Value;

/**
 * @author sbahdikyan
 */
public class KeyCardDataSource extends BaseAdapter {

    public KeyCardDataSource(Context context) {
        super(context);
    }

    /**
     * Inserts keyCard to the database
     *
     * @param keyCard to be inserted
     * @return id of the inserted key card
     */
    public long insert(KeyCard keyCard) {
        return insert(KeyCard.TABLE_NAME, null, keyCard.getValues());
    }

    /**
     * @param id of the key card
     * @return the key card from database
     * @throws IllegalAccessException
     */
    public KeyCard get(Long id) throws IllegalAccessException {
        Cursor result = fetchRow(KeyCard.TABLE_NAME, null, GenericDataObject.ID
                + " = " + id, null, null, null, null);
        if (result.moveToFirst()) {
            return KeyCard.createKeyCard(result);
        }
        throw new IllegalAccessException("no item fond with id " + id);
    }

    /**
     * @return all key cards from data base
     */
    public List<KeyCard> getKeyCards() {
        return KeyCard.createKeyCards(fetchTableData(KeyCard.TABLE_NAME, null,
                "upper(" + KeyCard.NAME + ")"));
    }

    /**
     * Updates key card record in database, rewrites the data.
     *
     * @param keyCard to be updated
     */
    public long update(KeyCard keyCard) {
        return update(KeyCard.TABLE_NAME, keyCard.getValues(),
                GenericDataObject.ID
                        + " = " + keyCard.getId(), null);
    }

    /**
     * Set isFavourite value of a key card in the data base.
     *
     * @param id         id of the key card
     * @param isFavorite indicates if the key card is favourite.
     */
    public void setIsFavoirite(Long id, boolean isFavorite) {
        ContentValues values = new ContentValues();
        int isFavouriteInt;
        if (isFavorite) {
            isFavouriteInt = 1;
        } else {
            isFavouriteInt = 0;
        }
        values.put(KeyCard.IS_FAVOURITE, isFavouriteInt);
        update(KeyCard.TABLE_NAME, values, GenericDataObject.ID + " = " + id,
                null);
    }

    /**
     * @return true if the table has no rows
     */
    public boolean isEmpty() {
        return super.isEmpty(KeyCard.TABLE_NAME);
    }

    /**
     * @param name  name of the row
     * @param value value to be checked
     * @return true if the is a row with that name and that value.
     */
    public boolean contains(String name, String value) {
        return super.contains(KeyCard.TABLE_NAME, name, value);
    }

    /**
     * Returns file ids related to a key card
     * param id id of the key card holding the files
     *
     * @return
     */
    public long[] getFileIds(long id) throws IllegalAccessException {
        Cursor result = fetchRow(KeyCard.TABLE_NAME, new String[]{KeyCard.FILE_IDS}, GenericDataObject.ID
                + " = " + id, null, null, null, null);

        if (result.moveToFirst()) {
            int fileIdsColumn = result.getColumnIndex(KeyCard.FILE_IDS);
            if ((fileIdsColumn > -1) && (result.getColumnCount() > fileIdsColumn)) {
                result.isNull(fileIdsColumn);
                String stringIds = result.getString(fileIdsColumn);
                long[] fileIds = GenericDataObject.getIDs(stringIds);
                return fileIds;
            }
        }
        throw new IllegalAccessException("no item fond with id " + id);
    }
    //XXX refactor

    /**
     * get all keycards that have expiring date fields.
     * @return
     */
    public List<KeyCard> getKeyCardsWithExpiringDates(){
        List<KeyCard> result = new ArrayList<KeyCard>();
        String expiringDateFieldsQuery = "select * from " + Field.TABLE_NAME + " where ((" +
        Field.FIELD_TYPE_ID + " = 3) " +
                "and ((" + Field.NAME+ " like 'expiration%') or (" + Field.NAME + " like 'end%')))";
        StringBuilder expiringValuesQuery = new StringBuilder("select * from " + Value.TABLE_NAME +" where ");

        List <Field> expireFields;
        Cursor fieldsResult = rawQuery(expiringDateFieldsQuery,null);
        if(fieldsResult.moveToFirst()){
            expireFields = Field.createFields(fieldsResult);
            for(int i = 0; i < expireFields.size();i++){
                expiringValuesQuery.append(Value.FIELD_ID + " = "+"'"+expireFields.get(i).getId() +"'");
                if(i != expireFields.size()-1){
                    expiringValuesQuery.append(" or ");
                }
            }
            List<Value> expiringDateValues;
            Cursor  expiringDateValuesCursor = rawQuery(expiringValuesQuery.toString(),null);
            if(expiringDateValuesCursor.moveToFirst()){
                expiringDateValues = Value.createValues(expiringDateValuesCursor);
                for(int i = 0; i < expiringDateValues.size(); i++){
                    Log.w("expiring values: ", expiringDateValues.get(i).getData());
                    try {
                        result.add(get(expiringDateValues.get(i).getKeyCardId()));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }
            }
        }
        return result;
    }

    /**
     * Deletes key card from database
     * @param id   of the key card to be deleted
     * @return
     */
    public long delete(long id){
         return delete(KeyCard.TABLE_NAME, id);
    }
}
