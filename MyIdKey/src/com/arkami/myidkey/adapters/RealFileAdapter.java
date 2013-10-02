package com.arkami.myidkey.adapters;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arkami.myidkey.R;
import com.arkami.myidkey.activity.FoldersActivity;

/**
 * Created with IntelliJ IDEA. User: sbahdikyan Date: 13-8-20 Time: 15:40 To
 * change this template use File | Settings | File Templates.
 */
public class RealFileAdapter extends ArrayAdapter<File> {

	private Context context;
	private int resourceId;
	private boolean isInMoveMode;
	private List<File> originalItems;
	private List<File> filterredItems;
	private List<File> selected;
	private Filter filter;

	public RealFileAdapter(Context context, int textViewResourceId,
			File[] items, Boolean isInMoveMode) {
		super(context, textViewResourceId);
		this.context = context;
		resourceId = textViewResourceId;
		this.isInMoveMode = isInMoveMode;
		originalItems = new ArrayList<File>();
		filterredItems = new ArrayList<File>();
		selected = new ArrayList<File>();
		if (items != null) {
			for (int i = 0; i < items.length; i++) {
				add(items[i]);
			}
			this.filterredItems = new ArrayList<File>(Arrays.asList(items));
		}else{
			this.filterredItems = new ArrayList<File>();
		}
		

	}

	public void clearSelection() {
		selected.clear();
	}

	/**
	 * 
	 * @return selected files by checkboxes
	 */
	public String[] getSelected() {
		String[] selected = new String[this.selected.size()];
		for (int i = 0; i < selected.length; i++) {
			selected[i] = this.selected.get(i).getPath();
		}
		return selected;
	}

	/**
	 * Should be set to true when have to move files.
	 * 
	 * @param inMoveMode
	 *            used when have to move files from one folder to another
	 */
	public void setInMoveMode(boolean inMoveMode) {
		this.isInMoveMode = inMoveMode;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = null;
		if (convertView == null) {
			LayoutInflater inflator = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflator.inflate(resourceId, null);
			final ViewHolder viewHolder = new ViewHolder();
			viewHolder.fileImage = (ImageView) view
					.findViewById(R.id.icon_image);
			viewHolder.fileName = (TextView) view.findViewById(R.id.textView);
			viewHolder.fileName.setTag(this.getItem(position));
			if (isInMoveMode) {
				viewHolder.checkBox = (CheckBox) view
						.findViewById(R.id.checkbox);
				viewHolder.checkBox.setVisibility(View.VISIBLE);
				viewHolder.checkBox
						.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
							@Override
							public void onCheckedChanged(
									CompoundButton buttonView, boolean isChecked) {
								if (isChecked) {
									selected.add(getItem(position));
									return;
								}
								selected.remove(getItem(position));
							}
						});
			}
			view.setTag(viewHolder);
		} else {
			view = convertView;
			((ViewHolder) view.getTag()).fileName
					.setTag(this.getItem(position));
		}
		ViewHolder holder = (ViewHolder) view.getTag();
		if (this.getItem(position) == null) {
			return view;
		}

		holder.fileName.setText(this.getItem(position).getName());
		holder.fileName.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (RealFileAdapter.this.getItem(position) != null) {
					if (RealFileAdapter.this.getItem(position).isFile()) {
						Intent viewFileIntent = new Intent();
						viewFileIntent
								.setAction(android.content.Intent.ACTION_VIEW);

						File file = new File(RealFileAdapter.this.getItem(
								position).getPath());
						try {
							File tempFile = createTempFile(file, context);
							viewFileIntent.setDataAndType(
									Uri.fromFile(tempFile),
									getMimeType(file.getName()));
							Log.w("Uri.fromFile(file) = ", tempFile.getPath());
							RealFileAdapter.this.getContext().startActivity(
									viewFileIntent);
							return;
						} catch (Exception e) {
							e.printStackTrace();
						}

					} else {
						if (isInMoveMode) {
							Intent foldersActivity = new Intent(context,
									FoldersActivity.class);
							foldersActivity.putExtra("isInMoveMode", true);
							context.startActivity(foldersActivity);
						}
					}
				}
			}
		});

		holder.fileImage.setImageResource(getImageResourceId(this
				.getItem(position)));
		return view;
	}

	private static String getMimeType(String url) {
		String parts[] = url.split("\\.");
		String extension = parts[parts.length - 1];
		String type = null;
		if (extension != null) {
			MimeTypeMap mime = MimeTypeMap.getSingleton();
			type = mime.getMimeTypeFromExtension(extension);
		}
		return type;
	}

	private int getImageResourceId(File file) {

		if (file.isFile()) {
			return R.drawable.form_fill;
		}
		return R.drawable.folder_selected;
		// switch ((int)fileTipe) {
		// case 1:
		// return R.drawable.photo;
		// case 2:
		// return R.drawable.speaker;
		// case 3:
		//
		// case 4:
		//
		// default:
		// return R.drawable.form_fill;
		// }

	}

	@Override
	public void add(File file) {
		super.add(file);
		originalItems.add(file);
		super.notifyDataSetChanged();

	}

	// @Override
	// public File getItem(int position) {
	// if (position < filterredItems.size()) {
	// return filterredItems.get(position);
	// }
	// return null;
	// }

	@Override
	public void clear() {
		super.clear();
		Log.e("cleared", "the list is");
	}

	@Override
	public Filter getFilter() {
		if (filter == null) {
			filter = new NameFilter();
		}
		return this.filter;
	}

	/**
	 * @author sbahdikyan
	 */
	private static class ViewHolder {
		ImageView fileImage;
		TextView fileName;
		CheckBox checkBox;
	}

	private class NameFilter extends Filter {
		private List<File> copyList(List<File> original) {
			List<File> copy = new ArrayList<File>();
			for (File file : original) {
				copy.add(file);
			}
			return copy;
		}

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			FilterResults results = new FilterResults();
			List<File> newItems = new ArrayList<File>();
			if (constraint != null && constraint.toString().trim().length() > 0) {

				// copy all items in new array list

				List<File> newOriginalItems = new ArrayList<File>();
				newOriginalItems = copyList(originalItems);
				// make list from constraint string
				String[] searchStrings = constraint.toString().split(" ", 0);
				for (String searchString : searchStrings) {
					// search by key card name
					newItems.clear();
					for (File file : newOriginalItems) {
						if (file.getName().contains(searchString)) {
							newItems.add(file);
						}
					}
					newOriginalItems = copyList(newItems);
					// Collections.copy(newOriginalItems, newItems);
				}
			}
			// originalItems
			results.count = newItems.size();
			results.values = newItems;
			return results;
		}

		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			clear();
			if (constraint.length() == 0) {
				addAll(originalItems);
				return;
			}
			if (results.count == 0) {
				return;
			}
			RealFileAdapter.this.notifyDataSetChanged();
			filterredItems = (List<File>) results.values;
			for (File item : filterredItems) {
				RealFileAdapter.super.add(item);
			}
			RealFileAdapter.this.notifyDataSetInvalidated();
			return;
		}

	}

	/**
	 * Copies file to temp directory so other apps can access it.
	 * 
	 * @param file
	 *            to be copied
	 * @return the created temp file
	 */
	private static File createTempFile(File file, Context context)
			throws Exception {

		if (!file.exists()) {
			throw new Exception("File does not exists.");
		}
		{

			File targetFile = new File(context.getExternalCacheDir() + "/"
					+ file.getName(),
					getMimeType(context.getExternalFilesDir(null) + "/"
							+ file.getName()));
			// File targetFile = new
			// File(Environment.getExternalStorageDirectory().getAbsolutePath()
			// + "/" + context.getString(R.string.app_name) + "/"
			// + file.getAbsolutePath().replace("/data/data/" +
			// context.getPackageName(), ""));
			targetFile.setReadable(true);
			Log.w("Location of the copied file", targetFile.getPath());
			File parentFile = targetFile.getParentFile();
			if (!parentFile.exists()) {
				parentFile.mkdirs();
			}
			// if (!targetFile.exists()) {
			// try {
			// targetFile.createNewFile();
			// } catch (IOException e) {
			// e.printStackTrace();
			// Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
			// }
			// }
			try {
				OutputStream oStream = new FileOutputStream(
						targetFile.getPath());
				FileInputStream iStream = new FileInputStream(file);
				byte[] buffer = new byte[512];
				int length = 0;
				while ((length = iStream.read(buffer)) > 0) {
					oStream.write(buffer, 0, length);
				}

				oStream.close();
				iStream.close();
				return targetFile;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG)
						.show();
			} catch (IOException e) {
				e.printStackTrace();
				Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG)
						.show();
			}
		}
		throw new Exception("something bad happened");
	}

}
