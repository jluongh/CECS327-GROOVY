package data.index;

public class Chunk {
	private int guid;
	String first, last;

	
	public Chunk() {
	}

	public int getGuid() {
		return guid;
	}

	public void setGuid(int guid) {
		this.guid = guid;
	}
	
	public void setFristLast(String f, String l)
	{
		this.first = f;
		this.last = l;
	}
}
