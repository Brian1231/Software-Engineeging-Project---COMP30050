package client.java.main;

import client.java.gameObjects.Auction;
import client.java.gameObjects.Location;
import client.java.gameObjects.Player;
import client.java.gameObjects.VillainGang;
import client.java.gui.PlayerCanvas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Game {

    public static ArrayList<Location> locations = new ArrayList<>();
    public static ArrayList<Player> players = new ArrayList<>();
    public static VillainGang villainGang = new VillainGang();
    public static ObservableList<Player> observablePlayers = FXCollections.observableList(players);
    public static Boolean gameStarted = false;
    public static int playerTurn;
    public static boolean locationsSet = false;
    private static PlayerCanvas pCanvas;
    private static boolean auctionActive;
    private static Auction auction = null;
    private static int auctionTimer;


    // Player Methods
    // Updates players from server.
    public static void updatePlayers(List<Player> plyrs, String action){
        for(Player p : plyrs){
            if(!observablePlayers.contains(p)) {
                addPlayer(p);
            }
            else{
                updatePlayerData(p,action);
            }
        }
        for (int i = 0; i< observablePlayers.size(); i++) {
            if (!plyrs.contains(observablePlayers.get(i))) {
                removePlayer(observablePlayers.get(i));
            }
        }
    }

    // Adds new player to player list.
    private static void addPlayer(Player player){
        observablePlayers.add(player);
        pCanvas.addPlayerToken(player);
    }

    public static void updateVillains(int pos, boolean status){
    	villainGang.setPosition(pos);
    	villainGang.setState(status);
    }
    // Updates player on player list
    private static void updatePlayerData(Player player, String action){
        if(observablePlayers.contains(player)){

            int index = observablePlayers.indexOf(player);
            Player oldData = observablePlayers.get(index);
            observablePlayers.get(index).setBalance(player.getBalance());
            observablePlayers.get(index).setFuel(player.getFuel());
            observablePlayers.get(index).setMovingForward(player.isMovingForward());

            // Only animates movement when action contains "spaces".
            if(oldData.getPosition() != player.getPosition()){
                if(action.contains("spaces")){
//                    if(oldData.isMovingForward()){
                           pCanvas.animatePlayer(oldData,player.getPosition());
//                    }
//                    else{
//                        pCanvas.animatePlayerBackwards(oldData,player.getPosition());
//                    }
                    observablePlayers.get(index).setPosition(player.getPosition());
                }else{
                    observablePlayers.get(index).setPosition(player.getPosition());
                    pCanvas.relocatePlayer( observablePlayers.get(index));
                }
            }
        }
    }

    // Removes player Token from draw loop
    private static void removePlayer(Player player){
        if(observablePlayers.contains(player)){
            pCanvas.removePlayerToken(player);
            observablePlayers.remove(player);
        }
    }

    public static Player getPlayer(int id){
        for(Player player: observablePlayers){
            if(player.getId() == id){
                return player;
            }
        }
        return null;
    }

    // Location Methods
    public static void initializeLocations(){
        for(int index = 0; index<39; index++){
            String initName = Integer.toString(index);
            locations.add(new Location(initName, index, 0,0,0, Color.GOLD, false, 0));
        }
    }

    // Adds new updated location to location list.
    public static void updateLocations(List<Location> locs) throws IOException, JSONException {
        for(Location l : locs){
            if(locations.contains(l)) {
                updateLocationData(l);
            }
            else{
                System.out.println("Couldn't find that location.");
            }
        }
    }

    private static void updateLocationData(Location location){
        if(locations.contains(location)){
            int index = locations.indexOf(location);
            locations.get(index).setName(location.getName());
            locations.get(index).setRent(location.getRent());
            locations.get(index).setPrice(location.getPrice());
            locations.get(index).setOwnerID(location.getOwnerID());
            locations.get(index).setColour(location.getColour());
            locations.get(index).setMortgaged(location.isMortgaged());
            locations.get(index).setHouses(location.getHouses());
            // etc
        }
    }

    public static Location getLocation(int position){
        for(Location loc: locations){
            if(loc.getPosition() == position){
                return loc;
            }
        }
        return null;
    }

    public static boolean isAuctionActive() {
        return auctionActive;
    }

    public static void setAuctionActive(boolean auctionActive) {
        Game.auctionActive = auctionActive;
    }

    public static Auction getAuction() {
        return auction;
    }

    public static void setAuction(Auction auction) {
        Game.auction = auction;
    }

    public static void setPlayerCanvas(PlayerCanvas canvas){
        pCanvas = canvas;
    }

    public static int getAuctionTimer() {
        return auctionTimer;
    }

    public static void setAuctionTimer(int auctionTimer) {
        Game.auctionTimer = auctionTimer;
    }
}
