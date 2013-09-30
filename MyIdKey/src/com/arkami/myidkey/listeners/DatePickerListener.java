/**
 * 
 */
package com.arkami.myidkey.listeners;


/**
 * @author sbahdikyan
 * 
 */
public interface DatePickerListener {
	String ID = "id";
	String CONTEXT = "context";
	String LISTENER = "listener";

	public void onDateSet(int id, String date);
}
