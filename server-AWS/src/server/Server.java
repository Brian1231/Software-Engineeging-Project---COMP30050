package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import game.GameState;
import main.Main;

public class Server {

	private ServerSocket server;
	private Socket socket;
	public ClientUpdater clientUpdater;

	public Server(int PORT) throws IOException{
		server = new ServerSocket(PORT);
		//Thread to update desktop
				clientUpdater = new ClientUpdater();
				clientUpdater.setup();
				clientUpdater.start();
		//clientUpdater = new ClientUpdater();
	}

	public void close() throws IOException{
		server.close();
	}

	public String listen(GameState gamestate) throws IOException, JSONException{
		System.out.println(InetAddress. getLocalHost().getHostAddress());
		System.out.println("Listening for connection on port " + server.getLocalPort() + " ...");
		socket = server.accept();
		String response = "null";

		//Read input from client
		InputStreamReader isr = new InputStreamReader(socket.getInputStream());
		BufferedReader reader = new BufferedReader(isr);
		String line = reader.readLine();
		if (!line.isEmpty()) {
			System.out.println("Message from Phone: " + line);
		}

		//Parse JSONObject from input
		JSONObject obj = new JSONObject(line);
		int id = (int) obj.get("id");
		JSONObject phoneResponse = new JSONObject();
		String client_ip = socket.getRemoteSocketAddress().toString().replace("/","").split(":")[0];
		String lastAction = "New Game";
		switch (id){
		//New player
		case -1:
			//Add player for new ip
			
			int newPlayerID = gamestate.addPlayer(client_ip);
			phoneResponse.put("id", newPlayerID);
			if(newPlayerID != -1){
				phoneResponse.put("action", "Created new Player");
			}
			else{
				phoneResponse.put("action", "You're already a player!");
			}
			gamestate.startGame();
			response = phoneResponse.toString();
			//clientUpdater.updateClient(gamestate.getInfo());
			break;
			//Desktop app
		//case 0:
			
			/*if(obj.get("action").equals("update")){
				//clientUpdater.updateDesktop();
			}*/
			//response = gamestate.getInfo().put("action_info", lastAction).toString();
			//break;
		case -10:
			server.close();
			Main.serverActive = false;
			response = "Done";
			break;
			//Player
		default:
			String action = (String) obj.get("action");
			String actionInfo = gamestate.playerAction(id, action);
			lastAction = actionInfo;
			JSONObject res = gamestate.getInfo();
			res.put("action_info", actionInfo);
			response = res.toString();
			break;
		}

		return response;

	}

	public void send(String message) throws IOException{
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		out.println(message);
		socket.close();
	}

	/*public void updateDesktop() throws JSONException{
		//clientUpdater.updateClient(Main.gameState.getInfo());
	}*/
}
