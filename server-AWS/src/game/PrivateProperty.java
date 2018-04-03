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

	public PrivateProperty(int location, String name, int price){
		super(name);
		this.location = location;
		this.price = price;
	}
	
	@Override
	public Playable getOwner() {
		return this.owner;
	}

	@Override
	public void setOwner(Playable player) {
		this.owner = (Player) player;		
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
		return info;
	}

}
