/**
 * 
 */
package com.arkami.myidkey.activity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.arkami.myidkey.R;
import com.arkami.myidkey.adapters.KeyCardsListAdapter;
import com.arkami.myidkey.communication.Service;
import com.arkami.myidkey.database.tables.KeyCard;

/**
 * @author sbahdikyan
 * 
 */
public class EditFavouritesActivity extends SherlockActivity {

	private ArrayAdapter<KeyCard> adapter;
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sortable_list);
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setCustomView(R.layout.actionbar_edit_favourites);

		adapter = new KeyCardsListAdapter(this, R.layout.list_view_row,
				R.id.textView, Service.getInstance(this).getKeyCards(), true);

		listView = (ListView) findViewById(R.id.myListView);
		listView.setAdapter(adapter);

	}
}
