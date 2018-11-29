package api.p2p;

import java.rmi.RemoteException;
import java.util.Set;

public class MyCounter implements CounterInterface{
	Long counter = 0;
	Set<Integer> set;
	public void add(Integer Key){
		set.add(Key);
	}
	public Boolean hasCompleted(){
		return (counter == 0 && set.isEmpty());
	}

	public void decrement() throws RemoteException{
		counter--;
	}

	public void increment(Integer key, Integer n) throws RemoteException {
		set.remove(key);
		counter+= n;
		
	}
}