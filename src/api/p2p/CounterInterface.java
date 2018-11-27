package api.p2p;

import java.rmi.RemoteException;

public interface CounterInterface {

	public void add(Integer Key) throws RemoteException;
	public void increment(Integer key, Integer n) throws RemoteException;
	public void decrement() throws RemoteException;
}
