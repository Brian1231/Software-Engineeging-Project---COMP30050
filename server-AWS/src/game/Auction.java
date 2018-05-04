package game;

import org.json.JSONException;
import org.json.JSONObject;

import game_interfaces.JSONable;

public class Auction implements JSONable{

	private RentalProperty prop;
	private int price;
	private Player playerBuying;
	private boolean auctionInProgress;
	
	public Auction(){
		this.prop = null;
		this.price = 0;
		this.playerBuying = null;
		this.auctionInProgress = false;
	}
	
	public void auction(RentalProperty prop, Player playerBuying, int price){
		this.auctionInProgress = true;
		this.price = price;
		this.prop = prop;
		this.playerBuying = playerBuying;
	}
	
	public void reset(){
		this.prop = null;
		this.price = 0;
		this.playerBuying = null;
		this.auctionInProgress = false;
	}
	public String finish(){
		
		prop.setOwner(playerBuying);
		playerBuying.addNewPropertyBought(prop, price);
		String res = playerBuying.getCharName() + " bought " + prop.getId() + " for " + this.price + ".";
		
		this.prop = null;
		this.price = 0;
		this.playerBuying = null;
		this.auctionInProgress = false;
		return res;
	}
	
	public boolean auctionInProgress(){
		return this.auctionInProgress;
	}
	
	public boolean update(Player player, int price){
		if(price>this.price){
			this.playerBuying = player;
			this.price = price;
			return true;
		}
		return false;
	}

	@Override
	public JSONObject getInfo() throws JSONException {
		JSONObject info = new JSONObject();
		info.put("player_selling", this.prop.getOwner().getID());
        info.put("player_buying", this.playerBuying.getID());
        info.put("price", this.price);
        info.put("location", this.prop.getLocation());
		return info;
	}
}
