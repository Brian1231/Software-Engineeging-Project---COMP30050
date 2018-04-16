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

public class ClientUpdater extends Thread{

	private ServerSocket server;
	private Socket socket;
	private String actionInfo;

	public void updateActionInfo(String s){
		this.actionInfo = s;
	}
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
			output.put("action_info", this.actionInfo);
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
			output.put("action_info", this.actionInfo);
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

			//out = new PrintWriter(socket.getOutputStream(), true);
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		while(Main.gameState.isActive){
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
								Main.gameState.startGame();
								Main.clientUpdater.updateActionInfo("\nGame has started! Good Luck!\n");
								Main.clientUpdater.updateDesktopPlayers();
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
