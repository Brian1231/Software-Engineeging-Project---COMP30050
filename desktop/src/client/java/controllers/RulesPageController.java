package client.java.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class RulesPageController {

    @FXML
    public BorderPane base;
    public Button backButton;

    public void onBackClick(ActionEvent event){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/resources/view/welcomeScreen.fxml"));
        Parent welcome = null;
        try {
            welcome =  loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could not load welcome page");
        }

        Scene welcomeScene = new Scene(welcome);
        welcomeScene.getStylesheets().addAll(this.getClass().getResource("/client/resources/css/welcome.css").toExternalForm());
        Stage welcomeStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        welcomeStage.setScene(welcomeScene);
    }
}
