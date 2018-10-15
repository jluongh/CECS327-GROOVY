package data.models;

public class Message {
	
	public int messageType;
	public int requestID;
	public int objectID;
	public int offset;
	public int count;
	public byte[] fragment;
	/**
	 * @return the messageType
	 */
	public int getMessageType() {
		return messageType;
	}
	/**
	 * @param messageType the messageType to set
	 */
	public void setMessageType(int messageType) {
		this.messageType = messageType;
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
	 * @return the offset
	 */
	public int getOffset() {
		return offset;
	}
	/**
	 * @param offset the offset to set
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}
	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}
	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}
	/**
	 * @return the fragment
	 */
	public byte[] getFragment() {
		return fragment;
	}
	/**
	 * @param fragment the fragment to set
	 */
	public void setFragment(byte[] fragment) {
		this.fragment = fragment;
	}
	
}
