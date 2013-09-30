/**
 * 
 */
package com.arkami.myidkey.util;

import java.util.Comparator;

/**
 * @author sbahdikyan
 *
 */
public class StringComparator implements Comparator<String> {

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(String first, String second) {
		return first.compareTo(second);
	}

}
