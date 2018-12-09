package api.p2p;

import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Set;

import net.tomp2p.peers.Number160;

public class Counter implements CounterInterface {
	Integer counter = 0;
	Set<Number160> set;
	
	public Counter() {
		set = new HashSet<Number160>();
	}

	public void add(Number160 guid) {
		set.add(guid);
	}

	public Boolean hasCompleted() {
		return (counter == 0 && set.isEmpty());
	}

	public void decrement() throws RemoteException {
		counter--;
	}

	public void increment(Number160 guid, Integer n) throws RemoteException {
		set.remove(guid);
		counter += n;

	}
}
