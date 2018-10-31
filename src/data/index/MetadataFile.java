package data.index;

import java.util.ArrayList;
import java.util.List;

public class MetadataFile {
	private String name;
	private String size;
	private List<Chunk> chunks = new ArrayList<Chunk>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public List<Chunk> getChunks() {
		return chunks;
	}
	public void setChunks(List<Chunk> chunks) {
		this.chunks = chunks;
	}
}
