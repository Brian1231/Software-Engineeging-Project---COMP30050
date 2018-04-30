package client.java.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class WelcomeScreenController {

    public BorderPane base;
    public Button newGameButton;
    public Button rulesButton;

    // New game
    public void onNewGameClick(ActionEvent event) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/resources/view/inGame.fxml"));
        Parent inGame =  loader.load();

        Scene gameScene = new Scene(inGame);
        gameScene.getStylesheets().addAll(this.getClass().getResource("/client/resources/css/game.css").toExternalForm());
        Stage gameStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        gameStage.setScene(gameScene);

        InGameController gameController = loader.getController();
        gameStage.setOnCloseRequest(e -> gameController.closeGame());
        //gameStage.setFullScreen(true);
        gameStage.setMaximized(true);
        gameStage.show();
    }

    public void onRulesClick(ActionEvent event){
        
    }

}
