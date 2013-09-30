/**
 * 
 */
package com.arkami.myidkey.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.arkami.myidkey.database.datasources.ValuesDataSource;
import com.arkami.myidkey.database.tables.Value;

/**
 * @author sbahdikyan
 * 
 */
public class SavingTask extends AsyncTask<Value[], Void, Void> {

	private ValuesDataSource valuesDataSource;
	private Context context;
	private ProgressDialog progressDialog;

	public SavingTask(Context context) {
		this.context = context;
		this.valuesDataSource = new ValuesDataSource(context);
	}

	@Override
	protected Void doInBackground(Value[]... params) {
		Value[] values = params[0];
		for (Value value : values) {
			valuesDataSource.update(value);
		}
		return null;
	}

	@Override
	protected void onPreExecute() {

		super.onPreExecute();
		progressDialog = new ProgressDialog(context);
		progressDialog.setMessage("saving");
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.setCancelable(false);
		progressDialog.show();
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		this.progressDialog.dismiss();
	}

}
