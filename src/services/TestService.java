package services;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import data.constants.Files;
import data.index.Chunk;
import data.index.MetadataFile;

public class TestService {
	
	private static final String newFile = "test-songs.txt";
	private static final String newMDF = "test-metadata.json";
	
	public static void main(String[] args) {
//		HashService hs = new HashService(true);
//		String payload = "Abba;5";
//		
//		System.out.println(hs.sha1(payload));
		
		
		//appendHash();
		//System.out.println(getHashByChunk(4));
		serializeMDF(new File(Files.ROOT+newFile));
	}
	// TODO: remove trailing spaces
	public static void appendHash() {
		try (BufferedReader br = new BufferedReader(new FileReader(new File(Files.SONGS)))) {
			
			FileWriter writer = new FileWriter(Files.ROOT+newFile);
			HashService hs = new HashService(true);
			String line = "";
		    String chunk;
		    
		    while ((chunk = br.readLine()) != null) {
		    	System.out.println(chunk);
		    	String hashedChunk = hs.sha1(chunk);
		    	System.out.println(hashedChunk);
		    	line = chunk + ";" + hashedChunk + System.lineSeparator();
		    	writer.write(line);
		    }
		    
		    writer.close();
		    
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getHashByChunk(int line) {
		String hash = null;
		
		try (BufferedReader br = new BufferedReader(new FileReader(new File(Files.ROOT+newFile)))) {
		
		    String chunk;
		    int lineCount = 1;
		    
		    while ((chunk = br.readLine()) != null) {
		    	if (lineCount == line) {
		    		String[] tokens = chunk.split(";");
		    		hash = tokens[tokens.length-1];
		    		break;
		    	}
		    	lineCount++;
		    }
		    
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 

		return hash;
	}
	
	public static void serializeMDF(File file) {
		MetadataFile mdf = new MetadataFile();
		
		mdf.name = file.getName();
		mdf.size = Long.toString(file.length()); // in bytes
		
		List<Chunk> chunks = new ArrayList<Chunk>();
		
		String guid = null;
		try (BufferedReader br = new BufferedReader(new FileReader(new File(Files.ROOT+newFile)))) {
			
		    String chunkLine;
		    
		    while ((chunkLine = br.readLine()) != null) {

		    	String[] tokens = chunkLine.split(";");
		    	guid = tokens[tokens.length-1];
		    	Chunk chunk = new Chunk();
		    	chunk.setGuid(guid);
		    	chunks.add(chunk);
		    }
		    
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		mdf.chunks = chunks;
		
		try (Writer writer = new FileWriter(Files.ROOT+newMDF)) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			gson.toJson(mdf, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
