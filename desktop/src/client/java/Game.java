package client.java;

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
    public static ObservableList<Player> observablePlayers = FXCollections.observableList(players);

// Player Methods
    // Updates players from server.
    public static void updatePlayers(List<Player> plyrs){
        for(Player p : plyrs){
            if(!observablePlayers.contains(p)) {
                addPlayer(p);
            }
            else{
                updatePlayerData(p);
            }
        }
    }

    // Adds new player to player list.
    public static void addPlayer(Player player){
        observablePlayers.add(player);
    }

    // Updates player on player list
    public static void updatePlayerData(Player player){
        if(observablePlayers.contains(player)){
            int index = observablePlayers.indexOf(player);
            observablePlayers.get(index).setBalance(player.getBalance());
            observablePlayers.get(index).setPosition(player.getPosition());
        }
    }

    // Removes player from the draw loop. (quits game etc)
    public static void removePlayer(Player player){
        if(observablePlayers.contains(player)){
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
        for(int index = 0; index<40; index++){
            String initName = Integer.toString(index);
            locations.add(new Location(initName, index, 0,0,0, Color.GOLD, false));
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

    public static void updateLocationData(Location location){
        if(locations.contains(location)){
            int index = locations.indexOf(location);
            locations.get(index).setName(location.getName());
            locations.get(index).setRent(location.getRent());
            locations.get(index).setPrice(location.getPosition());
            locations.get(index).setOwnerID(location.getOwnerID());
            locations.get(index).setColour(location.getColour());
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
}
