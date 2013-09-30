package com.arkami.myidkey.activity.web;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.arkami.myidkey.R;

/**
 * Created with IntelliJ IDEA.
 * User: sbahdikyan
 * Date: 13-9-26
 * Time: 17:39
 * To change this template use File | Settings | File Templates.
 */
public class KeyCardsWebActivity extends SherlockFragment {
    private ListView listView;
    private ActionBar actionBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.sortable_list, container, false);
        listView = (ListView) view.findViewById(R.id.myListView);

        actionBar = getSherlockActivity().getSupportActionBar();
        actionBar.setIcon(android.R.color.transparent);
        actionBar.setDisplayShowCustomEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);

        InputMethodManager imm = (InputMethodManager) getSherlockActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
        return view;
    }
}
