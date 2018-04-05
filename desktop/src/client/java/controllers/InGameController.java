package client.java.controllers;

import client.java.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class InGameController {

    @FXML
    public BorderPane rootPane;

    public BoardCanvas boardCanvas = new BoardCanvas();
    public PlayerCanvas playerCanvas = new PlayerCanvas();

    // Players
    private ObservableList<String> playerList = FXCollections.observableArrayList();

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

    public void initialize() {
        setUpBoard();
        try {
            showLobbyWindow();
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

    // Player lobby code
    public void showLobbyWindow() throws IOException {
        VBox lobbyRoot = new VBox();
        Button startGameButton = new Button("Start Game");
        ListView<String> playerListView = new ListView<>(playerList);

        lobbyRoot.getChildren().add(playerListView);
        lobbyRoot.getChildren().add(startGameButton);

        Scene lobbyScene = new Scene(lobbyRoot, 400,600);
        lobbyScene.getStylesheets().add("/client/resources/css/lobby.css");

        Stage lobbyStage = new Stage();
        lobbyStage.initStyle(StageStyle.UNDECORATED);
        lobbyStage.initModality(Modality.APPLICATION_MODAL);
        lobbyStage.setScene(lobbyScene);

        startGameButton.setOnAction((ActionEvent e) ->
        {
            try {
                JSONObject output = new JSONObject();
                output.put("id", 0);
                output.put("action", "start");
                connection.send(output);
                lobbyStage.close();
            } catch (JSONException e1) {
                e1.printStackTrace();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
                                    );
        lobbyStage.show();
    }

    // Called whenever a message/JSON is received form the server.
    public void onUpdateReceived(JSONObject update) throws JSONException {
        Platform.runLater(() -> {
            try {
                System.out.println("Current GameState: " + update.toString());

                int playerTurn = update.getInt("player_turn");
                String actionInfo = update.getString("action_info");

                JSONArray playerObjects = update.getJSONArray("players");
                List<Player> plyrs = new ArrayList<>();
                for(int i=0;i<playerObjects.length();i++){
                    int balance = playerObjects.getJSONObject(i).getInt("balance");
                    int id = playerObjects.getJSONObject(i).getInt("id");
                    int position = playerObjects.getJSONObject(i).getInt("position");
                    plyrs.add(new Player(balance,id,position,Color.WHITE));
                }

                // redraw players according to new player positions
                playerCanvas.updatePlayers(plyrs);

                // Update lobby list According to new players
                ArrayList<String> names = new ArrayList<>();
                for(Player p : plyrs){
                    String n = "Player " + p.getId();
                    names.add(n);
                }
                playerList.setAll(names);

            } catch (JSONException e) { e.printStackTrace(); }
        });

        // Extract JSON fields
        // Update player positions
        // Print action information
    }

    public void setUpBoard(){
        StackPane layers = new StackPane();
        layers.getChildren().add(boardCanvas);
        layers.getChildren().add(playerCanvas);

        rootPane.setCenter(layers);
        boardCanvas.widthProperty().bind(rootPane.widthProperty());
        boardCanvas.heightProperty().bind(rootPane.heightProperty());
        playerCanvas.widthProperty().bind(rootPane.widthProperty());
        playerCanvas.heightProperty().bind(rootPane.heightProperty());

        boardCanvas.draw();
        playerCanvas.draw();
    }

}


