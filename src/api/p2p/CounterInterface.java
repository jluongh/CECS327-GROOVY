package api.p2p;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CounterInterface extends Remote{

	public void add(Integer Key) throws RemoteException;
	public void increment(Integer key, Integer n) throws RemoteException;
	public void decrement() throws RemoteException;
}
