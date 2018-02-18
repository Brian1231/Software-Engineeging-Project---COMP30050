package client;

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
    public Button gamebutton;


    public void onButtonClick(ActionEvent event) throws Exception{

        System.out.println("New Game");
        Parent inGame = FXMLLoader.load(getClass().getResource("inGame.fxml"));
        Scene gameScene = new Scene(inGame);
        Stage gameStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        gameStage.setScene(gameScene);
        gameStage.show();

    }

}
