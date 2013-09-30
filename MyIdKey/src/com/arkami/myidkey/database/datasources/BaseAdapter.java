/**
 *
 */
package com.arkami.myidkey.database.datasources;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.arkami.myidkey.database.DataBaseHelper;

/**
 * Base class for all Data base adapters.
 *
 * @author Stepan Bahdikyan
 */
public abstract class BaseAdapter {
    // data base from helper
    private SQLiteDatabase database;

    /**
     * Default constructor
     *
     * @param context application context
     */
    public BaseAdapter(Context context) {
        this.database = DataBaseHelper.getInstance(context)
                .getWritableDatabase();
    }

    /**
     * Base insert method.
     *
     * @param tableName
     * @param values    table values to insert
     */

    public long insert(String tableName, String nullColumnHack,
                       ContentValues values) {
        return this.database.insert(tableName, null, values);
    }

    /**
     * Base delete method.
     *
     * @param table name of the table
     * @param id    id of the item to be deleted
     */

    public long delete(String table, long id) {
        return this.database.delete(table, "_id = " + id, null);
    }

    /**
     * Fetches a row from table by given id.
     *
     * @param table  table to fetch from
     * @param column column to fetch
     */
    public Cursor fetchRow(String table, String column, boolean distinct) {
        return this.database.query(distinct, table, new String[]{column},
                null, null, null, null, null, null);
    }

    /**
     * Fetches all data from table
     *
     * @param table   table name
     * @param columns columns to return, passing null will return all table data
     * @return {@link Cursor} with all data in table
     */
    public Cursor fetchTableData(String table, String[] columns) {
        return this.database
                .query(table, columns, null, null, null, null, null);
    }

    /**
     * Fetches all data from table
     *
     * @param table   table name
     * @param columns columns to return, passing null will return all table data
     * @param groupBy group by
     * @return {@link Cursor} with all data in table
     */
    public Cursor fetchTableData(String table, String[] columns, String groupBy) {
        return this.database.query(table, columns, null, null, null, null,
                groupBy);
    }

    /**
     * @param table
     * @param columns
     * @param selection
     * @param selectionArgs
     * @param groupBy
     * @param having
     * @param orderBy
     * @return
     */
    public Cursor fetchRow(String table, String[] columns, String selection,
                           String[] selectionArgs, String groupBy, String having,
                           String orderBy) {
        return this.database.query(table, columns, selection, selectionArgs,
                groupBy, having, orderBy);
    }

    /**
     * @param sql
     * @param selectionArgs
     * @return
     */
    public Cursor rawQuery(String sql, String[] selectionArgs) {
        return this.database.rawQuery(sql, selectionArgs);

    }

    /**
     * @return
     */
    public long update(String table, ContentValues values, String whereClause,
                       String[] whereArgs) {
        return this.database.update(table, values, whereClause, whereArgs);
    }

    /**
     * @return true if there are any expenses in written in table
     */
    protected boolean isEmpty(String tableName) {
        Cursor cursor = fetchTableData(tableName, new String[]{"count(*)"});

        if (cursor != null) {
            cursor.moveToFirst();
            if (cursor.getInt(0) == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param rowName
     * @param rowValue
     * @param table
     * @return true if the table contains the value
     */
    protected boolean contains(String table, String rowName, String rowValue) {
        // fix for apostrophe bug
        if (rowValue.contains("'")) {
            int indexOfApostrophe = rowValue.indexOf("'");
            rowValue = rowValue.substring(0, indexOfApostrophe) + "'"
                    + rowValue.substring(indexOfApostrophe, rowValue.length() - 1);
        }
        Cursor result = fetchRow(table, null, table + "." + rowName
                + " = '" + rowValue + "'", null, null, null, null);
        if (result.moveToFirst()) {
            return true;
        }
        return false;
    }

}
