//package data.init;
//
//import data.constants.Files;
//import data.index.*;
//
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.Writer;
//import java.util.*;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//
///**
// * Unoptimized/Semi-automated builder smh.
// * @author trishaechual
// *
// */
//public class MDFBuilder {
//	
//	private Chunk chunk1;
//	private Chunk chunk2;
//	private Chunk chunk3;
//	private List<Chunk> chunks;
//	private List<MetadataFile> mdfs;
//	
//	public MDFBuilder() {
//		//
//	}
//	
//	/**
//	 * 
//	 */
//	public void configure() {
//		mdfs = new ArrayList<MetadataFile>();
//		init();
//		build();
//	}
//	
//	/**
//	 * 
//	 */
//	private void init() {
//		
//		/* -------------------- album -------------------- */
//		chunk1 = new Chunk("4154");
//		chunk1.setFirstLine("Abba;13;4154");
//		chunk1.setLastLine("Can't Get Enough;5;4154");
//		
//		chunk2 = new Chunk("2861");
//		chunk2.setFirstLine("Greatest Hits;10;2861");
//		chunk2.setLastLine("I Like It When You Sleep, for You Are So Beautiful Yet So Unaware Of It;3;2861");
//		
//		chunk3 = new Chunk("1128");
//		chunk3.setFirstLine("Songs From The Big Chair;1;1128");
//		chunk3.setLastLine("�;15;1128");
//		
//		chunks = new ArrayList<Chunk>();
//		chunks.add(chunk1);
//		chunks.add(chunk2);
//		chunks.add(chunk3);
//		
//		MetadataFile mdf1 = new MetadataFile(new File(Files.ALBUMS));
//		mdf1.setChunks(chunks);
//		
//		mdfs.add(mdf1);
//		
//		/* -------------------- artist -------------------- */
//		chunk1 = new Chunk("1075");
//		chunk1.setFirstLine("ABBA;11;1075");
//		chunk1.setLastLine("Ed Sheeran;14;1075");
//		
//		chunk2 = new Chunk("9623");
//		chunk2.setFirstLine("Haley Reinhart;2;9623");
//		chunk2.setLastLine("Haley Reinhart;2;9623");
//		
//		chunk3 = new Chunk(null);
//		
//		chunks = new ArrayList<Chunk>();
//		chunks.add(chunk1);
//		chunks.add(chunk2);
//		chunks.add(chunk3);
//		
//		MetadataFile mdf2 = new MetadataFile(new File(Files.ARTISTS));
//		mdf2.setChunks(chunks);
//		
//		mdfs.add(mdf2);
//		
//	}
//	
//	/**
//	 * 
//	 */
//	private void build() {
//		try (Writer writer = new FileWriter(Files.ROOT+"test.json")) {
//			Gson gson = new GsonBuilder().setPrettyPrinting().create();
//			gson.toJson(this.mdfs, writer);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//}
