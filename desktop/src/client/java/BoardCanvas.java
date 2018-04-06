package client.java;

import client.java.ResizableCanvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;


public class BoardCanvas extends ResizableCanvas {

    final double PI = 3.14159265359;

    public BoardCanvas() {
        // Redraw canvas when size changes
        widthProperty().addListener(evt -> draw());
        heightProperty().addListener(evt -> draw());
    }

    public void draw(){
        double width =  getWidth();
        double height = getHeight();

        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, width, height);

        Image background = new Image("/client/resources/images/space.jpg");
        gc.drawImage(background,0,0,width,height);

        drawInfinityShape(gc,width,height);
        drawPropertyTiles(gc,width,height);
    }


    public void drawInfinityShape(GraphicsContext g, double width,double height){
        PixelWriter pw = g.getPixelWriter();

        for(double t = -PI; t<PI; t+=0.001){
            double x = (width/3*Math.sqrt(2)*Math.cos(t))/(Math.pow(Math.sin(t),2)+1);
            double y = (width/2.4*Math.sqrt(2)*Math.cos(t)*Math.sin(t))/(Math.pow(Math.sin(t),2)+1);

            int pixel_x =(int) Math.round(x + width/2) ;
            int pixel_y =(int) Math.round(y+ height/2);

            pw.setColor(pixel_x,pixel_y, Color.GOLD);
        }
    }

    public void drawPropertyTiles(GraphicsContext g, double width,double height){
        int count = 0;
        for(double t = -PI+0.15707963267; t<=PI; t+=0.15707963267){
            count+=1;

            double x = (width/3*Math.sqrt(2)*Math.cos(t))/(Math.pow(Math.sin(t),2)+1);
            double y = (width/2.4*Math.sqrt(2)*Math.cos(t)*Math.sin(t))/(Math.pow(Math.sin(t),2)+1);

            String s = "#" + count;

            g.setFill(Color.BLACK);
            g.setStroke(Color.GOLD);
            g.fillOval(x + (width/2) -width/30,y + (height/2)-width/30, width/15,width/15);
            g.strokeOval(x + (width/2) -width/30,y + (height/2)-width/30, width/15,width/15);

            g.setFill(Color.WHITE);
            g.fillText(s,x + (width/2),y + (height/2));
        }
    }
}
