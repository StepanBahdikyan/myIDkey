/**
 *
 */
package com.arkami.myidkey.activity;

import java.io.File;
import java.util.Date;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.arkami.myidkey.R;
import com.arkami.myidkey.database.datasources.FileDataSource;
import com.arkami.myidkey.database.tables.FileObject;

/**
 * @author sbahdikyan
 */
public class ViewImageIActivity extends SherlockFragmentActivity {

	private ImageView image;
	private FileObject imageFile;
	public static final String IMAGE_KEY = "imageKey";
	public Bitmap pictureBitmap;
	private View shareButton;
	private View editButton;
	private TextView imageName;
	private FileDataSource fileDataSource;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_image_activity);
		fileDataSource = new FileDataSource(this);
		image = (ImageView) findViewById(R.id.view_image_activity_image);
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setCustomView(R.layout.actionbar_view_image);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			imageFile = (FileObject) extras.getParcelable(IMAGE_KEY);
		}
		if (savedInstanceState != null) {
			imageFile = savedInstanceState.getParcelable("imageFile");
			pictureBitmap = (Bitmap) savedInstanceState
					.getParcelable("pictureBitmap");
		} else {
			pictureBitmap = BitmapFactory.decodeFile(imageFile.getPathToFile());
		}
		final File file = new File(imageFile.getPathToFile());
		image.setImageBitmap(pictureBitmap);
		imageName = (TextView) findViewById(R.id.view_image_activity_image_name);
		imageName.setText(file.getName());

		shareButton = actionBar.getCustomView().findViewById(
				R.id.action_bar_view_image_share);
		shareButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent share = new Intent(Intent.ACTION_SEND);
				share.setType("image/*");
				share.putExtra(android.content.Intent.EXTRA_STREAM,
						Uri.fromFile(file));
				startActivity(share);
			}
		});
		editButton = findViewById(R.id.action_bar_view_image_edit);
		editButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (imageFile.getId() == null) {
					Toast.makeText(ViewImageIActivity.this,
							"KeyCard is not saved.", Toast.LENGTH_LONG).show();
				} else {
					showRenamePopUp();
				}
			}
		});
	}

	/**
	 * Shows menu for rename functionality.
	 */
	private void showRenamePopUp() {
		final Dialog renameDialog = new Dialog(this);
		renameDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		renameDialog.setContentView(R.layout.rename_image_layout);
		Button ok = (Button) renameDialog.findViewById(R.id.rename_image_ok);
		Button cancel = (Button) renameDialog
				.findViewById(R.id.rename_image_cancel);
		final EditText newName = (EditText) renameDialog
				.findViewById(R.id.rename_image_new_name);
		cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				renameDialog.dismiss();
			}
		});
		ok.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String newfileName = newName.getText().toString();
				rename(newfileName);
				imageFile.setModifyDate(new Date().getTime());
				imageFile.setName(newfileName);
				imageFile = renameFileObject(newfileName, imageFile);
				if (imageFile.getId() == null) {
					fileDataSource.insert(imageFile);
				} else {
					fileDataSource.update(imageFile);
				}
				imageName.setText(newfileName);
			}
		});
		renameDialog.show();

	}

	/**
	 * @param name
	 * @param fileObject
	 * @return
	 */
	private FileObject renameFileObject(String name, FileObject fileObject) {
		if ((name != null) && (name.length() > 0) && (fileObject != null)
				&& (fileObject.getPathToFile() != null)
				&& (fileObject.getPathToFile().length() > 2)) {
			File file = new File(fileObject.getPathToFile());
			fileObject.setPathToFile(file.getParentFile().getPath() + "/"
					+ name);
		}
		return fileObject;
	}

	/**
	 * renames a file in the file system.
	 * 
	 * @param newfileName
	 *            the new file name.
	 * @return true if it succeed.
	 */
	private boolean rename(String newfileName) {
		File oldFile = new File(imageFile.getPathToFile());
		File newFile = new File(oldFile.getParentFile().getPath() + "/"
				+ newfileName);

		if ((newfileName != null) && (newfileName.length() > 0)
				&& (newFile.exists() == false)) {
			return oldFile.renameTo(newFile);
		}
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putParcelable("imageFile", imageFile);
		outState.putParcelable("pictureBitmap", pictureBitmap);
	}
}
