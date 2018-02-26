package main;

import java.io.IOException;

import org.json.JSONException;

import game.GameState;
import noc_db.DB_Manager;
import server.Server;

public class Main {

	public static GameState gameState;
	public static DB_Manager dbm;
	public static boolean serverActive = true;
	public static final int PORT = 8080;

	public static void main(String[] args) throws IOException, JSONException {

		
			//Used to listen for input from players
			Server server = new Server(PORT);
			
			//Contains all information about current game state
			gameState = new GameState();
			
			//Used to access NOC database
			/*dbm = new DB_Manager();
			dbm.connect();*/
			
			while (serverActive){
				String response = server.listen(gameState);
				
				server.send(response);
				if(response.equals("Done")){
					serverActive = false;
				}
			}
		
	}
}
