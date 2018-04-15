package game;

import org.json.JSONException;
import org.json.JSONObject;

import game_interfaces.JSONable;
import game_interfaces.Ownable;
import game_interfaces.Playable;

public class PrivateProperty extends NamedLocation implements Ownable, JSONable{

	private Player owner;
	private int price;
	private boolean isOwned;
	private int numInGroup;
	private String type;

	public PrivateProperty(String name, int price){
		super(name);
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
		return this.numInGroup;
	}

	@Override
	public void setNumInGroup(int numInGroup) {
		this.numInGroup = numInGroup;
	}

	@Override
	public String getType() {
		return this.type;
	}

	@Override
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public JSONObject getInfo() throws JSONException {
		JSONObject info = new JSONObject();
		info.put("id", this.getId());
		info.put("price", this.price);
		info.put("location", this.getLocation());
		info.put("color", "GRAY");
		info.put("is_mortgaged", false);
		info.put("houses", 0);
		if(this.owner != null)
			info.put("owner", this.owner.getID());
		else
			info.put("owner", 0);
		return info;
	}

}
