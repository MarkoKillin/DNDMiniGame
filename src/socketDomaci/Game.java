package socketDomaci;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Game extends Thread {
	private Socket client;
	private PrintWriter out;
	private BufferedReader in;
	private int demogorgornHP;
	private int clientHP;
	
	public Game(Socket client) {
		this.client = client;
		try {
			this.out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);
			this.in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.demogorgornHP = 250;
		this.clientHP = 100;
	}

	@Override
	public void run() {
		//Implement server logic
		String message = "";
		int diceNumber = 0;
		do {
			try {
				boolean attack = false;
				boolean blockDemogorgon = false;
				message = in.readLine();
				if(message == null)
					break;
				
				//korisnikov potez
				System.out.println();
				if(message.equals("n")) {
					attack = true;
					System.out.println("Korisnik je odabrao napad. Bacanje kockice...");
					diceNumber = rollDice();
					System.out.println("Broj je " + diceNumber);
					if(diceNumber == 20)
						demogorgornHP -= 15;
					else if(diceNumber >= 11 && diceNumber <= 19) 
						demogorgornHP -= 10;
				}
				else {
					System.out.println("Korisnik je odabrao bezanje...");
					clientHP -= 1;
				}
				
				if(demogorgornHP < 0)
					demogorgornHP = 0;
				System.out.println("Server baca kockice...");
				
				//serverov potez
				diceNumber = rollDice();
				System.out.println("Broj je " + diceNumber);
				if(diceNumber >= 16 && diceNumber <= 20)
					blockDemogorgon = true;
				else if(diceNumber >= 11 && diceNumber <= 15)
					clientHP += 5;
				
				//demogorgonov napad
				if(blockDemogorgon) {
					out.println("Nakon ove runde, HP korisnika je " + clientHP + ", HP demogorgona je " + demogorgornHP);
					if(demogorgornHP <= 0) {
						out.println("pobeda");
						break;
					}
					out.println("nastavi");
					continue;
				}
				else if(attack) 
					clientHP -= 10;				
				else 
					clientHP -= 2;
				
				if(clientHP < 0)
					clientHP = 0;
				out.println("Nakon ove runde, HP korisnika je " + clientHP + ", HP demogorgona je " + demogorgornHP);
				
				if(clientHP > 0 && demogorgornHP > 0)
					out.println("nastavi");
				else if(clientHP <= 0) 
					out.println("gubitak");
				else if(demogorgornHP <= 0) 
					out.println("pobeda");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} while (clientHP > 0 || demogorgornHP > 0);
		if(clientHP <= 0) 
			out.println("Izgubili ste, kraj partije...");
		else if(demogorgornHP <= 0) 
			out.println("Pobedili ste, kraj partije...");
		try {
			out.close();
			in.close();
			client.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private int rollDice() {
		return (int) (Math.random()*20 + 1);
	}
	
}