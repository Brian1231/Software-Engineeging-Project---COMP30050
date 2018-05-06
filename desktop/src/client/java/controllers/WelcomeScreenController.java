package client.java.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/*
    This Class Handles all activity while in the initial welcome screen of the game.
    Links to the Game screen.
    Links to the rule Page.
 */

public class WelcomeScreenController {

    @FXML
    public BorderPane base;
    public Button newGameButton;
    public Button rulesButton;
    public Button localGameButton;
    public VBox layout;

    private String ip = "52.48.249.220";

    // New game
    public void onNewGameClick(ActionEvent event){
        linkToGameStage(event);
    }

    // Links to Game stage with a specified server ip address
    public void onLocalGameClick(ActionEvent event){
        Label ipLabel = new Label("Server IP Address: ");
        ipLabel.setId("input");
        Button submitIpButton = new Button("Submit");
        Button cancelButton = new Button("cancel");
        TextField textField = new TextField ();
        textField.setMaxWidth(200);

        layout.getChildren().removeAll(localGameButton, newGameButton, rulesButton);
        layout.getChildren().addAll(ipLabel,textField,submitIpButton, cancelButton);

        cancelButton.setOnAction(e -> {
            layout.getChildren().removeAll(ipLabel,textField,submitIpButton,cancelButton);
            layout.getChildren().addAll(localGameButton, newGameButton, rulesButton);
        });

        submitIpButton.setOnAction(e -> {
            if(!textField.getText().equals("")){
                this.ip = textField.getText();
                linkToGameStage(e);
            }
        });
    }

    private void linkToGameStage(ActionEvent event){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/resources/view/inGame.fxml"));
        Parent inGame = null;

        try {
            inGame = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could not load fxml");
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
        gameController.setServerIP(ip);
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
