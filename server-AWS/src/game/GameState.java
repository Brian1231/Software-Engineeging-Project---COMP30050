package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import main.Main;

public class GameState {

	Random rand = new Random();
	private ArrayList<Player> players;
	private Map<String, Player> clientIPplayerIDMap;
	private boolean gameStarted;
	private int playerTurn;
	
	public GameState(){
		players = new ArrayList<Player>();
		clientIPplayerIDMap = new HashMap<String, Player>();
		gameStarted = false;
	}
	
	public void startGame(){
		gameStarted = true;
	}
	
	public void addPlayer(String client_ip){
		if(!clientIPplayerIDMap.containsKey(client_ip)){
		Player newPlayer = new Player(players.size()+1);
		players.add(newPlayer);
			clientIPplayerIDMap.put(client_ip, newPlayer);
		}
	}
	
	public String playerInfo(){
		return clientIPplayerIDMap.toString();
	}
}
