package data.index;

import java.util.*;

public class MetadataFile {

	private String name;
	private int size;
	private List<Chunk> chunks = new ArrayList<Chunk>();
	
	public MetadataFile() {
		//
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public List<Chunk> getChunks() {
		return chunks;
	}

	public void setChunks(List<Chunk> chunks) {
		this.chunks = chunks;
	}
	
}
