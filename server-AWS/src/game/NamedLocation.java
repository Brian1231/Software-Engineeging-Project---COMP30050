package game;

import java.awt.Color;

import game_interfaces.Typeable;
import org.json.JSONException;
import org.json.JSONObject;

import game_interfaces.Identifiable;
import game_interfaces.JSONable;
import game_interfaces.Locatable;

public class NamedLocation implements Identifiable, Locatable, JSONable, Typeable {

	private String identifier;
	private int location;
	private String type;

	public NamedLocation(String name){
		this.identifier = name;
		this.type = "NamedLocation";
	}
	@Override
	public String getId() {
		return this.identifier;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public JSONObject getInfo() throws JSONException {
		JSONObject info = new JSONObject();
		info.put("id", this.getId());
		info.put("location", this.getLocation());
		info.put("price", 0);
		info.put("owner", 0);
		info.put("color", Color.RED.getRGB());
		info.put("houses", 0);
		info.put("is_mortgaged", false);
		info.put("hasTrap", false);
		info.put("rent", 0);
		info.put("type", this.type);
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
