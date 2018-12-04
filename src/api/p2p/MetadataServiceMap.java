package api.p2p;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import net.tomp2p.peers.Number160;


public class MetadataServiceMap {

	List<Peer> peers;
	
	TreeMap <Number160, List <String>> maps;
	TreeMap <Number160, String> reduce;
	
	public MetadataServiceMap(List<Peer> peers) {
		this.peers = peers;
	}
	
	Set<Number160> set = new HashSet<>();
	
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
	
	public void emitReduce(Number160 key, String value,Counter counter)
	{
		if(isKeyBetween(key,predecessor.getID(),successor.getID())) //need to implement get predecessor and successor
		{
			reduce.put(key, value);
			counter.decrement();
		}
		else
		{
			Peer peer = this.locateSuccessor(key);
			peer.emitReduce(key,value);
		}
		
	}
	
	
	public Boolean completed(Number160 source, Counter counter)
	{
		if(source!=peers.get(0).getID())
		{
			counter.add(peers.get(0).getID());
		}
		successor.completed(source,counter);
		//create new file stores the tree in file output in page guid 
		counter.increment(peers.get(0).getID(), 0);
	}
	
	
	public void runMapReduce(File file)
	{
		Counter mapCounter = new Counter();
		Counter reduceCounter = new Counter();
		Counter completedCounter = new Counter();
		MapInterface mapper;
		ReduceInterface reducer;
		
		while (!mapCounter.hasCompleted())
		{
			//map Phases
			//for each page in metafile.file
				mapCounter.add(page);
				//let peer = storing pages
				peer.mapContext(page,mapper,mapCounter);
			//wait till
		}
		while (!reduceCounter.hasCompleted())
		{
			reduceContext(guid,reducer,reduceCounter);
		}
		
		while(completedCounter.hasCompleted())
		{
			completed(guid,completedCounter);
		}
	}
    
    //SAVE
    	//reduce into inverted index
    
    
}
