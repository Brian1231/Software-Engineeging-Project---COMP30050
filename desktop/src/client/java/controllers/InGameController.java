package client.java.controllers;

import client.java.gameObjects.Auction;
import client.java.gameObjects.Location;
import client.java.gameObjects.Player;
import client.java.gui.*;
import client.java.main.Game;
import client.java.network.NetworkConnection;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
	This Class handles all activity while in the game screen.
	Sets up the board and handles all updates from the server in order to display the gameState on the board
 */

public class InGameController {

	private Stage gameStage;
	@FXML
	public BorderPane rootPane;
	// Stacks each layer on top of each other.
	private StackPane layers = new StackPane();
	// 4 board layers.
	private BoardCanvas boardCanvas = new BoardCanvas();
	private PlayerCanvas playerCanvas = new PlayerCanvas();
	private InformationPane infoPane = new InformationPane();
	private Pane loadingPane = new Pane();

	private ImageView logo = new ImageView();
	private Button startButton = new Button("START GAME");

	private String[] loadStrings = {
	"Entering Game!",
    "Generating Worlds...",
    "Expanding Universe...",
    "Stabilising Dimensions...",
    "Calculating orbits...",
    "Charging Portals...",
    "Evicting squatters...",
    "Securing Intergalactic Prison..."
    };

    private static int loadTime = 7;
	private boolean imagesPlaced = false;

	// Networking.
	private String ip = "52.48.249.220";
	private final static int PORT = 8000;
	private NetworkConnection connection;

	// Initializes Game window
	public void initialize() throws IOException, JSONException {
		Game.initializeLocations();
		Game.observablePlayers.addListener(new ListChangeListener<Player>() {
			@Override
			public void onChanged(Change<? extends Player> c) {
				while (c.next()) {
					for (Player remitem : c.getRemoved()) {
						infoPane.removePlayerInfo(remitem);
					}
					for (Player additem : c.getAddedSubList()) {
						infoPane.addPlayerInfo(additem);
					}
				}
			}
		});
		setUpBoard();
		Game.setPlayerCanvas(playerCanvas);
	}

	void setGameStage(Stage gameStage) {
		this.gameStage = gameStage;
	}

	// Sets Server IP, Makes a connection and connects
	void setServerIP(String ipAddress){
		this.ip = ipAddress;
		connection = new NetworkConnection(ip,PORT, input -> {
			try {
				onUpdateReceived(input);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		});
		System.out.println(ip);

		try {
			connection.startConnection();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	// Adds main Gui elements
	private void setUpBoard() throws IOException, JSONException{
		
		// Loading Screen
		// Background
		Image l = new Image("client/resources/images/load.gif");
		ImageView loadImage = new ImageView(l);
		loadImage.fitWidthProperty().bind(loadingPane.widthProperty());
		loadImage.fitHeightProperty().bind(loadingPane.heightProperty());
		loadingPane.getChildren().add(loadImage);

		// Load Details
		Label loadDetails = new Label("");
		loadDetails.setFont(new Font("Verdana", 50));
		loadDetails.setTextFill(Color.WHITE);
		loadDetails.layoutXProperty().bind(loadingPane.widthProperty().divide(2).subtract(loadDetails.widthProperty().divide(2)));
		loadDetails.layoutYProperty().bind(loadingPane.heightProperty().subtract(100));
		loadingPane.getChildren().add(loadDetails);

		// Loading Screen Logo
		logo.fitWidthProperty().bind(loadingPane.widthProperty().divide(2.5));
		logo.fitHeightProperty().bind(loadingPane.heightProperty().divide(5));
		logo.layoutXProperty().bind(loadingPane.widthProperty().divide(2).subtract(logo.fitWidthProperty().divide(2)));
		logo.setImage(new Image("/client/resources/images/InterDimLogo.png"));
		loadingPane.getChildren().add(logo);

		// Loading Message Timeline
		Timeline timeLine = new Timeline();
		timeLine.setCycleCount(Timeline.INDEFINITE);
		timeLine.getKeyFrames().add(
				new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						loadTime--;

						Platform.runLater(()->{
							loadDetails.setText(loadStrings[loadTime]);
						});

						// Finished Loading
						if(loadTime <= 0){
							loadTime = 7;
							timeLine.stop();
							Platform.runLater(()-> {
								layers.getChildren().remove(loadingPane);
								layers.getChildren().remove(loadDetails);
							});
						}
					}
				}
				));
		timeLine.playFromStart();

		Pane boardWrapper = new Pane();
		boardWrapper.getChildren().add(boardCanvas);
		Pane playerWrapper = new Pane();
		playerWrapper.getChildren().add(playerCanvas);

		// Adding Layers
		infoPane.getChildren().add(startButton);
		layers.getChildren().add(boardWrapper);
		layers.getChildren().add(playerWrapper);
		layers.getChildren().add(infoPane);
		layers.getChildren().add(loadingPane);

		// Layout
		rootPane.setCenter(layers);
		boardCanvas.widthProperty().bind(rootPane.widthProperty());
		boardCanvas.heightProperty().bind(rootPane.heightProperty());
		playerCanvas.widthProperty().bind(rootPane.widthProperty());
		playerCanvas.heightProperty().bind(rootPane.heightProperty());

		// Initial Draw
		boardCanvas.draw();
		boardCanvas.addCenterTile();
		playerCanvas.draw();

		// Start game button
		startButton.layoutXProperty().bind(boardWrapper.widthProperty().divide(2).subtract(startButton.widthProperty().divide(2)));
		startButton.layoutYProperty().bind(boardWrapper.heightProperty().subtract(80));
		startButton.setOnAction(e -> {
					try {
						JSONObject output = new JSONObject();
						output.put("id", 0);
						output.put("action", "start");
						connection.send(output);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
		);
	}


	// Called whenever a message/JSON/update is received form the server.
	private void onUpdateReceived(JSONObject update) throws JSONException {

		// If desktop failed to connect to the server
		if(update.has("f")){
			onFalseConnection();
		}
		else{
			Platform.runLater(() -> {
				try {
					// Event Update
					String actionInfo = update.getString("action_info");

					// Game start update
					if(update.has("game_started") && !Game.getGameStarted()){
						boolean game_started = update.getBoolean("game_started");
						handleGameStartUpdate(game_started);
					}
					// Game End Update
					if(update.has("winner")){
						int winnerID = update.getInt("winner");
						showGameOverScreen(winnerID);
					}
					// Player Turn Update
					if(update.has("player_turn")){
						Game.playerTurn = update.getInt("player_turn");
						infoPane.updatePlayerTurn(Game.playerTurn);
					}
					// Players / Villains / Dice Update
					if(update.has("players")){
						JSONArray playerObjects = update.getJSONArray("players");

						// Update Players
						handlePlayerUpdate(playerObjects, actionInfo);

						// Update Villain Gang
						JSONObject villains = update.getJSONObject("villain_gang");
						Game.updateVillains(villains.getInt("position"), villains.getBoolean("is_active"));

						// Update Dice
						JSONArray dice = update.getJSONArray("dice_values");
						int dice1 = dice.getInt(0);
						int dice2 = dice.getInt(1);
						infoPane.updateDice(dice1, dice2);
					}
					// Locations Update
					if(update.has("locations")){
						JSONArray locationObjects = update.getJSONArray("locations");
						handleLocationUpdate(locationObjects);
					}
					// Auction Update
					if(update.has("auction")){
						JSONObject auctionObject = update.getJSONObject("auction");
						handleAuctionUpdate(auctionObject);
					}

					// Update Information Feed
					infoPane.updateFeed(actionInfo);

					// Update Magnified Tile info in right loop
					if(Game.gameStarted){
						int playerPos = 0;
						for(Player p : Game.players){
							if(p.getId() == Game.playerTurn) playerPos = p.getPosition();
						}
						Location locToDisplay = Game.getLocation(playerPos);
						infoPane.updateLocationInfo(locToDisplay);
					}
				} catch (JSONException | IllegalArgumentException | SecurityException e) { e.printStackTrace(); } catch (IOException e) {
					e.printStackTrace();
				}
			});
		}
	}

	// Sets games started boolean and transforms start button into end game button.
	private void handleGameStartUpdate(boolean gstart){
		if(gstart){
			infoPane.removeLogo();
			Game.gameStarted = true;
			startButton.setText("End Game");
			startButton.setOnAction(null);

			startButton.setOnAction(e2 -> {
				boolean answer = ConfirmBox.display("Are you sure?", "Are you sure that you want to quit the game?", gameStage);
				if (answer) {
					try {
						JSONObject output = new JSONObject();
						output.put("id", 0);
						output.put("action", "end");
						connection.send(output);
					} catch (JSONException e1) {
						e1.printStackTrace();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});
		}
	}

	// Update player information and Redraw players according to new player positions
	private void handlePlayerUpdate(JSONArray playerObjects, String actionInfo) throws JSONException {

		List<Player> plyrs = new ArrayList<>();

		for(int i=0;i<playerObjects.length();i++){
			int bal = playerObjects.getJSONObject(i).getInt("balance");
			String balance = Integer.toString(bal);
			int id = playerObjects.getJSONObject(i).getInt("id");
			int position = playerObjects.getJSONObject(i).getInt("position");
			int c = playerObjects.getJSONObject(i).getInt("colour");
			java.awt.Color col = new java.awt.Color(c);
			Color fxColor = Color.rgb(col.getRed(),col.getGreen(),col.getBlue());
			String character = playerObjects.getJSONObject(i).getString("character");
			int fuel = playerObjects.getJSONObject(i).getInt("fuel");
			boolean direction = playerObjects.getJSONObject(i).getBoolean("moving_forward");
			plyrs.add(new Player(balance,id,position,fxColor,character,fuel,direction));
		}
		Game.updatePlayers(plyrs, actionInfo);
		playerCanvas.draw();
	}

	// Updates location info and redraws locations according to new Location information.
	private void handleLocationUpdate(JSONArray locationObjects) throws JSONException, IOException {

		List<Location> locs = new ArrayList<>();

		for(int i=0;i<locationObjects.length();i++){
			String id = locationObjects.getJSONObject(i).getString("id");
			int price = locationObjects.getJSONObject(i).getInt("price");
			int rent = locationObjects.getJSONObject(i).getInt("rent");
			int position = locationObjects.getJSONObject(i).getInt("location");
			int owner = locationObjects.getJSONObject(i).getInt("owner");
			int houses = locationObjects.getJSONObject(i).getInt("houses");
			java.awt.Color col = new java.awt.Color(locationObjects.getJSONObject(i).getInt("color"));
			Color color = Color.rgb(col.getRed(), col.getGreen(), col.getBlue());
			boolean isMortgaged = locationObjects.getJSONObject(i).getBoolean("is_mortgaged");
			if(locationObjects.getJSONObject(i).has("type")){
				String type = locationObjects.getJSONObject(i).getString("type");
				locs.add(new Location(id,position,price,rent,owner, color, isMortgaged, houses, type));
			}
			else{
				locs.add(new Location(id,position,price,rent,owner, color, isMortgaged, houses, ""));
			}
		}

		Game.updateLocations(locs);
		Game.locationsSet = true;
		// Load location images
		if(!imagesPlaced){
			boardCanvas.getImages();
		}
		boardCanvas.removeTileTitles();
		boardCanvas.draw();
		imagesPlaced = true;
	}

	// Starts Auction / Updates auction information according to server update.
	private void handleAuctionUpdate(JSONObject auctionObject) throws JSONException {

		int player_selling = auctionObject.getInt("player_selling");
		int player_buying = auctionObject.getInt("player_buying");
		int highest_bid = auctionObject.getInt("price");
		int position = auctionObject.getInt("location");

		Auction auct = new Auction(player_selling,player_buying,highest_bid,position);
		Game.setAuction(auct);

		infoPane.updateAuctionInfo(auct);

		// Shows Auction Information on Screen
		if(!Game.isAuctionActive()){
			infoPane.addAuctionCircle();
			Game.setAuctionActive(true);
			Game.setAuctionTimer(10);
			Game.getAuction().setTime(10);

			// Auction Countdown Timer
			Timeline timeLine = new Timeline();
			timeLine.setCycleCount(Timeline.INDEFINITE);
			timeLine.getKeyFrames().add(
					new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {

							Game.getAuction().setTime(Game.getAuction().getTime()-1);
							infoPane.updateTimer(Game.getAuction().getTime());

							// Auction End
							if(Game.getAuction().getTime() <= 0){
								timeLine.stop();
								JSONObject output = new JSONObject();
								try {
									output.put("id", 0);
									output.put("action", "auction_over");
									connection.send(output);
								} catch (JSONException e) {
									e.printStackTrace();
								} catch (Exception e) {
									e.printStackTrace();
								}
								infoPane.removeAuctionCircle();
								Game.setAuctionActive(false);
							}
						}
					}
					));
			timeLine.playFromStart();
		}
		else{
			infoPane.updateAuctionInfo(auct);
			Game.setAuctionTimer(10);
			Game.getAuction().setTime(10);
		}
	}

	// Returns To Welcome Screen
	private void onFalseConnection(){
		Platform.runLater(() ->{
			AlertBox.display("Error Connecting","Could not Connect to the Server.", gameStage);

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/resources/view/welcomeScreen.fxml"));
			Parent welcome = null;
			try {
				welcome =  loader.load();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Scene welcomeScene = new Scene(welcome);
			welcomeScene.getStylesheets().addAll(this.getClass().getResource("/client/resources/css/welcome.css").toExternalForm());
			gameStage.setScene(welcomeScene);
		});
	}

	// Called on game exit
	void closeGame() {
		JSONObject output = new JSONObject();
		try {
			output.put("id", 0);
			output.put("action", "end");
			connection.send(output);
		} catch (Exception e) {
			e.printStackTrace();
		}
		connection.gameEnd();
	}

	// End Game -> Links to Game over Screen
	private void showGameOverScreen(int winnerID){
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/resources/view/gameOverScreen.fxml"));
		Parent gameOver = null;

		try {
			gameOver = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Scene gameOverScene = new Scene(gameOver);
		gameOverScene.getStylesheets().addAll(this.getClass().getResource("/client/resources/css/welcome.css").toExternalForm());
		Stage gameOverStage = new Stage();
		gameOverStage.setScene(gameOverScene);

		GameOverScreenController gameOverController = loader.getController();
		gameOverController.setWinner(winnerID);
		gameOverStage.setOnCloseRequest(e -> closeGame());
		gameOverStage.show();
	}
}