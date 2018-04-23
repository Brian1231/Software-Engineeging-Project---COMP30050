package client.java;


import javafx.scene.control.TextArea;

import javafx.scene.effect.Glow;
import javafx.scene.effect.Shadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.*;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.control.Label;


public class InformationPane extends Pane {

    //private Label title = new Label("Panopoly");
	private ImageView logo = new ImageView();
    private Circle eventLogger = new Circle();
    private TextArea feed = new TextArea();

    private Circle tileInfo = new Circle();
    private Label tileName = new Label("");
    private Label tileCost = new Label("");
    private Label tileOwner = new Label("");
    private Label tileRent = new Label("");
    private ImageView tileImage = new ImageView();

    public BorderPane playerInfoLayout = new BorderPane();
    HBox top = new HBox();
    HBox bottom = new HBox();
    Region spacing1 = new Region();
    Region spacing2 = new Region();

    public InformationPane() {
        //Title
       /* title.setTextFill(Color.rgb(232, 142, 39));
        title.setStyle("-fx-font-size: 50px;");
        title.layoutXProperty().bind(widthProperty().divide(2).subtract(title.widthProperty().divide(2)));
        getChildren().add(title);*/
    	logo.fitWidthProperty().bind(this.widthProperty().divide(2.5));
    	logo.fitHeightProperty().bind(this.heightProperty().divide(5));
    	logo.layoutXProperty().bind(widthProperty().divide(2).subtract(logo.fitWidthProperty().divide(2)));
    	//logo.setEffect(new Glow(5));
    	logo.setImage(new Image("/client/resources/images/InterDimLogo.png"));
    	getChildren().add(logo);

        // Circle text Area (currently invisible)
        eventLogger.setFill(Color.rgb(60, 67, 79, 0));
        eventLogger.setStroke(Color.rgb(119, 137, 165, 0));
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

        Glow g = new Glow(10);
        Shadow s = new Shadow(3, Color.RED);

		// Current players location info
		tileInfo.layoutXProperty().bind(widthProperty().subtract(widthProperty().divide(4.2)));
		tileInfo.layoutYProperty().bind(heightProperty().divide(2));
		tileInfo.setFill(Color.BLACK);
		tileInfo.setStroke(Color.GOLD);
		tileInfo.radiusProperty().bind(widthProperty().divide(9));
		tileInfo.setEffect(g);
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
        // Rent
        tileRent.layoutXProperty().bind(tileInfo.layoutXProperty().subtract(tileRent.widthProperty().divide(2)));
        tileRent.layoutYProperty().bind(tileInfo.layoutYProperty().add(tileInfo.radiusProperty().divide(2.4)));
        tileRent.setTextFill(Color.WHITE);
        getChildren().add(tileRent);
        // Owner
        tileOwner.layoutXProperty().bind(tileInfo.layoutXProperty().subtract(tileOwner.widthProperty().divide(2)));
        tileOwner.layoutYProperty().bind(tileInfo.layoutYProperty().add(tileInfo.radiusProperty().divide(3)));
        tileOwner.setTextFill(Color.WHITE);
        getChildren().add(tileOwner);
		// Tile Image
        tileImage.layoutXProperty().bind(tileInfo.layoutXProperty().subtract(tileImage.fitWidthProperty().divide(2)));
        tileImage.layoutYProperty().bind(tileInfo.layoutYProperty().subtract(tileImage.fitHeightProperty().divide(2)));
        tileImage.fitWidthProperty().bind(tileInfo.radiusProperty().multiply(1.8));
        tileImage.fitHeightProperty().bind(tileInfo.radiusProperty().divide(2));
        tileImage.setImage(new Image("/client/resources/images/InterDimLogo.png"));
        getChildren().add(tileImage);

        // Player stats in 4 corners
        playerInfoLayout.prefWidthProperty().bind(this.widthProperty());
        playerInfoLayout.prefHeightProperty().bind(this.heightProperty());
        playerInfoLayout.setTop(top);
        playerInfoLayout.setBottom(bottom);
        top.setMaxHeight(200);
        getChildren().add(playerInfoLayout);
    }

	public void updateFeed(String s) {
		//Set color of text in infoPane to player color


		/*Color c = Color.BLUE;
		if(InGameController.playerTurn > 100){
			for(Player p : Game.players) if(p.getId() == InGameController.playerTurn) c = p.getColor();
			switch(c.){
			case Color.Green;
			}
			feed.setStyle("-fx-text-fill: green;");
		}*/
		feed.appendText(s + "\n");
	}

	public void updateLocationInfo(Location loc) {
		tileName.setText(loc.getName());
		tileInfo.setStroke(loc.getColour());
		tileCost.setText("Price: $" + Integer.toString(loc.getPrice()));
		tileImage.setImage(loc.getImage());
		if(loc.getOwnerID()==0)
			tileOwner.setText("Owner: " + " Unowned");
		else
			tileOwner.setText("Owner: " + " Player " + loc.getOwnerID() +  ": " + Game.getPlayer(loc.getOwnerID()).getCharacter());
		tileRent.setText("Rent: " + loc.getRent());
	}

    public void addPlayerInfo(Player player){
       VBox stats = player.stats;

        switch(player.getId()){
            case 1:     top.getChildren().add(stats);
                        break;
            case 2:     HBox.setHgrow(spacing1,Priority.ALWAYS);
                        top.getChildren().addAll(spacing1,stats);
                        break;
            case 3:     bottom.getChildren().add(stats);
                        break;
            case 4:     HBox.setHgrow(spacing2,Priority.ALWAYS);
                        bottom.getChildren().addAll(spacing2,stats);
                        break;
            default:    break;
        }
    }

    public void removePlayerInfo(Player player) {
        switch(player.getId()){
            case 1:     top.getChildren().remove(player.stats);
                        break;
            case 2:     top.getChildren().removeAll(spacing1,player.stats);
                        break;
            case 3:     bottom.getChildren().remove(player.stats);
                        break;
            case 4:     bottom.getChildren().removeAll(spacing2,player.stats);
                        break;
            default:    break;
        }
    }
}
