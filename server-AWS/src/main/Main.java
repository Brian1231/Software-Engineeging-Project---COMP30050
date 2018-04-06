package main;

import java.io.IOException;
import java.net.InetAddress;

import org.json.JSONException;

import game.ChanceTemplate;
import game.GameState;
import noc_db.NOC_Manager;
import server.ClientUpdater;
import server.PortAllocator;

@SuppressWarnings("InfiniteLoopStatement")
public class Main {


	public static final int MAINPORT = 8080;
	public static final int DESKTOPPORT = 8000;
	public static GameState gameState;
	public static ClientUpdater clientUpdater;
	public static PortAllocator portAllocator;
	public static NOC_Manager noc;
	public static boolean isActive;

	public static void main(String[] args) throws IOException, JSONException {
		isActive = false;

		System.out.println(InetAddress.getLocalHost().getHostAddress());

		while(true){
			if(!isActive){
				//Create and populate noc list
				noc = new NOC_Manager();
				noc.setup();
				System.out.println(new ChanceTemplate(noc.getRandomChar()).template3());
				System.out.println(new ChanceTemplate(noc.getRandomChar()).template3());
				System.out.println(new ChanceTemplate(noc.getRandomChar()).template3());
				System.out.println(new ChanceTemplate(noc.getRandomChar()).template3());
				System.out.println(new ChanceTemplate(noc.getRandomChar()).template3());
				System.out.println(new ChanceTemplate(noc.getRandomChar()).template3());
				System.out.println(new ChanceTemplate(noc.getRandomChar()).template3());
				System.out.println(new ChanceTemplate(noc.getRandomChar()).template3());
				
				//Contains all information about current game state
				gameState = new GameState();
				
				System.out.println(gameState.getInfoBoard());
				//Thread for desktop connection
				clientUpdater = new ClientUpdater();
				clientUpdater.setup(DESKTOPPORT);
				clientUpdater.start();
				clientUpdater.updateActionInfo("Connected");
				clientUpdater.updateDesktopBoard();
				
				//Thread used to allocate phone connections to an available port
				//Creates a new PlayerConnection thread for each new player
				//Creates a new player object in gamestate for each new player
				portAllocator = new PortAllocator(MAINPORT);
				portAllocator.start();
				isActive = true;
			}
		}


		/*

			  Now have threads for each player all accessing Main.gamestate to update game

			  Also have ClientUpdater on separate thread to update desktop


		 */

	}
}
