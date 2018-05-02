package client.java.gui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.io.IOException;

import org.json.JSONException;

import javafx.geometry.Point2D;

public class ResizableCanvas extends Canvas {

    final double PI = 3.14159265359;
    double step = 2*PI/40;

    ResizableCanvas() {
        // Redraw canvas when size changes.
        widthProperty().addListener(evt -> {
			try {
				draw();
			} catch (IOException | JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        });
        heightProperty().addListener(evt -> {
			try {
				draw();
			} catch (IOException | JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
    }

    public void draw() throws IOException, JSONException {
        double width =  getWidth();
        double height = getHeight();

        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, width, height);
    }

    // Function to calculate a point on the infinity shape (Lemniscate of Bernoulli)
    public Point2D lemniscate(double t){
        double x = (xAmplitude()*Math.sqrt(2)*Math.cos(t))/(Math.pow(Math.sin(t),2)+1);
        double y = (yAmplitude()*Math.sqrt(2)*Math.cos(t)*Math.sin(t))/(Math.pow(Math.sin(t),2)+1);

        return new Point2D(x,y);
    }

    // Calculates curve proportions based on window size.
    private double xAmplitude(){
        return getWidth()/3.1;
    }

    private double yAmplitude(){
        return getHeight()/1.3;
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
