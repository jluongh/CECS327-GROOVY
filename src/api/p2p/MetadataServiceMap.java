package api.p2p;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;
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

	List<Mapper> mappers = new ArrayList<Mapper>();
	
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

	public void mapContext(File file, Mapper mapper, Counter counter) {

		// open page file
		BufferedReader reader;

		// file?
		// reader = new BufferedReader(new FileReader());
		String line = reader.readLine();

		while (line != null) {

			// read line by line execute mapper.map(key, value, counter)
			mapper.map(line);

			// read next line
			line = reader.readLine();
		}
		reader.close();

		// when its done complete file call counter increment(page n)
		counter.increment(page, 1);

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
		if (source != peers.get(0).getID()) {
			counter.add(peers.get(0).getID());
		}

		if (source != peers.get(0).getID()) {
			System.out.println("in reduceContext");
			successor.reduceContext(source, reducer, counter); // need function to find successor
		}

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					// counter.setWorkingPeer(getID());
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

	public void emitReduce(Number160 key, String value, Counter counter) {
		if (isKeyBetween(key, predecessor.getID(), successor.getID())) // need to implement get predecessor and
																		// successor
		{
			reduce.put(key, value);
			counter.decrement();
		} else {
			Peer peer = this.locateSuccessor(key);
			peer.emitReduce(key, value);
		}

	}

	public Boolean completed(Number160 source, Counter counter) {
		if (source != peers.get(0).getID()) {
			counter.add(peers.get(0).getID());
		}
		successor.completed(source, counter);
		// create new file stores the tree in file output in page guid
		counter.increment(peers.get(0).getID(), 0);
	}

	public void runMapReduce(List<File> files) {
		Counter mapCounter = new Counter();

		

		int index = 0;
		while (!mapCounter.hasCompleted()) {
			// map Phases
			// for each page in metafile.file
			for (File file : files) {
				mapCounter.add(file.getName());
				// let peer = storing pages
				mapContext(file, mappers.get(index), mapCounter);
				index++;
			}

		}

	}

	public void runReduce(String search) {
		Counter reduceCounter = new Counter();
		Counter completedCounter = new Counter();
		
		int index = 0;
		while (!reduceCounter.hasCompleted()) {
			reduceContext(search, mappers.get(index), reduceCounter);
			index++;
		}

		while (completedCounter.hasCompleted()) {
			completed(guid, completedCounter);
		}
	}
	// SAVE
	// reduce into inverted index

}