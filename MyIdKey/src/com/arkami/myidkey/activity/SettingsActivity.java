/**
 *
 */
package com.arkami.myidkey.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.arkami.myidkey.R;

/**
 * @author sbahdikyan
 */
@SuppressLint("NewApi")
public class SettingsActivity extends SherlockFragmentOnBackButtonPressed {

    private TextView importButton;
    private TextView exportButton;
    private TextView passwordSettings;
    private PasswordGeneratorDialog passwordGeneratorDialog;

    private Context context;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.settings, container, false);

        ActionBar actionBar = getSherlockActivity().getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setCustomView(R.layout.actionbar_settings);
        this.context = this.getSherlockActivity();
        passwordGeneratorDialog = new PasswordGeneratorDialog(context);
        initializeImportCSVButton();
        initializeExportCSVButton();
        initializePasswordSettings();


        InputMethodManager imm = (InputMethodManager) this.getSherlockActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        return view;
    }

    /**
     *
     */
    private void initializeImportCSVButton() {
        this.importButton = (TextView) this.view
                .findViewById(R.id.settings_import_from_csv);
        this.importButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String path = Environment.getExternalStorageDirectory().getPath();
                Intent fileIntent = new Intent(SettingsActivity.this.view.getContext(), FileChooserActivity.class);
                fileIntent.putExtra(FileChooserActivity.PATH, path);
                fileIntent.putExtra(FileChooserActivity.IMPORT,true);
                startActivity(fileIntent);
            }
        });
    }

    /**
     *
     */
    private void initializePasswordSettings() {
        this.passwordSettings = (TextView) this.view
                .findViewById(R.id.settings_password);
        this.passwordSettings.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                   passwordGeneratorDialog.show();
            }
        });
    }

    /**
     *
     */
    private void initializeExportCSVButton() {
        this.exportButton = (TextView) this.view
                .findViewById(R.id.settings_export_csv);
        this.exportButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                ChooseFile chooseFile = new ChooseFile(
//                        SettingsActivity.this.getSherlockActivity());

            }
        });
    }

    @Override
    public void onBackPressed() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
