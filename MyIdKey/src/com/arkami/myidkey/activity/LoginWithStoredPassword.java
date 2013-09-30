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
 * Date: 13-8-15
 * Time: 15:00
 * To change this template use File | Settings | File Templates.
 */
public class LoginWithStoredPassword extends Activity {
    private Button loginButton;
    private EditText passPhrase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_after);

        initializeLoginButton();
        passPhrase = (EditText) findViewById(R.id.passphrase);

    }

    /**
     * Sets onClickListener
     */
    private void initializeLoginButton() {
        this.loginButton = (Button) findViewById(R.id.login_button);
        this.loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (LoginManager.login(passPhrase.getText().toString(), LoginWithStoredPassword.this)) {
                    Intent keyCards = new Intent(LoginWithStoredPassword.this,
                            MainActivity.class);
                    keyCards.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    keyCards.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(keyCards);
                    LoginWithStoredPassword.this.finish();
                    return;
                }
                Toast.makeText(LoginWithStoredPassword.this, "cannot login",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
