package rmiDomaci;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {

	public static void main(String[] args) {
		try {
			Registry registry = LocateRegistry.createRegistry(1025);
			Server server = new ServerImpl();
			registry.rebind("DNDserver", server);
			System.err.println("Server je pokrenut...");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
}
