package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import main.Constants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import game_interfaces.JSONable;
import main.Main;
import noc_db.Character_noc;
import noc_db.World_noc;

public class GameState implements JSONable {

	private Random rand = new Random();
	private ArrayList<Player> players;
	private ArrayList<Character_noc> playerCharacters;
	private ArrayList<NamedLocation> locations;
	private Map<String, Player> clientIPplayerIDMap;
	private boolean gameStarted;
	private int playerTurn;
	private Dice dice;
	private PlayerActions playerActions = new PlayerActions();
	private VillainGang villainGang;
	private String actionInfo;


	public GameState() {
		players = new ArrayList<Player>();
		locations = new ArrayList<NamedLocation>();
		playerCharacters = new ArrayList<Character_noc>();
		clientIPplayerIDMap = new HashMap<String, Player>();
		gameStarted = false;
		playerTurn = 1;
		dice = new Dice();
		villainGang = new VillainGang();

		// Tiles generation & setup
		ArrayList<NamedLocation> properties = new ArrayList<NamedLocation>();
		World_noc randomWorld;

		//Investment Properties
		for (int i = 0; i < 24; i++) {
			randomWorld = Main.noc.getRandomWorld();
			properties.add(new InvestmentProperty(randomWorld.getWorld()));
		}

		//3 Tax squares
		for (int i = 0; i < 3; i++) {
			randomWorld = Main.noc.getRandomWorld();
			properties.add(new TaxSquare(randomWorld.getWorld()));
		}

		//Stations
		for (int i = 0; i < 4; i++) {
			randomWorld = Main.noc.getRandomWorld();
			Station station = new Station(randomWorld.getWorld(),Constants.STATION_PRICES[i], Constants.STATION_RENTS[i]);
			station.setMortgageAmount(Constants.STATION_MORTGAGE_VALUE[i]);
			properties.add(station);
		}

		//Utilities
		for (int i = 0; i < 2; i++) {
			randomWorld = Main.noc.getRandomWorld();
			Utility utility = new Utility(randomWorld.getWorld(), Constants.UTILITY_PRICES[i], Constants.UTILITY_RENTS[i]);
			utility.setMortgageAmount(Constants.UTILITY_MORTGAGE_VALUE[i]);
			properties.add(utility);
		}

		//Chance Squares
		for (int i = 0; i < 3; i++) {
			properties.add(new ChanceSquare("Interdimensional TV"));
		}

		//Shuffle Tiles
		Random random = new Random();
		while (!properties.isEmpty()) {
			locations.add(properties.remove(random.nextInt(properties.size())));
		}

		//Other tiles
		locations.add(0, new SpecialSquare("Go"));
		locations.add(10, new SpecialSquare("Go to Intergalactic Prison"));
		locations.add(29, new SpecialSquare("Intergalactic Prison"));

		int colourCount = 0;
		int colourIndex = 0;
		int investmentPropCount = 0;

		for (int i = 0; i < locations.size(); i++) {
			locations.get(i).setLocation(i);
			if (locations.get(i) instanceof InvestmentProperty) {
				InvestmentProperty prop = (InvestmentProperty) locations.get(i);

				// setting Investment properties variables
				prop.setPrice(Constants.INVESTMENT_PRICES[investmentPropCount]);
				prop.setRentAmounts(Constants.INVESTMENT_RENTS[investmentPropCount]);
				prop.setHousePrice(Constants.HOUSE_PRICES[investmentPropCount]);
				prop.setHotelPrice(Constants.HOUSE_PRICES[investmentPropCount]);
				prop.setMortgageAmount(Constants.INVESTMENT_MORTGAGE_VALUE[investmentPropCount]);
				prop.setColour(Constants.INVESTMENT_COLOUR_GROUPS[colourIndex]);

				// increments
				colourCount++;
				investmentPropCount++;

				if (colourCount % 3 == 0) {
					colourIndex++;
				}
			}
		}
	}

	public boolean isStarted() {
		return this.gameStarted;
	}

	public String getActionInfo(){
		return this.actionInfo;
	}
	
	public void updateActionInfo(String s){
		this.actionInfo = s;
	}
	
	public void startGame() {
		this.gameStarted = true;
		if (this.players.size() == 0) {
			playerTurn = rand.nextInt(this.players.size() + 1) + 1;
		} else {
			playerTurn = rand.nextInt(this.players.size()) + 1;
		}

	}

	/**
	 * Returns new player ID or -1
	 */
	public int addPlayer(String client_ip) {
		int newID = players.size() + 1;
		if (!clientIPplayerIDMap.containsKey(client_ip)) {

			//Get random unused character
			Character_noc ch = Main.noc.getRandomChar();
			while (this.playerCharacters.contains(ch)) {
				ch = Main.noc.getRandomChar();
			}
			Player newPlayer = new Player(newID, ch, Main.noc.getVehicle(ch.getVehicle()), Constants.playerColours[newID]);
			this.playerCharacters.add(ch);
			players.add(newPlayer);
			clientIPplayerIDMap.put(client_ip, newPlayer);
			return newID;
		} else {
			return -1;
		}

	}

	public void removePlayer(Player player) {
		this.playerCharacters.remove(player.getCharacter());
		this.players.remove(player);
		if(this.players.size()==1) 
			this.endGame();
		else
		{
			this.incrementPlayerTurn();
		}
	}

	public void activateVillainGang(int location){
		this.villainGang.activate(location);
	}
	public void updateVillainGang(){
		this.villainGang.update();
	}

	public boolean villainGangIsActive(){
		return this.villainGang.isActive();
	}

	public String villainGangCheck(Player player){
		if(this.villainGang.isActive() && this.villainGang.position() == player.getPos()){
			return this.villainGang.attackPlayer(player);
		}
		return "";
	}
	/**
	 * Returns result of player action
	 */
	public String playerAction(int id, String action, String[] args) {

		//Check if its the correct players turn
		if (this.playerTurn == id && this.gameStarted) {
			//Get player from id
			Player player = null;
			for (Player p : this.players) {
				if (p.getID() == id) {
					player = p;
				}
			}

			//Do player action
			switch (action) {
			case "roll":

				return playerActions.roll(player, dice, id, this.locations);

			case "buy":

				return playerActions.buy(player, this.locations.get(player.getPos()), id);


			case "sell":

				return playerActions.sell(player, this.locations.get(Integer.parseInt(args[0])), id);

			case "mortgage":

				return playerActions.mortgage(player, this.locations.get(Integer.parseInt(args[0])), id);

			case "redeem":

				return playerActions.redeem(player, this.locations.get(Integer.parseInt(args[0])), id);

			case "boost":

				return playerActions.boost(player, this.locations);

			case "build":

				return playerActions.build(player, this.locations.get(Integer.parseInt(args[0])), Integer.parseInt(args[1]), id);

			case "demolish":

				return playerActions.demolish(player, this.locations.get(Integer.parseInt(args[0])), Integer.parseInt(args[1]),id);

			case "pay":

				return player.payDebt();

			case "trap":

				return playerActions.setTrap(player, this.locations.get(Integer.parseInt(args[0])));

			case "bankrupt":

				return playerActions.bankrupt(player);

			case "done":

				return playerActions.done(player);
			default:

				return player.getCharName()+" did nothing.";
			}
		} else {
			return "It's not your turn!";
		}
	}

	public void incrementPlayerTurn(){
		this.playerTurn++;
		if (this.playerTurn > this.players.size()) {
			
			this.playerTurn = 1;
		}
		Main.portAllocator.alertPlayer(this.playerTurn);
	}
	
	public String getLocationName(int location){
		return this.locations.get(location).getId();
	}
	/**
	 * Returns full game state in JSON format
	 */
	public JSONObject getInfo() throws JSONException {
		JSONObject info = new JSONObject();

		JSONArray jsonPlayers = new JSONArray();
		for (Player p : this.players) {
			jsonPlayers.put(p.getInfo());
		}
		JSONArray jsonLocations = new JSONArray();
		for (NamedLocation l : this.locations) {
			jsonLocations.put(l.getInfo());
		}
		info.put("players", jsonPlayers);
		info.put("locations", jsonLocations);
		info.put("player_turn", this.playerTurn);
		info.put("game_started", this.gameStarted);
		return info;

	}

	public JSONObject getInfoPlayers() throws JSONException {
		JSONObject info = new JSONObject();

		JSONArray jsonPlayers = new JSONArray();
		for (Player p : this.players) {
			jsonPlayers.put(p.getInfo());
		}

		info.put("players", jsonPlayers);
		info.put("player_turn", this.playerTurn);
		info.put("game_started", this.gameStarted);
		info.put("villain_gang", this.villainGang.getInfo());
		return info;
	}

	public JSONObject getInfoBoard() throws JSONException {
		JSONObject info = new JSONObject();

		JSONArray jsonLocations = new JSONArray();
		for (NamedLocation l : this.locations) {
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
	public JSONObject getPlayerInfo(int id) throws JSONException {
		JSONObject info = new JSONObject();
		for (Player p : this.players) {
			if (p.getID() == id) {
				info = p.getInfo();
			}
		}

		return info;

	}

	public String getPlayerName(int id){
		for(Player player : this.players){
			if(player.getID() == id) return player.getCharName();
		}
		return "Someone";
	}

	public Player getWinner() {
		int max = 0;
		for (Player p : players) {
			int newMax = p.getNetWorth();
			if (newMax > max) {
				max = newMax;
			}
		}
		return players.get(0);
	}

	public void endGame() {
		Player winningPlayer = getWinner();

		this.updateActionInfo("Game Over");
		Main.clientUpdater.updateDesktopPlayers();
		Main.clientUpdater.updateDesktopBoardWithWinner(winningPlayer);
		Main.portAllocator.endGame();
		Main.isActive = false;
	}
}
