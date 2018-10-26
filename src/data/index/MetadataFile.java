package data.index;

import java.util.ArrayList;
import java.util.List;

public class MetadataFile {
	public String name;
	public String size;
	public List<Chunk> chunks = new ArrayList<Chunk>();
}
