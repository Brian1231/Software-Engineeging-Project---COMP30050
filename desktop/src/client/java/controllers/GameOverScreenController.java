package client.java.controllers;

import client.java.main.Game;
import client.java.gameObjects.Player;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameOverScreenController {

    @FXML
    BorderPane base;

    public VBox layout = new VBox();
    private Label gameOverLabel = new Label();

    public void initialize(){
        gameOverLabel.setTextFill(Color.rgb(232, 142, 39));
        gameOverLabel.setText("GAME OVER");
        gameOverLabel.setFont(Font.font("Verdana", 70) );
        gameOverLabel.setEffect(new Glow(.9));

        layout.setAlignment(Pos.CENTER);
        layout.getChildren().add(gameOverLabel);
        base.setCenter(layout);

        // Just prints any player for now
        for(Player p :Game.observablePlayers){
                Label pLabel = new Label();
                pLabel.setText("Player " + p.getId() + ": " + p.getCharacter() + " won!");
                pLabel.setTextFill(Color.rgb(232, 142, 39));
                pLabel.setFont(Font.font("Verdana", 30));
                layout.getChildren().add(pLabel);
                break;
        }
    }

}
