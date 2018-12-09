package api.p2p;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

import data.constants.Net;
import data.index.Chunk;
import net.tomp2p.dht.FutureGet;
import net.tomp2p.dht.PeerBuilderDHT;
import net.tomp2p.dht.PeerDHT;
import net.tomp2p.futures.FutureBootstrap;
import net.tomp2p.p2p.PeerBuilder;
import net.tomp2p.peers.Number160;
import net.tomp2p.storage.Data;

public class Peer implements MapInterface, ReduceInterface {

	private final PeerDHT peer;
	private final int port = Net.P2P_PORT; // change?
	Number160 guid;
	public TreeMap<String, List<String>> map;

	public Peer() throws IOException {

		map = new TreeMap<String, List<String>>();

		Random rnd = new Random();
		guid = new Number160(rnd);
		peer = new PeerBuilderDHT(new PeerBuilder(guid).ports(port).start()).start();

		FutureBootstrap fb = this.peer.peer().bootstrap().inetAddress(InetAddress.getByName(Net.HOST)).ports(port)
				.start();
		fb.awaitUninterruptibly();
		if (fb.isSuccess()) {
			peer.peer().discover().peerAddress(fb.bootstrapTo().iterator().next()).start().awaitUninterruptibly();
		}

		System.out.println("Peer created with guid:" + guid);
	}

	/**
	 * 
	 * @param guid
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public String get(String guid) throws IOException, ClassNotFoundException {
		FutureGet futureGet = peer.get(new Number160(guid)).start();
		futureGet.awaitUninterruptibly();
		if (futureGet.isSuccess()) {
			return futureGet.dataMap().values().iterator().next().object().toString();
		}
		return null;
	}

	/**
	 * 
	 * @param guid
	 * @param chunk
	 * @throws IOException
	 */
	public void put(Number160 guid, String content) throws IOException {
		peer.put(guid).data(new Data(content)).start().awaitUninterruptibly();
	}

	/**
	 * Delete chunk from dht //not tested yet
	 * 
	 * @param deletePeer
	 */
	public void delete(Chunk chunk) throws IOException {
		peer.remove(new Number160(chunk.getGuid()));
	}

	/**
	 * @return the guid
	 */
	public Number160 getID() {
		return guid;
	}

	public void map(String line) throws IOException {

		String[] values = line.split(";");

		String outputKey = values[0].toLowerCase();
		String outputValue = line;

		List<String> outputValues = new ArrayList<String>();

		if (map.containsKey(outputKey)) {
			outputValues = map.get(outputKey);

		}
		if (!outputValues.contains(outputValue)) {
			outputValues.add(outputValue);
			map.put(outputKey, outputValues);

		}

	}

	public List<String> reduce(String search) throws IOException {
		List<String> values = new ArrayList<String>();
		for (String s : map.keySet()) {
			System.out.println("SEARCHING: " + s);

			if (s.startsWith((search.toLowerCase()))) {
				values.addAll(map.get(s));
			}
		}
		return values;
	}

}
