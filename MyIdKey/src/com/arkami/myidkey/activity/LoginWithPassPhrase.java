package com.arkami.myidkey.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.arkami.myidkey.MainActivity;
import com.arkami.myidkey.R;
import com.arkami.myidkey.backend.LoginManager;

/**
 * Created with IntelliJ IDEA.
 * User: sbahdikyan
 * Date: 13-8-13
 * Time: 18:01
 * To change this template use File | Settings | File Templates.
 */
public class LoginWithPassPhrase extends Activity {
    private Button loginButton;
    private EditText passPhrase;
    private EditText repeatPassPhrase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_with_passphrase);

        initializeLoginButton();
        passPhrase = (EditText) findViewById(R.id.passphrase);
        repeatPassPhrase = (EditText) findViewById(R.id.repeat_passphrase);
    }

    /**
     * Sets onClickListener
     */
    private void initializeLoginButton() {
        this.loginButton = (Button) findViewById(R.id.login_button);
        this.loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (LoginManager.savePassphrase(passPhrase.getText().toString(),
                        repeatPassPhrase.getText().toString(), LoginWithPassPhrase.this)) {


                    Intent keyCards = new Intent(LoginWithPassPhrase.this,
                            MainActivity.class);
                    keyCards.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    keyCards.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(keyCards);
                    LoginWithPassPhrase.this.finish();
                    return;
                }
                Toast.makeText(LoginWithPassPhrase.this, "cannot login", Toast.LENGTH_LONG).show();

            }
        });
    }
}
