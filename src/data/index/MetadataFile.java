package data.index;

import java.io.File;
import java.util.*;

public class MetadataFile {

	private String name;
	private long size;
	private List<Chunk> chunks = new ArrayList<Chunk>();
	
	public MetadataFile(File file) {
		this.name = file.getName();
		this.size = file.length();
	}

	public String getName() {
		return name;
	}

	public long getSize() {
		return size;
	}

	public List<Chunk> getChunks() {
		return chunks;
	}

	public void setChunks(List<Chunk> chunks) {
		this.chunks = chunks;
	}
	
}
