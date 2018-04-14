package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import main.Main;

public class PlayerConnection extends Thread{


	private ServerSocket server;
	private Socket socket;
	private int port;
	private int playerID;
	private BufferedWriter out;

	public PlayerConnection(int id, int portNum){
		this.port = portNum;
		this.playerID = id;
		try {
			server = new ServerSocket(this.port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setup(){
		System.out.println("Opening connection for Player" +this.playerID+" on Port " + this.port + " ...");
		try {
			socket = server.accept();
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

		} catch (IOException e2) {
			e2.printStackTrace();
		}

		
		System.out.println("Connected Player to Port " +  + this.port);
		
		String client_ip = socket.getRemoteSocketAddress().toString().replace("/","").split(":")[0];
		Main.gameState.addPlayer(client_ip);
		Main.clientUpdater.updateActionInfo("Player "+ this.playerID+" has joined the game as "+Main.gameState.getPlayerName(this.playerID)+".");
		Main.clientUpdater.updateDesktopPlayers();
	}
	public void run(){
		
		BufferedReader reader = null;
		try {

			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		while(Main.gameState.isActive()){
			synchronized(this){
				
				try {

					if(reader.ready()){
						String line = reader.readLine();
						if (!line.isEmpty()) {
							System.out.println("Message from Player "+this.playerID+": " + line);
						}

						//Parse JSONObject from input
						JSONObject obj = new JSONObject(line);
						int id = (int) obj.get("id");
						if(id == this.playerID){
							String action = (String) obj.get("action");
							String[] args = ((String) obj.get("args")).split(",");
							//Update Main.gamestate based on phone input and this.playerID
							String actionInfo = Main.gameState.playerAction(id, action, args);

							System.out.println(actionInfo);
							
							//Update Desktop
							Main.clientUpdater.updateActionInfo(actionInfo);
							Main.clientUpdater.updateDesktopPlayers();
							
							//Update Player
							this.updatePlayer();
						}

						System.out.println("Listening for player "+this.playerID+" on port " + this.port+" ...");
					}
				}
				catch (IOException | JSONException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	public void updatePlayer(){
		JSONObject output;
		try {
			output = Main.gameState.getPlayerInfo(this.playerID);
			out.write(output.toString()+ "\n");
			out.flush();
			//output = Main.gameState.getInfo();
		} catch (JSONException | IOException e1) {
			e1.printStackTrace();
		}
		//Output to desktop
		
	}
}

