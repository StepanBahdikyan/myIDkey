/**
 * 
 */
package com.arkami.myidkey.util;

import android.content.Context;
import android.widget.Toast;

/**
 * @author sbahdikyan
 * 
 */
public class ErrorHandler {

	public static void error(boolean isShownToUser, ErrorCode errorCode,
			Context context) {
		if (isShownToUser) {
			Toast.makeText(context, errorCode.name(), Toast.LENGTH_LONG).show();
		}
	}
}
