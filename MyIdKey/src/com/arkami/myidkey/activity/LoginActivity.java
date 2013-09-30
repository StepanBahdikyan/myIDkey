/**
 *
 */
package com.arkami.myidkey.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.arkami.myidkey.MainActivity;
import com.arkami.myidkey.R;
import com.arkami.myidkey.backend.LoginManager;

/**
 * @author sbahdikyan
 */
public class LoginActivity extends SherlockActivity {

    private Button loginButton;
    private EditText username;
    private EditText password;
    private Button generatePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        initializeLoginButton();
        initializeGeneratePasswordButton();
        password = (EditText) findViewById(R.id.password);
        username = (EditText) findViewById(R.id.user_name_input);
    }

    /**
     * Sets onClickListener
     */
    private void initializeLoginButton() {
        this.loginButton = (Button) findViewById(R.id.login_button);
        this.loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (LoginManager.login(username.getText().toString(), LoginActivity.this)) {
                    Intent keyCards = new Intent(LoginActivity.this,
                            MainActivity.class);
                    keyCards.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    keyCards.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(keyCards);
                    LoginActivity.this.finish();
                    return;
                }
                Toast.makeText(LoginActivity.this, "can not login",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     *
     */
    private void initializeGeneratePasswordButton() {
        this.generatePassword = (Button) findViewById(R.id.password_generate);
        generatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog passwordGenerator = new PasswordGeneratorDialog(LoginActivity.this);
                passwordGenerator.show();
            }
        });
    }
}
