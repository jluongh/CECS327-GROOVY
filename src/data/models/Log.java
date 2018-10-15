package data.models;

import java.net.InetAddress;

public class Log implements Comparable<Object> {
	public int logID;
	public InetAddress clientAddress;
	public int port;
	public int requestID;
	public boolean success;
	/**
	 * @return the logID
	 */
	public int getLogID() {
		return logID;
	}
	/**
	 * @param logID the logID to set
	 */
	public void setLogID(int logID) {
		this.logID = logID;
	}
	/**
	 * @return the clientAddress
	 */
	public InetAddress getClientAddress() {
		return clientAddress;
	}
	/**
	 * @param clientAddress the clientAddress to set
	 */
	public void setClientAddress(InetAddress clientAddress) {
		this.clientAddress = clientAddress;
	}
	/**
	 * @return the requestID
	 */
	public int getRequestID() {
		return requestID;
	}
	/**
	 * @param requestID the requestID to set
	 */
	public void setRequestID(int requestID) {
		this.requestID = requestID;
	}
	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}
	/**
	 * @param success the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	/** 
	 * Comparing user for validation
	 * @param o - log object
	 * @return 
	 */
	@Override
	public int compareTo(Object o) {
		Log log = (Log) o;
		return this.logID - log.logID;
	}
	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}
	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}
}
