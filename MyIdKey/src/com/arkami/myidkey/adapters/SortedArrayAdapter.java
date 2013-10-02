/**
 * 
 */
package com.arkami.myidkey.adapters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.arkami.myidkey.R;
import com.arkami.myidkey.database.datasources.KeyCardDataSource;
import com.arkami.myidkey.database.tables.KeyCard;
import com.arkami.myidkey.model.KeyCardTypeEnum;
import com.arkami.myidkey.util.Cache;

/**
 * @author sbahdikyan
 * 
 */
@SuppressLint("NewApi")
public class SortedArrayAdapter extends ArrayAdapter<String> {

	private Context context;
	private int resource;
	private int textViewResourceId;
	private List<String> originalItems;
	private List<String> filterredItems;
	private List<KeyCard> keyCards;
	private Filter filter;
	private Boolean isInEditMode;
	private KeyCardDataSource keyCardDataSource;

	public SortedArrayAdapter(Context context, int resource,
			int textViewResourceId, List<KeyCard> items, Boolean isInEditMode) {
		super(context, resource, textViewResourceId, new ArrayList<String>());
		this.isInEditMode = isInEditMode;
		this.keyCardDataSource = new KeyCardDataSource(context);
		this.keyCards = items;
		this.context = context;
		this.resource = resource;
		this.textViewResourceId = textViewResourceId;

		this.originalItems = new ArrayList<String>();
		this.originalItems.addAll(getItemNames(items));
		this.filterredItems = this.originalItems;
		for (String keyCardName : originalItems) {
			super.add(keyCardName);
		}
	}

	/**
	 * @param keyCards
	 *            list of key cards from witch will be extracted another with
	 *            their names only
	 * @return list of key cards' names
	 */
	private List<String> getItemNames(List<KeyCard> keyCards) {
		List<String> keyCardsNames = new ArrayList<String>();
		for (KeyCard keyCard : keyCards) {
			keyCardsNames.add(keyCard.getName());
		}
		return keyCardsNames;
	}

	@Override
	public void add(String object) {
		super.add(object);
		originalItems.add(object);
		KeyCard newCard = new KeyCard();
		newCard.setName(object);

		keyCards.add(newCard);
		Collections.sort(originalItems);
		notifyDataSetChanged();
	}

	/**
	 * Adds a folder key card.
	 * 
	 * @param folderName
	 */
	public void addFolder(String folderName) {
		super.add(folderName);
		originalItems.add(folderName);
		KeyCard newFolder = new KeyCard();
		newFolder.setName(folderName);
		newFolder.setKeyCardTypeId(Cache.getInstance(context).getKeyCardTypeId(
				KeyCardTypeEnum.folder));
		keyCards.add(newFolder);
		Collections.sort(originalItems);
		notifyDataSetChanged();
	}

	@Override
	public String getItem(int position) {
		if (position < filterredItems.size()) {
			return filterredItems.get(position);
		}
		return null;
	}

	/**
	 * Returns key card item by given name.
	 * 
	 * @param name
	 *            name of key card to fetch
	 * @return key card object or throws exception if item is not found.
	 */
	private KeyCard getItemByName(String name) {
		for (KeyCard keyCard : this.keyCards) {
			if (name.equals(keyCard.getName())) {
				return keyCard;
			}
		}
		throw new IllegalArgumentException("No such element found. " + name);
	}

	/**
	 * Returns key card item by given id.
	 * 
	 * @param id
	 *            id of key card to fetch
	 * @return key card object or throws exception if item is not found.
	 */
	private KeyCard getItemById(long id) {
		for (KeyCard keyCard : this.keyCards) {
			Log.w("sortedArrayAdapter.getItemById()", "id = " + id
					+ " keyCard.getId() = " + keyCard.getId().longValue());
			if (id == keyCard.getId().longValue()) {
				return keyCard;
			}
		}
		throw new IllegalArgumentException("No such element found. " + id);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// -------------------------------------------

		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater inflator = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			rowView = inflator.inflate(resource, null);
			final ViewHolder viewHolder = new ViewHolder();
			viewHolder.keyCardImage = (ImageView) rowView
					.findViewById(R.id.icon_image);
			viewHolder.keyCardName = (TextView) rowView
					.findViewById(textViewResourceId);
			viewHolder.keyCardCheckBox = (CheckBox) rowView
					.findViewById(R.id.checkbox);

			if (isInEditMode) {
				viewHolder.keyCardCheckBox.setVisibility(View.VISIBLE);
				if (getItem(position) != null) {
					viewHolder.keyCardCheckBox.setChecked(getItemByName(
							getItem(position)).isFavourite());
				}
				viewHolder.keyCardCheckBox
						.setOnCheckedChangeListener(new OnCheckedChangeListener() {

							@Override
							public void onCheckedChanged(
									CompoundButton buttonView, boolean isChecked) {

								KeyCard keyCard = (KeyCard) viewHolder.keyCardCheckBox
										.getTag();
								keyCard.setFavourite(buttonView.isChecked());
								keyCardDataSource.setIsFavoirite(
										viewHolder.keyCardId,
										buttonView.isChecked());
								// SortedArrayAdapter.this
								// .notifyDataSetInvalidated();
							}
						});
			}
			rowView.setTag(viewHolder);
			viewHolder.keyCardCheckBox.setTag(getItemByName(getItem(position)));
		}
		ViewHolder holder = (ViewHolder) rowView.getTag();
		// set holder images and text
		holder.keyCardId = getItemByName(getItem(position)).getId();
		holder.keyCardName.setText(getItem(position));
		holder.keyCardImage.setImageResource(getImageResourseId(getItemByName(
				getItem(position)).getKeyCardTypeId()));
		holder.keyCardCheckBox.setChecked(getItemByName(getItem(position))
				.isFavourite());
		return rowView;
	}

	/**
	 * 
	 * @param possition
	 *            of a key card at this position
	 * @return key card at this position
	 */
	public KeyCard getKeyCardByPosition(int position) {
		return this.keyCards.get(position);
	}

	@Override
	public Filter getFilter() {
		if (filter == null) {
			filter = new KeyCardsFilter();
		}
		return this.filter;
	}

	/**
	 * @param type
	 *            type of the item
	 * @return image of that type
	 */
	private int getImageResourseId(Long id) {
		switch (id.intValue()) {

		case 1:
			return R.drawable.folder_selected;
		case 2:
			return R.drawable.letter;
		case 3:
			return R.drawable.photo;
		case 4:
			return R.drawable.pin;
		case 5:
			return R.drawable.visa;
		case 6:
			return R.drawable.globe;
		default:
			return R.drawable.ic_launcher;
		}

	}

	/**
	 * returns key card model object of a possition
	 * 
	 * @param possition
	 * @return key card model
	 */
	public KeyCard getKeyCardAtPossition(int possition) {
		return this.keyCards.get(possition);
	}

	private class KeyCardsFilter extends Filter {

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			FilterResults results = new FilterResults();
			List<String> newItems = new ArrayList<String>();
			boolean areSet = false;
			if ((constraint != null) && (constraint.length() != 0)) {
				for (String item : originalItems) {
					if (item.startsWith(constraint.toString())) {
						newItems.add(item);
						areSet = true;
					}
				}
			} else {
				newItems = originalItems;
			}
			results.count = newItems.size();
			results.values = newItems;
			return results;
		}

		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {

			SortedArrayAdapter.this.notifyDataSetChanged();
			clear();
			filterredItems = (List<String>) results.values;
			for (String item : filterredItems) {
				SortedArrayAdapter.super.add(item);
			}
			SortedArrayAdapter.this.notifyDataSetInvalidated();
			return;
		}

	}

	/**
	 * @author sbahdikyan
	 * 
	 */
	private static class ViewHolder {
		ImageView keyCardImage;
		TextView keyCardName;
		CheckBox keyCardCheckBox;
		long keyCardId;
	}
}