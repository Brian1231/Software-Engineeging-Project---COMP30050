package game;

import org.json.JSONException;
import org.json.JSONObject;

import game_interfaces.JSONable;
import game_interfaces.Ownable;
import game_interfaces.Playable;

public class PrivateProperty extends NamedLocation implements Ownable, JSONable{

	private Player owner;
	private int price;
	private int location;
	private boolean isOwned;

	public PrivateProperty(int location, String name, int price){
		super(name);
		this.location = location;
		this.price = price;
		this.isOwned = false;
	}

	public boolean isOwned(){
		return this.isOwned;
	}
	@Override
	public Playable getOwner() {
		return this.owner;
	}

	@Override
	public void setOwner(Playable player) {
		this.isOwned = true;
		this.owner = (Player) player;		
	}
	
	public void setUnOwned(){
		this.owner = null;
		this.isOwned = false;
	}

	@Override
	public int getPrice() {
		return this.price;
	}

	@Override
	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public int getNumInGroup() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setNumInGroup() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setType(String type) {
		// TODO Auto-generated method stub

	}

	@Override
	public JSONObject getInfo() throws JSONException {
		JSONObject info = new JSONObject();
		info.put("id", this.getId());
		info.put("price", this.price);
		info.put("location", this.location);
		if(this.owner != null)
			info.put("owner", this.owner.getID());
		else
			info.put("owner", 0);
		return info;
	}

}
