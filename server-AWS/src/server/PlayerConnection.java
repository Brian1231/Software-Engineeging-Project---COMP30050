package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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

	public PlayerConnection(int id, int portNum){
		this.port = portNum;
		this.playerID = id;
		try {
			server = new ServerSocket(this.port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run(){
		System.out.println("Opening connection for Player" +this.playerID+" on Port " + this.port + " ...");
		try {
			socket = server.accept();
		} catch (IOException e2) {
			e2.printStackTrace();
		}

		
		System.out.println("Connected Player to Port " +  + this.port);
		
		String client_ip = socket.getRemoteSocketAddress().toString().replace("/","").split(":")[0];
		Main.gameState.addPlayer(client_ip);
		Main.clientUpdater.updateActionInfo("New Player Connected!");
		Main.clientUpdater.updateDesktop();

		while(Main.gameState.isActive()){
			synchronized(this){


				BufferedReader reader = null;

				try {

					reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
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
							//Update Main.gamestate based on phone input and this.playerID
							String actionInfo = Main.gameState.playerAction(id, action);

							//Update Desktop
							Main.clientUpdater.updateActionInfo(actionInfo);
							Main.clientUpdater.updateDesktop();
							
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
		JSONObject output = null;
		PrintWriter out = null;
		try {
			output = Main.gameState.getPlayerInfo(this.playerID);
			//output = Main.gameState.getInfo();
			out = new PrintWriter(socket.getOutputStream(), true);
		} catch (JSONException | IOException e1) {
			e1.printStackTrace();
		}
		//Output to desktop
		out.println(output.toString());
	}
}

