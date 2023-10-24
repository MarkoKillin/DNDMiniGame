package rmiDomaci;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class GameImpl extends UnicastRemoteObject implements GameInterface{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int demogorgornHP;
	private int clientHP;
	
	protected GameImpl(int clientHP, int demogorgonHP) throws RemoteException {
		super();
		this.clientHP = clientHP;
		this.demogorgornHP = demogorgonHP;
	}

	@Override
	public String attack() throws RemoteException {
		String message = "Korisnik je bacio ";
		int diceNumber = rollDice();
		message += (diceNumber + ", server je bacio ");
		if(diceNumber == 20)
			demogorgornHP -= 15;
		else if(diceNumber >= 11 && diceNumber <= 19) 
			demogorgornHP -= 10;
		if(demogorgornHP < 0)
			demogorgornHP = 0;
		diceNumber = rollDice();
		message += (diceNumber + ". ");
		boolean blockDemogorgon = false;
		if(diceNumber >= 16 && diceNumber <= 20)
			blockDemogorgon = true;
		else if(diceNumber >= 11 && diceNumber <= 15)
			clientHP += 5;
		if(blockDemogorgon)
			return message + "Nakon ove runde, HP korisnika je " + clientHP + ", HP demogorgona je " + demogorgornHP;
		clientHP -= 10;
		if(clientHP < 0)
			clientHP = 0;
		return message + "Nakon ove runde, HP korisnika je " + clientHP + ", HP demogorgona je " + demogorgornHP;
	}

	@Override
	public String escape() throws RemoteException {
		String message = "Server je bacio ";
		clientHP -=1;
		int diceNumber = rollDice();
		message += diceNumber;
		boolean blockDemogorgon = false;
		if(diceNumber >= 16 && diceNumber <= 20)
			blockDemogorgon = true;
		else if(diceNumber >= 11 && diceNumber <= 15)
			clientHP += 5;
		if(blockDemogorgon)
			return message + ". Nakon ove runde, HP korisnika je " + clientHP + ", HP demogorgona je " + demogorgornHP;
		clientHP -= 2;
		if(clientHP < 0)
			clientHP = 0;
		return message + ". Nakon ove runde, HP korisnika je " + clientHP + ", HP demogorgona je " + demogorgornHP;
	}

	@Override
	public boolean isVictory() throws RemoteException {
		if(demogorgornHP <= 0)
			return true;
		return false;
	}

	@Override
	public boolean isDefeat() throws RemoteException {
		if(clientHP <= 0)
			return true;
		return false;
	}
	
	private int rollDice() {
		return (int) (Math.random()*20 + 1);
	}

}
