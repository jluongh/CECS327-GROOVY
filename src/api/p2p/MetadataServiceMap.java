package api.p2p;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import data.constants.Files;
import data.models.Song;

import java.util.Set;
import java.util.TreeMap;

import net.tomp2p.peers.Number160;

public class MetadataServiceMap {

	List<Peer> peers;

	List<String> results;
	
	public MetadataServiceMap(List<Peer> peers) {
		this.peers = peers;
	}

	public List<String> search(String query, int type) throws IOException
	{
		List<String> values = new ArrayList<String>();
		//peer 0-2 has songs.txt peer 3-5 has artists.txt peer 6-8 has albums.txt
		switch(type)
		{
			case Files.SONGTYPE:
				for(int i = 0; i < 3; i++)
				{
					values.addAll(peers.get(i).reduce(query));
				}
			case Files.ARTISTTYPE:
				for(int j = 3; j < 6; j++)
				{
					values.addAll(peers.get(j).reduce(query));
				}
			case Files.ALBUMTYPE:
				for(int k = 6; k < 9; k++)
				{
					values.addAll(peers.get(k).reduce(query));
				}
		}
		
		return values;
	}

	public void mapContext(File F, Peer peer, Counter counter) {

		// open page file
		BufferedReader reader;

		// file?
		reader = new BufferedReader(new FileReader(F));
		String line = reader.readLine();

		while (line != null) {

			// read line by line execute mapper.map(key, value, counter)
			peer.map(line);

			// read next line
			line = reader.readLine();
		}
		reader.close();

		// when its done complete file call counter increment(page n)
		// what's the key here? 
		counter.increment(1);
		counter.hasCompleted();

	}


	// CAITLIN
	public void reduceContext(String search, Peer peer, Counter counter) {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					List<String> results = peer.reduce(search);
					counter.increment(peer.getID(), results.size());

				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		});
		thread.run();

	}


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

//	public Boolean completed(String search, Mapper reducer, Counter counter) {
//		boolean complete = false;
//		for(String s : reducer.reduce(search)) {
//			
//		}
//		if() {
//			complete = true;
//		}
//		return complete;
		
		
//		if (source != peers.get(0).getID()) {
//			counter.add(peers.get(0).getID());
//		}
//		successor.completed(source, counter);
//		// create new file stores the tree in file output in page guid
//		counter.increment(peers.get(0).getID(), 0);
		
//	}

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
