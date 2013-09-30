package com.arkami.myidkey.activity.dialog;

import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.arkami.myidkey.R;
import com.arkami.myidkey.activity.KeyCardEditActivity;
import com.arkami.myidkey.activity.TypeListener;
import com.arkami.myidkey.adapters.KeyCardTypeAdapter;
import com.arkami.myidkey.database.datasources.KeyCardTypesDataSource;
import com.arkami.myidkey.database.tables.KeyCardType;

/**
 * Created with IntelliJ IDEA.
 * User: sbahdikyan
 * Date: 13-8-22
 * Time: 17:47
 * To change this template use File | Settings | File Templates.
 */
public class SelectKeyCardTypeDialog extends Dialog {
    private Context context;
    private TypeListener typeListener;
    private ListView types;
    private ArrayAdapter<KeyCardType> kayCardTypeArrayAdapter;
    private KeyCardTypesDataSource keyCardTypesDataSource;
    private List<KeyCardType> keyCardTypes;

    /**
     * Constructor
     *
     * @param context
     */
    public SelectKeyCardTypeDialog(Context context, TypeListener typeListener) {
        super(context);
        this.typeListener = typeListener;
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.key_card_type_pop_up);

        this.types = (ListView) findViewById(R.id.listView);
        this.types.setDivider(null);
        keyCardTypesDataSource = new KeyCardTypesDataSource(context);
        keyCardTypesDataSource.open();
        keyCardTypes = keyCardTypesDataSource.getAllTypes();
        this.setTitle("Select Key Card Type");
        this.setCancelable(true);
        kayCardTypeArrayAdapter = new KeyCardTypeAdapter(context, R.layout.list_view_row, keyCardTypes);
        types.setAdapter(kayCardTypeArrayAdapter);
        types.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context, "selected: " + keyCardTypes.get(position).getName(), Toast.LENGTH_LONG).show();
                typeListener.onTypeSet(keyCardTypes.get(position));
                dismiss();
            }
        });
    }
}
