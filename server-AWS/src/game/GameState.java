package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


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
		playerTurn = 0;
	}

	public boolean isStarted(){
		return this.gameStarted;
	}

	public void startGame(){
		gameStarted = true;
		playerTurn = rand.nextInt(this.players.size()+1) + 1;
	}

	public int addPlayer(String client_ip){
		int newID = players.size()+1;
		if(!clientIPplayerIDMap.containsKey(client_ip)){
			Player newPlayer = new Player(newID, client_ip);
			players.add(newPlayer);
			clientIPplayerIDMap.put(client_ip, newPlayer);
		}
		return newID;
	}

	public JSONObject getInfo() throws JSONException{
		JSONObject info = new JSONObject();

		JSONArray jsonPlayers = new JSONArray();
		for(Player p : this.players){
			jsonPlayers.put(p.getInfo());
		}
		info.put("players", jsonPlayers);
		info.put("player_turn", this.playerTurn);
		info.put("game_started", this.gameStarted);
		info.put("action_info", "Something happened!");
		return info;

	}
}
