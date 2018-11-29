package api.p2p;

import java.util.List;


public class MetadataServiceMap {

	List<Peer> peers;

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
	// reduceContext()
	// Calls emitReduce()
	
	// emitReduce()
	
	
	// completed()
	
}
