package rmiDomaci;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

	public static void main(String[] args) {
		try {
			Registry registry = LocateRegistry.getRegistry("localhost", 1025);
			Server server = (Server) registry.lookup("DNDserver");
			GameInterface dnd = server.newGame();
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			
			while(!dnd.isDefeat() && !dnd.isVictory()) {
				System.out.println("Napad ili bezanje? n/b");
				String input = in.readLine();
				if(input.equals("n"))
					System.out.println(dnd.attack());
				else
					System.out.println(dnd.escape());
				if(dnd.isDefeat()) 
					System.out.println("Izgubili ste.");
				else if(dnd.isVictory())
					System.out.println("Pobedili ste.");
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
