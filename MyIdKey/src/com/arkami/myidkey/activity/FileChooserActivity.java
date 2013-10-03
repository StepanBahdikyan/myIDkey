package com.arkami.myidkey.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Window;
import com.arkami.myidkey.MainActivity;
import com.arkami.myidkey.R;
import com.arkami.myidkey.adapters.FileChooserAdapter;
import com.arkami.myidkey.database.datasources.FileDataSource;
import com.arkami.myidkey.database.tables.FileObject;
import com.arkami.myidkey.util.ImportFromCSVTask;

/**
 * Created with IntelliJ IDEA. User: sbahdikyan Date: 13-7-8 Time: 12:06 To
 * change this template use File | Settings | File Templates.
 */
public class FileChooserActivity extends SherlockActivity {
	private ListView listView;
	private ArrayAdapter<File> adapter;
	private File path;
	private FilenameFilter filter;
	private boolean isForCSVImport;
	public static final String PATH = "path";
	public static final String DESTINATION = "destination";
	public static final String IMPORT = "import";
	private String[] filesToMove;
	private LinearLayout movingButtonsLayout;
	private Button movingOk;
	private Button movingCancel;
	private static boolean hasToExit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		path = new File(getIntent().getStringExtra(PATH));
        requestWindowFeature((int) Window.FEATURE_NO_TITLE);
		setContentView(R.layout.sortable_list);

        View title = findViewById(R.id.title);
        title.setVisibility(View.VISIBLE);

		listView = (ListView) findViewById(R.id.myListView);
//		listView.setFastScrollEnabled(true);
		if (getIntent().hasExtra("selected")) {
			this.setTitle("Select a Destination Folder");
			filesToMove = getIntent().getStringArrayExtra("selected");
			movingButtonsLayout = (LinearLayout) findViewById(R.id.movingDestinationButtons);
			movingButtonsLayout.setVisibility(View.VISIBLE);
			movingCancel = (Button) findViewById(R.id.movingCancelButton);
			movingCancel.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					hasToExit = true;
					FileChooserActivity.this.onBackPressed();
				}
			});
			movingOk = (Button) findViewById(R.id.movingOkButton);
			movingOk.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					for (int i = 0; i < filesToMove.length; i++) {
						copyFiles(new File(filesToMove[i]), path,
								FileChooserActivity.this);
					}
					for (int i = 0; i < filesToMove.length; i++) {
						new File(filesToMove[i]).delete();
					}
					hasToExit = true;
					onBackPressed();
				}
			});
		} else {
			this.setTitle("Choose File");
		}

		// set this to true if not working
		isForCSVImport = getIntent().getBooleanExtra(IMPORT, false);

		final File destination;
		if (getIntent().hasExtra(DESTINATION)) {
			destination = new File(getIntent().getStringExtra(DESTINATION));
		} else {
			destination = new File(getFilesDir().getPath()+getString(R.string.files_folder));
		}
		if ((path == null)) {
			path = new File(Environment.getExternalStorageDirectory().getPath());
//					+ getString(R.string.files_folder));
		}
		if (path.isFile()) {
			if (isForCSVImport) {
				ImportFromCSVTask importFromCSVTask = new ImportFromCSVTask(
						this);
				importFromCSVTask.execute(path.getPath());
				return;
			}
			int id = android.os.Process.myPid();

			copyFiles(path, destination, this);
			FileObject fileObject = new FileObject();
			fileObject.setFileType(getFileType(path));
			fileObject.setKeyCardAttribute(false);
			fileObject.setPathToFile(path.getPath());
			fileObject.setName(path.getName());
			fileObject.setSize(path.length());
			fileObject.setCreateDate(new Date().getTime());
			fileObject.setModifyDate(fileObject.getCreateDate());
			FileDataSource fileDataSource = new FileDataSource(this);
			fileDataSource.insert(fileObject);
			//Returning to home
			Intent returnToHome = new Intent(this, MainActivity.class);
			returnToHome.putExtra(MainActivity.SELECTED_TAB, 1);
			returnToHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			this.startActivity(returnToHome);
			return;
		}
		filter = new FilenameFilter() {
			public boolean accept(File dir, String filename) {
				File sel = new File(dir, filename);
				if (isForCSVImport) {
					return filename.contains(".csv")
							|| (sel.isDirectory() && !sel.isHidden());
				}
				return ((sel.isDirectory() || sel.isFile()) && !sel.isHidden());
			}
		};

		File[] files = new File[] {};
		try {
			path.mkdirs();
		} catch (SecurityException e) {
			Log.e("choose file",
					"unable to write on the sd card " + e.toString());
		}
		if (path.exists()) {
			files = path.listFiles(filter);
		}
		this.adapter = new FileChooserAdapter(this, R.layout.list_view_row,
				files);
		this.listView.setAdapter(this.adapter);
		this.listView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Intent fileIntent = new Intent(new Intent(
								FileChooserActivity.this,
								FileChooserActivity.class));
						fileIntent.putExtra(PATH, adapter.getItem(position)
								.getPath());
						if (destination != null) {
							fileIntent.putExtra(DESTINATION,
									destination.getPath());
						}
						if (filesToMove != null) {

							fileIntent.putExtra("selected", filesToMove);
						}
						fileIntent.putExtra(IMPORT, isForCSVImport);
						startActivity(fileIntent);
					}
				});
	}

	private long getFileType(File file) {
		if ((file.getName().contains(".jpg"))
				|| (file.getName().contains(".jpeg"))
				|| (file.getName().contains(".png"))
				|| (file.getName().contains(".gif"))) {
			return 1L;
		}
		return 4L;
	}

	/**
	 * Copies file to the app private folder.
	 * 
	 * @param file
	 *            to be copied
	 * @param context
	 */
	public static void copyFiles(File file, File destination, Context context) {
		if ((file == null) || (!file.exists()) || (destination == null)
				|| (!destination.exists())) {
			return;
		}

		if (file.isDirectory()) {
			File[] listFiles = file.listFiles();
			for (File afile : listFiles) {
				copyFiles(afile, destination, context);
			}

		} else {
			File targetFile = new File(destination.getPath() + "/"
					+ file.getName());
			// File targetFile = new
			// File(Environment.getExternalStorageDirectory().getAbsolutePath()
			// + "/" + context.getString(R.string.app_name) + "/"
			// + file.getAbsolutePath().replace("/data/data/" +
			// context.getPackageName(), ""));
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
				Log.w("Location of the copied file", targetFile.getPath());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onResume() {
		if (hasToExit) {
			onBackPressed();
		}
		super.onResume();
	}

}
