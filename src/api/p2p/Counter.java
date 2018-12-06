package api.p2p;

import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Set;

public class Counter implements CounterInterface {
	Integer counter = 0;
	Set<Integer> set;
	
	public Counter() {
		set = new HashSet<Integer>();
	}

	public void add(Integer Key) {
		set.add(Key);
	}

	public Boolean hasCompleted() {
		return (counter == 0 && set.isEmpty());
	}

	public void decrement() throws RemoteException {
		counter--;
	}

	public void increment(Integer key, Integer n) throws RemoteException {
		set.remove(key);
		counter += n;

	}
}
