/**
 * 
 */
package com.arkami.myidkey.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.AsyncTask;

import com.arkami.myidkey.MainActivity;
import com.arkami.myidkey.R;

/**
 * @author sbahdikyan
 * 
 */
public class ImportFromCSVTask extends AsyncTask<String, Void, Void> {

	private Context context;
	private ProgressDialog progressDialog;
	private int imported;
	private int all;

	public ImportFromCSVTask(Context context) {
		this.context = context;
	}

	@Override
	protected Void doInBackground(String... params) {
		CSVImporter csvImporter = new CSVImporter(context);
		csvImporter.importFromCSV(params[0]);
		imported = csvImporter.getImported();
		all = csvImporter.getAll();
		return null;
	}

	@Override
	protected void onPreExecute() {

		super.onPreExecute();
		progressDialog = new ProgressDialog(context);
		progressDialog.setMessage(context
				.getString(R.string.import_csv_file_progress_message));
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.setCancelable(false);
		progressDialog.show();
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		this.progressDialog.dismiss();
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage("Imported " + this.imported + "/" + this.all)
				.setCancelable(true);
		builder.setPositiveButton("ok", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent returnToHome = new Intent(context,MainActivity.class);
                returnToHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(returnToHome);
            }
        });

		Dialog message = builder.create();
		message.show();
	}

	@Override
	protected void onCancelled() {
	}

	@Override
	protected void onCancelled(Void result) {
	}
}
