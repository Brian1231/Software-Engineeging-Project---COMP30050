package main;

import java.io.IOException;
import java.net.InetAddress;

import org.json.JSONException;

import game.GameState;
import server.ClientUpdater;
import server.PortAllocator;

public class Main {

	public static GameState gameState;
	public static final int MAINPORT = 8080;
	public static final int DESKTOPPORT = 8000;
	public static ClientUpdater clientUpdater;
	public static PortAllocator portAllocator;

	public static void main(String[] args) throws IOException, JSONException {

			System.out.println(InetAddress.getLocalHost().getHostAddress());
			//Contains all information about current game state
			gameState = new GameState();
			
			//Thread for desktop connection
			clientUpdater = new ClientUpdater();
			clientUpdater.setup(DESKTOPPORT);
			clientUpdater.start();
			
			//Thread used to allocate phone connections to an available port
			//Creates a new PlayerConnection thread for each new player
			//Creates a new player object in gamestate for each new player
			portAllocator = new PortAllocator(MAINPORT);
			portAllocator.start();
			
			/**
			 * 
			 * Now have threads for each player all accessing Main.gamestate to update game
			 * 
			 * Also have ClientUpdater on separate thread to update desktop
			 * 
			 * 
			 * */
		
	}
}
