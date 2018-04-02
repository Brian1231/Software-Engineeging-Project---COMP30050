package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import main.Main;

public class PortAllocator extends Thread{

	private ServerSocket server;
	private Socket socket;
	private int port;
	private int playerPortCount;
	private ArrayList<PlayerConnection> playerConnections;

	public PortAllocator(int portNum){
		this.port = portNum;
		playerPortCount = this.port;
		try {
			server = new ServerSocket(this.port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		playerConnections = new ArrayList<PlayerConnection>();
	}

	public void run(){
		while(Main.gameState.isActive()){
			System.out.println("Listening for incoming connections on Port "+this.port+" ...");
			try {
				socket = server.accept();
			} catch (IOException e2) {
				e2.printStackTrace();
			}

			System.out.println("New Connection on Port "+this.port);

			synchronized(this){
				try {

					//Read input from client
					InputStreamReader isr = new InputStreamReader(socket.getInputStream());
					BufferedReader reader = new BufferedReader(isr);
					String line = reader.readLine();
					if (!line.isEmpty()) {
						System.out.println("Message from Phone: " + line);
					}

					String client_ip = socket.getRemoteSocketAddress().toString().replace("/","").split(":")[0];
					int playerID = Main.gameState.addPlayer(client_ip);
					
					if(playerID != -1){
						this.playerPortCount++;
						playerConnections.add(new PlayerConnection(playerID, this.playerPortCount));
					}

					for(PlayerConnection pc : playerConnections){
						if(!pc.isAlive()){
							pc.start();
						}
					}

					PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
					JSONObject response = new JSONObject();
					response.put("id", playerID);
					response.put("port", this.playerPortCount);
					out.println(response.toString());


				}catch (IOException | JSONException e) {
					e.printStackTrace();
				}
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

