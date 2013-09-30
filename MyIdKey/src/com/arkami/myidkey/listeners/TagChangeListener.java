/**
 * 
 */
package com.arkami.myidkey.listeners;

/**
 * @author sbahdikyan
 * 
 */
public interface TagChangeListener {
	public static final String LISTENER = "listener";

	/**
	 * called when tags are set
	 */
	public void onTagsSet();

	/**
	 * Called when new tag is created by the user;
	 */
	public void onTagAdded();
}
