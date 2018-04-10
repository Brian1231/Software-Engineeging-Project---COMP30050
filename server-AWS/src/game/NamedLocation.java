package game;



import org.json.JSONException;
import org.json.JSONObject;

import game_interfaces.Identifiable;
import game_interfaces.JSONable;
import game_interfaces.Locatable;

public class NamedLocation implements Identifiable, Locatable, JSONable{

	private String identifier;
	private int location;

	public NamedLocation(String name){
		this.identifier = name;
	}
	@Override
	public String getId() {
		return this.identifier;
	}

	@Override
	public void setId(String id) {
		this.identifier = id;		
	}
	@Override
	public JSONObject getInfo() throws JSONException {
		JSONObject info = new JSONObject();
		info.put("id", this.getId());
		info.put("location", this.getLocation());
		info.put("price", 0);
		info.put("owner", 0);
		info.put("color", "RED");
		return info;
	}
	@Override
	public int getLocation() {
		return this.location;
	}
	@Override
	public void setLocation(int location) {
		this.location = location;		
	}
}
