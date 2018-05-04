package client.java.controllers;

import client.java.gameObjects.Location;
import client.java.gameObjects.Player;
import client.java.gui.*;
import client.java.main.Game;
import client.java.network.NetworkConnection;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;

import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

	private boolean imagesPlaced = false;

	// Networking.
	private final static String IP = "52.48.249.220";
	private final static int PORT = 8000;
	private NetworkConnection connection = new NetworkConnection(IP,PORT, input -> {
		try {
			onUpdateReceived(input);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	});

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
		// Testing
//		Player p1 = new Player("1500", 1, 0, Color.WHITE, "SuperMan", 1, true);
//		Player p2 = new Player("2000", 2, 4, Color.RED, "Batman", 2, true);
//		ArrayList<Player> ps = new ArrayList<>();
//		ps.add(p1);
//		ps.add(p2);
//		Game.updatePlayers(ps,"");
		try {
			connection.startConnection();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

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

	public Stage getGameStage() {
		return gameStage;
	}

	public void setGameStage(Stage gameStage) {
		this.gameStage = gameStage;
	}

	// Called whenever a message/JSON is received form the server.
	private void onUpdateReceived(JSONObject update) throws JSONException {
		// If desktop failed to connect to the server
		if(update.has("f")){
			onFalseConnection();
		}
		else{
			Platform.runLater(() -> {
				try {

					System.out.println("Current GameState: " + update.toString());

					Game.playerTurn = update.getInt("player_turn");
					infoPane.updatePlayerTurn(Game.playerTurn);
					String actionInfo = update.getString("action_info");

					// Redraw players according to new player positions
					List<Player> plyrs = new ArrayList<>();
					if(update.has("players")){
						JSONArray playerObjects = update.getJSONArray("players");

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

						JSONObject villains = update.getJSONObject("villain_gang");
						Game.updateVillains(villains.getInt("position"), villains.getBoolean("is_active"));

						// Update Dice
						JSONArray dice = update.getJSONArray("dice_values");
						int dice1 = dice.getInt(0);
						int dice2 = dice.getInt(1);
						infoPane.updateDice(dice1, dice2);
					}

					// Redraw locations according to new Location information.
					List<Location> locs = new ArrayList<>();
					if(update.has("locations")){
						JSONArray locationObjects = update.getJSONArray("locations");

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
							locs.add(new Location(id,position,price,rent,owner, color, isMortgaged, houses));
						}
						Game.updateLocations(locs);
						Game.locationsSet = true;
						if(!imagesPlaced){
							boardCanvas.getImages();
						}
						boardCanvas.removeTileTitles();
						boardCanvas.draw();
						imagesPlaced = true;
					}

					infoPane.updateFeed(actionInfo);

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

	private void onFalseConnection(){
		Platform.runLater(() ->{
			AlertBox.display("Error Connecting","Could not Connect to the Server.");

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

	private void setUpBoard() throws IOException, JSONException{
		Pane boardWrapper = new Pane();
		boardWrapper.getChildren().add(boardCanvas);

		Pane playerWrapper = new Pane();
		playerWrapper.getChildren().add(playerCanvas);

		// Start game button
		Button startButton = new Button("START GAME");
		startButton.layoutXProperty().bind(boardWrapper.widthProperty().divide(2).subtract(startButton.widthProperty().divide(2)));
		startButton.layoutYProperty().bind(boardWrapper.heightProperty().subtract(80));
		startButton.setOnAction(e -> {
			try {
				JSONObject output = new JSONObject();
				output.put("id", 0);
				output.put("action", "start");
				connection.send(output);
				infoPane.removeLogo();
				Game.gameStarted = true;
				startButton.setText("End Game");
				startButton.setOnAction(e2 -> {
					boolean answer = ConfirmBox.display("Are you sure?", "Are you sure that you want to quit the game?");
					if (answer) {
						showGameOverScreen();
					}
				});
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		);

		infoPane.getChildren().add(startButton);
		layers.getChildren().add(boardWrapper);
		layers.getChildren().add(playerWrapper);
		layers.getChildren().add(infoPane);
		rootPane.setCenter(layers);
		boardCanvas.widthProperty().bind(rootPane.widthProperty());
		boardCanvas.heightProperty().bind(rootPane.heightProperty());
		playerCanvas.widthProperty().bind(rootPane.widthProperty());
		playerCanvas.heightProperty().bind(rootPane.heightProperty());

		boardCanvas.draw();
		playerCanvas.draw();
	}

	private void showGameOverScreen(){
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

		//InGameController gameController = loader.getController();
		gameOverStage.setOnCloseRequest(e -> closeGame());
		gameOverStage.show();
	}
}