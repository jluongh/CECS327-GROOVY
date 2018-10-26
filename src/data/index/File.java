package data.index;

import java.util.ArrayList;
import java.util.List;

/*
 * I renamed this to MetadataFile model since it conflicts with the java.io.File class.  Delete later... - @trishaechual
 */
public class File {
	public String name;
	public String size;
	public List<Chunk> chunks = new ArrayList<Chunk>();
	
	public void test() {
		File a = new File();
		a.name = "song.txt";
		a.size = "B";
		Chunk a1 = new Chunk();
		a1.guid = "Everybody Wants To Rule The World;1;Tears For Fears;Songs From The Big Chair;4.13";
		Chunk a2 = new Chunk();
		a2.guid = "September;2;Earth, Wind, \\u0026 Fire;Greatest Hits;3.35";

		a.chunks.add(a1);
		a.chunks.add(a2);

	}
}

