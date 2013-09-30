/**
 * 
 */
package com.arkami.myidkey.adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.arkami.myidkey.R;
import com.arkami.myidkey.database.tables.Tag;

/**
 * @author sbahdikyan
 * 
 */
public class TagCheckListAdapter_new extends ArrayAdapter<Tag>

{
	private List<Tag> tagsList;
	// private long[] selectedIds;
	private Map<Long, Boolean> selectedTags;
	private final Context context;

	/**
	 * @param context
	 * @param list
	 */
	public TagCheckListAdapter_new(Context context, List<Tag> list) {
		super(context, R.layout.tag_check_box_item_layout, list);
		this.selectedTags = new HashMap<Long, Boolean>();
		this.context = context;
		this.tagsList = list;
	}

	/**
	 * @author sbahdikyan
	 * 
	 */
	private static class ViewHolder {

		TextView tagName;
		CheckBox tagCheckBox;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		if (convertView == null) {
			LayoutInflater inflator = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			view = inflator.inflate(R.layout.tag_check_box_item_layout, null);
			final ViewHolder viewHolder = new ViewHolder();
			viewHolder.tagName = (TextView) view
					.findViewById(R.id.tag_item_text);
			viewHolder.tagCheckBox = (CheckBox) view
					.findViewById(R.id.tag_check_box);
			viewHolder.tagCheckBox
					.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							Tag tag = (Tag) viewHolder.tagCheckBox.getTag();
							if (buttonView.isChecked()) {
								addSelected(tag.getId());
							} else {
								removeSelected(tag.getId());
							}
						}
					});
			viewHolder.tagCheckBox.setTag(tagsList.get(position));
			viewHolder.tagName.setTag(tagsList.get(position));
			view.setTag(viewHolder);

		} else {
			view = convertView;
			((ViewHolder) view.getTag()).tagCheckBox.setTag(tagsList
					.get(position));
			((ViewHolder) view.getTag()).tagName.setTag(tagsList.get(position));
		}
		ViewHolder holder = (ViewHolder) view.getTag();
		holder.tagName.setText(tagsList.get(position).getName());
		holder.tagCheckBox
				.setChecked(isSelected(tagsList.get(position).getId()));
		return view;
	}

	/**
	 * @param id
	 *            to be added in selected tags
	 */
	public void addSelected(Long id) {
		if ((id == null) || (id < 1)) {
			return;
		}
		if (selectedTags.containsKey(id)) {
			return;
		}
		selectedTags.put(id, true);
	}

	/**
	 * @param id
	 *            to be added in selected tags
	 */
	public void removeSelected(Long id) {
		if ((id == null) || (id < 1)) {
			return;
		}
		if (selectedTags.containsKey(id)) {
			selectedTags.remove(id);
		}
	}

	/**
	 * Checks if selected list contains the id
	 * 
	 * @param id
	 *            to be checked
	 * @return true if it does
	 */
	public boolean isSelected(long id) {
		if ((getSelectedIds() == null) || (id < 0)) {
			return false;
		}
		return this.selectedTags.containsKey(id);
	}

	/**
	 * @param name
	 *            name of the tag we want
	 * @return tag by given name
	 */
	public Tag getItem(String name) {
		for (Tag tag : getItems()) {
			if (String.valueOf(tag.getName()).equals(String.valueOf(name))) {
				return tag;
			}
		}
		throw new NoSuchElementException();
	}

	/**
	 * Checks if the adapter contains tag with element with that name
	 * 
	 * @param name
	 *            name to be searched
	 * @return true if contains an item with that name
	 */
	public boolean contains(String name) {
		for (Tag tag : getItems()) {
			if (String.valueOf(tag.getName()).equals(String.valueOf(name))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @return all items from list view
	 */
	public List<Tag> getItems() {
		List<Tag> items = new ArrayList<Tag>();
		for (int i = 0; i < getCount(); i++) {
			items.add(getItem(i));
		}
		return items;
	}

	/**
	 * @return the selectedIds
	 */
	public long[] getSelectedIds() {
		long[] selectedIds = new long[selectedTags.size()];
		int i = 0;
		for (long id : selectedTags.keySet()) {
			selectedIds[i++] = id;
		}
		return selectedIds;
	}

	/**
	 * @param selectedIds
	 *            the selectedIds to set
	 */
	public void setSelectedIds(long[] selectedIds) {
		for (long tagId : selectedIds) {
			this.selectedTags.put(tagId, true);
		}
		// this.selectedIds = selectedIds;
	}

	@Override
	public void add(Tag object) {
		super.add(object);
		// this.selectedIds = Arrays.copyOf(this.selectedIds,
		// this.selectedIds.length + 1);
	}
}
//
