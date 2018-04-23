package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
	private HashMap<String, Integer> ipIdMap = new HashMap<String, Integer> ();

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
		while(Main.isActive){
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
					BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					boolean hasRead = false;
					while(!hasRead){
						if(reader.ready()){
							hasRead = true;
							String line = reader.readLine();
							if (!line.isEmpty()) {
								System.out.println("Message from Phone: " + line);
							}
							String client_ip = socket.getRemoteSocketAddress().toString().replace("/","").split(":")[0];
							
							//If we havn't seen this player;s ip before, create a new connection
							if(!ipIdMap.keySet().contains(client_ip) && ipIdMap.size()<4){
								ipIdMap.put(client_ip, this.playerCount);//Maybe not needed, Just array of ip's could do

								playerConnections.add(new PlayerConnection(this.playerCount, this.playerPortCount));

								BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
								JSONObject response = new JSONObject();
								response.put("id", playerCount);
								response.put("port", this.playerPortCount);
								out.write(response.toString()+ "\n");
								out.flush();

								this.playerCount++;
								this.playerPortCount++;
							}
							//Attempt to reconnect player we've seen before
							else if (ipIdMap.size()<4){
								
								int id = ipIdMap.get(client_ip);
								System.out.println("Reconnecting player " + id + " ...");
								for(int i=0;i<this.playerConnections.size();i++){
									PlayerConnection pc = this.playerConnections.get(i);
									if(pc.getPlayerId() == id){
										System.out.println("Killing player thread: "+ pc.getPlayerId());
										pc.kill();
										this.playerConnections.remove(pc);
										
									}
								}
								int port = 8080+id;
								playerConnections.add(new PlayerConnection(id, port));
								
								BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
								JSONObject response = new JSONObject();
								response.put("id", id);
								response.put("port", port);
								out.write(response.toString()+ "\n");
								out.flush();
							}

							for(PlayerConnection pc : playerConnections){
								if(!pc.isAlive()){
									pc.setup();
									pc.start();
									pc.updatePlayer();
								}
							}
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

			pc.kill();
			//Send endgame message JSON
		}
	}
}

