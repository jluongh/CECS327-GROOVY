package services;

import java.io.*;

import data.constants.Files;

public class TestService {
	
	private static final String newFile = "test-songs.txt";
	
	public static void main(String[] args) {
//		HashService hs = new HashService(true);
//		String payload = "Abba;5";
//		
//		System.out.println(hs.sha1(payload));
		
		
		//appendHash();
		System.out.println(getHashByChunk(4));
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
}
