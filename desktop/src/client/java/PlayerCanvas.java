package client.java;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class PlayerCanvas extends ResizableCanvas {

    final double PI = 3.14159265359;

    ArrayList<Player> players = new ArrayList<>();

    public PlayerCanvas() {
        // Redraw canvas when size changes.
        widthProperty().addListener(evt -> draw());
        heightProperty().addListener(evt -> draw());
    }

    public void draw(){
        double width =  getWidth();
        double height = getHeight();

        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, width, height);

        drawPlayers(gc, width, height );
    }

    public void drawPlayers(GraphicsContext g, double width, double height){

        double r = 0.15707963267;

        for(Player player : players){
            int position = player.getPosition();

            double t = -PI + r*position;

            double x = (width/3*Math.sqrt(2)*Math.cos(t))/(Math.pow(Math.sin(t),2)+1);
            double y = (width/2.4*Math.sqrt(2)*Math.cos(t)*Math.sin(t))/(Math.pow(Math.sin(t),2)+1);

            g.setFill(player.getColor());
            g.fillOval(x + (width/2) -10,y + (height/2)-10, 20,20);
        }
    }

    public void updatePlayers(List<Player> players){
        for(Player p : players){
            if(!players.contains(p)) {
                addPlayer(p);
            }
            else{
                updatePlayerData(p);
            }
        }

    }

    public void addPlayer(Player player){
        players.add(player);
    }

    public void updatePlayerData(Player player){
        if(players.contains(player)){
            int index = players.indexOf(player);
            players.get(index).setBalance(player.getBalance());
            players.get(index).setPosition(player.getPosition());
        }
    }
}
