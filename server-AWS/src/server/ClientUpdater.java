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
	public void setup() throws IOException{
		System.out.println("Connecting to Desktop...");
		server = new ServerSocket(8081);
		socket = server.accept();
		System.out.println("Connected!");
	}
	public void run(){

		JSONObject output = null;
		PrintWriter out = null;
		BufferedReader reader = null;

		try {
			output = new JSONObject("{}");
			out = new PrintWriter(socket.getOutputStream(), true);
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (JSONException | IOException e1) {
			e1.printStackTrace();
		}

		synchronized(this){
			while(Main.serverActive){
				try{
					output = Main.gameState.getInfo();
					output.put("action_info", this.actionInfo);
					wait(1000);
					
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
							}
						}
					}
					//Output to desktop
					out.println(output.toString());
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
}
