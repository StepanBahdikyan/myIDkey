package com.arkami.myidkey.activity.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import android.widget.ToggleButton;
import com.arkami.myidkey.R;
import com.arkami.myidkey.activity.KeyCardEditActivity;
import com.arkami.myidkey.database.datasources.KeyCardDataSource;
import com.arkami.myidkey.database.tables.KeyCard;

/**
 * Created with IntelliJ IDEA.
 * User: sbahdikyan
 * Date: 13-9-17
 * Time: 17:04
 * To change this template use File | Settings | File Templates.
 */
public class LongClickKeyCardMenuDialog extends Dialog {

    private TextView delete;
    private TextView view;
    private TextView edit;
    private TextView export;
    private ToggleButton favorite;
    private KeyCard keyCard;
    private Button cancel;
    private KeyCardDataSource keyCardDataSource;

    /**
     * @param context
     */
    public LongClickKeyCardMenuDialog(Context context, KeyCard keyCard) {
        super(context);
        this.keyCard = keyCard;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (keyCard == null) {
            dismiss();
        }
        keyCardDataSource = new KeyCardDataSource(getContext());
        setTitle(keyCard.getName());
        setContentView(R.layout.key_card_long_click_menu);
        delete = (TextView) findViewById(R.id
                .key_card_on_long_click_menu_delete);
        view = (TextView) findViewById(R.id
                .key_card_on_long_click_menu_view);
        export = (TextView) findViewById(R.id
                .key_card_on_long_click_menu_export);
        edit = (TextView) findViewById(R.id
                .key_card_on_long_click_menu_edit);
        favorite = (ToggleButton) findViewById(R.id
                .keycard_favorite_toggle);
        cancel = (Button) findViewById(R.id
                .key_card_on_long_click_menu_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        favorite.setChecked(keyCard.isFavourite());
        favorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                keyCard.setFavourite(isChecked);
                keyCardDataSource.update(keyCard);
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewKeyCardIntent = new Intent(view.getContext(),
                        KeyCardEditActivity.class);
                viewKeyCardIntent.putExtra(KeyCardEditActivity.KEY_CARD_ID,
                        keyCard.getId());
                getContext().startActivity(viewKeyCardIntent);
                dismiss();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editKeyCardIntent = new Intent(view.getContext(),
                        KeyCardEditActivity.class);
                editKeyCardIntent.putExtra(KeyCardEditActivity.KEY_CARD_ID,
                        keyCard.getId());
                editKeyCardIntent.putExtra(KeyCardEditActivity.IS_IN_EDIT_MODE,
                        true);
                getContext().startActivity(editKeyCardIntent);
                dismiss();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // XXX delete
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        getContext());
                builder.setCancelable(true)
                        .setMessage(R.string.delete_message)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        keyCardDataSource.delete(keyCard.getId());
                                        dialog.dismiss();
                                    }
                                })
                        .setNegativeButton("No",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        // if this button is clicked, just close
                                        // the dialog box and do nothing
                                        dialog.dismiss();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }
    //    public static Dialog createDeleteDialog(keyCard keyCard,
//                                            KeyCardDataSource keyCardDataSource){

//    }
}
