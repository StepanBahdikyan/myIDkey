/**
 *
 */
package com.arkami.myidkey.activity;

import java.io.File;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.actionbarsherlock.widget.SearchView;
import com.arkami.myidkey.R;
import com.arkami.myidkey.activity.dialog.LongClickKeyCardMenuDialog;
import com.arkami.myidkey.adapters.KeyCardsListAdapter;
import com.arkami.myidkey.communication.Service;
import com.arkami.myidkey.database.datasources.FileDataSource;
import com.arkami.myidkey.database.datasources.KeyCardDataSource;
import com.arkami.myidkey.database.tables.FileObject;
import com.arkami.myidkey.database.tables.KeyCard;

/**
 * @author sbahdikyan
 */
@SuppressLint("NewApi")
public class KeyCardsActivity extends SherlockFragmentOnBackButtonPressed {
    private ListView listView;
    private KeyCardsListAdapter adapter;
    private KeyCardDataSource keyCardDataSource;
    private FileDataSource fileDataSource;
    private ActionBar actionBar;
    private static boolean isFavoriteEnabled;
    // private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        keyCardDataSource = new KeyCardDataSource(getSherlockActivity());
        //XXX remove this

        fileDataSource = new FileDataSource(getSherlockActivity());
        View view = inflater.inflate(R.layout.sortable_list, container, false);
        listView = (ListView) view.findViewById(R.id.myListView);

        listView.setFastScrollEnabled(true);
        // listView.setFastScrollAlwaysVisible(true);
        initListView(view, false);

//        setActionBar();
//        setEditButton();
//        setPlusButton();
        setHasOptionsMenu(true);
        getSherlockActivity().supportInvalidateOptionsMenu();
        actionBar = getSherlockActivity().getSupportActionBar();
        actionBar.setIcon(android.R.color.transparent);
        actionBar.setDisplayShowCustomEnabled(false);
//        actionBar.getCustomView().setVisibility(View.GONE);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);

//        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        InputMethodManager imm = (InputMethodManager) getSherlockActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
        return view;
    }

    private void initListView(View view, boolean favorite) {
        List<KeyCard> keyCards;
        if (favorite) {
            keyCards  = Service.getInstance(getSherlockActivity())
                    .getFavouriteKeyCards();
        } else {
            keyCards  = Service.getInstance(getSherlockActivity())
                    .getKeyCards();
        }
        this.adapter = new KeyCardsListAdapter(view.getContext(),
                R.layout.list_view_row, R.id.textView, keyCards, false);
        listView.setAdapter(this.adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parrent, View view,
                                    int position, long id) {

//                if (adapter.getItem(position).getKeyCardTypeId() == 21) {
//                    //it's a folder
//                    return;
//                }

                Intent addKeyKardIntent = new Intent(view.getContext(),
                        KeyCardEditActivity.class);
                addKeyKardIntent.putExtra(KeyCardEditActivity.KEY_CARD_ID,
                        adapter.getItem(position).getId());
                startActivity(addKeyKardIntent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                LongClickKeyCardMenuDialog menuDialog = new
                        LongClickKeyCardMenuDialog(getSherlockActivity(),
                        adapter.getItem(position));
                menuDialog.show();
                return true;
            }
        });
        listView.requestFocus();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.key_cards, menu);
        SearchView searchView = (SearchView) menu.getItem(0).getActionView();
//      SearchView searchView = new SearchView(getSherlockActivity());

        searchView.setQueryHint(getString(R.string.search_hint));
        searchView.setLayoutParams(
                new android.widget.FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                String[] searchQuery = query.split(" ");
                for (int i = 0; i < searchQuery.length; i++) {
                    KeyCardsActivity.this.adapter.getFilter().filter(searchQuery[i]);
                }
                actionBar.setIcon(android.R.color.transparent);
                actionBar.getCustomView().setVisibility(View.GONE);
                actionBar.setDisplayShowCustomEnabled(false);
                actionBar.setDisplayHomeAsUpEnabled(false);
                actionBar.setDisplayShowTitleEnabled(false);
                actionBar.setDisplayUseLogoEnabled(false);
                actionBar.setDisplayShowHomeEnabled(false);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                KeyCardsActivity.this.adapter.getFilter().filter(newText);
                if (actionBar == null) {
                    actionBar = getSherlockActivity().getSupportActionBar();
                }
                actionBar.setIcon(android.R.color.transparent);
                actionBar.setDisplayShowCustomEnabled(false);
                actionBar.setDisplayHomeAsUpEnabled(false);
                actionBar.setDisplayShowTitleEnabled(false);
                actionBar.setDisplayUseLogoEnabled(false);
                actionBar.setDisplayShowHomeEnabled(false);
                return false;
            }
        });

//        menu.add(getString(R.string.search_hint))
//                .setIcon(R.drawable.abs__ic_search)
//                .setActionView(searchView)
//                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);

        MenuItem plus = menu.getItem(3);
        plus.setOnMenuItemClickListener((MenuItem.OnMenuItemClickListener) createPlusButtonOnItemClickListener());
//        menu.add(getString(R.string.plus))
//                .setOnMenuItemClickListener((MenuItem.OnMenuItemClickListener) createPlusButtonOnItemClickListener())
//                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        // ...place to add other menu items
        final MenuItem favorites = menu.getItem(2);
        favorites.setVisible(true);
        favorites.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
//                ((MainActivity)KeyCardsActivity.this.getSherlockActivity()).bla(isFavoriteEnabled);
                isFavoriteEnabled = !isFavoriteEnabled;
                if(isFavoriteEnabled){
                	favorites.setIcon(getResources().getDrawable(R.drawable.star_s_selected));
                }else{
                	favorites.setIcon(getResources().getDrawable(R.drawable.star_s_selected_not));
                }
                initListView(getView(),isFavoriteEnabled);

                return false;
            }
        });
        final MenuItem notification = menu.getItem(4);
        final List<KeyCard> expiringKeyCards = keyCardDataSource.getKeyCardsWithExpiringDates();
        if(expiringKeyCards.size()>0){
            //.setVisibility(View.VISIBLE);
            notification.setVisible(true);

            notification.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    final Dialog dialog = new Dialog(getSherlockActivity());
                    dialog.setContentView(R.layout.sortable_list);
                    dialog.setTitle("Expiring Keycards");
                    ListView expiringKeyCardsListView = (ListView)dialog.findViewById(R.id.myListView);
                    expiringKeyCardsListView.setAdapter(
                            new KeyCardsListAdapter(getSherlockActivity(), R.layout.list_view_row, R.id.textView, expiringKeyCards, false));
                    LinearLayout buttonHolder = (LinearLayout)dialog.findViewById(R.id.movingDestinationButtons);
                    buttonHolder.setVisibility(View.VISIBLE);
                    Button snooze = (Button)dialog.findViewById(R.id.movingOkButton);
                    snooze.setText("Snooze");
                    snooze.setVisibility(View.VISIBLE);
                    snooze.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            notification.setVisible(false);
                            dialog.dismiss();
                        }
                    });
                    Button dismiss = (Button)dialog.findViewById(R.id.movingCancelButton);
                    dismiss.setText("Dismiss");
                    dismiss.setVisibility(View.VISIBLE);
                    dismiss.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            notification.setVisible(false);
                            dialog.dismiss();
                        }
                    });
                    dialog.setCancelable(true);
                    dialog.show();
                    return false;
                }
            }) ;

        }

        super.onCreateOptionsMenu(menu, inflater);
    }

//    private void setActionBar() {
//        actionBar = getSherlockActivity().getSupportActionBar();
//        actionBar.setCustomView(R.layout.actionbar_search);
//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//        actionBar.setDisplayUseLogoEnabled(false);
//        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.abs__item_background_holo_light));
//        final TextView search = (TextView) actionBar.getCustomView().findViewById(
//                R.id.inputSearch);
//        search.setWidth(50);
//        search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    search.setWidth(500);
//                } else {
//                    search.setWidth(50);
//                }
//            }
//        });
//        search.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int strat,
//                                      int before, int count) {
//                KeyCardsActivity.this.adapter.getFilter().filter(charSequence);
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int arg1,
//                                          int arg2, int arg3) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//            }
//        });
//        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//
//    }
//
//    private void setPlusButton() {
//        Button plusButton = (Button) actionBar.getCustomView().findViewById(
//                R.id.buttonPlus);
//        plusButton.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // XXX refactor!!!
//                final Dialog plusMenuPopUp = new Dialog(getSherlockActivity());
//                plusMenuPopUp
//                        .requestWindowFeature((int) Window.FEATURE_NO_TITLE);
//                plusMenuPopUp.setContentView(R.layout.plus_keycard_popup);
//                final View plusFolder = plusMenuPopUp
//                        .findViewById(R.id.plus_menu_popup_new_folder);
//                final View plusKeyCard = plusMenuPopUp
//                        .findViewById(R.id.plus_menu_popup_new_key_card);
//
//                plusKeyCard.setOnClickListener(new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View v) {
//                        Intent addKeyKardIntent = new Intent(
//                                KeyCardsActivity.this.getSherlockActivity(),
//                                KeyCardEditActivity.class);
//                        KeyCard keycard = new KeyCard();
//                        keycard.setName(" ");
//                        // XXX
//                        keycard.setKeyCardTypeId(1L);
//                        addKeyKardIntent.putExtra(
//                                KeyCardEditActivity.KEY_CARD_ID,
//                                keyCardDataSource.insert(keycard));
//                        startActivity(addKeyKardIntent);
//                        plusMenuPopUp.dismiss();
//                    }
//                });
//
//                plusFolder.setOnClickListener(new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View v) {
//                        final Dialog plusPopUp = new Dialog(
//                                getSherlockActivity());
//                        plusPopUp
//                                .requestWindowFeature((int) Window.FEATURE_NO_TITLE);
//                        plusPopUp.setContentView(R.layout.plus_folder_popup);
//                        final EditText newItem = (EditText) plusPopUp
//                                .findViewById(R.id.actionbar_main_submenu_plus_edit_text);
//                        Button ok = (Button) plusPopUp
//                                .findViewById(R.id.actionbar_main_submenu_plus_button_ok);
//                        Button cancel = (Button) plusPopUp
//                                .findViewById(R.id.actionbar_main_submenu_plus_button_cancel);
//                        cancel.setOnClickListener(new View.OnClickListener() {
//
//                            @Override
//                            public void onClick(View v) {
//                                plusPopUp.dismiss();
//                            }
//                        });
//                        ok.setOnClickListener(new View.OnClickListener() {
//
//                            @Override
//                            public void onClick(View v) {
//                                KeyCard newFolder = new KeyCard();
//                                newFolder.setName(newItem.getText().toString());
//                                newFolder.setCreateDate(new Date().getTime());
//                                // XXX get it from data base
//                                newFolder.setKeyCardTypeId(21L);
//                                newFolder.setId(keyCardDataSource
//                                        .insert(newFolder));
//                                keyCardDataSource.insert(newFolder);
//                                KeyCardsActivity.this.adapter = new KeyCardsListAdapter(
//                                        getSherlockActivity(),
//                                        R.layout.list_view_row, R.id.textView,
//                                        Service.getInstance(getActivity())
//                                                .getFavouriteKeyCards(), false);
//                                onResume();
//                                plusPopUp.dismiss();
//                                plusMenuPopUp.dismiss();
//                            }
//                        });
//                        plusPopUp.show();
//                    }
//                });
//                // -----
//
//                plusMenuPopUp.show();
//            }
//        });
//
//    }

    /**
     * creates on click listener for plus
     *
     * @return
     */
    private MenuItem.OnMenuItemClickListener createPlusButtonOnItemClickListener() {
        return new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                {
                    // XXX refactor!!!
                    final Dialog plusMenuPopUp = new Dialog(getSherlockActivity());
                    plusMenuPopUp
                            .requestWindowFeature((int) Window.FEATURE_NO_TITLE);
                    plusMenuPopUp.setContentView(R.layout.plus_keycard_popup);
                    final View plusFolder = plusMenuPopUp
                            .findViewById(R.id.plus_menu_popup_new_folder);
                    final View plusFile = plusMenuPopUp
                            .findViewById(R.id.plus_menu_file);
                    final View plusKeyCard = plusMenuPopUp
                            .findViewById(R.id.plus_menu_popup_new_key_card);
                    final View cancel = plusMenuPopUp
                            .findViewById(R.id.plus_keycard_popup_cancel_button);

                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            plusMenuPopUp.dismiss();
                        }
                    });

                    plusKeyCard.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            Intent addKeyKardIntent = new Intent(
                                    KeyCardsActivity.this.getSherlockActivity(),
                                    KeyCardEditActivity.class);
                            startActivity(addKeyKardIntent);
                            plusMenuPopUp.dismiss();
                        }
                    });

                    plusFile.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String path = Environment.getExternalStorageDirectory().getPath();
                            //XXX remove this. it' just to see the copied files.
                            // path = KeyCardsActivity.this.getSherlockActivity().getFilesDir().getPath();
                            Intent fileIntent = new Intent(KeyCardsActivity.this.getSherlockActivity(), FileChooserActivity.class);
                            fileIntent.putExtra(FileChooserActivity.PATH, path);
                            fileIntent.putExtra(FileChooserActivity.IMPORT, false);
                            startActivity(fileIntent);
                        }
                    });

                    plusFolder.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            final Dialog plusPopUp = new Dialog(
                                    getSherlockActivity());
                            plusPopUp
                                    .requestWindowFeature((int) Window.FEATURE_NO_TITLE);
                            plusPopUp.setContentView(R.layout.plus_folder_popup);

                            final EditText newItem = (EditText) plusPopUp
                                    .findViewById(R.id.actionbar_main_submenu_plus_edit_text);
                            Button ok = (Button) plusPopUp
                                    .findViewById(R.id.actionbar_main_submenu_plus_button_ok);
                            Button cancel = (Button) plusPopUp
                                    .findViewById(R.id.actionbar_main_submenu_plus_button_cancel);
                            cancel.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    plusPopUp.dismiss();
                                }
                            });
                            ok.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {

                                    File newRealFolder = new File(
                                            KeyCardsActivity.this.getSherlockActivity().getFilesDir().getPath()
                                                    + "/" + newItem.getText().toString());

                                    if (!newRealFolder.exists()) {
                                        newRealFolder.mkdir();
                                        Toast.makeText(KeyCardsActivity.this.getSherlockActivity(),
                                                "folder created", Toast.LENGTH_SHORT).show();
                                    }
                                    FileObject newFolder = new FileObject();


//                                    KeyCard newFolder = new KeyCard();
                                    newFolder.setFileType(3L);
                                    newFolder.setName(newItem.getText().toString());
                                    newFolder.setCreateDate(new Date().getTime());
                                    // XXX get it from data base
                                    newFolder.setKeyCardAttribute(false);
                                    newFolder.setPathToFile(Environment.getExternalStorageDirectory().getPath());

                                    newFolder.setId(fileDataSource
                                            .insert(newFolder));

//                                    KeyCardsActivity.this.adapter = new KeyCardsListAdapter(
//                                            getSherlockActivity(),
//                                            R.layout.list_view_row, R.id.textView,
//                                            Service.getInstance(getActivity())
//                                                    .getFavouriteKeyCards(), false);
                                    onResume();
                                    plusPopUp.dismiss();
                                    plusMenuPopUp.dismiss();
                                }
                            });
                            plusPopUp.show();
                        }
                    });
                    // -----

                    plusMenuPopUp.show();

                    return true;
                }
            }
        };

    }

//    private void setEditButton() {
//        Button edit = (Button) actionBar.getCustomView().findViewById(
//                R.id.buttonEdit);
//        edit.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//            }
//        });
//    }

    @Override
    public void onResume() {
        super.onResume();

        getSherlockActivity().invalidateOptionsMenu();
        actionBar.setDisplayShowCustomEnabled(false);
        setKeyCardsListView(getView());
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        onResume();
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
                getActivity()).getKeyCards(), false);
        listView.setAdapter(adapter);
        listView.requestFocus();
    }

    @Override
    public void onBackPressed() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
