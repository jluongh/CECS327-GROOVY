package api.p2p;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import net.tomp2p.peers.Number160;

public class MetadataServiceMap {

	List<Peer> peers;

	TreeMap<Number160, List<String>> maps;
	TreeMap<Number160, String> reduce;

	List<Mapper> mappers;
	List<String> results;
	
	public MetadataServiceMap(List<Peer> peers) {
		this.peers = peers;
	}

//	public void map(String key, String value, C con) throws IOException, InterruptedException {
//		String line = value.toString();
//		String[] words = line.split(",");
//		for (String word : words) {
//			String outputKey = new String(word.toUpperCase().trim());
//			String outputValue = new String();
//			con.write(outputKey, outputValue);
//		}
//	}

	public void mapContext(File F, Mapper mapper, Counter counter) {

		// open page file
		BufferedReader reader;

		// file?
		reader = new BufferedReader(new FileReader(F));
		String line = reader.readLine();

		while (line != null) {

			// read line by line execute mapper.map(key, value, counter)
			mapper.map(line);

			// read next line
			line = reader.readLine();
		}
		reader.close();

		// when its done complete file call counter increment(page n)
		// what's the key here? 
		counter.increment(key, 1);

		counter.hasCompleted();

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

	// XINY
	// void reduce()
	// Calls reduceContext(), completed()

	// CAITLIN
	public void reduceContext(Number160 source, ReduceInterface reducer, Counter counter) {
//		if (source != peers.get(0).getID()) {
//			counter.add(peers.get(0).getID());
//			System.out.println("in reduceContext");
//			peers.get(1).reduceContext(source, reducer, counter); // needs to be implemented for peer class
//		}
		
		if (source != maps.firstKey()) {
			counter.add(maps.firstKey());
			System.out.println("in reduceContext");
			maps.higherKey(source).reduceContext(source, reducer, counter); // needs to be implemented for peer successor
		}
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					// counter.setWorkingPeer(getID());
				} catch (IOException e) {
					e.printStackTrace();
				}
				for (Number160 s : maps.keySet()) {
					int n = 0;
					reducer.reduce(s, maps.get(n), counter);
					n++;
				}
			}

		});
		thread.run();

	}

	// Calls emitReduce()

	public void emitReduce(String key, String value, Counter counter) throws IOException {
		//go to every mapper, do the reduce. 
		results = new ArrayList<String>();
		for(int i = 0; i<mappers.size();i++)
		{
			results.addAll(mappers.get(i).reduce(key));
		}
		
//		if (isKeyBetween(key, predecessor.getID(), successor.getID())) 
//		{
//			mappers.emit(key, value);
//			counter.decrement();
//		} else {
//			Peer peer = this.locateSuccessor(key);
//			peer.emitReduce(key, value);
//		}


	}

	public Boolean completed(Number160 source, Counter counter) {
		if (source != peers.get(0).getID()) {
			counter.add(peers.get(0).getID());
		}
		successor.completed(source, counter);
		// create new file stores the tree in file output in page guid
		counter.increment(peers.get(0).getID(), 0);
	}

	public void runMapReduce(File file) {
		Counter mapCounter = new Counter();
		Counter reduceCounter = new Counter();
		Counter completedCounter = new Counter();
		MapInterface mapper;
		ReduceInterface reducer;

		while (!mapCounter.hasCompleted()) {
			// map Phases
			// for each page in metafile.file
			mapCounter.add(page);
			// let peer = storing pages
			peer.mapContext(page, mapper, mapCounter);
			// wait till
		}
		while (!reduceCounter.hasCompleted()) {
			reduceContext(guid, reducer, reduceCounter);
		}

		while (completedCounter.hasCompleted()) {
			completed(guid, completedCounter);
		}
	}

	// SAVE
	// reduce into inverted index

}
