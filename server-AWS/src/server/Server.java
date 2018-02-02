package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {

	public ServerSocket server;
	public Socket socket;

	public Server(int PORT) throws IOException{
		server = new ServerSocket(8080);
	}

	public void close() throws IOException{
		server.close();
	}
	
	public String listen() throws IOException{
		System.out.println("Listening for connection on port " + server.getLocalPort() + " ...");
		socket = server.accept();
		String response = "null";

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
			Main.serverActive = false;
			response = "Done";
			break;
		case "roll":
			response = "Player rolled!";
			break;
		case "buy":
			response = "Player bought property!";
			break;
		default:
			response = "Hello from AWS!";//JOptionPane.showInputDialog("Message from server", "hello from server");
		}

		return response;
		
	}
	
	public void send(String message) throws IOException{
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		out.println(message);
	}
}
