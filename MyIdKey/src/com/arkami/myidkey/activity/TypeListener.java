package com.arkami.myidkey.activity;

import com.arkami.myidkey.database.tables.KeyCardType;

/**
 * Created with IntelliJ IDEA.
 * User: sbahdikyan
 * Date: 13-9-26
 * Time: 11:42
 * To change this template use File | Settings | File Templates.
 */
public interface TypeListener {
    /**
     * called when a tag is set on the dialog.
     */
    public void onTypeSet(KeyCardType type);
}
