/**
 * 
 */
package com.arkami.myidkey.database.tables;

/**
 * @author sbahdikyan
 * 
 */
public class GenericDataObject {
	public static final String ID = "_id";
	public static final String CREATE_DATE = "createDate";
	public static final String MODIFY_DATE = "modifyDate";

	private Long id;
	private Long createDate;
	private Long modifyDate;
	public static final String SEPARATOR = ":";

	/**
	 * @param ids
	 *            to be concated with | separator
	 * @return
	 */
	protected static String concatIds(long[] ids) {
		if ((ids == null) || (ids.length == 0)) {
			return String.valueOf("");
		}
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < ids.length - 1; i++) {
			builder.append(ids[i]);
			builder.append(SEPARATOR);
		}
		builder.append(ids[ids.length - 1]);
		return builder.toString();
	}

	/**
	 * @param ids
	 *            string of concated ids
	 * @return list of ids
	 */
	public static long[] getIDs(String ids) {
		long[] result = new long[0];

		if ((ids != null) && (ids.length() > 0)) {
			String[] split = ids.split(SEPARATOR, 0);
			result = new long[split.length];

			for (int i = 0; i < result.length; i++) {
				long id = Long.valueOf(split[i]);
				if (id > 0) {
					result[i] = id;
				}
			}
		}
		return result;
	}

	/**
	 * removes a tag from keyCard
	 * 
	 * @param id
	 */
	public static long[] removeId(long idForRemove, long[] ids) {
		long[] newIds = new long[ids.length - 1];
		int i = 0;
		for (long id : ids) {
			if (id != idForRemove) {
				newIds[i++] = id;
			}
		}
		return newIds;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the createDate
	 */
	public Long getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate
	 *            the createDate to set
	 */
	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the modifyDate
	 */
	public Long getModifyDate() {
		return modifyDate;
	}

	/**
	 * @param modifyDate
	 *            the modifyDate to set
	 */
	public void setModifyDate(Long modifyDate) {
		this.modifyDate = modifyDate;
	}
}
