package client.java;


import java.util.ArrayList;

import client.java.controllers.InGameController;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.TextArea;

import javafx.scene.effect.Glow;
import javafx.scene.effect.Shadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.*;

import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;


public class InformationPane extends Pane {

	private ImageView logo = new ImageView();
    private Circle eventLogger = new Circle();
    //private TextArea feed = new TextArea();
    private TextFlow newsfeed = new TextFlow();
    private ArrayList<Text> messages = new ArrayList<Text>();

    private Circle tileInfo = new Circle();
    private Label tileName = new Label("");
    private Label tileCost = new Label("");
    private Label tileOwner = new Label("");
    private Label tileRent = new Label("");
    private ImageView tileImage = new ImageView();

    private Label mortgaged = new Label("MORTGAGED");
    private Rectangle mortRect = new Rectangle();

    Rectangle diceLeft = new Rectangle();
    Rectangle diceRight = new Rectangle();
    private ArrayList<Image> diceFaces = new ArrayList<>();

    public BorderPane playerInfoLayout = new BorderPane();
    HBox top = new HBox();
    HBox bottom = new HBox();
    Region spacing1 = new Region();
    Region spacing2 = new Region();

    public InformationPane() {
        //Title
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
		/*feed.prefWidthProperty().bind(eventLogger.radiusProperty().multiply(2));
		feed.prefHeightProperty().bind(eventLogger.radiusProperty().multiply(2));
		feed.layoutXProperty().bind(eventLogger.layoutXProperty().subtract(eventLogger.radiusProperty()));
		feed.layoutYProperty().bind(heightProperty().divide(2).subtract(feed.prefHeightProperty().divide(2)));
		feed.setEditable(false);
		feed.setWrapText(true);
		feed.appendText("Welcome to Interdimensional Panopoly!\n");
		feed.appendText("Press the start button when all players have joined.\n");
		getChildren().add(feed);*/

        newsfeed.prefWidthProperty().bind(eventLogger.radiusProperty().multiply(2));
        newsfeed.prefHeightProperty().bind(eventLogger.radiusProperty().multiply(2));
		newsfeed.layoutXProperty().bind(eventLogger.layoutXProperty().subtract(eventLogger.radiusProperty()));
		newsfeed.layoutYProperty().bind(heightProperty().divide(2).subtract(newsfeed.prefHeightProperty().divide(2)));
		getChildren().add(newsfeed);
		
        //t1.setStyle("-fx-fill: #4F8A10;-fx-font-weight:bold;");
        
		Text welcome = new Text("Welcome to Interdimensional Panopoly!\n");
		
		welcome.setStyle("-fx-fill: rgb(254, 254, 254);");
		messages.add(welcome);
		newsfeed.getChildren().add(messages.get(0));
		
		
		
        Glow g = new Glow(10);
        //Shadow s = new Shadow(3, Color.RED);

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

        // Mortgaged banner.
//        mortgaged.setFont(Font.font("Verdana",50));
//        mortgaged.setTextFill(Color.RED);
//        mortgaged.layoutXProperty().bind(tileInfo.layoutXProperty().subtract(mortgaged.widthProperty().divide(2)));
//        mortgaged.layoutYProperty().bind(tileInfo.layoutYProperty().add(tileInfo.radiusProperty().divide(3)));
//        mortgaged.setRotate(-45);

        //Dice
        loadDiceImages();

        diceLeft.widthProperty().bind(widthProperty().divide(20));
        diceLeft.heightProperty().bind(widthProperty().divide(20));

        diceRight.widthProperty().bind(widthProperty().divide(20));
        diceRight.heightProperty().bind(widthProperty().divide(20));

        diceLeft.layoutXProperty().bind(widthProperty().divide(2).subtract(diceLeft.widthProperty().multiply(3).divide(2)));
        diceLeft.layoutYProperty().bind(heightProperty().subtract(heightProperty().divide(4.5)));

        diceRight.layoutXProperty().bind(widthProperty().divide(2).add(diceRight.widthProperty().divide(2)));
        diceRight.layoutYProperty().bind(heightProperty().subtract(heightProperty().divide(4.5)));

        diceLeft.setFill(Color.WHITE);
        diceRight.setFill(Color.WHITE);

        getChildren().add(diceLeft);
        getChildren().add(diceRight);
    }

	public void updateFeed(String s) {
		//Set color of text in infoPane to player color

		Color c = Color.WHITE;
		for(Player p : Game.players) if(p.getId() == Game.playerTurn) c = p.getColor();
	
		Text newText = new Text(s + "\n");
		int r = (int)(c.getRed()*254);
		int g = (int)(c.getGreen()*254);
		int b = (int)(c.getBlue()*254);
		newText.setStyle("-fx-fill: rgb("+r+", "+g+", "+b+");");
		messages.add(newText);
		if(messages.size()>7) messages.remove(0);
		
		newsfeed.getChildren().clear();
		for (Text m : messages) newsfeed.getChildren().add(m);
		//newsfeed.appendText(s + "\n");
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
        tileInfo.setFill(loc.getColour());

        /*
        if(!getChildren().contains(mortgaged) && loc.isMortgaged()){
            getChildren().add(mortgaged);
        }

        if(getChildren().contains(mortgaged) && !loc.isMortgaged()){
            getChildren().remove(mortgaged);
        }
        */

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

    public void updateDice(int dice1, int dice2){
        diceLeft.setFill(new ImagePattern(diceFaces.get(dice1-1)));
        diceRight.setFill(new ImagePattern(diceFaces.get(dice2-1)));
    }

    private void loadDiceImages(){
        for(int i = 1; i<=6; i++){
            StringBuilder sb = new StringBuilder();
            sb.append("/client/resources/images/dice/dice");
            sb.append(i);
            sb.append(".png");
            try{
                Image image = new Image(sb.toString());
                diceFaces.add(image);
            }catch(Exception e){
                System.out.println("Failed to find dice image");
                e.printStackTrace();
            }
        }
    }
}
