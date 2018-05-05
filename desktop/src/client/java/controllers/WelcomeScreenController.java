package client.java.controllers;

import client.java.network.NetworkConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.json.JSONException;

import java.io.IOException;


public class WelcomeScreenController {

    public BorderPane base;
    public Button newGameButton;
    public Button rulesButton;

    // New game
    public void onNewGameClick(ActionEvent event){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/resources/view/inGame.fxml"));
        Parent inGame = null;

        try {
            inGame = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could load fxml");
        }

        Scene gameScene = new Scene(inGame);
        gameScene.getStylesheets().addAll(this.getClass().getResource("/client/resources/css/game.css").toExternalForm());
        Stage gameStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        gameStage.setScene(gameScene);

        InGameController gameController = loader.getController();
        gameStage.setOnCloseRequest(e -> gameController.closeGame());
        gameController.setGameStage(gameStage);
        //gameStage.setFullScreen(true);
        gameStage.setMaximized(true);
        gameStage.show();
    }

    // Open Rules Page
    public void onRulesClick(ActionEvent event){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/resources/view/rulesPage.fxml"));
        Parent rules = null;
        try {
            rules =  loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could not load rules page");
        }

        Scene rulesScene = new Scene(rules);
        rulesScene.getStylesheets().addAll(this.getClass().getResource("/client/resources/css/welcome.css").toExternalForm());
        Stage welcomeStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        welcomeStage.setScene(rulesScene);
    }

}
