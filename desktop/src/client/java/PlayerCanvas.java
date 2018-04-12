package client.java;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class PlayerCanvas extends ResizableCanvas {

    ArrayList<Player> players = new ArrayList<>();

    public PlayerCanvas() {
        // Test code
        //players.add(new Player(2000, 1,0,Color.WHITE));
        //players.add(new Player(2000, 2,10,Color.WHITE));
        //players.add(new Player(2000, 3,30,Color.WHITE));
        //players.add(new Player(2000, 4,40,Color.WHITE));

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

    // Draws players at their current location.
    public void drawPlayers(GraphicsContext g, double width, double height){

        for(Player player : players){
            int position = player.getPosition();
            double t = -PI/2 + step*position;

            Point2D point = lemniscate(t);
            Point2D offset = playerOffset(player);

            double playerX = point.getX() + (width/2) -10 + offset.getX();
            double playerY = point.getY() + (height/2)-10 + offset.getY();

            g.setFill(player.getColor());
            g.fillOval(playerX,playerY,20,20);

            // Payer ID numbers for testing
            g.setStroke(Color.RED);
            g.strokeText(Integer.toString(player.getId()),playerX+5,playerY+15);
        }
    }

    // Calculates each players x,y offset so they don't draw on top of each other.
    public Point2D playerOffset(Player player){
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

        return new Point2D(offsetX,offsetY);
    }

    // Updates players from server.
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

    // Adds new player to player list.
    public void addPlayer(Player player){
        players.add(player);
    }

    // Updates player on player list
    public void updatePlayerData(Player player){
        if(players.contains(player)){
            int index = players.indexOf(player);
            players.get(index).setBalance(player.getBalance());
            players.get(index).setPosition(player.getPosition());
        }
    }

    // Removes player from the draw loop. (quits game etc)
    public void removePlayer(Player player){
        if(players.contains(player)){
            players.remove(player);
        }
    }

    public Player getPlayer(int id){
        for(Player player: players){
            if(player.getId() == id){
                return player;
            }
        }
        return null;
    }
}
