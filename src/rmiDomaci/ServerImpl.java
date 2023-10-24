package rmiDomaci;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerImpl extends UnicastRemoteObject implements Server {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ServerImpl() throws RemoteException {
		super();
	}

	@Override
	public GameInterface newGame() throws RemoteException {
		GameInterface game = new GameImpl(100, 250);
		return game;
	}

}
