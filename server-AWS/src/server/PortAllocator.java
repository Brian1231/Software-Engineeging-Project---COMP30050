package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import main.Main;

public class PortAllocator extends Thread{

	private ServerSocket server;
	private Socket socket;
	private int port;
	private int playerPortCount;
	private int playerCount=1;
	private ArrayList<PlayerConnection> playerConnections;
	private HashMap<Integer, String> idIpMap = new HashMap<Integer, String> ();

	public PortAllocator(int portNum){
		this.port = portNum;
		playerPortCount = this.port +1;
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

					if(!idIpMap.values().contains(client_ip)){
						idIpMap.put(this.playerCount, client_ip);//Maybe not needed, Just array of ip's could do
						
						playerConnections.add(new PlayerConnection(this.playerCount, this.playerPortCount));
						
						PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
						JSONObject response = new JSONObject();
						response.put("id", playerCount);
						response.put("port", this.playerPortCount);
						out.println(response.toString());
						
						this.playerCount++;
						this.playerPortCount++;
					}

					for(PlayerConnection pc : playerConnections){
						if(!pc.isAlive()){
							pc.start();
						}
					}

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

	public void endGame() {
		for(PlayerConnection pc : this.playerConnections){
			pc.updatePlayer();
			//Send endgame message JSON
		}
		Main.gameState.isActive = false;
	}
}

