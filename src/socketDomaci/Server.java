package socketDomaci;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	public static void main(String[] args) {
		ServerSocket ss = null;
		try {
			ss = new ServerSocket(1289);
			System.err.println("Server up...");
			while (!Thread.interrupted()) {
				Socket client = ss.accept();
				System.out.println("Nova partija...");
				Game game = new Game(client);
				game.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				ss.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
