package com.arkami.myidkey.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.arkami.myidkey.R;
import com.arkami.myidkey.database.datasources.PasswordSettingsDataSource;
import com.arkami.myidkey.database.tables.PasswordSettings;
import com.arkami.myidkey.listener.OnSeekBarChangedListener;
import com.arkami.myidkey.util.Generator;

/**
 * Created with IntelliJ IDEA.
 * User: sbahdikyan
 * Date: 13-7-23
 * Time: 16:00
 * To change this template use File | Settings | File Templates.
 */
public class PasswordGeneratorDialog extends Dialog implements PasswordChangeListener {
    private SeekBar seekBar;
    private TextView numberOfCharsTextView;
    //    private TextView numberOfAZTextView;
//    private TextView numberOfAzTextView;
    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener;
    private ToggleButton numberToggle;
    private ToggleButton capitalLettersToggle;
    private ToggleButton smallLettersToggle;
    private ToggleButton specialSymbolsToggle;
    private ImageView passwordStrengthColor;
//    private TextView password;
    private TextView passwordStrength;
    private ToggleButton hexadecimalToggle;
    private Button okButton;
    private Button cancelButton;
//    private EditText passwordTextView;
    private PasswordSettingsDataSource passwordSettingsDataSource;
    private PasswordSettings passwordSettings;
//    private Activity context;

    /**
     * @param context
     */
    public PasswordGeneratorDialog(Context context) {
        super(context);
        this.passwordSettingsDataSource = new PasswordSettingsDataSource(context);
        this.passwordSettings = new PasswordSettings();
        passwordSettings = passwordSettingsDataSource.getPasswordSettings();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.password_generator_layout);

        okButton = (Button) findViewById(R.id.password_generator_ok);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                passwordTextView.setText(password.getText());
                passwordSettings = new PasswordSettings();
                passwordSettings.setPasswordLength(seekBar.getProgress());
                passwordSettings.setHexadecimal(hexadecimalToggle.isChecked());
                passwordSettings.setHasSpecialCharacters(specialSymbolsToggle.isChecked());
                passwordSettings.setHasUppercaseLetters(capitalLettersToggle.isChecked());
                passwordSettings.setHasSmallLetters(smallLettersToggle.isChecked());
                passwordSettings.setHasNumbers(numberToggle.isChecked());
                passwordSettingsDataSource.saveSettings(passwordSettings);
                dismiss();
            }
        });
        cancelButton = (Button) findViewById(R.id.password_generator_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        numberOfCharsTextView = (TextView) findViewById(R.id.password_generator_number_of_chars_text);
        if(passwordSettings != null){
            numberOfCharsTextView.setText(passwordSettings.getPasswordLength()+"");
        }else{
            numberOfCharsTextView.setText("8");
        }

        passwordStrength = (TextView) findViewById(R.id.password_generator_password_strength);
        passwordStrengthColor = (ImageView) findViewById(R.id.password_generator_password_color);
//        password = (TextView) findViewById(R.id.password_generator_password);
//        numberOfAZTextView = (TextView)findViewById(R.id.password_generator_aa_zz_text);
//        numberOfAzTextView = (TextView)findViewById(R.id.password_generator_a_z_text);
        numberToggle = (ToggleButton) findViewById(R.id.password_generator_numbers_toggle);
        hexadecimalToggle = (ToggleButton) findViewById(R.id.password_generator_hexadecimal_toggle);
        capitalLettersToggle = (ToggleButton) findViewById(R.id.password_generator_aa_zz_toggle);
        specialSymbolsToggle = (ToggleButton) findViewById(R.id.password_generator_special_character_toggle);
        smallLettersToggle = (ToggleButton) findViewById(R.id.password_generator_a_z_toggle);

        numberToggle.setOnCheckedChangeListener(createTooggleListener());
        capitalLettersToggle.setOnCheckedChangeListener(createTooggleListener());
        specialSymbolsToggle.setOnCheckedChangeListener(createTooggleListener());
        smallLettersToggle.setOnCheckedChangeListener(createTooggleListener());
        OnSeekBarChangedListener onSeekBarChangedListener = new OnSeekBarChangedListener(this, numberOfCharsTextView);

        seekBar = (SeekBar) findViewById(R.id.password_generator_number_of_characters_seekbar);
        seekBar.setOnSeekBarChangeListener(onSeekBarChangedListener);
        if (passwordSettings != null) {
            seekBar.setProgress(passwordSettings.getPasswordLength());
            capitalLettersToggle.setChecked(passwordSettings.hasUppercaseLetters());
            hexadecimalToggle.setChecked(passwordSettings.isHexadecimal());
            numberToggle.setChecked(passwordSettings.hasNumbers());
            specialSymbolsToggle.setChecked(passwordSettings.hasSpecialCharacters());
            smallLettersToggle.setChecked(passwordSettings.hasSmallLetters());

        } else {
            seekBar.setProgress(8);
        }
        hexadecimalToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    numberToggle.setChecked(false);
                    capitalLettersToggle.setChecked(false);
                    smallLettersToggle.setChecked(false);
                    specialSymbolsToggle.setChecked(false);
                    setPassword();
                    return;
                }

            }
        });
        smallLettersToggle.setChecked(true);
    }

    /**
     * sets text of password with generated string.
     */
    private void setPassword() {
        String passwordString = Generator.generate(
                specialSymbolsToggle.isChecked(),
                numberToggle.isChecked(),
                smallLettersToggle.isChecked(),
                capitalLettersToggle.isChecked(),
                hexadecimalToggle.isChecked(),
                seekBar.getProgress());
//        password.setText(passwordString);
        if ((seekBar.getProgress() > 6)
                && (
                (smallLettersToggle.isChecked())
                        || (capitalLettersToggle.isChecked())
                        || (specialSymbolsToggle.isChecked()
                        || (hexadecimalToggle.isChecked()))
        )
                ) {

//            passwordStrengthColor.setBackground(new ColorDrawable(Color.RED));
            passwordStrength.setText("Strong");
            return;
        }
//        passwordStrengthColor.setBackground(new ColorDrawable(Color.GREEN));
        passwordStrength.setText("Weak");
    }

    /**
     * @return toggle listener.
     */
    private CompoundButton.OnCheckedChangeListener createTooggleListener() {
        if (onCheckedChangeListener == null) {
            onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (hexadecimalToggle.isChecked() && (isChecked)) {
                        hexadecimalToggle.setChecked(false);
                    }
                    setPassword();
                }
            };
        }
        return onCheckedChangeListener;
    }

    @Override
    public void onChange() {
        setPassword();

    }
}
