package com.arkami.myidkey.database.datasources;

import android.content.Context;
import android.database.Cursor;

import com.arkami.myidkey.database.tables.PasswordSettings;

/**
 * Created with IntelliJ IDEA.
 * User: sbahdikyan
 * Date: 13-8-15
 * Time: 17:46
 * To change this template use File | Settings | File Templates.
 */
public class PasswordSettingsDataSource extends BaseAdapter {

    public PasswordSettingsDataSource(Context context) {
        super(context);
    }

    public PasswordSettings getPasswordSettings() {
        PasswordSettings passwordSettings;
        Cursor cursor = super.fetchTableData(PasswordSettings.TABLE_NAME, PasswordSettings.columns);
        if (cursor.moveToFirst()) {
            passwordSettings = PasswordSettings.create(cursor);
            return passwordSettings;
        }
        return null;
    }

    /**
     *
     * @param passwordSettings
     * @return
     */
    public boolean saveSettings(PasswordSettings passwordSettings) {
        if (super.isEmpty(PasswordSettings.TABLE_NAME)) {
            return (super.insert(PasswordSettings.TABLE_NAME,
                    null, passwordSettings.getValues()) > 0);
        } else {
            return (update(PasswordSettings.TABLE_NAME,
                    passwordSettings.getValues(), PasswordSettings.ID + " = 1", null) > 0);
        }
    }
}
