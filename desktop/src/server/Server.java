package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

public class Server {

	public void run(int PORT) throws IOException{
		System.out.println("Your IP is: " + Inet4Address.getLocalHost().getHostAddress().toString());
		final ServerSocket server = new ServerSocket(PORT);
		System.out.println("Listening for connection on port " + server.getLocalPort() + " ...");
		boolean serverActive = true;
		while (serverActive){
			Socket socket = server.accept();
			String response;

			//Read input from client
			InputStreamReader isr = new InputStreamReader(socket.getInputStream());
			BufferedReader reader = new BufferedReader(isr);
			String line = reader.readLine();
			if (!line.isEmpty()) {
				System.out.println("Message from client: " + line);
			}

			switch (line.toLowerCase().split(" ")[0]){
			case "exit":
				server.close();
				serverActive = false;
				response = "Bye!";
				break;
			case "roll":
				response = "Player rolled!";
				break;
			case "buy":
				response = "Player bought property!";
				break;
			default:
				response = JOptionPane.showInputDialog("Message from server", "hello from server");
			}

			//Send response
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			out.println(response);
		}
		System.out.println("Done!");
	}
}
