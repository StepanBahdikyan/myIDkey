/**
 * 
 */
package com.arkami.myidkey.model;

import java.io.Serializable;

/**
 * Photo or Video file
 * 
 * @author sbahdikyan
 * 
 */
public class AppFile implements Serializable {
	private String fileName;
	private String pathToFile;
	private FileTypeEnum fileType;
	private long fileSize;

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the pathToFile
	 */
	public String getPathToFile() {
		return pathToFile;
	}

	/**
	 * @param pathToFile
	 *            the pathToFile to set
	 */
	public void setPathToFile(String pathToFile) {
		this.pathToFile = pathToFile;
	}

	/**
	 * @return the fileType
	 */
	public FileTypeEnum getFileType() {
		return fileType;
	}

	/**
	 * @param fileType
	 *            the fileType to set
	 */
	public void setFileType(FileTypeEnum fileType) {
		this.fileType = fileType;
	}

	/**
	 * @return the fileSize
	 */
	public long getFileSize() {
		return fileSize;
	}

	/**
	 * @param fileSize
	 *            the fileSize to set
	 */
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
}
