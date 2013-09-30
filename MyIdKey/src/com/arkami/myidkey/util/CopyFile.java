/**
 * 
 */
package com.arkami.myidkey.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

/**
 * @author sbahdikyan
 * 
 */
public class CopyFile extends AsyncTask<File, Void, Boolean> {

    public static final String BROADCAST_MESSAGE_PHOTO_COPY_ACTION = "BROADCAST_MESSAGE_PHOTO_COPY_ACTION";
    public static final String BROADCAST_MESSAGE_PHOTO_COPY_EXTRA = "BROADCAST_MESSAGE_PHOTO_COPY_EXTRA";


//	private ProgressDialog progressDialog;
	private Context context;
    private File file;
    private String filePath;
	/**
	 * @param context
	 *            needed to show progress bar
	 */
	public CopyFile(Context context) {
		this.context = context;
	}

	@Override
	protected Boolean doInBackground(File... args) {
	    file = (File) args[0];

		if ((file != null) && (file.exists()) && (file.isFile())
				&& (file.canRead())) {
            File fileDir = new File("/sdcard/myAppFolder");
            if(!fileDir.exists()){
                if(fileDir.mkdir()) {
                    Log.w("copying file ", "/sdcard/myAppFolder crated!");
                }
            }
            filePath = "/sdcard/myAppFolder"
//			context.getFilesDir().getAbsoluteFile()
					+ "/" + file.getName();

			long start = Calendar.getInstance().getTimeInMillis();
			InputStream in;
			try {
				in = new FileInputStream(file);

				OutputStream out = new FileOutputStream(new File(filePath));
				// Transfer bytes from in to out
				byte[] buf = new byte[4096];
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				in.close();
				out.close();
				long stop = Calendar.getInstance().getTimeInMillis();
                Log.w("file copied in: ",filePath);
				Log.w("time.", stop - start + " ms");
			} catch (FileNotFoundException e) {
                Log.e("copy file ", "file not found");
				e.printStackTrace();
				return false;
			} catch (IOException e) {
                Log.e("copy file ", "IOE");
				e.printStackTrace();
				return false;
			}
		} else{
            Log.w("File is null", (file == null) + "");
            Log.w("File is existing", file.exists()  + "");
            Log.w("File is file", file.isFile() + "");
            Log.w("File is readable", file.canRead() + "");
        }
		return true;
	}

	@Override
	protected void onPreExecute() {

		super.onPreExecute();
//		progressDialog = new ProgressDialog(context);
//		progressDialog.setMessage(context
//				.getString(R.string.copy_file_progress_message));
//		progressDialog.show();
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
//		this.progressDialog.dismiss();
        if(filePath != null){
        Intent broadcastIntent = new Intent(context, context.getClass());
        broadcastIntent.setAction(BROADCAST_MESSAGE_PHOTO_COPY_ACTION);
        broadcastIntent.putExtra(BROADCAST_MESSAGE_PHOTO_COPY_EXTRA, filePath);
        broadcastIntent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);

        LocalBroadcastManager.getInstance(context).sendBroadcast(broadcastIntent);
        }
        Log.w("Copy File is null: ", (filePath==null)+"");
	}

	@Override
	protected void onCancelled() {
	}

	@Override
	protected void onCancelled(Boolean result) {
	}
}
