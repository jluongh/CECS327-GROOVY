package data.index;

import java.util.ArrayList;
import java.util.List;

import services.HashService;

public class MetadataFile {
	private String name;
	private String size;
	private List<Chunk> chunks = new ArrayList<Chunk>();
	private HashService hs = new HashService();
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
	public void append(String content)
	{
		String guid = hs.sha1(content);
		Chunk c = new Chunk();
		c.setGuid(guid);
		chunks.add(c);
		//find peer and put guid and content
	}
}
