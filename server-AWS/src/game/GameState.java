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

	private Random rand = new Random();
	private ArrayList<Player> players;
	private ArrayList<Character_noc> playerCharacters;
	private ArrayList<NamedLocation> locations;
	private Map<String, Player> clientIPplayerIDMap;
	private boolean gameStarted;
	private int playerTurn;
	private Dice dice;
	public boolean isActive;
	private PlayerActions playerActions = new PlayerActions();


	public GameState() {
		players = new ArrayList<Player>();
		locations = new ArrayList<NamedLocation>();
		playerCharacters = new ArrayList<Character_noc>();
		clientIPplayerIDMap = new HashMap<String, Player>();
		gameStarted = false;
		isActive = true;
		playerTurn = 1;
		dice = new Dice();

		// Tiles generation & setup
		ArrayList<NamedLocation> properties = new ArrayList<NamedLocation>();
		World_noc randomWorld;

		//Investment Properties
		int[] rents = {100, 200, 300, 400};
		for (int i = 0; i < 24; i++) {
			randomWorld = Main.noc.getRandomWorld();
			properties.add(new InvestmentProperty(randomWorld.getWorld(), 200 + i * 20, rents));
		}

		//3 Tax squares
		for (int i = 0; i < 3; i++) {
			randomWorld = Main.noc.getRandomWorld();
			properties.add(new TaxSquare(randomWorld.getWorld()));
		}

		//Stations
		for (int i = 0; i < 4; i++) {
			randomWorld = Main.noc.getRandomWorld();
			properties.add(new Station(randomWorld.getWorld(),200  + i * 20, rents));
		}

		//Utilities
		for (int i = 0; i < 2; i++) {
			randomWorld = Main.noc.getRandomWorld();
			properties.add(new Utility(randomWorld.getWorld(), 200 + i * 20));
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
		locations.add(10, new SpecialSquare("Go to Intergalactic Prison!"));
		locations.add(29, new SpecialSquare("Intergalactic Prison"));

		String[] colours = {"CYAN", "GREEN", "MAGENTA", "YELLOW", "ORANGE", "BLUE", "WHITE", "PINK"};
		int colourCount = 0;
		int colourIndex = 0;

		for (int i = 0; i < locations.size(); i++) {
			locations.get(i).setLocation(i);
			if (locations.get(i) instanceof InvestmentProperty) {
				InvestmentProperty prop = (InvestmentProperty) locations.get(i);
				prop.setColour(colours[colourIndex]);
				colourCount++;
				if (colourCount % 3 == 0) colourIndex++;
			}
		}
	}

	public boolean isStarted() {
		return this.gameStarted;
	}

	public boolean isActive() {
		return this.isActive;
	}

	public boolean isPlayerCharacter(Character_noc ch) {
		return this.playerCharacters.contains(ch);
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
			while (this.isPlayerCharacter(ch)) {
				ch = Main.noc.getRandomChar();
			}
			Player newPlayer = new Player(newID, client_ip, ch, Main.noc.getVehicle(ch.getVehicle()));
			this.playerCharacters.add(ch);
			players.add(newPlayer);
			clientIPplayerIDMap.put(client_ip, newPlayer);
			return newID;
		} else {
			return -1;
		}

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

			case "done":
				if(!player.isInDebt()){
					playerActions.done(player);
					//Increment player turn
					this.playerTurn++;
					if (this.playerTurn > this.players.size()) {
						this.playerTurn = 1;
					}
					return player.getCharName()+" finished their turn.";
				}
				return "You must pay your debt before ending your turn.";
			default:
				return player.getCharName()+" did nothing.";
			}
		} else {
			return "It's not your turn!";
		}
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
		//info.put("action_info", "Something happened!");
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

	public void endGame() {
		Main.clientUpdater.updateActionInfo("Game Over");
		Main.clientUpdater.updateDesktopPlayers();
		Main.clientUpdater.updateDesktopBoard();
		Main.portAllocator.endGame();
		Main.isActive = false;
	}
}
