package data.models;

import java.io.Serializable;

public class FileEvent implements Serializable {

	//global variables
	private static final long serialVersionUID = 1L;

	private String path;
	private String fileName;
	private long fileSize;
	private byte[] fileData;
	private String status;
	
	public FileEvent() {
		//no initialization
	}

	/**
	 * Getter method for path
	 * @return path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Setter method for path
	 * @param path - {String} name of the file path
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * Getter method for file name
	 * @return fileName
	 */
	public String getfileName() {
		return fileName;
	}

	/**
	 * Setter method for file name
	 * @param fileName - {String} name of the file
	 */
	public void setfileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * Getter method for file size
	 * @return fileSize
	 */
	public long getFileSize() {
		return fileSize;
	}

	/**
	 * Setter method for file size
	 * @param fileSize - {long} size of the file
	 */
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	/**
	 * Getter method for the status
	 * @return status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Setter method for the status
	 * @param status - {String} status of the file event
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Getter method for file data
	 * @return fileData
	 */
	public byte[] getFileData() {
		return fileData;
	}

	/**
	 * Setter method for file data
	 * @param fileData - {List} the file data stored as a list of bytes 
	 */
	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}
}