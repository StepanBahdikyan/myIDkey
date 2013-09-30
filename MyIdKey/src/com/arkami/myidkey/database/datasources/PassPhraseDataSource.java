package com.arkami.myidkey.database.datasources;

import android.content.Context;
import android.database.Cursor;

import com.arkami.myidkey.database.tables.PassPhrase;

/**
 * Created with IntelliJ IDEA.
 * User: sbahdikyan
 * Date: 13-8-15
 * Time: 15:34
 * To change this template use File | Settings | File Templates.
 */
public class PassPhraseDataSource extends BaseAdapter {
    /**
     * Default constructor
     *
     * @param context application context
     */
    public PassPhraseDataSource(Context context) {
        super(context);
    }

    /**
     * Inserts a pass phrase in database
     *
     * @param passPhrase
     * @return
     */
    public long insert(PassPhrase passPhrase) {
        if (isEmpty(PassPhrase.TABLE_NAME) == false) {
            super.rawQuery("delete from " + PassPhrase.TABLE_NAME, null);
        }
        return super.insert(PassPhrase.TABLE_NAME, null, passPhrase.getValues());
    }

    public String getPassPhrase() {
        PassPhrase pass;
        Cursor cursor = super.fetchTableData(PassPhrase.TABLE_NAME, PassPhrase.columns);
        if (cursor.moveToFirst()) {
            pass = PassPhrase.create(cursor);
            return pass.getPassPhrase();
        }
       return null;
    }

    public boolean hasPassPhrase() {
        return (isEmpty(PassPhrase.TABLE_NAME) == false);
    }
}
