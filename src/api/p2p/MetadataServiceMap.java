package api.p2p;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import net.tomp2p.peers.Number160;


public class MetadataServiceMap {

	List<Peer> peers;
	
	TreeMap <String, List <String>> maps;
	TreeMap <String, String> reduce;
	
	public MetadataServiceMap(List<Peer> peers) {
		this.peers = peers;
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

	// XINYI
	// void reduce()
	// Calls reduceContext(), completed()
	
	// CAITLIN
	public void reduceContext(Number160 source, ReduceInterface reducer, Counter counter){
		if (source != peers.get(0).getID()) {
			counter.add(peers.get(0).getID());
		}
		
		  if (source != peers.get(0).getID()) {
	            System.out.println("in reduceContext");
	            successor.reduceContext(source, reducer, counter); //need function to find successor
	        }

	        Thread thread = new Thread(new Runnable() {
	            @Override
	            public void run() {
	                try {
	                    //counter.setWorkingPeer(getID());
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	                for (Entry<String, List<String>> entry : maps.entrySet()) {
	                    String key = entry.getKey();
	                    List<String> values = entry.getValue();
	                    String strings[] = new String[values.size()];
	                    values.toArray(strings);
	                    try {
	                        reducer.reduce(key, values);

	                    } catch (IOException e) {
	                        e.printStackTrace();
	                    }
	                }

	                System.out.println("Finish mapReduce.");
	      
	            }

	        });
	        thread.run();


	}
	

	// Calls emitReduce()
	
	// emitReduce()
	
	
	// completed()
	
    
    //SAVE
    	//reduce into inverted index
    
    
}
