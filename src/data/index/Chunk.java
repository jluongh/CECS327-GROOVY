package data.index;

public class Chunk {
	private String guid;
	private String firstLine;
	private String lastLine;

	public Chunk(String guid) {
		this.guid = guid;
	}

	public String getGuid() {
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
