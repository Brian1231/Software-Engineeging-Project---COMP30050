package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.json.JSONException;

import game.GameState;
import main.Main;

public class Server {

	private ServerSocket server;
	private Socket socket;

	public Server(int PORT) throws IOException{
		server = new ServerSocket(PORT);
	}

	public void close() throws IOException{
		server.close();
	}
	
	public String listen(GameState gamestate) throws IOException, JSONException{
		System.out.println(InetAddress. getLocalHost().getHostAddress());
		System.out.println("Listening for connection on port " + server.getLocalPort() + " ...");
		socket = server.accept();
		String response = "null";

		//Add player for new ip
		String client_ip = socket.getRemoteSocketAddress().toString().replace("/","").split(":")[0];
		gamestate.addPlayer(client_ip);
		
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
			response = gamestate.getInfo().toString();//JOptionPane.showInputDialog("Message from server", "hello from server");
		}

		return response;
		
	}
	
	public void send(String message) throws IOException{
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		out.println(message);
	}
}
