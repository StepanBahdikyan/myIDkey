/**
 * 
 */
package com.arkami.myidkey.adapters;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.arkami.myidkey.R;
import com.arkami.myidkey.model.AppFile;

/**
 * @author sbahdikyan
 * 
 */
@Deprecated
public class PhotoAudioListAdapter extends ArrayAdapter<AppFile> {

	private int resource;
	private LayoutInflater inflater;

	/**
	 * @param context
	 * @param resource
	 * @param textViewResourceId
	 * @param objects
	 */
	public PhotoAudioListAdapter(Context context, int resource,
			int textViewResourceId, List<AppFile> files) {
		super(context, resource, textViewResourceId, files);
		this.resource = resource;
		this.inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public void add(AppFile object) {
		super.add(object);
		// this.files.add(object);
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View view = convertView;
		if (convertView == null) {
			view = inflater.inflate(resource, null);
		}
		TextView fileName = (TextView) view
				.findViewById(R.id.photo_audio_list_item_file_name);
		TextView fileSize = (TextView) view
				.findViewById(R.id.photo_audio_list_item_file_size);
		ImageView thumb = (ImageView) view
				.findViewById(R.id.photo_audio_list_item_thumb);
		if (getItem(position) != null) {
			fileName.setText(getItem(position).getFileName());
			// thumb.setImageBitmap(getItem(position).getThumb());
			fileSize.setText(getItem(position).getFileSize() + "");

			view.setVisibility(View.VISIBLE);
			Log.w("photo view ", "added ");
		}

		return view;
	}
}
