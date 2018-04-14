package client.java.controllers;

import client.java.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InGameController {

    private Boolean gameStarted = false;
    @FXML
    public BorderPane rootPane;
    // Stacks each layer on top of each other.
    private StackPane layers = new StackPane();
    // 4 board layers.
    private BoardCanvas boardCanvas = new BoardCanvas();
    private PlayerCanvas playerCanvas = new PlayerCanvas();
    private InformationPane infoPane = new InformationPane();

    // Players
    private int playerTurn;

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
                System.out.println("change detected");
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
        try {
            connection.startConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeGame() {
        connection.gameEnd();
    }

    // Called whenever a message/JSON is received form the server.
    public void onUpdateReceived(JSONObject update) throws JSONException {
        Platform.runLater(() -> {
            try {
                System.out.println("Current GameState: " + update.toString());

                playerTurn = update.getInt("player_turn");

                String actionInfo = update.getString("action_info");

                // Redraw players according to new player positions
                List<Player> plyrs = new ArrayList<>();
                if(update.has("players")){
                    JSONArray playerObjects = update.getJSONArray("players");

                    for(int i=0;i<playerObjects.length();i++){
                        int b = playerObjects.getJSONObject(i).getInt("balance");
                        String balance = Integer.toString(b);
                        int id = playerObjects.getJSONObject(i).getInt("id");
                        int position = playerObjects.getJSONObject(i).getInt("position");
                        String character = playerObjects.getJSONObject(i).getString("character");
                        int fuel = playerObjects.getJSONObject(i).getInt("fuel");
                        plyrs.add(new Player(balance,id,position,Color.WHITE,character,fuel));
                    }
                    Game.updatePlayers(plyrs);
                    playerCanvas.draw();
                }

                // Redraw locations according to new Location information.
                List<Location> locs = new ArrayList<>();
                if(update.has("locations")){
                    JSONArray locationObjects = update.getJSONArray("locations");

                    for(int i=0;i<locationObjects.length();i++){
                        String id = locationObjects.getJSONObject(i).getString("id");
                        int price = locationObjects.getJSONObject(i).getInt("price");
                        int position = locationObjects.getJSONObject(i).getInt("location");
                        int owner = locationObjects.getJSONObject(i).getInt("owner");
                        String c = locationObjects.getJSONObject(i).getString("color");
                        Color color = (Color) Color.class.getField(c).get(null);
                        boolean isMortgaged = locationObjects.getJSONObject(i).getBoolean("is_mortgaged");
                        locs.add(new Location(id,position,price,0,owner, color, isMortgaged));
                    }
                    Game.updateLocations(locs);

                    boardCanvas.draw();
                    BoardCanvas.locationsSetProperty.setValue(true);
                }

                infoPane.updateFeed(actionInfo);

                if(gameStarted == true){
                	int playerPos = 0;
                	for(Player p : Game.players){
                		if(p.getId() == playerTurn) playerPos = p.getPosition();
                	}
                    Location locToDisplay = Game.getLocation(playerPos);
                    infoPane.updateLocationInfo(locToDisplay);
                }


            } catch (JSONException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) { e.printStackTrace(); } catch (IOException e) {
				e.printStackTrace();
			}
        });
    }

    public void setUpBoard() throws IOException, JSONException{

        Pane boardWrapper = new Pane();
        boardWrapper.getChildren().add(boardCanvas);
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
                        gameStarted = true;
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }

        );

        infoPane.getChildren().add(startButton);
        layers.getChildren().add(boardWrapper);

        layers.getChildren().add(playerCanvas);
        layers.getChildren().add(infoPane);

        rootPane.setCenter(layers);
        boardCanvas.widthProperty().bind(rootPane.widthProperty());
        boardCanvas.heightProperty().bind(rootPane.heightProperty());
        playerCanvas.widthProperty().bind(rootPane.widthProperty());
        playerCanvas.heightProperty().bind(rootPane.heightProperty());

        boardCanvas.draw();
        playerCanvas.draw();
    }
}


