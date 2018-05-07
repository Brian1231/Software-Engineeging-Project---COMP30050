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

/*
 * This class runs on its own thread and is used to hold open a connection between the server and an android application.
 * It may send and receive messages from the android app.
 * 
 * */

@SuppressWarnings("ConstantConditions")
class PlayerConnection extends Thread{

	private ServerSocket server;
	private Socket socket;
	private final int port;
	private final int playerID;
	private final String androidId;
	private BufferedWriter out;
	private boolean keepAlive;

	public PlayerConnection(int id, int port, String androidId){
		this.port = port;
		this.playerID = id;
		this.keepAlive = true;
		this.androidId = androidId;
		try {
			server = new ServerSocket(this.port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getPlayerId(){
		return this.playerID;
	}

	public void kill(){
		this.keepAlive = false;
		try {
			socket.close();
			server.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
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

		//String client_ip = socket.getRemoteSocketAddress().toString().replace("/","").split(":")[0];
		Main.gameState.addPlayer(this.androidId);
		Main.gameState.updateActionInfo("Player "+ this.playerID+" has joined the game as "+Main.gameState.getPlayerName(this.playerID)+".");
		Main.clientUpdater.updateDesktopPlayers();
	}
	public void run(){

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(Main.isActive && this.keepAlive){
			synchronized(Main.gameState){

				try {
					if(reader.ready()){
						//System.out.println("Listening for player "+this.playerID+" on port " + this.port+" ...");
						String line = reader.readLine();
						if (!line.isEmpty()) {
							System.out.println("Message from Player "+this.playerID+": " + line);
						}

						//Parse JSONObject from input
						JSONObject obj = new JSONObject(line);
						int id = obj.getInt("id");
						if(id == this.playerID){
							String action = obj.getString("action");
							String[] args =  obj.getString("args").split(",");
							//Update Main.gamestate based on phone input and this.playerID
							if(!action.equals("connect")){
								String actionInfo = Main.gameState.playerAction(id, action, args);

								System.out.println(actionInfo);

								//Update Desktop
								Main.gameState.updateActionInfo(actionInfo);
								if(action.equals("buy")||action.equals("sell")||action.equals("trap") ||
										action.equals("build")||action.equals("demolish") || 
										action.equals("mortgage") || action.equals("redeem"))
									Main.clientUpdater.updateDesktopAll();
								else
									Main.clientUpdater.updateDesktopPlayers();
								//Update all players
								Main.portAllocator.updatePlayers();
							}
						}
						System.out.println("Listening for player "+this.playerID+" on port " + this.port+" ...");
					}
				}
				catch (IOException | JSONException e1) {
					e1.printStackTrace();
				}
			}
		}
		try {
			socket.close();
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void vibrate(){
		JSONObject output = new JSONObject();
		
		try {
			output.put("id", this.playerID);
			output.put("action", "vibrate");
			out.write(output.toString()+ "\n");
			out.flush();
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}	
	}
	
	public void auctionAlert(){
		JSONObject output = new JSONObject();
		
		try {
			output.put("id", this.playerID);
			output.put("action", "auction");
			out.write(output.toString()+ "\n");
			out.flush();
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}	
	}
	
	public void updatePlayer(){
		JSONObject output;
		try {
			output = Main.gameState.getPlayerInfo(this.playerID);
			output.put("action_info", Main.gameState.getActionInfo());
			out.write(output.toString()+ "\n");
			out.flush();
		} catch (JSONException | IOException e1) {
			e1.printStackTrace();
		}
	}
}

