package client.java.gui;


import java.util.ArrayList;

import client.java.gameObjects.Auction;
import client.java.main.Game;
import client.java.gameObjects.Location;
import client.java.gameObjects.Player;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.*;

import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.json.JSONObject;


public class InformationPane extends Pane {

    private Image currency;

    // General
	private ImageView logo = new ImageView();
    private Circle eventLogger = new Circle();
    private TextFlow newsfeed = new TextFlow();
    private ArrayList<Text> messages = new ArrayList<>();

    // Current Tile Info
    private Circle tileInfo = new Circle();
    private Arc infoBackground = new Arc();
    private Label tileType = new Label("");
    private Label tileName = new Label("");
    private Label tileCost = new Label("");
    private Label tileOwner = new Label("");
    private Label mortgaged = new Label("");
    private Label tileRent = new Label("");
    private ImageView tileImage = new ImageView();
    private VBox infoLayout = new VBox(10);
    private VBox upperAuctionLayout = new VBox(10);
    private VBox lowerAuctionLayout = new VBox(10);

    // Dice
    private Rectangle diceLeft = new Rectangle();
    private Rectangle diceRight = new Rectangle();
    private ArrayList<Image> diceFaces = new ArrayList<>();

    // Player Stats in 4 Corners
    private BorderPane playerInfoLayout = new BorderPane();
    private HBox top = new HBox();
    private HBox bottom = new HBox();
    private Region spacing1 = new Region();
    private Region spacing2 = new Region();

    // Auction
    private Circle auctionCircle = new Circle();
    private Label auctionHeading = new Label("AUCTION STARTED");
    private Circle auctionProp = new Circle();
    private Label auctionName = new Label("");
    private Label auctionPrice = new Label("");
    private Label highestBidder = new Label("");
    private Label playerSelling = new Label("");
    private Label timer = new Label("10");
    HBox current_Price = new HBox(3);


    // Initialisation
    public InformationPane() {
        // Load Images
        currency = new Image("/client/resources/images/Schmeckles.png");
        //Title
    	logo.fitWidthProperty().bind(this.widthProperty().divide(2.5));
    	logo.fitHeightProperty().bind(this.heightProperty().divide(5));
    	logo.layoutXProperty().bind(widthProperty().divide(2).subtract(logo.fitWidthProperty().divide(2)));
    	logo.setImage(new Image("/client/resources/images/InterDimLogo.png"));
    	getChildren().add(logo);
        // News Feed
        setupNewsFeed();
		// Tile Info in right loop
		setupTileInfoCircle();
        // Player stats in 4 corners
        setupPlayerStats();
        // Dice
        setupDiceLayout();
        // Auction info setup
        setUpperAuctionLayout();
    }

    private void setupPlayerStats(){
        playerInfoLayout.prefWidthProperty().bind(this.widthProperty());
        playerInfoLayout.prefHeightProperty().bind(this.heightProperty());
        playerInfoLayout.setTop(top);
        playerInfoLayout.setBottom(bottom);
        top.setMaxHeight(200);
        getChildren().add(playerInfoLayout);
    }

    private void setupNewsFeed(){
        // Circle text Area (currently invisible)
        eventLogger.setFill(Color.rgb(60, 67, 79, 0));
        eventLogger.setStroke(Color.rgb(119, 137, 165, 0));
        eventLogger.radiusProperty().bind(widthProperty().divide(9));
        eventLogger.layoutXProperty().bind(widthProperty().divide(4.2));
        eventLogger.layoutYProperty().bind(heightProperty().divide(2));
        getChildren().add(eventLogger);

        newsfeed.prefWidthProperty().bind(eventLogger.radiusProperty().multiply(2));
        newsfeed.prefHeightProperty().bind(eventLogger.radiusProperty().multiply(2));
        newsfeed.layoutXProperty().bind(eventLogger.layoutXProperty().subtract(eventLogger.radiusProperty()));
        newsfeed.layoutYProperty().bind(heightProperty().divide(2).subtract(newsfeed.prefHeightProperty().divide(2)));
        getChildren().add(newsfeed);

        Text welcome = new Text("Welcome to Interdimensional Panopoly!\n");
        welcome.setFont(new Font("Verdana", 18));
        welcome.setStyle("-fx-fill: rgb(254, 254, 254);");
        messages.add(welcome);
        newsfeed.getChildren().add(messages.get(0));
    }

    private void setupTileInfoCircle(){
        // Current players location info
        tileInfo.layoutXProperty().bind(widthProperty().subtract(widthProperty().divide(4.2)));
        tileInfo.layoutYProperty().bind(heightProperty().divide(2));
        tileInfo.setFill(Color.BLACK);
        tileInfo.setStroke(Color.GOLD);
        tileInfo.radiusProperty().bind(widthProperty().divide(9));
        getChildren().add(tileInfo);

        // Semi Circle
        infoBackground.centerXProperty().bind(tileInfo.layoutXProperty());
        infoBackground.centerYProperty().bind(tileInfo.layoutYProperty());
        infoBackground.radiusXProperty().bind(tileInfo.radiusProperty());
        infoBackground.radiusYProperty().bind(tileInfo.radiusProperty());
        infoBackground.setStartAngle(180.0f);
        infoBackground.setLength(180.0f);
        infoBackground.setType(ArcType.ROUND);
        infoBackground.setFill(Color.rgb(255,255,255,0.2));
        getChildren().add(infoBackground);

        // Tile Text Info
        tileType.setId("infolabel");
        tileName.setId("infolabel");
        tileCost.setId("infolabel");
        tileRent.setId("infolabel");
        tileOwner.setId("infolabel");

        // Mortgaged
        mortgaged.setFont(Font.font("Verdana",50));
        mortgaged.setTextFill(Color.RED);

        // Tile labels layout
        infoLayout.layoutXProperty().bind(tileInfo.layoutXProperty().subtract(infoLayout.widthProperty().divide(2)));
        infoLayout.layoutYProperty().bind(tileInfo.layoutYProperty());
        infoLayout.getChildren().addAll(tileType, tileName, tileCost, tileRent, tileOwner, mortgaged);
        infoLayout.setAlignment(Pos.CENTER);
        getChildren().add(infoLayout);

        // Tile Image
        tileImage.layoutXProperty().bind(tileInfo.layoutXProperty().subtract(tileImage.fitWidthProperty().divide(2)));
        tileImage.layoutYProperty().bind(tileInfo.layoutYProperty().subtract(tileImage.fitHeightProperty().divide(2)));
        tileImage.fitWidthProperty().bind(tileInfo.radiusProperty().multiply(1.8));
        tileImage.fitHeightProperty().bind(tileInfo.radiusProperty().divide(2));
        tileImage.setImage(new Image("/client/resources/images/InterDimLogo.png"));
        getChildren().add(tileImage);
    }

    private void setUpperAuctionLayout(){
        // Property Image
        auctionProp.layoutXProperty().bind(auctionCircle.layoutXProperty());
        auctionProp.layoutYProperty().bind(auctionCircle.layoutYProperty());
        auctionProp.radiusProperty().bind(auctionCircle.radiusProperty().divide(3));

        // Upper layout
        upperAuctionLayout.layoutXProperty().bind(auctionCircle.layoutXProperty().subtract(upperAuctionLayout.widthProperty().divide(2)));
        upperAuctionLayout.layoutYProperty().bind(auctionCircle.layoutYProperty().subtract(auctionCircle.radiusProperty()).add(60));
        upperAuctionLayout.getChildren().addAll(auctionHeading, playerSelling);
        upperAuctionLayout.setAlignment(Pos.CENTER);

        auctionHeading.setFont(new Font("Verdana", 30));
        playerSelling.setId("pslabel");

        // Auction countdown Timer
        timer.layoutXProperty().bind(auctionCircle.layoutXProperty().subtract(timer.widthProperty().divide(2)));
        timer.layoutYProperty().bind(auctionCircle.layoutYProperty().subtract(timer.heightProperty().divide(2)));
        timer.setFont(new Font("Verdana", 65));
        timer.setTextFill(Color.ORANGE);

        // Lower Layout
        lowerAuctionLayout.layoutXProperty().bind(auctionCircle.layoutXProperty().subtract(lowerAuctionLayout.widthProperty().divide(2)));
        lowerAuctionLayout.layoutYProperty().bind(auctionCircle.layoutYProperty().add(auctionProp.radiusProperty()));
        lowerAuctionLayout.getChildren().addAll(auctionName,auctionPrice,highestBidder);
        lowerAuctionLayout.setAlignment(Pos.CENTER);

        auctionPrice.setFont(new Font("Verdana", 20));
        auctionCircle.layoutXProperty().bind(tileInfo.layoutXProperty());
        auctionCircle.layoutYProperty().bind(tileInfo.layoutYProperty());
        auctionCircle.setFill(Color.WHITE);
        auctionCircle.setStroke(Color.AQUA);
        auctionCircle.radiusProperty().bind(tileInfo.radiusProperty().add(10));
    }

    // Updates information Feed
	public void updateFeed(String s) {
		// Set color of text in infoPane to player color
		Color c = Color.WHITE;
		for(Player p : Game.players) if(p.getId() == Game.playerTurn) c = p.getColor();
	
		Text newText = new Text(s + "\n");
		int r = (int)(c.getRed()*254);
		int g = (int)(c.getGreen()*254);
		int b = (int)(c.getBlue()*254);
		newText.setStyle("-fx-fill: rgb("+r+", "+g+", "+b+");");
		newText.setFont(new Font("Verdana", 18));
		messages.add(newText);
		if(messages.size()>4) messages.remove(0);
		
		newsfeed.getChildren().clear();
		for(int i=0;i<messages.size();i++){
			Text m = messages.get(i);
			if(i == messages.size()-1 || i == messages.size()-2)
				m.setOpacity(1);
			else
				m.setOpacity(((double)i/(double)messages.size()+0.2));
			newsfeed.getChildren().add(m);
		}
	}

	// Removes "Interdimensional Panopoly" Logo from Tile Info Circle
	public void removeLogo(){
        getChildren().remove(tileImage);
    }

    // Updates current Tile information in Right Infinity Loop
	public void updateLocationInfo(Location loc) {
        tileName.setText(loc.getName());
        tileInfo.setStroke(loc.getColour());

        if(loc.getOwnerID()==0){
            if(loc.getType().equals("investment") || loc.getType().equals("utility") || loc.getType().equals("station")){
                tileOwner.setText("Owner: Intergalactic Banking Assoc");
            }
            else{
                tileOwner.setText("");
            }
        }
        else{
            if(Game.getPlayer(loc.getOwnerID()).getCharacter() != null){
                tileOwner.setText("Owner: " + " Player " + loc.getOwnerID() +  ": " + Game.getPlayer(loc.getOwnerID()).getCharacter());
            }
        }
        if(loc.getRent()>0)
            tileRent.setText("Rent: " + loc.getRent() + " Shm");
        else
            tileRent.setText("");
        if(loc.getPrice()>0)
            tileCost.setText("Price: " + Integer.toString(loc.getPrice()) + " Shm");
        else
            tileCost.setText("");
        tileInfo.setFill(new ImagePattern(loc.getImage()));
        if(loc.isMortgaged()){
            mortgaged.setText("MORTGAGED");
        }
        else{
            mortgaged.setText("");
        }

        Color original = loc.getColour();
        Color faded = Color.color(original.getRed(),original.getGreen(),original.getBlue(), 0.6);
        infoBackground.setFill(faded);

        switch (loc.getType()) {
            case "investment":
                tileType.setText("Investment Property");
                break;
            case "tax":
                tileType.setText("Tax");
                break;
            case "utility":
                tileType.setText("Utility");
                break;
            case "station":
                tileType.setText("Station");
                break;
        }
	}

	// Add Auction Display
	public void addAuctionCircle(){
        getChildren().add(auctionCircle);
        getChildren().add(auctionProp);
        getChildren().add(upperAuctionLayout);
        getChildren().add(lowerAuctionLayout);
        getChildren().add(timer);
    }

    // Update Auction Display from server update
    public void updateAuctionInfo(Auction auction){
        ImagePattern locImg = new ImagePattern(auction.getLocation().getImage());
        Player pSelling = Game.getPlayer(auction.getPlayerSelling());
        if(pSelling != null){
            playerSelling.setText(pSelling.getCharacter() + " is auctioning: ");
        }else{
            playerSelling.setText("Intergalactic Banking Assoc is selling: ");
        }
        auctionProp.setFill(locImg);
        auctionName.setText(auction.getLocation().getName());
        auctionPrice.setText("Current Bid: " + Integer.toString(auction.getCurrentPrice()) + " SHM");
        Player currentHighest = Game.getPlayer(auction.getHighestBidder());
        if(currentHighest != null){
            highestBidder.setText("Highest Bidder: " + currentHighest.getCharacter());
        }
    }

    // Update auction countdown timer
    public void updateTimer(int time){
        timer.setText(Integer.toString(time));
    }

    // Remove Auction display at Auction End
    public void removeAuctionCircle(){
        getChildren().remove(auctionCircle);
        getChildren().remove(auctionProp);
        getChildren().remove(upperAuctionLayout);
        getChildren().remove(lowerAuctionLayout);
        getChildren().remove(timer);

        playerSelling.setText("");
        auctionPrice.setText("");
        highestBidder.setText("");
        auctionName.setText("");
        timer.setText("10");
    }

    // Adds a player's stats in 4 corners of window
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

    // Removes a player's stats from window
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

    // Indicates which Player's turn it is
    public void updatePlayerTurn(int playerTurn){
        Player currentPlayer = Game.getPlayer(playerTurn);

        for(Player p : Game.observablePlayers){
            if(p == currentPlayer){
                p.stats.setBorder(new Border(new BorderStroke(currentPlayer.getColor(),BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            }
            else{
                p.stats.setBorder(null);
            }
        }
    }

    private void setupDiceLayout(){
        loadDiceImages();

        diceLeft.widthProperty().bind(widthProperty().divide(20));
        diceLeft.heightProperty().bind(widthProperty().divide(20));

        diceRight.widthProperty().bind(widthProperty().divide(20));
        diceRight.heightProperty().bind(widthProperty().divide(20));

        diceLeft.layoutXProperty().bind(widthProperty().divide(2).subtract(diceLeft.widthProperty().multiply(3).divide(2)));
        diceLeft.layoutYProperty().bind(heightProperty().subtract(heightProperty().divide(4.5)));

        diceRight.layoutXProperty().bind(widthProperty().divide(2).add(diceRight.widthProperty().divide(2)));
        diceRight.layoutYProperty().bind(heightProperty().subtract(heightProperty().divide(4.5)));

        diceLeft.setFill(Color.TRANSPARENT);
        diceRight.setFill(Color.TRANSPARENT);

        getChildren().add(diceLeft);
        getChildren().add(diceRight);
    }

    // Updates Dice Images
    public void updateDice(int dice1, int dice2){
        if(dice1 != 0 && dice2 != 0){
            diceLeft.setFill(new ImagePattern(diceFaces.get(dice1-1)));
            diceRight.setFill(new ImagePattern(diceFaces.get(dice2-1)));
        }
    }

    // Loads Dice Images at Game start
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
