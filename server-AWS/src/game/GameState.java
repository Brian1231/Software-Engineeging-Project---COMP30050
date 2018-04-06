package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import game_interfaces.JSONable;
import main.Main;
import noc_db.World_noc;

public class GameState implements JSONable {

	Random rand = new Random();
	private ArrayList<Player> players;
	private ArrayList<NamedLocation> locations;
	private Map<String, Player> clientIPplayerIDMap;
	private boolean gameStarted;
	private int playerTurn;
	private Dice dice;
	public boolean isActive;


	public GameState() {
		players = new ArrayList<Player>();
		locations = new ArrayList<NamedLocation>();
		clientIPplayerIDMap = new HashMap<String, Player>();
		gameStarted = false;
		isActive = true;
		playerTurn = 1;
		dice = new Dice();

		for(int i=0;i<39;i++){
			World_noc rand = Main.noc.getRandomWorld();
			locations.add(new PrivateProperty(i, rand.getWorld(), 200 + i*20));
		}
	}

	public boolean isStarted(){
		return this.gameStarted;
	}
	public boolean isActive(){
		return this.isActive;
	}

	public void startGame(){
		gameStarted = true;
		if(this.players.size() == 0 ){
			playerTurn = rand.nextInt(this.players.size()+1) + 1;
		}
		else{
			playerTurn = rand.nextInt(this.players.size()) + 1;
		}

	}

	/** 
	 * Returns new player ID or -1
	 */
	public int addPlayer(String client_ip){
		int newID = players.size()+1;
		if(!clientIPplayerIDMap.containsKey(client_ip)){
			Player newPlayer = new Player(newID, client_ip);
			players.add(newPlayer);
			clientIPplayerIDMap.put(client_ip, newPlayer);
			return newID;
		}
		else{
			return -1;
		}

	}

	/** 
	 * Returns result of player action
	 */
	public String playerAction(int id, String action){

		//Check if its the correct players turn
		if(this.playerTurn == id){
			//Get player from id
			Player player = null;
			for(Player p : this.players){
				if(p.getID() == id){
					player = p;
				}
			}

			//Do player action
			switch(action){
			case "roll":
				int spaces = dice.roll();
				player.moveForward(spaces);
				return "Player " + id + " rolled " + spaces + ".";
			case "done":
				//Increment player turn
				this.playerTurn++;
				if(this.playerTurn > this.players.size()) {
					this.playerTurn=1;
				}
				return "Player " + id + " finished their turn.";
			default:
				return "Player " + id + " did nothing.";
			}	
		}
		else{
			return "It's not your turn!";
		}
	}

	/** 
	 * Returns full game state in JSON format
	 */
	public JSONObject getInfo() throws JSONException{
		JSONObject info = new JSONObject();

		JSONArray jsonPlayers = new JSONArray();
		for(Player p : this.players){
			jsonPlayers.put(p.getInfo());
		}
		JSONArray jsonLocations = new JSONArray();
		for(NamedLocation l : this.locations){
			jsonLocations.put(l.getInfo());
		}
		info.put("players", jsonPlayers);
		info.put("locations", jsonLocations);
		info.put("player_turn", this.playerTurn);
		info.put("game_started", this.gameStarted);
		//info.put("action_info", "Something happened!");
		return info;

	}

	public JSONObject getInfoPlayers() throws JSONException{
		JSONObject info = new JSONObject();

		JSONArray jsonPlayers = new JSONArray();
		for(Player p : this.players){
			jsonPlayers.put(p.getInfo());
		}

		info.put("players", jsonPlayers);
		info.put("player_turn", this.playerTurn);
		info.put("game_started", this.gameStarted);
		return info;
	}

	public JSONObject getInfoBoard() throws JSONException{
		JSONObject info = new JSONObject();

		JSONArray jsonLocations = new JSONArray();
		for(NamedLocation l : this.locations){
			jsonLocations.put(l.getInfo());
		}

		info.put("locations", jsonLocations);
		info.put("player_turn", this.playerTurn);
		info.put("game_started", this.gameStarted);
		return info;
	}

	/** 
	 * Returns player state in JSON format
	 */
	public JSONObject getPlayerInfo(int id) throws JSONException{
		JSONObject info = new JSONObject();
		for(Player p : this.players){
			if(p.getID() == id){
				info = p.getInfo();
			}
		}
		return info;

	}

	public void endGame(){
		Main.clientUpdater.updateActionInfo("Game Over");
		Main.clientUpdater.updateDesktopPlayers();
		Main.clientUpdater.updateDesktopBoard();
		Main.portAllocator.endGame();
		Main.isActive = false;
	}
}
