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
		Chunk a3 = new Chunk();
		a3.guid = "Let\\u0027s Groovy;6;Earth, Wind, \\u0026 Fire;Greatest Hits;3.55";
		Chunk a4 = new Chunk();
		a4.guid = "Dancing Queen;3;ABBA;Arrival;3.51";
		Chunk a5 = new Chunk();
		a5.guid = "Mamma Mia;5;ABBA;Abba;3.31";
		Chunk a6 = new Chunk();
		a6.guid = "Bohemian Rhapsody;4;Queen;Stone Cold Classics;6.07";
		
		a.chunks.add(a1);
		a.chunks.add(a2);
		a.chunks.add(a3);
		a.chunks.add(a4);
		a.chunks.add(a5);
		a.chunks.add(a6);

	}
}

