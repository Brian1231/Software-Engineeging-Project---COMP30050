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
import noc_db.Character_noc;
import noc_db.World_noc;

public class GameState implements JSONable {

	Random rand = new Random();
	private ArrayList<Player> players;
	private ArrayList<Character_noc> playerCharacters;
	private ArrayList<NamedLocation> locations;
	private Map<String, Player> clientIPplayerIDMap;
	private boolean gameStarted;
	private int playerTurn;
	private Dice dice;
	public boolean isActive;


	public GameState() {
		players = new ArrayList<Player>();
		locations = new ArrayList<NamedLocation>();
		playerCharacters = new ArrayList<Character_noc>();
		clientIPplayerIDMap = new HashMap<String, Player>();
		gameStarted = false;
		isActive = true;
		playerTurn = 1;
		dice = new Dice();

		//int numberOfChance = rand.nextInt(3) + 2; //2 - 4

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
	
	public boolean isPlayerCharacter(Character_noc ch){
		return this.playerCharacters.contains(ch);
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
			
			//Get random unused character
			Character_noc ch = Main.noc.getRandomChar();
			while(this.isPlayerCharacter(ch)){
				ch = Main.noc.getRandomChar();
			}
			Player newPlayer = new Player(newID, client_ip, ch);
			this.playerCharacters.add(ch);
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
	public String playerAction(int id, String action, String[] args){

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
				if(!player.hasRolled()){
					int spaces = dice.roll();
					player.moveForward(spaces);
					player.useRoll();
					return "Player " + id + " rolled " + spaces + ".";
				}
				else{
					return "Player " + id + " has already rolled this turn.";
				}
			case "buy":
				int playerPosition = player.getPos();
				NamedLocation tile = this.locations.get(playerPosition);
				if(tile instanceof PrivateProperty){
					PrivateProperty prop = (PrivateProperty) tile;
					if(!((prop).getOwner() == null)){
						if(player.getBalance() >= prop.getPrice()){
							(prop).setOwner(player);
							player.addNewPropertyBought(prop);
							player.payMoney(prop.getPrice());
							return "Player " + id + " bought " + prop.getId() + " for " + prop.getPrice() + ".";
						}
						return prop.getId() + " is already owned by " + prop.getOwner().getId() + ".";
					}
					return prop.getId() + " is already owned by " + prop.getOwner().getId() + ".";
				}
				return "You can't buy this.";
			case "sell":
				int locationNumber = Integer.parseInt(args[0]);
				PrivateProperty property = (PrivateProperty) this.locations.get(locationNumber);
					if((property).getOwner().equals(player)){
						if(property instanceof RentalProperty){
							RentalProperty rental = (RentalProperty) property;
							if(!rental.isMortgaged()){
								property.setOwner(null);
								player.removePropertySold(property);
								player.receiveMoney(property.getPrice());
								return "Player " + id + " sold " + property.getId() + " for " + property.getPrice() + ".";
							}
							return "You can't sell a mortgaged property.";
						}
						property.setOwner(null);
						player.removePropertySold(property);
						player.receiveMoney(property.getPrice());
						return "Player " + id + " sold " + property.getId() + " for " + property.getPrice() + ".";
					}
					return "You don't own that property.";
				
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
