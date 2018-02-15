package main;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

import game.GameState;
import server.Server;

public class Main {

	public static Map<String, Integer> CLIENT_IP_PLAYER_ID_MAP;
	public static GameState gameState;
	public static boolean serverActive = true;
	static final int PORT = 8080;
	
	public static void main(String[] args) throws IOException {
		
		Server server = new Server(PORT);
		
		gameState = new GameState();
		
		while (serverActive){
			String response = server.listen(gameState);
			server.send(response);
			if(response.equals("Done")){
				serverActive = false;
			}
			System.out.println(gameState.playerInfo() + "\n");
		}
	}
}
