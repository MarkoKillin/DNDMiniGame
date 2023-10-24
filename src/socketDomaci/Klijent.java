package socketDomaci;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Klijent {
	
	public static void main(String[] args) {
		PrintWriter out = null;
		BufferedReader in = null;
		Socket server = null;
		BufferedReader terminalIn = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			server = new Socket("localhost", 1289);
			System.out.println("Connected to server...");
			out = new PrintWriter(new OutputStreamWriter(server.getOutputStream()), true);
			in = new BufferedReader(new InputStreamReader(server.getInputStream()));
			
			String message = "";
			String response = "";
			boolean isEnd = false;
			do {
				System.out.println("Napad ili bezanje? n/b");
				message = terminalIn.readLine();
				out.println(message);
				response = in.readLine();
				System.out.println(response);
				response = in.readLine();
				if(response.equals("gubitak")) {
					System.out.println("Izgubili ste.");
					isEnd = true;
				} else if(response.equals("pobeda")) {
					System.out.println("Pobedili ste.");
					isEnd = true;
				} else
					System.out.println("Sledeca runda...");
				
			} while (message != null && !isEnd);
			response = in.readLine();
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
				in.close();
				terminalIn.close();
				server.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
