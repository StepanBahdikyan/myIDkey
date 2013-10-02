/**
 *
 */
package com.arkami.myidkey.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.arkami.myidkey.R;
import com.arkami.myidkey.activity.customuicomponents.CustomComponentHolder;
import com.arkami.myidkey.activity.customuicomponents.CustomViewComponent;
import com.arkami.myidkey.activity.customuicomponents.predifined.BankAccount;
import com.arkami.myidkey.activity.customuicomponents.predifined.Clothing;
import com.arkami.myidkey.activity.customuicomponents.predifined.CreditCardNew;
import com.arkami.myidkey.activity.customuicomponents.predifined.Database;
import com.arkami.myidkey.activity.customuicomponents.predifined.DrivingLicense;
import com.arkami.myidkey.activity.customuicomponents.predifined.EmailAccount;
import com.arkami.myidkey.activity.customuicomponents.predifined.FormFill;
import com.arkami.myidkey.activity.customuicomponents.predifined.InstantMessenger;
import com.arkami.myidkey.activity.customuicomponents.predifined.Insurance;
import com.arkami.myidkey.activity.customuicomponents.predifined.Membership;
import com.arkami.myidkey.activity.customuicomponents.predifined.Note;
import com.arkami.myidkey.activity.customuicomponents.predifined.Passport;
import com.arkami.myidkey.activity.customuicomponents.predifined.Prescription;
import com.arkami.myidkey.activity.customuicomponents.predifined.RewardsProgram;
import com.arkami.myidkey.activity.customuicomponents.predifined.Server;
import com.arkami.myidkey.activity.customuicomponents.predifined.Site;
import com.arkami.myidkey.activity.customuicomponents.predifined.SocialSecurity;
import com.arkami.myidkey.activity.customuicomponents.predifined.SoftwareLicense;
import com.arkami.myidkey.activity.customuicomponents.predifined.Vehicle;
import com.arkami.myidkey.activity.customuicomponents.predifined.WiFi;
import com.arkami.myidkey.activity.dialog.RecordAudioDialog;
import com.arkami.myidkey.activity.dialog.SelectKeyCardTypeDialog;
import com.arkami.myidkey.activity.dialog.TakePicturePopUp;
import com.arkami.myidkey.database.datasources.FileDataSource;
import com.arkami.myidkey.database.datasources.KeyCardDataSource;
import com.arkami.myidkey.database.datasources.KeyCardTypesDataSource;
import com.arkami.myidkey.database.datasources.TagDataSource;
import com.arkami.myidkey.database.datasources.ValuesDataSource;
import com.arkami.myidkey.database.tables.FileObject;
import com.arkami.myidkey.database.tables.KeyCard;
import com.arkami.myidkey.database.tables.KeyCardType;
import com.arkami.myidkey.database.tables.Tag;
import com.arkami.myidkey.database.tables.Value;
import com.arkami.myidkey.dialog.TagDialog;
import com.arkami.myidkey.listeners.DatePickerListener;
import com.arkami.myidkey.listeners.TagChangeListener;
import com.arkami.myidkey.model.FileTypeEnum;
import com.arkami.myidkey.model.KeyCardTypeEnum;
import com.arkami.myidkey.util.Cache;
import com.arkami.myidkey.util.Common;
import com.arkami.myidkey.util.CopyFile;
import com.arkami.myidkey.util.SavingTask;

/**
 * @author sbahdikyan
 */

public class KeyCardEditActivity extends SherlockFragmentActivity implements
        DatePickerListener, Parcelable, TagChangeListener, TypeListener {

    private LinearLayout selectedTags;
    private TextView dateText;
    private Spinner typeSpinner;
    private TextView tagSpinner;
    private TextView type;
    private ImageButton recordAudio;
    private EditText keyCardName;
    private ImageButton actionBarCameraButton;
    private ActionBar actionBar;
    // XXX make new class for the star button, thank you :P
    private ToggleButton favourite;
    private ImageView typeArrows;
    // private ArrayList<FileObject> appPhotos;
    // private ArrayList<FileObject> appAudios;
    private LinearLayout photosLinearLayout;
    private LinearLayout audioLinearLayout;
    private RelativeLayout photosListRelativeLayout;
    private RelativeLayout audioListRelativeLayout;
    private TagDialog selectTagDialog;
    private LinearLayout bottom;
    private CustomComponentHolder componentHolder;
    private Button deleteButton;
    private KeyCard keyCard;
    private KeyCardDataSource keyCardDataSource;
    private FileDataSource fileDataSource;
    private ValuesDataSource valuesDataSource;
    private TagDataSource tagDataSource;
    private KeyCardTypesDataSource keyCardTypeDataSource;
    private boolean isInEditMode;
    public static final String KEY_CARD_ID = "keyCardId";
    // private static final String CREDIT_CARD_PHOTO_LIST =
    // "creditCardPhotoList";
    // private static final String CREDIT_CARD_AUDIO_LIST =
    // "creditCardAudioList";
    private static Uri photoUri;
    // private static Uri audioUri;
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    public static final int CAPTURE_AUDIO_ACTIVITY_REQUEST_CODE = 101;
    public static final int CHOOSE_IMAGE_ACTIVITY_REQUEST_CODE = 200;
    public static final int CHOOSE_AUDIO_ACTIVITY_REQUEST_CODE = 201;
    public static final String IS_IN_EDIT_MODE = "isInEditMode";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.w("onCreate", "called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.key_card);
        typeArrows = (ImageView) findViewById(R.id.key_card_type_arrows);
        if (savedInstanceState != null) {
            photoUri = savedInstanceState.getParcelable("photoUri");
            keyCard = savedInstanceState.getParcelable("keyCard");
            isInEditMode = savedInstanceState
                    .getBoolean(IS_IN_EDIT_MODE, false);
        }
        isInEditMode = getIntent().getBooleanExtra(IS_IN_EDIT_MODE, false);
        // this.keyCard = Service.getInstance(this).getKeyCard(keyCardId);
        this.keyCardDataSource = new KeyCardDataSource(this);
        this.valuesDataSource = new ValuesDataSource(this);
        this.fileDataSource = new FileDataSource(this);
        this.tagDataSource = new TagDataSource(this);
        bottom = (LinearLayout) findViewById(R.id.key_card_specific_items_layout);
        long keyCardId = this.getIntent().getLongExtra(KEY_CARD_ID, -1);
        if (keyCardId == -1) {
            keyCard = new KeyCard();
        } else {
            try {
                this.keyCard = keyCardDataSource.get(keyCardId);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                Toast.makeText(this, "No such keycard found in database.",
                        Toast.LENGTH_LONG).show();
                this.finish();

            }
        }
        Log.w("Selected key card: ", keyCard.toString());
        keyCardName = (EditText) findViewById(R.id.key_card_name_edit_text);
        keyCardName.setText(keyCard.getName());
        //
        this.selectTagDialog = new TagDialog(KeyCardEditActivity.this,
                KeyCardEditActivity.this);
        selectedTags = (LinearLayout) findViewById(R.id.key_card_tags_selected);
        initializeTagSpinner();
        // initFavourite();
        keyCardTypeDataSource = new KeyCardTypesDataSource(
                this);
        keyCardTypeDataSource.open();
        if (keyCard.getKeyCardTypeId() == null) {
            keyCard.setKeyCardTypeId(1L);
        }
        KeyCardType keyCardType = keyCardTypeDataSource.getTypeById(keyCard
                .getKeyCardTypeId());
        // Cache.getInstance(this)
        // .getKeyCardTypeEnum(keyCard.getKeyCardTypeId()).getTitle();
        this.componentHolder = new CustomComponentHolder(this);
        initializeTypeSpinner(keyCardType);
        initializeFilesLists();

        initializeBottom();
        setDeleteButton();
        if ((keyCard.getValueIds() == null)
                || (keyCard.getValueIds().length < 1) || (isInEditMode)) {
            setKeyCardActionbarEdit();
        } else {
            setKeyCardActionbarView();
        }

    }

    private void setDeleteButton() {
        deleteButton = (Button) this.findViewById(R.id.delete_button);

        if (keyCard.getId() == null) {
            deleteButton.setVisibility(View.GONE);
        }

        deleteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // XXX delete
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        KeyCardEditActivity.this);
                builder.setCancelable(true)
                        .setMessage(R.string.delete_message)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        deleteKeyCard();
                                        KeyCardEditActivity.this.finish();
                                    }
                                })
                        .setNegativeButton("No",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        // if this button is clicked, just close
                                        // the dialog box and do nothing
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = builder.create();
                // show it
                alertDialog.show();
            }
        });
    }

    /**
     * Sets views and their listeners if its in edit mode
     */
    private void setKeyCardActionbarEdit() {
        isInEditMode = true;
        actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.actionbar_key_card_edit);
        if (keyCard.getId() != null) {

            typeArrows.setVisibility(View.INVISIBLE);
            deleteButton.setVisibility(View.VISIBLE);
        }
        Button save = (Button) actionBar.getCustomView().findViewById(
                R.id.actionbar_key_card_edit_save);
        save.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String keyCardNameString = keyCardName.getText().toString()
                        .trim();
                if (keyCardNameString.length() < 1) {
                    AlertDialog.Builder requiredAlert = new AlertDialog.Builder(
                            KeyCardEditActivity.this).setMessage(
                            R.string.message_reqired).setPositiveButton(
                            android.R.string.ok,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {

                                }
                            });
                    requiredAlert.show();
                    return;
                }
                keyCard.setName(keyCardNameString);

                // if is new key card(new key cards have no values)
                if (keyCard.getId() == null) {
                    // cannot save two key cards with the same name
                    if (keyCardDataSource.contains(KeyCard.NAME,
                            keyCardNameString)) {
                        AlertDialog.Builder sameKeyCardNameAlert = new AlertDialog.Builder(
                                KeyCardEditActivity.this).setMessage(
                                R.string.same_key_card_name).setPositiveButton(
                                android.R.string.ok,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {

                                    }
                                });
                        sameKeyCardNameAlert.show();
                        return;
                    }
                    long keyCardId = keyCardDataSource.insert(keyCard);
                    if (keyCardId != -1) {
                        keyCard.setId(keyCardId);
                    } else {
                        AlertDialog.Builder errorDatabaseAlert = new AlertDialog.Builder(
                                KeyCardEditActivity.this).setMessage(
                                "Error. Could not save to database.")
                                .setPositiveButton(android.R.string.ok,
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(
                                                    DialogInterface dialog,
                                                    int which) {

                                            }
                                        });
                        errorDatabaseAlert.show();
                        return;
                    }
                    long[] valueIds = new long[componentHolder
                            .getCustomComponents().size()];
                    int i = 0;
                    for (CustomViewComponent component : componentHolder
                            .getCustomComponents()) {

                        component.getValue().setKeyCardId(keyCard.getId());
                        long id = valuesDataSource.insert(component.getValue());
                        valueIds[i++] = id;
                    }
                    keyCard.setValueIds(valueIds);
                }

                SavingTask savingTask = new SavingTask(KeyCardEditActivity.this);
                savingTask.execute(componentHolder.getValues());

                //
                KeyCardTypesDataSource keyCardTypesDataSource = new KeyCardTypesDataSource(
                        KeyCardEditActivity.this);
                keyCardTypesDataSource.open();
                try {
                    long id = keyCardTypesDataSource
                            .getByName((String) type.getText());
                    if (id != -1) {
                        keyCard.setKeyCardTypeId(id);
                    }
                } catch (IllegalAccessException e) {

                    e.printStackTrace();
                }
                updateFiles();
                keyCardDataSource.update(keyCard);
                setKeyCardActionbarView();
            }
        });
        actionBarCameraButton = (ImageButton) actionBar.getCustomView()
                .findViewById(R.id.actionbar_key_card_edit_photo);
        actionBarCameraButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final Dialog takePicturePopUp = new TakePicturePopUp(
                        KeyCardEditActivity.this);
                // photoUri = getContentResolver().
                // insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new
                // ContentValues());
                // if (photoUri == null) {
                // return;
                // }
                // ((TakePicturePopUp) takePicturePopUp).setPhotoUri(photoUri);
                // parent.getContentResolver().
                // insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                takePicturePopUp.show();
            }
        });

        recordAudio = (ImageButton) actionBar.getCustomView().findViewById(
                R.id.actionbar_key_card_edit_audio);

        recordAudio.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                final Dialog audioDialog = new RecordAudioDialog(
                        KeyCardEditActivity.this);
                // audioUri = getContentResolver().
                // insert(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, new
                // ContentValues());
                // ((RecordAudioDialog) audioDialog).setUri(audioUri);
                audioDialog.show();
            }
        });
        favourite = (ToggleButton) actionBar.getCustomView().findViewById(
                R.id.actionbar_key_card_view_favorite);
        favourite.setChecked(keyCard.isFavourite());
        favourite
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView,
                                                 boolean isChecked) {
                        keyCard.setFavourite(isChecked);
                    }
                });
        setEditable(true);
    }

    /**
     * searches for a difference between the list of files in the database and
     * the one that this key card have
     */
    private void updateFiles() {
        if (keyCard.getFileIds() != null) {
            Arrays.sort(keyCard.getFileIds());
            try {
                long[] inDB = keyCardDataSource.getFileIds(keyCard.getId());
                boolean deleteThisFile;
                Arrays.sort(inDB);
                for (int i = 0; i < inDB.length; i++) {
                    deleteThisFile = true;
                    for (int j = 0; j < keyCard.getFileIds().length; j++) {
                        if (inDB[i] == keyCard.getFileIds()[j]) {
                            deleteThisFile = false;
                            break;
                        }
                    }
                    if (deleteThisFile) {
                        fileDataSource.delete(inDB[i]);
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Sets views and their listeners if its in edit mode
     */
    private void setKeyCardActionbarView() {
        typeArrows.setVisibility(View.INVISIBLE);
        isInEditMode = false;
        type.setOnClickListener(null);
        actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.actionbar_key_card_view);
        deleteButton.setVisibility(View.GONE);
        ImageButton edit = (ImageButton) actionBar.getCustomView()
                .findViewById(R.id.actionbar_key_card_view_edit);
        edit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // enable all fields
                setKeyCardActionbarEdit();
            }
        });

        ImageButton share = (ImageButton) actionBar.getCustomView()
                .findViewById(R.id.actionbar_key_card_view_share);
        share.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // XXX share
                Toast.makeText(KeyCardEditActivity.this, "share",
                        Toast.LENGTH_SHORT).show();
            }
        });

        favourite = (ToggleButton) actionBar.getCustomView().findViewById(
                R.id.actionbar_key_card_view_favorite);
        favourite.setChecked(keyCard.isFavourite());
        favourite
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView,
                                                 boolean isChecked) {
                        keyCard.setFavourite(isChecked);
                    }
                });
        setEditable(false);
    }

    /**
     * Sets screen components edit mode
     *
     * @param editable if the components are editable or not.
     */
    private void setEditable(boolean editable) {

        this.keyCardName.setEnabled(editable);
        this.favourite.setEnabled(editable);
        if (editable) {
            this.tagSpinner.setVisibility(View.VISIBLE);
        } else {
            this.tagSpinner.setVisibility(View.INVISIBLE);
        }
        for (int i = 0; i < this.photosLinearLayout.getChildCount(); i++) {
            RelativeLayout photoLayout = (RelativeLayout) this.photosLinearLayout
                    .getChildAt(i);
            if (editable) {
                photoLayout.getChildAt(1).setVisibility(View.VISIBLE);
            } else {
                photoLayout.getChildAt(1).setVisibility(View.GONE);
            }
        }

        for (int i = 0; i < this.audioLinearLayout.getChildCount(); i++) {
            RelativeLayout audioLayout = (RelativeLayout) this.audioLinearLayout
                    .getChildAt(i);
            if (editable) {
                audioLayout.getChildAt(1).setVisibility(View.VISIBLE);
            } else {
                audioLayout.getChildAt(1).setVisibility(View.GONE);
            }
        }

        for (int i = 0; i < this.selectedTags.getChildCount(); i++) {
            LinearLayout tag = (LinearLayout) this.selectedTags.getChildAt(i);
            RelativeLayout tagLayout = (RelativeLayout) tag.getChildAt(0);
            if (editable) {
                tagLayout.getChildAt(1).setVisibility(View.VISIBLE);
            } else {
                tagLayout.getChildAt(1).setVisibility(View.GONE);
            }
        }

        for (CustomViewComponent component : this.componentHolder
                .getCustomComponents()) {
            component.setEditable(editable);
        }

    }

    /**
     * Creates views for the keycard's values
     */
    private void initializeBottom() {
        // make some magic and show key cards' content
        try {
            if (keyCard.getValueIds() != null) {
                componentHolder.removeAllViews();
                for (long valueId : this.keyCard.getValueIds()) {
                    Value value;
                    value = valuesDataSource.get(valueId);
                    componentHolder.addCustomComponent(new CustomViewComponent(
                            this, value));
                }
            }
            bottom.removeAllViews();
            bottom.addView(this.componentHolder);
            bottom.invalidate();
            bottom.requestLayout();
        } catch (IllegalAccessException e) {
            initializeCustomBottomScreenAfterSelectionOfAKeyCardType(keyCardTypeDataSource.getTypeById(keyCard
                    .getKeyCardTypeId()).getName());
            e.printStackTrace();
        }

    }

    /**
     * creates list with audio and video files.
     */
    private void initializeFilesLists() {
        photosListRelativeLayout = (RelativeLayout) findViewById(R.id.key_card_photos);
        photosLinearLayout = (LinearLayout) findViewById(R.id.key_card_photos_list);
        audioListRelativeLayout = (RelativeLayout) findViewById(R.id.key_card_sounds);
        audioLinearLayout = (LinearLayout) findViewById(R.id.key_card_audio_list);
        List<FileObject> files = this.fileDataSource.getFiles(this.keyCard
                .getFileIds());

        for (FileObject fileObject : files) {
            addFileToList(fileObject);
        }
    }

    /**
     * Initializes favourite button behavior.
     */
    // private void initFavourite() {
    //
    //
    // // favourite.setOnClickListener(new View.OnClickListener() {
    // //
    // // @Override
    // // public void onClick(View v) {
    // //
    // // if (keyCard.isFavourite()) {
    // // favourite
    // // .setBackgroundResource(R.drawable.favourite_selected_not);
    // // keyCard.setFavourite(false);
    // //// Service.getInstance(KeyCardEditActivity.this)
    // //// .getKeyCard(keyCard.getId())
    // //// .setFavourite(keyCard.isFavourite());
    // //// keyCardDataSource.setIsFavoirite(keyCard.getId(),
    // //// keyCard.isFavourite());
    // // return;
    // // }
    // // favourite.setBackgroundResource(R.drawable.favourite_selected);
    // // keyCard.setFavourite(true);
    // // //XXX make notification for other view
    // //// Service.getInstance(KeyCardEditActivity.this)
    // //// .getKeyCard(keyCard.getId())
    // //// .setFavourite(keyCard.isFavourite());
    // //// keyCardDataSource.setIsFavoirite(keyCard.getId(),
    // //// keyCard.isFavourite());
    // // }
    // //
    // // });
    // }

    /**
     *
     */
    private void initializeTagSpinner() {

        tagSpinner = (TextView) findViewById(R.id.keyCardTagSpinner);
        tagSpinner.setFocusable(true);
        tagSpinner.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    Toast.makeText(getApplicationContext(),
                            "tagSpinner onFocusChange()", Toast.LENGTH_LONG)
                            .show();
            }
        });
        tagSpinner.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                selectTagDialog.show();
                if ((keyCard != null) && (keyCard.getId() != null)
                        && (keyCard.getTagIds() != null)) {
                    for (long tagId : keyCard.getTagIds()) {
                        selectTagDialog.getAdapter().addSelected(tagId);
                    }
                }
            }
        });
        if (keyCard.getTagIds() != null) {
            for (Long tagId : keyCard.getTagIds()) {
                try {
                    if (tagId > 0) {
                        addTagToView(tagDataSource.get(tagId));
                    }
                } catch (IllegalAccessException e) {
                    Toast.makeText(this,
                            "couldm't find tag with id = " + tagId,
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    }



    /**
     *
     */
    private void initializeCustomBottomScreenAfterSelectionOfAKeyCardType(
            String typeName) {
        KeyCardTypeEnum type = KeyCardTypeEnum.getValueByTitle(typeName);
        if (this.componentHolder == null) {
            this.componentHolder = new CustomComponentHolder(this);
        }
        KeyCardEditActivity.this.componentHolder.removeAllViews();
        KeyCardEditActivity.this.componentHolder.clear();
        try {
            switch (type) {
                case site:
                    componentHolder.addValues(new Site());
                    break;
                case bank_account:
                    componentHolder.addValues(new BankAccount());
                    break;
                case clothing:
                    componentHolder.addValues(new Clothing());
                    break;
                case credit_card:
                    componentHolder.addValues(new CreditCardNew());
                    break;
                case database:
                    componentHolder.addValues(new Database());
                    break;
                case drivers_license:
                    componentHolder.addValues(new DrivingLicense());
                    break;
                case email:
                    componentHolder.addValues(new EmailAccount());
                    break;
                case form_fill:
                    componentHolder.addValues(new FormFill());
                    break;
                case instant_messenger:
                    componentHolder.addValues(new InstantMessenger());
                    break;
                case insurance:
                    componentHolder.addValues(new Insurance());
                    break;
                case membership:
                    componentHolder.addValues(new Membership());
                    break;
                case note:
                    componentHolder.addValues(new Note());
                    break;
                case passport:
                    componentHolder.addValues(new Passport());
                    break;
                case prescription:
                    componentHolder.addValues(new Prescription());
                    break;
                case reward_program:
                    componentHolder.addValues(new RewardsProgram());
                    break;
                case wifi:
                    componentHolder.addValues(new WiFi());
                    break;
                case vehicle:
                    componentHolder.addValues(new Vehicle());
                    break;
                case software_licence:
                    componentHolder.addValues(new SoftwareLicense());
                    break;
                case social_security:
                    componentHolder.addValues(new SocialSecurity());
                    break;
                case server:
                    componentHolder.addValues(new Server());
                    break;
                default:
                    break;
            }
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        }
        bottom.removeAllViews();
        bottom.addView(componentHolder);
        bottom.invalidate();
        bottom.requestLayout();
    }

    /**
     * Creates listener for setting keycard's type
     *
     * @return
     */
    private OnClickListener createTypeOnclickListener() {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog typeDialog = new SelectKeyCardTypeDialog(
                        KeyCardEditActivity.this, KeyCardEditActivity.this);
                typeDialog.show();
            }
        };
    }

    /**
     * Fills key card spinner with items from keyCardType enum and sets items
     * type az selected;
     */
    private void initializeTypeSpinner(KeyCardType keyCardType) {
        type = (TextView) findViewById(R.id.key_card_specific_items_title);
        type.setText(keyCardType.getName());
        type.setOnClickListener(createTypeOnclickListener());
        initializeCustomBottomScreenAfterSelectionOfAKeyCardType(keyCardType.getName());
        keyCard.setKeyCardTypeId(keyCardType.getId());
//XXX new!!!!
//        if (((componentHolder == null) || (componentHolder.getCustomComponents() == null))
//                || ((componentHolder.getCustomComponents().isEmpty() == false)
//                && (componentHolder.getCustomComponents().get(0)
//                .getValue().getId() != null))
//                && (keyCard.getValueIds() != null)
//                && (keyCard.getValueIds().length > 0)) {
//            return;
//        }


//        XXX New end!!!


//        typeSpinner = (Spinner) findViewById(R.id.key_card_type_spinner);
//
//        // Create an ArrayAdapter using the string array and a default
//        // spinner
//        // layout
//
//        List<KeyCardType> allTypes = Cache
//                .getInstance(KeyCardEditActivity.this).getKeyCardTypes();
//        final List<CharSequence> stringTypes = new ArrayList<CharSequence>();
//
//        for (KeyCardType type : allTypes) {
//            stringTypes.add(type.getName());
//        }
//
//        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
//                this, android.R.layout.simple_spinner_item, stringTypes);
//
//        // Specify the layout to use when the list of choices appears
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        typeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//            @Override
//            public void onItemSelected(AdapterView<?> arg0, View arg1,
//                                       int index, long arg3) {
//                if ((componentHolder.getCustomComponents().isEmpty() == false)
//                        && (componentHolder.getCustomComponents().get(0)
//                        .getValue().getId() != null)
//                        && (keyCard.getValueIds() != null)
//                        && (keyCard.getValueIds().length > 0)) {
//                    return;
//                }
//                type.setText((String) stringTypes.get(index));
//                initializeCustomBottomScreenAfterSelectionOfAKeyCardType((String) stringTypes
//                        .get(index));
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> arg0) {
//
//            }
//        });
//
//        // Apply the adapter to the spinner
//        typeSpinner.setAdapter(adapter);
//
//        int itemsCount = typeSpinner.getAdapter().getCount();
//        for (int i = 0; i < itemsCount; i++) {
//            if (adapter.getItem(i).equals(typeName)) {
//                typeSpinner.setSelection(i);
//                // KeyCardTypeEnum type = KeyCardTypeEnum.valueOf(typeName);
//                // initializeBottomScreen(type);
//                if ((keyCard.getValueIds() != null)
//                        && (keyCard.getValueIds().length > 0)) {
//                    // this.typeSpinner.setClickable(false);
//                    this.typeSpinner.setEnabled(false);
//                }
//            }
//        }
    }

    @Override
    public void onDateSet(int id, String date) {
        if (id == R.id.key_card_credit_card_expire_date_picker) {
            dateText.setText(date);
        }
    }

    @Override
    public int describeContents() {

        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    /**
     *
     */
    private String getImageUrlFromResult(Intent data) {
        Uri selectedImage = data.getData();
        if (selectedImage == null) {
            throw new IllegalArgumentException(
                    "Cannot get image. No data in intent.");
        }
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String filePath = cursor.getString(columnIndex);
        cursor.close();
        return filePath;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if ((resultCode == RESULT_OK)) {

            //

            File file = null;
            FileObject fileObject = new FileObject();
            fileObject.setKeyCardAttribute(true);

            if ((requestCode == CHOOSE_IMAGE_ACTIVITY_REQUEST_CODE)
                    || (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE)) {

                //
                File image;
                try {
                    image = new File(getImageUrlFromResult(data));
                    // copy file
                    File destination = new File(
                            KeyCardEditActivity.this
                                    .getFilesDir()
                                    + getString(R.string.key_cards_files_folder));
                    String copyToFileName = destination.getPath() + "/"
                            + image.getName();

                    FileChooserActivity
                            .copyFiles(
                                    image,
                                    destination,
                                    KeyCardEditActivity.this);
                    image = new File(copyToFileName);
                } catch (IllegalArgumentException ex) {
                    Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG)
                            .show();
                    ex.printStackTrace();
                    return;
                }
                fileObject.setFileType(1L);
                fileObject.setKeyCardAttribute(true);
                fileObject.setName(image.getName());
                fileObject.setPathToFile(image.getPath());
                fileObject.setSize(image.getTotalSpace());
                fileObject.setCreateDate(new Date().getTime());
                fileObject.setModifyDate(new Date().getTime());
                addFileToList(fileObject);
                return;
                // File file = null;
                // FileObject fileObject = new FileObject();
                // fileObject.setKeyCardAttribute(true);
                // if ((requestCode == CHOOSE_IMAGE_ACTIVITY_REQUEST_CODE)
                // || (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE)) {
                // fileObject.setFileType(Cache.getInstance(this).getFileTypeId(
                // FileTypeEnum.photo));
                // String selectedImageUrl;
                // if (data == null) {
                // selectedImageUrl = photoUri.getPath();
                // } else {
                // String[] filePathColumn = {MediaColumns.DATA};
                // Cursor cursor = getContentResolver().query(data.getData(),
                // filePathColumn, null, null, null);
                // cursor.moveToFirst();
                // int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                // selectedImageUrl = cursor.getString(columnIndex);
                // cursor.close();
                // }
                // if (selectedImageUrl == null) {
                // return;
                // }
                //
                //
                // if ((selectedImageUrl != null)) {
                // file = new File(selectedImageUrl);
                // if ((file != null) && (file.exists()) && (file.isFile())
                // && (file.canRead())) {
                // // try {
                // // Common.copyFileToAppFolder(file, this);
                // // move to receiving message
                // fileObject.setCreateDate(new Date().getTime());
                // fileObject.setModifyDate(new Date().getTime());
                // fileObject.setName(file.getName());
                // fileObject.setPathToFile( file.getPath());
                // //fileObject.setPathToFile("/myAppFolder/" + file.getName());
                // fileObject.setSize(file.length());
                // fileObject.setId(fileDataSource.insert(fileObject));
                // //
                // // } catch (IOException e) {
                // // e.printStackTrace();
                // // }
                //
                // } else{
                // Log.w("key card File is null", (file == null) + "");
                // Log.w("key card File is existing", file.exists() + "");
                // Log.w("key card File is file",file.isFile() + "");
                // Log.w("key card File is readable", file.canRead() + "");
                // }
                // }
            }

            if ((requestCode == CHOOSE_AUDIO_ACTIVITY_REQUEST_CODE)
                    || (requestCode == CAPTURE_AUDIO_ACTIVITY_REQUEST_CODE)) {

                fileObject.setFileType(Cache.getInstance(this).getFileTypeId(
                        FileTypeEnum.audio));
                Uri selectedAudio = null;
                if (data != null) {
                    selectedAudio = data.getData();
                }

                String[] filePathColumn = {MediaStore.Audio.Media.DATA};
                if ((selectedAudio != null) && (filePathColumn != null)) {

                    Cursor cursor = getContentResolver().query(selectedAudio,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();
                    file = new File(filePath);

                    File destination = new File(
                            KeyCardEditActivity.this
                                    .getFilesDir()
                                    + getString(R.string.key_cards_files_folder));
                    String copyToFileName = destination.getPath() + "/"
                            + file.getName();

                    FileChooserActivity
                            .copyFiles(
                                    file,
                                    destination,
                                    KeyCardEditActivity.this);
                    file = new File(copyToFileName);

                    fileObject.setCreateDate(new Date().getTime());
                    fileObject.setModifyDate(new Date().getTime());
                    fileObject.setName(file.getName());
                    fileObject.setPathToFile(file.getPath());
                    fileObject.setSize(file.length());
                    fileObject.setKeyCardAttribute(true);
                    fileObject.setId(fileDataSource.insert(fileObject));
                    // appAudios.add(file);
                }
            }
            // addFileToList(fileObject);

            // try {
            // Common.copyFileToAppFolder(audio, this);
            // XXX duplicate code
            long[] newIds = Arrays.copyOf(keyCard.getFileIds(),
                    keyCard.getFileIds().length + 1);
            long newId = fileObject.getId();
            newIds[newIds.length - 1] = newId;
            Toast.makeText(this, newId + "", Toast.LENGTH_SHORT).show();
            keyCard.setFileIds(newIds);
            // keyCardDataSource.update(keyCard);
            Log.w("file ", "added");
        }
        Toast.makeText(this, "no file", Toast.LENGTH_LONG).show();

        // super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Adds view to linear layout
     */
    private void addFileToList(final FileObject fileObject) {
        if (fileObject == null) {
            return;
        }
        if (fileObject.getId() == null) {
            fileObject.setId(fileDataSource.insert(fileObject));
            long[] fileIds = Arrays.copyOf(keyCard.getFileIds(),
                    keyCard.getFileIds().length + 1);
            fileIds[fileIds.length - 1] = fileObject.getId();
            keyCard.setFileIds(fileIds);
        }
        // long[] newIds = Arrays.copyOf(keyCard.getFileIds(),
        // keyCard.getFileIds().length + 1);
        // long newId = fileObject.getId();
        // newIds[newIds.length - 1] = newId;
        // Toast.makeText(this, newId + "", Toast.LENGTH_SHORT).show();
        // keyCard.setFileIds(newIds);
        // keyCardDataSource.update(keyCard);
        final View view = View.inflate(this, R.layout.photo_audio_list_item,
                null);

        TextView fileName = (TextView) view
                .findViewById(R.id.photo_audio_list_item_file_name);
        TextView fileSize = (TextView) view
                .findViewById(R.id.photo_audio_list_item_file_size);
        ImageView thumb = (ImageView) view
                .findViewById(R.id.photo_audio_list_item_thumb);
        ImageButton close = (ImageButton) view
                .findViewById(R.id.photo_audio_list_item_close_button);
        fileName.setText(fileObject.getName());
        fileSize.setText(fileObject.getSize() + " bytes");
        view.setVisibility(View.VISIBLE);

        switch (Cache.getInstance(this).getFileTypeEnum(
                fileObject.getFileType())) {
            case audio:
                // add audio

                close.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        audioLinearLayout.removeView(view);
                        if (audioLinearLayout.getChildCount() == 0) {
                            audioListRelativeLayout.setVisibility(View.GONE);
                        }
                        keyCard = removeFileID(fileObject.getId(), keyCard);
                        // KeyCardEditActivity.this.keyCardDataSource.update(keyCard);

                    }
                });
                thumb.setImageResource(R.drawable.speaker);
                thumb.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setAction(android.content.Intent.ACTION_VIEW);
                        File file = new File(fileObject.getPathToFile());
                        intent.setDataAndType(Uri.fromFile(file), "audio/3gp");
                        Log.w("Uri.fromFile(file) = ", fileObject.getPathToFile());
                        startActivity(intent);
                    }
                });
                audioLinearLayout.addView(view);
                audioListRelativeLayout.setVisibility(View.VISIBLE);
                break;
            case photo:
                // add photo
                // for photo file
                close.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        photosLinearLayout.removeView(view);

                        if (photosLinearLayout.getChildCount() == 0) {
                            photosListRelativeLayout.setVisibility(View.GONE);
                        }
                        if (fileObject.getId() != null) {
                            keyCard = removeFileID(fileObject.getId(), keyCard);
                        }
                    }
                });

                thumb.setImageBitmap(Common.getImageFromIntent(
                        fileObject.getPathToFile(), true));
                thumb.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent viewImageIntent = new Intent(
                                KeyCardEditActivity.this, ViewImageIActivity.class);
                        viewImageIntent.putExtra(ViewImageIActivity.IMAGE_KEY,
                                fileObject);
                        startActivity(viewImageIntent);
                    }
                });
                photosLinearLayout.addView(view);
                photosListRelativeLayout.setVisibility(View.VISIBLE);
                // end for photo file
                break;
            default:
                break;
        }
    }

    /**
     * @param idToRemove
     * @param keyCard
     * @return
     */
    private KeyCard removeFileID(long idToRemove, KeyCard keyCard) {
        long[] fileIds = new long[keyCard.getFileIds().length - 1];
        int j = 0;
        for (int i = 0; i < keyCard.getFileIds().length; i++) {
            if (keyCard.getFileIds()[i] != idToRemove) {
                fileIds[j++] = keyCard.getFileIds()[i];
            }
        }
        keyCard.setFileIds(fileIds);
        Toast.makeText(this, "file id removed " + idToRemove,
                Toast.LENGTH_SHORT).show();
        return keyCard;
    }

    /**
     * Adds a tag to the layout with tags;
     *
     * @param tag
     */
    private void addTagToView(final Tag tag) {
        final View tagItem = View.inflate(this, R.layout.tag_item_layout, null);
        TextView tagName = (TextView) tagItem.findViewById(R.id.tag_item_name);
        ImageView removeButton = (ImageView) tagItem
                .findViewById(R.id.tag_item_close_button);
        removeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                selectedTags.removeView(tagItem);
                keyCard.setTagIds(KeyCard.removeId(tag.getId(),
                        keyCard.getTagIds()));
                // keyCardDataSource.update(keyCard);
                if ((selectTagDialog != null)
                        && (selectTagDialog.getAdapter() != null)) {
                    selectTagDialog.getAdapter().removeSelected(tag.getId());
                    selectTagDialog.getAdapter().notifyDataSetChanged();
                }
            }
        });
        tagName.setText(tag.getName());
        selectedTags.addView(tagItem);
    }

    @Override
    public void onTagsSet() {
        List<Tag> items = selectTagDialog.getAdapter().getItems();
        selectedTags.removeAllViews();
        long[] selectedIds = selectTagDialog.getAdapter().getSelectedIds();
        keyCard.setTagIds(selectedIds);
        // keyCardDataSource.update(keyCard);
        for (int i = 0; i < items.size(); i++) {
            final Tag tag = items.get(i);
            if (selectTagDialog.getAdapter().isSelected(tag.getId())) {
                addTagToView(tag);
            }
        }
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        if (broadcastReceiver != null) {
            Log.w("onBackPressed", "unregistering receiver.");
            LocalBroadcastManager.getInstance(this).unregisterReceiver(
                    broadcastReceiver);
            broadcastReceiver = null;
        }
        if (keyCard.getValueIds() == null || keyCard.getValueIds().length < 1) {
            deleteKeyCard();
        }
    }

    /**
     * Deletes key card from database with all of its' properties.
     */
    private void deleteKeyCard() {
        if (keyCard.getId() != null) {
            keyCardDataSource.delete(KeyCard.TABLE_NAME, keyCard.getId());
            for (int i = 0; i < keyCard.getFileIds().length; i++) {
                fileDataSource.delete(keyCard.getFileIds()[i]);
            }
            for (int i = 0; i < keyCard.getValueIds().length; i++) {
                valuesDataSource.delete(keyCard.getValueIds()[i]);
            }
        }
    }

    @Override
    public void onTagAdded() {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("keyCard", keyCard);
        outState.putBoolean("isInEditMode", isInEditMode);
        outState.putParcelableArrayList("componentHolder",
                (ArrayList<? extends Parcelable>) componentHolder
                        .getCustomComponents());
        outState.putParcelable("photoUri", photoUri);
        Log.w("onSaveInstanceState", "called");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState); // To change body of
        // overridden
        // methods use File
        // | Settings | File
        // Templates.
        this.keyCard = savedInstanceState.getParcelable("keyCard");
        this.photoUri = savedInstanceState.getParcelable("photoUri");
        this.isInEditMode = savedInstanceState.getBoolean("isInEditMode");
        this.componentHolder
                .setCustomComponents(savedInstanceState
                        .<CustomViewComponent>getParcelableArrayList("componentHolder"));
        Log.w("onRestoreInstanceState", "called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w("onResume", "called");

        // here we specify MESSAGE_SENT_ACTION as an action
        IntentFilter filter = new IntentFilter();
        filter.addAction(SoundRecordingActivity.BROADCAST_MESSAGE_AUDIO_RECORDED_ACTION);
        filter.addAction(CopyFile.BROADCAST_MESSAGE_PHOTO_COPY_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(
                broadcastReceiver, filter);
    }

    @Override
    protected void onPause() {
        Log.w("onPause", "called");
        super.onPause();
    }

    /**
     * receives a message when photo or audio file is attached to keycard.
     */
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (intent.hasExtra(CopyFile.BROADCAST_MESSAGE_PHOTO_COPY_EXTRA)) {
                String filePath = bundle
                        .getString(CopyFile.BROADCAST_MESSAGE_PHOTO_COPY_EXTRA);
                FileObject photo = new FileObject();
                photo.setFileType(Cache.getInstance(KeyCardEditActivity.this)
                        .getFileTypeId(FileTypeEnum.photo));

                File photoFile = new File(filePath);
                photo.setCreateDate(new Date().getTime());
                photo.setModifyDate(new Date().getTime());
                photo.setName(photoFile.getName());
                photo.setPathToFile(photoFile.getPath());
                photo.setSize(photoFile.length());
                // photo.setId(fileDataSource.insert(photo));
                addFileToList(photo);

                LocalBroadcastManager.getInstance(KeyCardEditActivity.this)
                        .unregisterReceiver(broadcastReceiver);
                return;
            }
            String filePath = bundle
                    .getString(SoundRecordingActivity.BROADCAST_MESSAGE_AUDIO_RECORDED_EXTRA);
            FileObject file = new FileObject();
            file.setFileType(Cache.getInstance(KeyCardEditActivity.this)
                    .getFileTypeId(FileTypeEnum.audio));
            File audio = new File(filePath);
            file.setCreateDate(new Date().getTime());
            file.setModifyDate(new Date().getTime());
            file.setName(audio.getName());
            file.setPathToFile(filePath);
            file.setSize(audio.length());
            // file.setId(fileDataSource.insert(file));
            addFileToList(file);
            Log.w("message received ", filePath);
            // do something useful with message
            LocalBroadcastManager.getInstance(KeyCardEditActivity.this)
                    .unregisterReceiver(broadcastReceiver);
        }
    };

    @Override
    public void onTypeSet(KeyCardType type) {
        initializeTypeSpinner(type);
    }
}
