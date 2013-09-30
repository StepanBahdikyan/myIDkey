/**
 *
 */
package com.arkami.myidkey.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.arkami.myidkey.R;
import com.arkami.myidkey.adapters.KeyCardsListAdapter;
import com.arkami.myidkey.communication.Service;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class FavouritesActivity extends SherlockFragmentOnBackButtonPressed {

    private KeyCardsListAdapter adapter;
    private ListView listView;
//    private Button edit;
//    private KeyCardDataSource keyCardDataSource;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        keyCardDataSource = new KeyCardDataSource(getSherlockActivity());
        View view = inflater.inflate(R.layout.sortable_list, container, false);
        ListView listView = (ListView) view.findViewById(R.id.myListView);
        listView.setFastScrollEnabled(true);

        adapter = new KeyCardsListAdapter(view.getContext(),
                R.layout.list_view_row, R.id.textView, Service.getInstance(
                getActivity()).getFavouriteKeyCards(), false);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parrent, View view,
                                    int position, long id) {

                Intent addKeyKardIntent = new Intent(view.getContext(),
                        KeyCardEditActivity.class);
                addKeyKardIntent.putExtra(KeyCardEditActivity.KEY_CARD_ID,
                        adapter.getItem(position).getId());
                startActivity(addKeyKardIntent);
            }
        });

        // action bar
//        ActionBar actionBar = getSherlockActivity().getSupportActionBar();
//        actionBar.setCustomView(R.layout.actionbar_search);
//        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//
//        TextView search = (TextView) actionBar.getCustomView().findViewById(
//                R.id.inputSearch);
//
//        search.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int arg1,
//                                      int arg2, int arg3) {
//                FavouritesActivity.this.adapter.getFilter()
//                        .filter(charSequence);
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int arg1,
//                                          int arg2, int arg3) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//            }
//        });
//        // XXX move to another file
//        Button plusButton = (Button) actionBar.getCustomView().findViewById(
//                R.id.buttonPlus);
//        plusButton.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                final Dialog plusPopUp = new Dialog(FavouritesActivity.this
//                        .getActivity());
//                plusPopUp.requestWindowFeature((int) Window.FEATURE_NO_TITLE);
//                plusPopUp.setContentView(R.layout.plus_folder_popup);
//                final EditText newItem = (EditText) plusPopUp
//                        .findViewById(R.id.actionbar_main_submenu_plus_edit_text);
//                Button ok = (Button) plusPopUp
//                        .findViewById(R.id.actionbar_main_submenu_plus_button_ok);
//                Button cancel = (Button) plusPopUp
//                        .findViewById(R.id.actionbar_main_submenu_plus_button_cancel);
//                cancel.setOnClickListener(new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View v) {
//                        plusPopUp.dismiss();
//                    }
//                });
//                ok.setOnClickListener(new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View v) {
//
//                        KeyCard newFolder = new KeyCard();
//                        newFolder.setName(newItem.getText().toString());
//                        // newFolder.setCreateDate(new Date().getTime());
//                        // XXX get it from data base
//                        newFolder.setKeyCardTypeId(1L);
//                        newFolder.setFavourite(true);
//                        newFolder.setId(keyCardDataSource.insert(newFolder));
//                        keyCardDataSource.insert(newFolder);
//                        setKeyCardsListView(getView());
//                        // FavouritesActivity.this.adapter.addFolder(newItem
//                        // .getText().toString());
//
//                        plusPopUp.dismiss();
//
//                    }
//                });
//                plusPopUp.show();
//            }
//        });
//
//        edit = (Button) actionBar.getCustomView().findViewById(R.id.buttonEdit);
//        edit.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Intent editFavourites = new Intent(FavouritesActivity.this
//                        .getSherlockActivity(), EditFavouritesActivity.class);
//                startActivity(editFavourites);
//            }
//        });

        // setKeyCardsListView(view);
        listView.requestFocus();
        InputMethodManager imm = (InputMethodManager) this.getSherlockActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setKeyCardsListView(getView());
    }

    @Override
    public void onBackPressed() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     *
     */
    private void setKeyCardsListView(View view) {
        listView = (ListView) view.findViewById(R.id.myListView);
        listView.setFastScrollEnabled(true);
        // listView.setFastScrollAlwaysVisible(true);

        adapter = new KeyCardsListAdapter(this.getSherlockActivity(),
                R.layout.list_view_row, R.id.textView, Service.getInstance(
                getActivity()).getFavouriteKeyCards(), false);
        listView.setAdapter(adapter);
        listView.requestFocus();
    }
}
