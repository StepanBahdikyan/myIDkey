package com.arkami.myidkey.listener;

import android.app.Dialog;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.arkami.myidkey.R;
import com.arkami.myidkey.activity.PasswordChangeListener;

/**
 * Created with IntelliJ IDEA.
 * User: sbahdikyan
 * Date: 13-7-23
 * Time: 15:51
 * To change this template use File | Settings | File Templates.
 */
public class OnSeekBarChangedListener implements OnSeekBarChangeListener {
    private Dialog dialog;
    private TextView textView;
//    private TextView passwordView;
    private SeekBar mSeekBar;

    public OnSeekBarChangedListener(Dialog dialog, TextView textView) {
        this.dialog = dialog;
//        this.passwordView = passwordView;
        this.textView = textView;
        mSeekBar = (SeekBar) dialog.findViewById(R.id.password_generator_number_of_characters_seekbar);
        mSeekBar.setOnSeekBarChangeListener(this);

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        textView.setText(String.valueOf(progress));
        if (dialog != null) {
            ((PasswordChangeListener) dialog).onChange();
        }
    }
}