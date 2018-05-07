package main;

import java.io.IOException;
import java.net.InetAddress;

import game_models.Dice;
import game.GameState;
import noc_db.NOC_Manager;
import server.ClientUpdater;
import server.PortAllocator;

/*
 * This is our main class containing all of our main objects
 * 
 * */
public class Main {

	private static final int MAINPORT = 8080;
	private static final int DESKTOPPORT = 8000;
	public static boolean isActive;
	public static GameState gameState;
	public static ClientUpdater clientUpdater;
	public static PortAllocator portAllocator;
	public static NOC_Manager noc;
	public static Dice dice;

	@SuppressWarnings("InfiniteLoopStatement")
	public static void main(String[] args) throws IOException {
		isActive = false;

		//Print Local IP for playing locally
		System.out.println(InetAddress.getLocalHost().getHostAddress());

		while(true){
			if(!isActive){
				isActive = true;
				dice = new Dice();

				//Create and populate noc list
				noc = NOC_Manager.getNocManager();
				noc.setup();
				
				//Contains all information about current game state
				gameState = new GameState();
				
				//Thread for desktop connection
				clientUpdater = new ClientUpdater();
				clientUpdater.setup(DESKTOPPORT);
				clientUpdater.start();
				gameState.updateActionInfo("Connected");
				clientUpdater.updateDesktopBoard();
				
				//Thread used to allocate phone connections to an available port
				//Creates a new PlayerConnection thread for each new player
				//Creates a new player object in game state for each new player
				portAllocator = new PortAllocator(MAINPORT);
				portAllocator.start();
			}
		}
	}
}
