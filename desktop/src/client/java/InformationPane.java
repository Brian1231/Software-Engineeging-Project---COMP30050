package client.java;

import javafx.beans.property.DoubleProperty;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.effect.Shadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.awt.*;
import java.util.ArrayList;

public class InformationPane extends Pane {

    ArrayList<Player> players = new ArrayList<>();
    Circle eventLogger = new Circle();
    TextArea feed = new TextArea();

    public InformationPane(){

        // Effects
        //Shadow s = new Shadow(5,Color.ORANGE);
        //DropShadow ds = new DropShadow();
        //ds.setOffsetX(0);
        //ds.setOffsetY(0);
        //ds.setColor(Color.WHITE);

        // Circle
        eventLogger.setFill(Color.rgb(60,67,79,0));
        eventLogger.setStroke(Color.rgb(119,137,165, 0));
        eventLogger.radiusProperty().bind(widthProperty().divide(9));
        eventLogger.layoutXProperty().bind(widthProperty().divide(4.2));
        eventLogger.layoutYProperty().bind(heightProperty().divide(2));
        getChildren().add(eventLogger);

        // TexArea
        feed.prefWidthProperty().bind(eventLogger.radiusProperty().multiply(2));
        feed.prefHeightProperty().bind(eventLogger.radiusProperty().multiply(2));
        feed.layoutXProperty().bind(eventLogger.layoutXProperty().subtract(eventLogger.radiusProperty()));
        feed.layoutYProperty().bind(heightProperty().divide(2).subtract(feed.prefHeightProperty().divide(2)));
        feed.setEditable(false);
        feed.setWrapText(true);

        feed.appendText("Welcome to Interdimensional Panopoly!\n");
        feed.appendText("Press the start button when all players have joined.\n");
        getChildren().add(feed);
    }

    public void updateFeed(String s){
        feed.appendText(s + "\n");
    }


}
