/**
 *
 */
package com.arkami.myidkey.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arkami.myidkey.R;
import com.arkami.myidkey.database.datasources.KeyCardDataSource;
import com.arkami.myidkey.database.datasources.TagDataSource;
import com.arkami.myidkey.database.tables.KeyCard;
import com.arkami.myidkey.database.tables.Tag;
import com.arkami.myidkey.util.Common;

/**
 * @author sbahdikyan
 */
public class KeyCardsListAdapter extends ArrayAdapter<KeyCard> {
    private Context context;
    private int resource;
    private int textViewResourceId;
    private List<KeyCard> originalItems;
    private List<KeyCard> filterredItems;
    private Filter filter;
    private Boolean isInEditMode;
    private KeyCardDataSource keyCardDataSource;

    public KeyCardsListAdapter(Context context, int resource,
                               int textViewResourceId, List<KeyCard> items, Boolean isInEditMode) {
        super(context, resource, textViewResourceId, items);
        this.isInEditMode = isInEditMode;
        this.keyCardDataSource = new KeyCardDataSource(context);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.originalItems = new ArrayList<KeyCard>();
        this.originalItems.addAll(items);
        this.filterredItems = this.originalItems;
    }

    @Override
    public void add(KeyCard keyCard) {
        super.add(keyCard);
        originalItems.add(keyCard);
        super.notifyDataSetChanged();
    }

    @Override
    public KeyCard getItem(int position) {
        if (position < filterredItems.size()) {
            return filterredItems.get(position);
        }
        return null;
    }

    /**
     * Returns key card item by given name.
     *
     * @param name name of key card to fetch
     * @return key card object or throws exception if item is not found.
     */
    private KeyCard getItemByName(String name) {
        for (KeyCard keyCard : this.originalItems) {
            if (name.equals(keyCard.getName())) {
                return keyCard;
            }
        }
        throw new IllegalArgumentException("No such element found. " + name);
    }

    /**
     * Returns key card item by given id.
     *
     * @param id id of key card to fetch
     * @return key card object or throws exception if item is not found.
     */
    private KeyCard getItemById(long id) {
        for (KeyCard keyCard : this.originalItems) {
            if (id == keyCard.getId().longValue()) {
                return keyCard;
            }
        }
        throw new IllegalArgumentException("No such element found. " + id);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = null;
        if (convertView == null) {
            LayoutInflater inflator = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = inflator.inflate(resource, null);

            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.keyCardImage = (ImageView) view
                    .findViewById(R.id.icon_image);
            viewHolder.keyCardTagName = (TextView) view.findViewById(R.id.tagTextView);
            /*
             android:layout_margin="5dp"
        android:layout_marginBottom="15dp"
        android:layout_marginTop="15dp"
        android:layout_marginLeft="35dp"
             */

            //android.widget.RelativeLayout$LayoutParams


            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.CENTER_IN_PARENT, viewHolder.keyCardImage.getId());
            params.addRule(RelativeLayout.RIGHT_OF, viewHolder.keyCardImage.getId());
//            params.setMargins(35,15,15,15);
//            viewHolder.keyCardImage.setPadding(15, 15, 15, 15);
            viewHolder.keyCardName = (TextView) view
                    .findViewById(textViewResourceId);
            viewHolder.keyCardName.setLayoutParams(params);
            viewHolder.keyCardCheckBox = (CheckBox) view
                    .findViewById(R.id.checkbox);
            RelativeLayout.LayoutParams tagParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            tagParams.addRule(RelativeLayout.ALIGN_BOTTOM, viewHolder.keyCardName.getId());
            tagParams.addRule(RelativeLayout.ALIGN_TOP, viewHolder.keyCardName.getId());
            tagParams.addRule(RelativeLayout.RIGHT_OF, viewHolder.keyCardName.getId());
            //tagParams.addRule(RelativeLayout.ABOVE,viewHolder.keyCardName.getId());

            tagParams.setMargins(5, 0, 0, 0);
            viewHolder.keyCardTagName.setLayoutParams(tagParams);
            if (isInEditMode) {
                viewHolder.keyCardCheckBox.setVisibility(View.VISIBLE);
                viewHolder.keyCardCheckBox
                        .setOnCheckedChangeListener(new OnCheckedChangeListener() {

                            @Override
                            public void onCheckedChanged(
                                    CompoundButton buttonView, boolean isChecked) {

                                KeyCard keyCard = (KeyCard) viewHolder.keyCardCheckBox
                                        .getTag();
                                keyCard.setFavourite(buttonView.isChecked());
                                keyCardDataSource.setIsFavoirite(
                                        keyCard.getId(), buttonView.isChecked());
                            }
                        });
            }
            viewHolder.keyCardCheckBox.setTag(filterredItems.get(position));
            viewHolder.keyCardName.setTag(filterredItems.get(position));
            view.setTag(viewHolder);

        } else {
            view = convertView;
            ((ViewHolder) view.getTag()).keyCardCheckBox.setTag(filterredItems
                    .get(position));
            ((ViewHolder) view.getTag()).keyCardName.setTag(filterredItems
                    .get(position));
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.keyCardName.setText(filterredItems.get(position).getName());
        if (filterredItems.get(position).getTag() != null) {
            holder.keyCardTagName.setText(filterredItems.get(position).getTag());
            holder.keyCardTagName.setVisibility(View.VISIBLE);
        } else {
            holder.keyCardTagName.setVisibility(View.GONE);
        }
        holder.keyCardCheckBox.setChecked(filterredItems.get(position)
                .isFavourite());
        int imageResourseId = Common.getImageResourseId(getItem(position)
                .getKeyCardTypeId());
        holder.keyCardImage.setImageResource(imageResourseId);
        return view;
    }

    /**
     * @param position of a key card at this position
     * @return key card at this position
     */
    public KeyCard getKeyCardByPosition(int position) {
        return this.originalItems.get(position);
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new KeyCardsNameFilter();
        }
        return this.filter;
    }



    /**
     * returns key card model object of a possition
     *
     * @param possition
     * @return key card model
     */
    public KeyCard getKeyCardAtPossition(int possition) {
        return this.originalItems.get(possition);
    }

    /**
     * @author sbahdikyan
     */
    private static class ViewHolder {
        ImageView keyCardImage;
        TextView keyCardName;
        CheckBox keyCardCheckBox;
        TextView keyCardTagName;
    }

    private class KeyCardsNameFilter extends Filter {
        private List<KeyCard> copyList(List<KeyCard> original) {
            List<KeyCard> copy = new ArrayList<KeyCard>();
            for (KeyCard card : original) {
                copy.add(card);
            }
            return copy;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<KeyCard> newItems = new ArrayList<KeyCard>();
            if (constraint != null && constraint.toString().trim().length() > 0) {


                //copy all items in new array list

                List<KeyCard> newOriginalItems = new ArrayList<KeyCard>();
                newOriginalItems = copyList(originalItems);
                //make list from constraint string
                String[] searchStrings = constraint.toString().split(" ", 0);
                for (String searchString : searchStrings) {
                    //search by key card name
                    newItems.clear();
                    for (KeyCard keyCard : newOriginalItems) {
                        if (keyCard.getName().contains(searchString)) {
                            newItems.add(keyCard);
                        } else {
                            TagDataSource tagDataSource = new TagDataSource(context);
                            try {
                                List<Tag> tags = tagDataSource.getTagLike(searchString);
                                for (long tagId : keyCard.getTagIds()) {
                                    for (Tag tag : tags) {
                                        if (tagId == tag.getId()) {
                                            keyCard.setTag(tag.getName());
                                            newItems.add(keyCard);
                                            break;
                                        }
                                    }
                                }
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                            //XXX add if statement for key card type
                        }
                    }
                    newOriginalItems = copyList(newItems);
                    //Collections.copy(newOriginalItems, newItems);
                }
            } else {
                newItems = originalItems;
                for (KeyCard item : newItems) {
                    item.setTag(null);
                }

            }
//            originalItems
            results.count = newItems.size();
            results.values = newItems;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {

            KeyCardsListAdapter.this.notifyDataSetChanged();
            clear();
            filterredItems = (List<KeyCard>) results.values;
            for (KeyCard item : filterredItems) {
                KeyCardsListAdapter.super.add(item);
            }
            KeyCardsListAdapter.this.notifyDataSetInvalidated();
            return;
        }

    }

}