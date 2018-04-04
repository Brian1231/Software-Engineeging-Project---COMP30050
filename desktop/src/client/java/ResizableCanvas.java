package client.java;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class ResizableCanvas extends Canvas {


    public ResizableCanvas() {
        // Redraw canvas when size changes.
        widthProperty().addListener(evt -> draw());
        heightProperty().addListener(evt -> draw());
    }

    public void draw() {
        double width =  getWidth();
        double height = getHeight();

        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, width, height);
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
