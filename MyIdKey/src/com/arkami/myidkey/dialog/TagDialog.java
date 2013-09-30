/**
 * 
 */
package com.arkami.myidkey.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.arkami.myidkey.R;
import com.arkami.myidkey.adapters.TagCheckListAdapter_new;
import com.arkami.myidkey.database.datasources.TagDataSource;
import com.arkami.myidkey.database.tables.Tag;
import com.arkami.myidkey.listeners.TagChangeListener;

/**
 * @author sbahdikyan
 * 
 */
public class TagDialog extends Dialog {

	private Context context;
	private TagCheckListAdapter_new tagCheckListAdapter;
	private TextView newTagName;
	private TagChangeListener tagChangeListener;
	private TagDataSource tagDataSource;

	/**
	 * @param context
	 */
	public TagDialog(Context context, TagChangeListener listener) {
		super(context);
		this.tagDataSource = new TagDataSource(context);
		this.tagChangeListener = listener;
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.select_tag_pop_up);
		initListView();
		initDoneButton();
		initCancelButton();
		initNewTagTexView();
		initAddButton();

	}

	/**
	 *  
	 */
	private void initNewTagTexView() {
		this.newTagName = (TextView) findViewById(R.id.tag_popup_tags_new_tag);
	}

	/**
	 * initializes tag add buton witch adds new tag to the list of tags in the
	 * dialog
	 */
	private void initAddButton() {
		Button add = (Button) findViewById(R.id.tag_popup_tags_add_button);
		add.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String tagName = newTagName.getText().toString().trim();
				Tag tag = new Tag();
				tag.setName(tagName);
				// adds all not null not in list values.

				try {
					if ((tagName.length() > 0)
							&& (!tagCheckListAdapter.contains(tagName))) {
						tag = tagDataSource.get(tagDataSource.insert(tag));
						tagCheckListAdapter.add(tag);
						tagCheckListAdapter.addSelected(tag.getId());
						tagChangeListener.onTagAdded();
					}
				} catch (Exception e) {
					Toast.makeText(context, "problem adding tag.",
							Toast.LENGTH_SHORT).show();
				}
				newTagName.setText("");
			}
		});
	}

	/**
	 * inits cancel button - dismisses the dialog
	 */
	private void initCancelButton() {
		Button cancel = (Button) findViewById(R.id.tag_popup_tags_cancel_button);
		cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
	}

	/**
	 * initializes the tags list view, sets adapter to it.
	 */
	private void initListView() {
		ListView tagsPopUpListView = (ListView) findViewById(R.id.tag_popup_tags_list);

		tagCheckListAdapter = new TagCheckListAdapter_new(this.context,
				tagDataSource.getTags());
		tagsPopUpListView.setAdapter(tagCheckListAdapter);
	}

	/**
	 * Sets listener on done button.
	 */
	private void initDoneButton() {
		Button doneButton = (Button) findViewById(R.id.tag_popup_tags_done_button);
		doneButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.w("tags", "----------------------------------");
				for (Long id : getAdapter().getSelectedIds()) {
					Log.w("selected: ", id + "");
				}
				Log.w("tags", "----------------------------------");
				tagChangeListener.onTagsSet();
				dismiss();
			}

		});

	}

	/**
	 * @return TagCheckListAdapter.
	 */
	public TagCheckListAdapter_new getAdapter() {
		return tagCheckListAdapter;
	}

}
