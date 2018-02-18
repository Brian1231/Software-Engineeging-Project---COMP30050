package client.java.controllers;

import client.java.NetworkConnection;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.json.JSONObject;


public class InGameController {

    // Temporary street names.
    // To be replaced by NOC-List
    private String[] SquareNames = {
            "Go","Old Kent Road","Community Chest","Whitechapel Road","Income Tax", "King Cross", "The Angel Islington", "Chance", "Euston Road", "Pentonville Road", "Jail",
            "Pall Mall","Electric Co","Whitehall","Northumberland Ave","Marylebone Station", "Bow St", "Community Chest","Marlborough St","Vine St",
            "Free Parking","Strand","Chance", "Fleet St","Trafalgar Sq","Fenchurch St Station", "Leicester Sq", "Coventry St","Water Works", "Piccadilly","Go To Jail",
            "Regent St","Oxford St","Community Chest","Bond St","Liverpool St Station","Chance","Park Lane","Super Tax","Mayfair"
    };

    // Streets
    public HBox top;
    public HBox bottom;
    public VBox left;
    public VBox right;

    // Networking.
    private final static String IP = "52.48.249.220";
    private final static int PORT = 8080;
    private NetworkConnection connection = new NetworkConnection(IP,PORT, input -> onUpdateReceived(input));


    public void initialize() throws Exception{
        drawProperty();
        connection.startConnection();
    }

    // called whenever a message/JSON is received form the server.
    public void onUpdateReceived(JSONObject update){
        Platform.runLater(() -> System.out.println("message from controller!: " + update.toString()));
        // Extract JSON fields
        // Update player positions
        // Print action information
    }

    // Populates the board with property. Plan to refactor a lot.
    public void drawProperty() {

        for (int i = 0; i < 11; i++) {
            Pane squarePane = new Pane();

            squarePane.setStyle("-fx-background-color: #F2F4F4;" + "-fx-border-color: #34495E;");

            squarePane.setMinSize(50, 75);
            squarePane.setPrefSize(60, 100);

            HBox.setHgrow(squarePane, Priority.ALWAYS);

            Label name = new Label(SquareNames[10 - i]);
            name.setPrefWidth(60);
            name.setWrapText(true);
            name.setStyle("-fx-text-fill: #34495E;");

            name.layoutXProperty().bind(squarePane.widthProperty().subtract(name.widthProperty()).divide(2));

            squarePane.getChildren().add(name);
            bottom.getChildren().add(squarePane);
        }

        for (int i = 0; i < 8; i++) {
            Pane squarePane = new Pane();

            squarePane.setStyle("-fx-background-color: #F2F4F4;" + "-fx-border-color: #34495E;");

            squarePane.setMinSize(75, 50);
            squarePane.setPrefSize(100, 60);

            VBox.setVgrow(squarePane, Priority.ALWAYS);

            Label name = new Label(SquareNames[21 - i]);
            name.setRotate(90.0);
            name.setPrefWidth(60);
            name.setWrapText(true);
            name.setStyle("-fx-text-fill: #34495E;");

            name.layoutYProperty().bind(squarePane.heightProperty().subtract(name.heightProperty()).divide(2));


            squarePane.getChildren().add(name);
            left.getChildren().add(squarePane);
        }
    }

    // Draws players in their current positions.
    public void drawPlayers() {

    }
}


