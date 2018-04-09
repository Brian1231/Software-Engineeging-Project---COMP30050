package client.java;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

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

        drawPlayers(gc, width, height);
    }

    public void drawPlayers(GraphicsContext g, double width, double height){

        final double STEP = 0.15707963267;

        for(Player player : players){
            int position = player.getPosition();
            double baseOffset = 30;
            double offsetX;
            double offsetY;

            switch(player.getId()){
                case 1:     offsetX = baseOffset;
                            offsetY = baseOffset;
                            break;
                case 2:     offsetX = baseOffset;
                            offsetY = -baseOffset;
                            break;
                case 3:     offsetX = -baseOffset;
                            offsetY = -baseOffset;
                            break;
                case 4:     offsetX = -baseOffset;
                            offsetY = baseOffset;
                            break;
                default:    offsetX = baseOffset;
                            offsetY = baseOffset;
            }

            double t = -PI + STEP*position;

            double x = (width/3*Math.sqrt(2)*Math.cos(t))/(Math.pow(Math.sin(t),2)+1);
            double y = (width/2.4*Math.sqrt(2)*Math.cos(t)*Math.sin(t))/(Math.pow(Math.sin(t),2)+1);

            double playerX = x + (width/2) -10 + offsetX;
            double playerY = y + (height/2)-10 + offsetY;

            g.setFill(player.getColor());
            g.fillOval(playerX,playerY,20,20);

            // Payer ID numbers for testing
            g.setStroke(Color.RED);
            g.strokeText(Integer.toString(player.getId()),playerX+5,playerY+15);
        }
    }

    public void updatePlayers(List<Player> plyrs){
        for(Player p : plyrs){
            if(!players.contains(p)) {
                addPlayer(p);
            }
            else{
                updatePlayerData(p);
            }
        }
        draw();
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

    public void removePlayer(Player player){
        if(players.contains(player)){
            players.remove(player);
        }
    }
}
