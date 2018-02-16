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

	public Server(int PORT) throws IOException{
		server = new ServerSocket(PORT);
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
			System.out.println("Message from client: " + line);
		}

		//Parse JSONObject from input
		JSONObject obj = new JSONObject(line);
		int id = (int) obj.get("id");
		JSONObject phoneResponse = new JSONObject();
		switch (id){
		//New player
		case -1:
			//Add player for new ip
			String client_ip = socket.getRemoteSocketAddress().toString().replace("/","").split(":")[0];
			int newPlayerID = gamestate.addPlayer(client_ip);
			phoneResponse.put("id", newPlayerID);
			phoneResponse.put("action", "Created new Player");
			response = phoneResponse.toString();
			break;
			//Desktop app
		case 0:
			if(obj.get("action").equals("Start")){
				gamestate.startGame();
			}
			response = gamestate.getInfo().toString();
			break;
		case -10:
			server.close();
			Main.serverActive = false;
			response = "Done";
			break;
			//Player
		default:
			if(obj.get("action").equals("roll")){
				String actionInfo = gamestate.playerRoll(id);
				JSONObject res = gamestate.getInfo();
				res.put("action_info", actionInfo);
				response = res.toString();
			}
			else{
				response = gamestate.getInfo().toString();
			}
			break;
		}
		System.out.println();

		return response;

	}

	public void send(String message) throws IOException{
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		out.println(message);
	}

	public void sendToDesktop(JSONObject obj){

	}
}
