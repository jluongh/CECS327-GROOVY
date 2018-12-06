package api.p2p;

import java.rmi.Remote;
import java.rmi.RemoteException;

import net.tomp2p.peers.Number160;

public interface CounterInterface extends Remote{

	public void add(String Key) throws RemoteException;
	public void increment(String key, Integer n) throws RemoteException;
	public void decrement() throws RemoteException;
}
