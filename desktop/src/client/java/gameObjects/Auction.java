package client.java.gameObjects;

import client.java.main.Game;

public class Auction {

    int playerSelling;
    int highestBidder;
    int currentPrice;
    Location location;
    int time;
    
    public Auction(int playerSelling, int highestBidder, int currentPrice, int position ) {
        this.playerSelling = playerSelling;
        this.highestBidder = highestBidder;
        this.currentPrice = currentPrice;
        this.location = Game.getLocation(position);
    }

    public int getPlayerSelling() {
        return playerSelling;
    }

    public void setPlayerSelling(int playerSelling) {
        this.playerSelling = playerSelling;
    }

    public int getHighestBidder() {
        return highestBidder;
    }

    public void setHighestBidder(int highestBidder) {
        this.highestBidder = highestBidder;
    }

    public int getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(int currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
