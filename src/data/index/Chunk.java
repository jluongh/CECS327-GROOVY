package data.index;

public class Chunk {
	private int guid;
	private String firstLine;
	private String lastLine;

	public Chunk(int guid) {
		this.guid = guid;
	}

	public int getGuid() {
		return guid;
	}

	public String getFirstLine() {
		return firstLine;
	}

	public void setFirstLine(String firstLine) {
		this.firstLine = firstLine;
	}

	public String getLastLine() {
		return lastLine;
	}

	public void setLastLine(String lastLine) {
		this.lastLine = lastLine;
	}

}
