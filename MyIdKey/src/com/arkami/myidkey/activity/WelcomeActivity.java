/**
 *
 */
package com.arkami.myidkey.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.arkami.myidkey.R;
import com.arkami.myidkey.R.color;
import com.arkami.myidkey.backend.LoginManager;

/**
 * @author sbahdikyan
 */
public class WelcomeActivity extends SherlockActivity {

    private Button pairButton;
    private TextView welcomeText;
    private TextView youMustText;
    private ImageView devices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        this.pairButton = (Button) findViewById(R.id.pair_now);
        this.welcomeText = (TextView) findViewById(R.id.welcome_text);
        this.youMustText = (TextView) findViewById(R.id.you_must);
        this.devices = (ImageView) findViewById(R.id.welcome_devices);

        this.pairButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                devices.setImageResource(R.drawable.pairing2);
                pairButton.setVisibility(View.INVISIBLE);
                youMustText.setVisibility(View.INVISIBLE);

                welcomeText.setText(getString(R.string.turn_on_and_));
                devices.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        welcomeText.setText(R.string.connecting_devices);
                        devices.setImageResource(R.drawable.pairing3);
                        AsyncTask<Void, Void, Void> connectingAsyncTask = new AsyncTask<Void, Void, Void>() {

                            @Override
                            protected Void doInBackground(Void... params) {
                                Looper.prepare();

                                try {
                                    Thread.sleep(500);

                                    devices.setOnClickListener(null);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                return null;

                            }

                            @Override
                            protected void onPostExecute(Void result) {
                                super.onPostExecute(result);
                                welcomeText.setTextColor(getResources()
                                        .getColor(color.app_blue));
                                welcomeText.setText(R.string.success);
                                pairButton.setText(R.string.login);
                                youMustText.setText(R.string.now_connected);
                                pairButton.setVisibility(View.VISIBLE);
                                youMustText.setVisibility(View.VISIBLE);
                                devices.setImageResource(R.drawable.pairing4);
                                pairButton
                                        .setOnClickListener(new View.OnClickListener() {

                                            @Override
                                            public void onClick(View v) {
                                                Intent login;
                                                if (LoginManager.hasPassPhrase(WelcomeActivity.this)) {
                                                    login = new Intent(
                                                            WelcomeActivity.this,
                                                            LoginWithStoredPassword.class);
                                                } else {
                                                    login = new Intent(
                                                            WelcomeActivity.this,
                                                            LoginWithPassPhrase.class);
                                                }

                                                login.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                                login.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                                WelcomeActivity.this.finish();
                                                startActivity(login);
                                            }
                                        });
                            }
                        };
                        connectingAsyncTask.execute(new Void[]{});

                    }
                });
            }
        });
    }
}
