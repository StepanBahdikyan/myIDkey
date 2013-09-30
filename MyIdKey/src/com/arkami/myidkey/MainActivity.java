package com.arkami.myidkey;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.internal.app.ActionBarImpl;
import com.actionbarsherlock.internal.app.ActionBarWrapper;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.Window;
import com.arkami.myidkey.activity.FavouritesActivity;
import com.arkami.myidkey.activity.FoldersActivity;
import com.arkami.myidkey.activity.KeyCardsActivity;
import com.arkami.myidkey.activity.SettingsActivity;
import com.arkami.myidkey.listener.MainTabListener;

public class MainActivity extends SherlockFragmentActivity {

    private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
    private static ActionBar actionBar;
    private static Activity activity;
    private static MainTabListener foldersListener;
    private Tab foldersTab;
    public static final String SELECTED_TAB = "selectedTab";

    private Tab keyCardsTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_main);
        activity = this;
//         <bool name="abs__action_bar_embed_tabs">false</bool>
//        <bool name="android:action_bar_embed_tabs">false</bool>

        actionBar = getSupportActionBar();
//        actionBar.setCustomView(R.layout.actionbar_search);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.abs__item_background_holo_light));

        keyCardsTab = actionBar.newTab();
        //Fragment keyCardsFragment = new KeyCardsActivity();

        keyCardsTab.setTabListener(
                new MainTabListener(new KeyCardsActivity(), this));
        keyCardsTab.setCustomView(R.layout.actionbar_tab_keycards);

//        keyCardsTab.setText(R.string.action_key_kards);
//		keyCardsTab.setIcon(R.drawable.key_cards);
        actionBar.addTab(keyCardsTab);

//        Tab favouritekeyCardsTab = actionBar.newTab();
//        favouritekeyCardsTab
//                .setTabListener(new MainTabListener(new FavouritesActivity(), this));
//
//
////		favouritekeyCardsTab.setIcon(R.drawable.favourite_selected);
//        favouritekeyCardsTab.setCustomView(R.layout.actionbar_tab_favorites);
//        actionBar.addTab(favouritekeyCardsTab);

        foldersTab = actionBar.newTab();
        foldersListener = new MainTabListener(new FoldersActivity(), this);
        foldersTab
                .setTabListener(foldersListener);
        foldersListener.getFragment().onBackPressed();

//		favouritekeyCardsTab.setIcon(R.drawable.favourite_selected);
        foldersTab.setCustomView(R.layout.actionbar_tab_folders);
        actionBar.addTab(foldersTab);


        Tab settingsTab = actionBar.newTab();
        settingsTab.setTabListener(
                new MainTabListener(new SettingsActivity(), this));
        settingsTab.setCustomView(R.layout.actionbar_tab_settings);

//        settingsTab.setIcon(android.R.drawable.ic_lock_lock);
        // settingsTab.setIcon(R.drawable.favourite_star);
        actionBar.addTab(settingsTab);
        actionBar.getTabAt(0).setCustomView(new View(this));
        if (getIntent().hasExtra(SELECTED_TAB)) {
            actionBar.selectTab(actionBar.getTabAt(getIntent().getIntExtra(SELECTED_TAB, 0)));
        }
        // ActionBar actionBar = getActionBar();
        // actionBar.setDisplayShowTitleEnabled(false);
        // actionBar.setDisplayUseLogoEnabled(false);

        // Intent pairActivity = new Intent(this, WelcomeActivity.class);
        // startActivity(pairActivity);

        // actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        // actionBar.setCustomView(R.layout.actionbar_main);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.abs__item_background_holo_light));
        //pre-ICS


        if (actionBar instanceof ActionBarImpl) {
            enableEmbeddedTabs(actionBar);

//ICS and forward
        } else if (actionBar instanceof ActionBarWrapper) {
            try {
                Field actionBarField = actionBar.getClass().getDeclaredField("mActionBar");
                actionBarField.setAccessible(true);
                enableEmbeddedTabs(actionBarField.get(actionBar));
            } catch (Exception e) {
                Log.e("action bar", "Error enabling embedded tabs", e);
            }
        }

//helper method

        // ActionBar actionBar = getActionBar();
        // actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        // actionBar.setDisplayShowHomeEnabled(false);
        // actionBar.setDisplayShowTitleEnabled(false);
        // actionBar.setDisplayUseLogoEnabled(false);
        //
        // actionBar.addTab(actionBar.newTab().setText(R.string.action_key_kards)
        // .setTabListener(this));
        // actionBar.addTab(actionBar.newTab().setText(R.string.action_favourites)
        // .setTabListener(this));
        // actionBar.addTab(actionBar.newTab().setText(R.string.action_settings)
        // .setTabListener(this));

        // Inflate the menu; this adds items to the action bar if it is present.

        // getMenuInflater().inflate(R.menu.bottom, menu);
        // getActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        return true;
    }

    private static void enableEmbeddedTabs(Object actionBar) {
        try {
            Method setHasEmbeddedTabsMethod = actionBar.getClass().getDeclaredMethod("setHasEmbeddedTabs", boolean.class);
            setHasEmbeddedTabsMethod.setAccessible(true);
            setHasEmbeddedTabsMethod.invoke(actionBar, true);
        } catch (Exception e) {
            Log.e("action bar", "Error marking actionbar embedded", e);
        }
    }

    @Override
    public void onBackPressed() {
        if (actionBar.getSelectedTab().equals(foldersTab)) {
            foldersListener.getFragment().onBackPressed();
            return;
        }
        finish();
    }

    @Override
    public void finish() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.super.finish();
            }
        }).setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setTitle("Do you want to close myIDkey?");
        Dialog quit = builder.create();
        quit.show();
        //super.finish();
    }

    public void bla(boolean isFavorite) {
        if (isFavorite) {
            keyCardsTab.setTabListener(new MainTabListener(new FavouritesActivity(), this));
        } else {
            keyCardsTab.setTabListener(new MainTabListener(new KeyCardsActivity(), this));
        }
    }
    //    /**
//     * initializes tabs again because of the overlaying expandable search view.
//     */
//    public static void initTabs(){
//        actionBar.setCustomView(R.layout.actionbar_search);
//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//        actionBar.setDisplayUseLogoEnabled(false);
////        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.abs__item_background_holo_light));
//
//        Tab keyCardsTab = actionBar.newTab();
//        //Fragment keyCardsFragment = new KeyCardsActivity();
//
//        keyCardsTab.setTabListener(
//                new MainTabListener(new KeyCardsActivity(),MainActivity.activity));
//        keyCardsTab.setText(R.string.action_key_kards);
////		keyCardsTab.setIcon(R.drawable.key_cards);
//        actionBar.addTab(keyCardsTab);
//
//        Tab favouritekeyCardsTab = actionBar.newTab();
//        favouritekeyCardsTab
//                .setTabListener(new MainTabListener(new FavouritesActivity(),MainActivity.activity));
////		favouritekeyCardsTab.setIcon(R.drawable.favourite_selected);
//        favouritekeyCardsTab.setText(R.string.action_favourites);
//        actionBar.addTab(favouritekeyCardsTab);
//
//        Tab settingsTab = actionBar.newTab();
//        settingsTab.setTabListener(
//                new MainTabListener(new SettingsActivity(),MainActivity.activity));
//        settingsTab.setText(R.string.action_settings);
////        settingsTab.setIcon(android.R.drawable.ic_lock_lock);
//        // settingsTab.setIcon(R.drawable.favourite_star);
//        actionBar.addTab(settingsTab);
//        enableEmbeddedTabs(actionBar);
//    }

}
