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
    private int winner = 0;

    public void initialize(){
        gameOverLabel.setTextFill(Color.rgb(232, 142, 39));
        gameOverLabel.setText("GAME OVER");
        gameOverLabel.setFont(Font.font("Verdana", 70) );
        gameOverLabel.setEffect(new Glow(.9));

        layout.setAlignment(Pos.CENTER);
        layout.getChildren().add(gameOverLabel);
        base.setCenter(layout);
    }

    // Sets Winner and calls display
    void setWinner(int playerId){
        this.winner = playerId;
        showGameOverInfo();
    }

    // Displays Winner and losers
    private void showGameOverInfo(){
        if(winner != 0){
            for(Player p :Game.bankruptPlayers){
                Label pLabel = new Label();
                pLabel.setText("Player " + p.getId() + ": " + p.getCharacter() + " lost.");
                pLabel.setTextFill(Color.rgb(232, 142, 39));
                pLabel.setFont(Font.font("Verdana", 20));
                layout.getChildren().add(pLabel);
            }

            Label winnerLabel = new Label();
            Player winnerP = Game.getPlayer(winner);
            if(winnerP != null){
                winnerLabel.setText("Player " + winnerP.getId() + ": " + winnerP.getCharacter() + "won!" +  " Capital: " + winnerP.getBalance());
                winnerLabel.setTextFill(Color.rgb(232, 142, 39));
                winnerLabel.setFont(Font.font("Verdana", 25));
                layout.getChildren().add(winnerLabel);
            }
        }
        else{
            if(!Game.bankruptPlayers.isEmpty()) {
                for (Player p : Game.observablePlayers) {
                    Label pLabel = new Label();
                    pLabel.setText("Player " + p.getId() + ": " + p.getCharacter() + " won!");
                    pLabel.setTextFill(Color.rgb(232, 142, 39));
                    pLabel.setFont(Font.font("Verdana", 20));
                    layout.getChildren().add(pLabel);
                }
            }
            if(!Game.bankruptPlayers.isEmpty()){
                for(Player p : Game.bankruptPlayers){
                    Label pLabel = new Label();
                    pLabel.setText("Player " + p.getId() + ": " + p.getCharacter() + " lost!");
                    pLabel.setTextFill(Color.rgb(232, 142, 39));
                    pLabel.setFont(Font.font("Verdana", 20));
                    layout.getChildren().add(pLabel);
                }
            }
        }
    }

}
