package com.arkami.myidkey.activity;

import java.io.File;
import java.io.IOException;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.arkami.myidkey.R;

/**
 * Created with IntelliJ IDEA.
 * User: sbahdikyan
 * Date: 13-7-12
 * Time: 17:14
 * To change this template use File | Settings | File Templates.
 */
public class SoundRecordingActivity extends Dialog {

    private MediaRecorder recorder;
    private File audiofile = null;
    private static final String TAG = "SoundRecordingActivity";
    private ImageView startButton;
//    private ImageView wave;
    private TextView manual_text;
    private View pauseButton;
    private View cancelButton;
    private Button done;
    private Uri uri;
    private Context context;
    public static final String BROADCAST_MESSAGE_AUDIO_RECORDED_ACTION = "com.arkami.android.sound.recorded";
    public static final String BROADCAST_MESSAGE_AUDIO_RECORDED_EXTRA = "com.arkami.android.sound.recorded.extra";

    public SoundRecordingActivity(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.recording_pop_up);
//        wave = (ImageView) findViewById(R.id.wave);
        done = (Button) findViewById(R.id.done_recording);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopRecording(v);
            }
        });
        pauseButton = findViewById(R.id.record_pause);
        this.manual_text = (TextView) findViewById(R.id.tap_microphone_to_record);
        startButton = (ImageView) findViewById(R.id.record_icon);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    startRecording(v);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
        cancelButton = findViewById(R.id.record_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });
    }

    public void startRecording(View view) throws IOException {

        startButton.setVisibility(View.GONE);
        pauseButton.setVisibility(View.VISIBLE);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseButton.setVisibility(View.GONE);
                startButton.setVisibility(View.VISIBLE);
                stopRecording(v);
            }
        });
//        startButton.setImageResource(R.drawable.pause);
//        startButton.setBackground(getResources().getDrawable(R.drawable.abs__item_background_holo_light));
//        startButton.setPadding(50,50,50,50);
        cancelButton.setEnabled(true);
//        wave.setVisibility(View.VISIBLE);
        manual_text.setText("Recording");
        File sampleDir = Environment.getExternalStorageDirectory();
        try {
            audiofile = File.createTempFile("sound", ".3gp", sampleDir);
            Log.w("path to file ", audiofile.getPath());
        } catch (IOException e) {
            Log.e(TAG, "sdcard access error");
            return;
        }
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(audiofile.getAbsolutePath());
        recorder.prepare();
        recorder.start();
    }

    /**
     * @param view
     */
    public void stopRecording(View view) {
        startButton.setImageResource(R.drawable.record);
//        startButton.setBackground(context.getResources().getDrawable(R.drawable.abs__item_background_holo_light));
        startButton.setPadding(50, 50, 50, 50);
//        wave.setVisibility(View.GONE);
        manual_text.setText(R.string.tap_microphone);
        startButton.setEnabled(true);
        cancelButton.setEnabled(false);
        recorder.stop();
        recorder.release();
        addRecordingToMediaLibrary();
    }

    protected void addRecordingToMediaLibrary() {
        ContentValues values = new ContentValues(4);
        long current = System.currentTimeMillis();
        values.put(MediaStore.Audio.Media.TITLE, "audio" + audiofile.getName());
        values.put(MediaStore.Audio.Media.DATE_ADDED, (int) (current / 1000));
        values.put(MediaStore.Audio.Media.MIME_TYPE, "audio/3gp");
        values.put(MediaStore.Audio.Media.DATA, audiofile.getAbsolutePath());

        ContentResolver contentResolver = context.getContentResolver();
        Uri base = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        uri = contentResolver.insert(base, values);


//        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
        Log.w("uri ", uri.getPath());
        Intent broadcastIntent = new Intent(context, context.getClass());
        broadcastIntent.setAction(BROADCAST_MESSAGE_AUDIO_RECORDED_ACTION);
        broadcastIntent.putExtra(BROADCAST_MESSAGE_AUDIO_RECORDED_EXTRA, audiofile.getPath());
        broadcastIntent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        LocalBroadcastManager.getInstance(context).sendBroadcast(broadcastIntent);
        onBackPressed();
    }

    
}

