package game;

import org.json.JSONException;
import org.json.JSONObject;

import game_interfaces.JSONable;

public class Auction implements JSONable{

	private RentalProperty prop;
	private int price;
	private Player playerBuying;
	private Player playerSelling;
	private boolean auctionInProgress;

	public Auction(){
		this.prop = null;
		this.price = 0;
		this.playerBuying = null;
		this.playerSelling = null;
		this.auctionInProgress = false;
	}

	public void auction(RentalProperty prop, Player playerBuying, int price){
		this.auctionInProgress = true;
		this.price = price;
		this.prop = prop;
		this.playerBuying = playerBuying;
		this.playerSelling = prop.getOwner();
	}

	public void reset(){
		this.prop = null;
		this.price = 0;
		this.playerBuying = null;
		this.auctionInProgress = false;
	}
	public String finish(){

		String res = "";
		if(!(playerSelling == null)){
			if(playerBuying.equals(playerSelling))
				res = playerBuying.getCharName() + " keeps " + prop.getId() + ".";
			else{
				prop.setOwner(playerBuying);
				playerBuying.addNewPropertyBought(prop, price);
				playerSelling.removePropertySold(prop, price);
				res = playerBuying.getCharName() + " bought " + prop.getId() + " for " + this.price + ".";
			}
		}
		else if(!(playerBuying == null)){
			playerBuying.addNewPropertyBought(prop, price);
			res = playerBuying.getCharName() + " bought " + prop.getId() + " for " + this.price + ".";
		}
		else{
			res = "No one bought " + prop.getId() + ". It remains unowned. ";
		}

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
		if(this.playerSelling != null)
			info.put("player_selling", this.prop.getOwner().getID());
		else
			info.put("player_selling", 0);
		info.put("player_buying", this.playerBuying.getID());
		info.put("price", this.price);
		info.put("location", this.prop.getLocation());
		return info;
	}

	public boolean isValidBid(Player player) {
		return !player.equals(playerSelling);
	}
}
