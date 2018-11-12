package api.p2p;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Random;

import data.constants.Net;
import data.index.Chunk;
import net.tomp2p.dht.FutureGet;
import net.tomp2p.dht.PeerBuilderDHT;
import net.tomp2p.dht.PeerDHT;
import net.tomp2p.futures.FutureBootstrap;
import net.tomp2p.p2p.PeerBuilder;
import net.tomp2p.peers.Number160;
import net.tomp2p.storage.Data;

public class PeerService {
	
	private final PeerDHT peer;
//	private final int port = 4001; //change
	private final int port = Net.PORT; //change?
	
	public PeerService() throws IOException {
		
		Random rnd = new Random();
        peer = new PeerBuilderDHT(new PeerBuilder(new Number160(rnd)).ports(port).start()).start();
        
        FutureBootstrap fb = this.peer.peer().bootstrap().inetAddress(InetAddress.getByName(Net.HOST)).ports(port).start();
        fb.awaitUninterruptibly();
        if(fb.isSuccess()) {
            peer.peer().discover().peerAddress(fb.bootstrapTo().iterator().next()).start().awaitUninterruptibly();
        }
	}
	
	/**
	 * 
	 * @param guid
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public Chunk get(String guid) throws IOException, ClassNotFoundException {
		FutureGet futureGet = peer.get(new Number160(guid)).start();
		futureGet.awaitUninterruptibly();
		if (futureGet.isSuccess()) {
			return (Chunk) futureGet.dataMap().values().iterator().next().object();
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
	 * @param deletePeer
	 */
	public void delete(Chunk chunk) throws IOException{
		peer.remove(new Number160(chunk.getGuid()));
	}
}

