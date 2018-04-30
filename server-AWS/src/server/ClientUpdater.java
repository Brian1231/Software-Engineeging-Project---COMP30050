package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import game.Player;
import org.json.JSONException;
import org.json.JSONObject;

import main.Main;

public class ClientUpdater extends Thread{

	private ServerSocket server;
	private Socket socket;
	
	public void setup(int port) throws IOException{
		System.out.println("Connecting to Desktop...");
		server = new ServerSocket(port);
		socket = server.accept();
		System.out.println("Connected!");
	}

	public void updateDesktopBoard(){
		JSONObject output = null;
		PrintWriter out = null;
		try {
			output = new JSONObject("{}");
			output = Main.gameState.getInfoBoard();
			output.put("action_info", Main.gameState.getActionInfo());
			out = new PrintWriter(socket.getOutputStream(), true);
		} catch (JSONException | IOException e1) {
			e1.printStackTrace();
		}
		//Output to desktop
		out.println(output.toString());
	}

	public void updateDesktopBoardWithWinner(Player player){
		JSONObject output = null;
		PrintWriter out = null;
		try {
			output = new JSONObject("{}");
			output = Main.gameState.getInfoBoard();
			output.put("action_info", Main.gameState.getActionInfo());
			output.put("winner", player.getID());
			out = new PrintWriter(socket.getOutputStream(), true);
		} catch (JSONException | IOException e1) {
			e1.printStackTrace();
		}
		//Output to desktop
		out.println(output.toString());
	}

	public void updateDesktopPlayers(){
		JSONObject output = null;
		PrintWriter out = null;
		try {
			output = new JSONObject("{}");
			output = Main.gameState.getInfoPlayers();
			output.put("action_info", Main.gameState.getActionInfo());
			if (Main.gameState.isStarted()) {
				output.put("dice_values", Main.dice.getDiceValues());
			}
			out = new PrintWriter(socket.getOutputStream(), true);
		} catch (JSONException | IOException e1) {
			e1.printStackTrace();
		}
		//Output to desktop
		out.println(output.toString());
	}

	public void run(){
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		while(Main.isActive){
			//synchronized(Main.gameState){ maybe better
			synchronized(this){

				try{
					//Input from desktop
					if(reader.ready()){
						String line = reader.readLine();
						if (!line.isEmpty()) {
							System.out.println("Message from Desktop: " + line);
						}

						//Parse JSONObject from input
						JSONObject obj = new JSONObject(line);
						int id = (int) obj.get("id");
						if(id == 0){
							if(obj.get("action").equals("start")){
//								if (Main.gameState.players.size() >=2) {
									Main.gameState.startGame();
									Main.gameState.updateActionInfo("\nGame has started! Good Luck!\n");
									Main.clientUpdater.updateDesktopPlayers();
//								} else {
//									Main.gameState.updateActionInfo("Must have at least 2 players to start the game!");
//								}
							}
							if(obj.get("action").equals("end")){
								Main.gameState.endGame();
							}
						}
					}

				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
}
