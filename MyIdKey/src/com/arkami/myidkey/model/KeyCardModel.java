/**
 * 
 */
package com.arkami.myidkey.model;

import java.io.Serializable;
import java.util.List;

/**
 * Represents key card object.
 * 
 * @author sbahdikyan
 * 
 */
public class KeyCardModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5396288214235863584L;
	private Long id;
	private String name;
	private List<Tag> tags;
	private boolean isFavourite;
	private KeyCardTypeEnum type;
	private Long parentId;
	private List<Long> childrenIds;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the tags
	 */
	public List<Tag> getTags() {
		return tags;
	}

	/**
	 * @param tags
	 *            the tags to set
	 */
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	/**
	 * @return the isFavourite
	 */
	public boolean isFavourite() {
		return isFavourite;
	}

	/**
	 * @param isFavourite
	 *            the isFavourite to set
	 */
	public void setFavourite(boolean isFavourite) {
		this.isFavourite = isFavourite;
	}

	/**
	 * @return the type
	 */
	public KeyCardTypeEnum getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(KeyCardTypeEnum type) {
		this.type = type;
	}

	// /**
	// * @param parrent
	// * the parrent to set
	// */
	// public void setParrent(KeyCardModel parrent) {
	//
	// if (parrent == null) {
	// throw new IllegalArgumentException("parrent cannot be null.");
	// }
	//
	// if (parrent.getType() != KeyCardType.folder) {
	// throw new IllegalArgumentException(
	// "parrent must be of type folder.");
	// }
	//
	// if (parrent.getName().equals(this.name)) {
	// throw new IllegalArgumentException(
	// "Item cannot be a parent of itself or a sub directory.");
	// }
	//
	// if (children != null) {
	// for (KeyCardModel child : children) {
	// child.containsChild(child);
	// }
	// }
	// this.parrent = parrent;
	// }

	// /**
	// * Returns true if this has a child with the name of that child
	// *
	// * @param child
	// * child to be searched in childrens' list
	// */
	// private boolean containsChild(KeyCardModel child) {
	// if ((children != null) && (children.isEmpty() == false)) {
	// for (KeyCardModel childNode : children) {
	// if(childNode.getName().equals(child.getName())){
	// return false;
	// }
	// return containsChild(child);
	// }
	// }
	// return true;
	// }

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
	 * @return the childrenIds
	 */
	public List<Long> getChildrenIds() {
		return childrenIds;
	}

	/**
	 * @param childrenIds
	 *            the childrenIds to set
	 */
	public void setChildrenIds(List<Long> childrenIds) {
		this.childrenIds = childrenIds;
	}

	/**
	 * @return the parentId
	 */
	public Long getParentId() {
		return parentId;
	}

	/**
	 * @param parentId
	 *            the parentId to set
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

}
