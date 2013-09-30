/**
 *
 */
package com.arkami.myidkey.listener;

import android.app.Activity;
import android.support.v4.app.FragmentTransaction;

import android.view.View;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.arkami.myidkey.MainActivity;
import com.arkami.myidkey.R;
import com.arkami.myidkey.activity.SherlockFragmentOnBackButtonPressed;
import com.arkami.myidkey.view.TabTag;

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
        if ((tab.getTag() != null)) {
            TabTag tabTag = (TabTag) tab.getTag();
            tab.getCustomView().setVisibility(View.GONE);
            if (tabTag.getSelectedTabLayoutId() != tab.getCustomView().getId()) {
                tab.setCustomView(tabTag.getSelectedTabLayoutId());
            }
        }
        ft.replace(R.id.tabcontent, this.fragment);

    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
        mainActivity.invalidateOptionsMenu();
        if ((tab.getTag() != null)) {

            TabTag tabTag = (TabTag) tab.getTag();
            tab.getCustomView().setVisibility(View.GONE);
            tab.setCustomView(tabTag.getUnselectedTabLayoutId());

        }
    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {
        onTabSelected(tab, ft);
    }

    public SherlockFragmentOnBackButtonPressed getFragment() {
        return this.fragment;
    }
}
