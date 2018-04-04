package client.java;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class ResizableCanvas extends Canvas {

    final double PI = 3.14159265359;

    private ArrayList<Player> players = new ArrayList<>();

    public ResizableCanvas() {

        players.add(new Player(2000,0,1, Color.WHITE));
        // Redraw canvas when size changes.
        widthProperty().addListener(evt -> draw());
        heightProperty().addListener(evt -> draw());
    }

    public void draw() {
        double width =  getWidth();
        double height = getHeight();

        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, width, height);

        gc.setFill(Color.BLACK);

        Image background = new Image("/client/resources/images/space.jpg");
        gc.drawImage(background,0,0,width,height);

        PixelWriter pw = gc.getPixelWriter();

        for(double t = -PI; t<PI; t+=0.001){
            double x = (width/3*Math.sqrt(2)*Math.cos(t))/(Math.pow(Math.sin(t),2)+1);
            double y = (width/2.4*Math.sqrt(2)*Math.cos(t)*Math.sin(t))/(Math.pow(Math.sin(t),2)+1);

            int pixel_x =(int) Math.round(x + width/2) ;
            int pixel_y =(int) Math.round(y+ height/2);

            pw.setColor(pixel_x,pixel_y, Color.GOLD);
        }

        gc.setStroke(Color.GOLD);
        int count = 0;
        System.out.println(width/3);
        for(double t = -PI+0.15707963267; t<=PI; t+=0.15707963267){
            count+=1;

            double x = (width/3*Math.sqrt(2)*Math.cos(t))/(Math.pow(Math.sin(t),2)+1);
            double y = (width/2.4*Math.sqrt(2)*Math.cos(t)*Math.sin(t))/(Math.pow(Math.sin(t),2)+1);

            String s = "::" + count;

            gc.setFill(Color.BLACK);
            gc.fillOval(x + (width/2) -width/30,y + (height/2)-width/30, width/15,width/15);
            gc.strokeOval(x + (width/2) -width/30,y + (height/2)-width/30, width/15,width/15);

            gc.setFill(Color.WHITE);
            gc.fillText(s,x + (width/2),y + (height/2));
        }

        System.out.println("Drawing");

        drawPlayers(gc,players,width,height);
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
    @Override
    public boolean isResizable() {
        return true;
    }

    @Override
    public double prefWidth(double height) {
        return getWidth();
    }

    @Override
    public double prefHeight(double width) {
        return getHeight();
    }


}
