package client.java;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class PlayerCanvas extends ResizableCanvas {

    final double PI = 3.14159265359;

    // test code
    Player player1 = new Player(2000, 1, 30, Color.WHITE);
    Player player2 = new Player(2000, 2, 13, Color.RED);

    ArrayList<Player> players = new ArrayList<>();

    public PlayerCanvas() {
        //test Code
        players.add(player1);
        players.add(player2);
        // Redraw canvas when size changes.
        widthProperty().addListener(evt -> draw());
        heightProperty().addListener(evt -> draw());
    }

    public void draw(){
        double width =  getWidth();
        double height = getHeight();

        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, width, height);

        drawPlayers(gc, players, width, height );
    }

    public void drawPlayers(GraphicsContext g, ArrayList<Player> players, double width, double height){

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

    }
}
