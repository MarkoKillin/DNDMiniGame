package rmiDomaci;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote{
	public GameInterface newGame() throws RemoteException;
}
