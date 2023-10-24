package rmiDomaci;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameInterface extends Remote {
	public String attack() throws RemoteException;
	public String escape() throws RemoteException;
	public boolean isVictory() throws RemoteException;
	public boolean isDefeat() throws RemoteException;
}
