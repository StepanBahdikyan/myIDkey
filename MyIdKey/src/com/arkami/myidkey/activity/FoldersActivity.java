package com.arkami.myidkey.activity;

import java.io.File;
import java.util.Date;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.actionbarsherlock.widget.SearchView;
import com.arkami.myidkey.R;
import com.arkami.myidkey.adapters.RealFileAdapter;
import com.arkami.myidkey.database.datasources.FileDataSource;
import com.arkami.myidkey.database.tables.FileObject;

/**
 * Created with IntelliJ IDEA. User: sbahdikyan Date: 13-8-20 Time: 10:48 To
 * change this template use File | Settings | File Templates.
 */
public class FoldersActivity extends SherlockFragmentOnBackButtonPressed {
	private ListView listView;
	private ActionBar actionBar;
	// private List<FileObject> files;
	private static File realFile;
	private FileDataSource fileDataSource;
	private static long parentId;
	private static boolean isInMoveMode;
	private RealFileAdapter filesAdapter;
	private Button moveButton;
	private MenuItem check;

	//

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.sortable_list, container, false);
		listView = (ListView) view.findViewById(R.id.myListView);
		fileDataSource = new FileDataSource(getSherlockActivity());
		// files = fileDataSource.getNonKeyCardFiles(true);
		setRealFile(new File(getSherlockActivity().getFilesDir()
				+ getString(R.string.files_folder)));
		setMovingButton(view);
		setFilesAdapter(isInMoveMode);
		setHasOptionsMenu(true);
		getSherlockActivity().supportInvalidateOptionsMenu();
		actionBar = getSherlockActivity().getSupportActionBar();
		actionBar.setDisplayShowCustomEnabled(false);
		// actionBar.getCustomView().setVisibility(View.GONE);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayHomeAsUpEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayUseLogoEnabled(false);
		actionBar.setDisplayShowHomeEnabled(false);
		return view;
	}

	/**
	 * initializes moving button
	 * 
	 * @param view
	 */
	private void setMovingButton(View view) {
		moveButton = (Button) view.findViewById(R.id.movingButton);
		moveButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String[] selected = filesAdapter.getSelected();
				if ((selected == null) || (selected.length < 1)) {
					return;
				}
				Intent fileIntent = new Intent(FoldersActivity.this
						.getSherlockActivity(), FileChooserActivity.class);
//				String path = Environment.getExternalStorageDirectory()
//						.getPath();
				fileIntent.putExtra(FileChooserActivity.PATH,
						FoldersActivity.this.getSherlockActivity()
								.getFilesDir().getPath()
								+ getString(R.string.files_folder));
				fileIntent.putExtra(FileChooserActivity.DESTINATION,
						realFile.getPath());
				fileIntent.putExtra(FileChooserActivity.IMPORT, false);
				fileIntent.putExtra("selected", selected);
				startActivity(fileIntent);
				return;
			}
		});
	}

	private static void setRealFile(File file) {
		realFile = file;
		Log.e("realfile ", realFile.getName());
	}

	public void onBackPressed() {
		if (filesAdapter != null) {

			// if (fileDataSource.get(parentId).getParentId() < 0) {
			// Toast.makeText(getSherlockActivity(), "parentId = " + parentId,
			// Toast.LENGTH_LONG).show();
			// Toast.makeText(getSherlockActivity(), "back pressed",
			// Toast.LENGTH_LONG).show();
			// return;
			// }
			// parentId = fileDataSource.get(parentId).getParentId();
			// filesAdapter.addAll(fileDataSource.getChildren(parentId));

			if (String.valueOf(realFile.getPath()).equals(
					String.valueOf(FoldersActivity.this.getSherlockActivity()
							.getFilesDir() + getString(R.string.files_folder)))) {
				getSherlockActivity().finish();
				return;
			}
			filesAdapter.clear();
			setRealFile(realFile.getParentFile());
			filesAdapter.addAll(realFile.listFiles());

		}
	}

	@Override
	public void onResume() {
		super.onResume();
		isInMoveMode = false;
		setFilesAdapter(isInMoveMode);

	}

	@Override
	public void onStart() {
		super.onStart();
		this.getView().setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.equals(KeyEvent.KEYCODE_BACK)) {
					// Toast.makeText(getSherlockActivity(), "back pressed",
					// Toast.LENGTH_LONG).show();
				}
				return false;
			}
		});
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.key_cards, menu);

		SearchView searchView = (SearchView) menu.getItem(0).getActionView();
		// SearchView searchView = new SearchView(getSherlockActivity());

		searchView.setQueryHint(getString(R.string.search_hint));

		searchView.setLayoutParams(new android.widget.FrameLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));
		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String query) {
				String[] searchQuery = query.split(" ");
				for (int i = 0; i < searchQuery.length; i++) {
					FoldersActivity.this.filesAdapter.getFilter().filter(
							searchQuery[i]);
				}
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
				FoldersActivity.this.filesAdapter.getFilter().filter(newText);
				if (actionBar == null) {
					actionBar = getSherlockActivity().getSupportActionBar();
				}

				actionBar.setDisplayShowCustomEnabled(false);
				actionBar.setDisplayHomeAsUpEnabled(false);
				actionBar.setDisplayShowTitleEnabled(false);
				actionBar.setDisplayUseLogoEnabled(false);
				actionBar.setDisplayShowHomeEnabled(false);
				return false;
			}
		});
		MenuItem moveCheckButton = menu.getItem(1);
		moveCheckButton.setVisible(true);
		moveCheckButton.setOnMenuItemClickListener(createMoveListener());
		MenuItem plus = menu.getItem(3);
		plus.setOnMenuItemClickListener((MenuItem.OnMenuItemClickListener) createPlusButtonOnItemClickListener());

		// ...place to add other menu items

		super.onCreateOptionsMenu(menu, inflater);
	}

	private MenuItem.OnMenuItemClickListener createMoveListener() {
		return new MenuItem.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				// filesAdapter = new RealFileAdapter(getSherlockActivity(),
				// R.layout.list_view_row, realFile.listFiles(),true);
				// listView.setAdapter(filesAdapter);
				isInMoveMode = !isInMoveMode;
				setFilesAdapter(isInMoveMode);
				return false;
			}
		};

	}

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
					final Dialog plusMenuPopUp = new Dialog(
							getSherlockActivity());
					plusMenuPopUp
							.requestWindowFeature((int) Window.FEATURE_NO_TITLE);
					plusMenuPopUp.setContentView(R.layout.plus_keycard_popup);
					final View plusFolder = plusMenuPopUp
							.findViewById(R.id.plus_menu_popup_new_folder);
					final View plusFile = plusMenuPopUp
							.findViewById(R.id.plus_menu_file);
					final View plusKeyCard = plusMenuPopUp
							.findViewById(R.id.plus_menu_popup_new_key_card);
					plusKeyCard.setVisibility(View.GONE);
					final View cancel = plusMenuPopUp
							.findViewById(R.id.plus_keycard_popup_cancel_button);

					cancel.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							plusMenuPopUp.dismiss();
						}
					});

					plusFile.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							String path = Environment
									.getExternalStorageDirectory().getPath();
							// XXX remove this. it' just to see the copied
							// files.
							// path =
							// FoldersActivity.this.getSherlockActivity().getFilesDir().getPath();
							Intent fileIntent = new Intent(FoldersActivity.this
									.getSherlockActivity(),
									FileChooserActivity.class);
							fileIntent.putExtra(FileChooserActivity.PATH, path);
							fileIntent.putExtra(
									FileChooserActivity.DESTINATION,
									realFile.getPath());
							fileIntent.putExtra(FileChooserActivity.IMPORT,
									false);
							startActivity(fileIntent);
							return;
						}
					});

					plusFolder.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							final Dialog plusPopUp = new Dialog(
									getSherlockActivity());
							plusPopUp
									.requestWindowFeature((int) Window.FEATURE_NO_TITLE);
							plusPopUp
									.setContentView(R.layout.plus_folder_popup);

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
									File newRealFolder = new File(realFile
											.getPath()
											+ "/"
											+ newItem.getText().toString());

									if (!newRealFolder.exists()) {
										newRealFolder.mkdir();
										Toast.makeText(
												FoldersActivity.this
														.getSherlockActivity(),
												"folder created",
												Toast.LENGTH_SHORT).show();
									}
									filesAdapter.add(newRealFolder);
									FileObject newFolder = new FileObject();

									// KeyCard newFolder = new KeyCard();
									newFolder.setFileType(3L);
									newFolder.setName(newItem.getText()
											.toString());
									newFolder.setCreateDate(new Date()
											.getTime());
									// XXX get it from data base
									newFolder.setKeyCardAttribute(false);
									newFolder.setPathToFile(Environment
											.getExternalStorageDirectory()
											.getPath());

									newFolder.setId(fileDataSource
											.insert(newFolder));

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

	// sets adapter for moving action
	private void setFilesAdapter(boolean isInMoveMode) {
		if (isInMoveMode) {
			moveButton.setVisibility(View.VISIBLE);

		} else {
			moveButton.setVisibility(View.GONE);
		}
		filesAdapter = new RealFileAdapter(getSherlockActivity(),
				R.layout.list_view_row, realFile.listFiles(), isInMoveMode);
		listView.setAdapter(filesAdapter);
		listView.setFastScrollEnabled(true);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if ((filesAdapter.getItem(position) != null)
						&& (filesAdapter.getItem(position).isDirectory())) {
					setRealFile(filesAdapter.getItem(position));
					filesAdapter.clear();
					filesAdapter.addAll(realFile.listFiles());
				}
			}
		});
	}

}
