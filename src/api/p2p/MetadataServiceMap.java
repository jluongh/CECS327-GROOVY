package api.p2p;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;


public class MetadataServiceMap extends Mapper<String, String, String, String>{

	List<Peer> peers;

	public MetadataServiceMap(List<Peer> peers) {
		this.peers = peers;
	}
	
	

	   public void map(String key, String value, Context con) throws IOException, InterruptedException
	    {
	        String line = value.toString();
	        String[] words=line.split(",");
	        for(String word: words )
	        {
	            String outputKey = new String(word.toUpperCase().trim());
	            String outputValue = new String();
	            con.write(outputKey, outputValue);
	        }
	    }
	   
	   
	   public void mapContext(int page, MapInterface mapper, Counter counter) {
		   
		   // open page file 
		   BufferedReader reader;
		   String line = reader.readLine();
		   
			while (line != null) {
				
				// read line by line execute mapper.map(key, value, counter)
				mapper.map(key, value);
				
				// read next line
				line = reader.readLine();
			}
			reader.close();
		  
		   // when its done complete file call counter increment(page n)
		   counter.increment(page, 1);
		   
		   counter.hasCompleted = true;
		   
	   }
	
	   
	   
	   
	   
	   
	   
	// -------- MAPPING -------- //

	// NASSER
	// emitMap()
	// for each file in metadata, select a peer to map
	// Calls mapContext()
	
	
	// mapContext()
	// Read the inverted index file
	// Map it
	// increment counter
	
	
	// -------- REDUCING -------- //
	// Basically sorts the inverted index files

	// XINY
	// void reduce()
	// Calls reduceContext(), completed()
	
	// CAITLIN
	// reduceContext()
	// Calls emitReduce()
	
	// emitReduce()
	
	
	// completed()
	
}
