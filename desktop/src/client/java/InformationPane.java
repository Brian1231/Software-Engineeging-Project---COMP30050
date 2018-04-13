package client.java;

import com.sun.org.apache.xalan.internal.utils.XMLSecurityPropertyManager;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.control.Label;

import java.util.ArrayList;


public class InformationPane extends Pane {

    private Label title = new Label("Panopoly");
    private Circle eventLogger = new Circle();
    private TextArea feed = new TextArea();
    private Circle tileInfo  = new Circle();
    private Label tileName = new Label("Name of current Tile");
    private Label tileCost = new Label("$200");

    ArrayList<Label> playerNameLabels = new ArrayList();
    ArrayList<ProgressBar> playerFuelBars = new ArrayList();
    ArrayList<Label> playerBalanceLabels = new ArrayList<>();

    public InformationPane(){
        //Title
        title.setTextFill(Color.rgb(232, 142, 39));
        title.setStyle("-fx-font-size: 50px;");
        title.layoutXProperty().bind(widthProperty().divide(2).subtract(title.widthProperty().divide(2)));
        getChildren().add(title);

        // Circle text Area (currently invisible)
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

        // Current players location info
        tileInfo.layoutXProperty().bind(widthProperty().subtract(widthProperty().divide(4.2)));
        tileInfo.layoutYProperty().bind(heightProperty().divide(2));
        tileInfo.setFill(Color.BLACK);
        tileInfo.setStroke(Color.GOLD);
        tileInfo.radiusProperty().bind(widthProperty().divide(9));
        getChildren().add(tileInfo);
            // Tile name
        tileName.layoutXProperty().bind(tileInfo.layoutXProperty().subtract(tileName.widthProperty().divide(2)));
        tileName.layoutYProperty().bind(tileInfo.layoutYProperty().subtract(tileInfo.radiusProperty().divide(2)));
        tileName.setTextFill(Color.WHITE);
        getChildren().add(tileName);
            // Tile Cost
        tileCost.layoutXProperty().bind(tileInfo.layoutXProperty().subtract(tileCost.widthProperty().divide(2)));
        tileCost.layoutYProperty().bind(tileInfo.layoutYProperty().add(tileInfo.radiusProperty().divide(2)));
        tileCost.setTextFill(Color.WHITE);
        getChildren().add(tileCost);


    }

    public void updateFeed(String s){
        feed.appendText(s + "\n");
    }

    public void updateLocationInfo(Location loc){
        tileName.setText(loc.getName());
        tileInfo.setStroke(loc.getColour());
        tileCost.setText("$" + Integer.toString(loc.getPrice()));
    }


    public void updatePlayerInfo(){

    }

}
