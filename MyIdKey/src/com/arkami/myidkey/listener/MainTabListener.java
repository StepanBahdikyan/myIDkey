/**
 *
 */
package com.arkami.myidkey.listener;

import android.app.Activity;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.arkami.myidkey.R;
import com.arkami.myidkey.activity.SherlockFragmentOnBackButtonPressed;

/**
 * @author sbahdikyan
 */
public class MainTabListener implements ActionBar.TabListener {

    private SherlockFragmentOnBackButtonPressed fragment;
    private Activity mainActivity;

    /**
     * @param fragment to be started
     */
    public MainTabListener(SherlockFragmentOnBackButtonPressed fragment, Activity mainActivity) {
        this.fragment = fragment;
        this.mainActivity = mainActivity;
    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        mainActivity.invalidateOptionsMenu();
        ft.replace(R.id.tabcontent, this.fragment);

    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
        mainActivity.invalidateOptionsMenu();
    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {
        mainActivity.invalidateOptionsMenu();
    }

    public  SherlockFragmentOnBackButtonPressed getFragment(){
        return this.fragment;
    }
}
