/**
 *
 */
package com.arkami.myidkey.activity.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import com.actionbarsherlock.view.Window;
import com.arkami.myidkey.R;
import com.arkami.myidkey.activity.KeyCardEditActivity;

/**
 * @author sbahdikyan
 */
public class TakePicturePopUp extends Dialog {

    private Button takePictureButton;
    private Button chooseExistingPhotoButton;
    private Button cancelPhoto;
    private Activity parent;
    private Uri photoUri;

    public TakePicturePopUp(Context context) {
        super(context);
        parent = (Activity) context;
    }

    public void setPhotoUri(Uri uri){
        this.photoUri = uri;
    }

    public Uri getPhotoUri(){
        return this.photoUri;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature((int) Window.FEATURE_NO_TITLE);
        setContentView(R.layout.actionbar_photo_popup);
        setTakePictureButton();
        setChooseExistingPhotoButton();
        setCancelButton();
        super.onCreate(savedInstanceState);
    }

    /**
     * Cancel button behavior.
     */
    private void setCancelButton() {
        cancelPhoto = (Button) this.findViewById(R.id.photo_menu_popup_cancel);
        cancelPhoto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    /**
     *
     */
    private void setChooseExistingPhotoButton() {
        chooseExistingPhotoButton = (Button) this
                .findViewById(R.id.photo_menu_popup_choose_existing_photo_button);
        chooseExistingPhotoButton
                .setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent pictureIntent = new Intent(
                                Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                        // pictureIntent
                        // .setData(MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                        // pictureIntent.setType("image/*");

                        // call method in the activity
                        parent.startActivityForResult(
                                pictureIntent,
                                KeyCardEditActivity.CHOOSE_IMAGE_ACTIVITY_REQUEST_CODE);
                        dismiss();

                    }
                });

    }

    /**
     *
     */
    private void setTakePictureButton() {
        takePictureButton = (Button) this
                .findViewById(R.id.photo_menu_popup_take_photo_button);
        takePictureButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // create Intent to take a picture and return control to the calling application
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

//                photoUri = parent.getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri); // set the image file name

                // start the image capture Intent
                parent.startActivityForResult(intent, KeyCardEditActivity.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);



//                Intent pictureIntent = new Intent(
//                        MediaStore.ACTION_IMAGE_CAPTURE);
//
////              parent.getContentResolver().
////                        insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
//                parent.startActivityForResult(pictureIntent,
//                        KeyCardEditActivity.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                dismiss();
            }
        });
    }
}
