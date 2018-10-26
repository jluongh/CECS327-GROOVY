package data.index;

import java.util.ArrayList;
import java.util.List;

public class MetadataFile {
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
	
	public void testAlbum() {
		File b = new File();
		b.name = "album.txt";
		b.size = "B";
		Chunk b1 = new Chunk();
		b1.guid = "Abba;5";
		Chunk b2 = new Chunk();
		b2.guid = "Arrival;3";
		Chunk b3 = new Chunk();
		b3.guid = "Greatest Hits;2";
		Chunk b4 = new Chunk();
		b4.guid = "Songs From The Big Chair;1";
		Chunk b5 = new Chunk();
		b5.guid = "Stone Cold Classics;4";
		
		b.chunks.add(b1);
		b.chunks.add(b2);
		b.chunks.add(b3);
		b.chunks.add(b4);
		b.chunks.add(b5);
	}
	
	public void testArtist() {
		File c = new File();
		c.name = "artist.txt";
		c.size = "B";
		Chunk c1 = new Chunk();
		c1.guid = "ABBA;3";
		Chunk c2 = new Chunk();
		c2.guid = "Earth, Wind, & Fire;2";
		Chunk c3 = new Chunk();
		c3.guid = "Queen;4";
		Chunk c4 = new Chunk();
		c4.guid = "Tears For Fears;1";
		
		c.chunks.add(c1);
		c.chunks.add(c2);
		c.chunks.add(c3);
		c.chunks.add(c4);
	}
}
