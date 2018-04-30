package main;

import java.io.IOException;
import java.net.InetAddress;

import game.Dice;
import game.GameState;
import noc_db.NOC_Manager;
import server.ClientUpdater;
import server.PortAllocator;

public class Main {

	private static final int MAINPORT = 8080;
	private static final int DESKTOPPORT = 8000;
	public static GameState gameState;
	public static ClientUpdater clientUpdater;
	public static PortAllocator portAllocator;
	public static NOC_Manager noc;
	public static boolean isActive;

	// create game dice object
	public static Dice dice;

	public static void main(String[] args) throws IOException {
		isActive = false;

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
				//Creates a new player object in gamestate for each new player
				portAllocator = new PortAllocator(MAINPORT);
				portAllocator.start();
			}
			/* - possible fix to kill all threads to re-launch game ??
			if(!isActive) {
				portAllocator.interrupt();
				clientUpdater.interrupt();
			}
			*/
		}


		/*

			  Now have threads for each player all accessing Main.gamestate to update game

			  Also have ClientUpdater on separate thread to update desktop


		 */

	}
}
