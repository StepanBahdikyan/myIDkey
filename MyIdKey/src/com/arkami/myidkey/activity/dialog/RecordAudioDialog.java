package com.arkami.myidkey.activity.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.actionbarsherlock.view.Window;
import com.arkami.myidkey.R;
import com.arkami.myidkey.activity.KeyCardEditActivity;
import com.arkami.myidkey.activity.SoundRecordingActivity;

/**
 * Created with IntelliJ IDEA.
 * User: sbahdikyan
 * Date: 13-7-2
 * Time: 10:36
 * To change this template use File | Settings | File Templates.
 */
public class RecordAudioDialog extends Dialog {
    private Button takeAudioButton;
    private Button chooseExistingAudioButton;
    private Button cancelAudio;
    private Activity parent;
//    private Uri uri;

    public RecordAudioDialog(Context context) {
        super(context);
        parent = (Activity) context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature((int) Window.FEATURE_NO_TITLE);
        setContentView(R.layout.actionbar_audio_popup);
        setRecordAudioButton();
        setChooseExistingPhotoButton();
        setCancelButton();
        super.onCreate(savedInstanceState);
    }

    /**
     * Cancel button behavior.
     */
    private void setCancelButton() {
        cancelAudio = (Button) this.findViewById(R.id.audio_menu_popup_cancel);
        cancelAudio.setOnClickListener(new View.OnClickListener() {

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
        chooseExistingAudioButton = (Button) this
                .findViewById(R.id.audio_menu_popup_choose_existing_audio_button);
        chooseExistingAudioButton
                .setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent();
                        intent.setType("audio/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        parent.startActivityForResult(
                                Intent.createChooser(intent, "Select Audio "),
                                KeyCardEditActivity.CHOOSE_AUDIO_ACTIVITY_REQUEST_CODE);
                        dismiss();

                    }
                });

    }

    /**
     *
     */
    private void setRecordAudioButton() {
        takeAudioButton = (Button) this
                .findViewById(R.id.audio_menu_popup_record_audio_button);
        takeAudioButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // recording audio

//                Intent audioIntent = new Intent(
//                        MediaStore.Audio.Media.RECORD_SOUND_ACTION);
//
//                audioIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//
//                parent.startActivityForResult(audioIntent,
//                        KeyCardEditActivity.CAPTURE_AUDIO_ACTIVITY_REQUEST_CODE);
//                Intent recordAudio = new Intent(parent, SoundRecordingActivity.class);
//                parent.startActivity(recordAudio);
                Dialog  soundRecordingActivity = new SoundRecordingActivity(parent);
                soundRecordingActivity.show();
                dismiss();
            }
        });
    }

//    public void setUri(Uri uri){
//        this.uri = uri;
//    }

//    public Uri getUri(){
//        return this.uri;
//    }
}