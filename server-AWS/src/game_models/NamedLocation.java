package game_models;

import java.awt.Color;

import game_interfaces.*;
import org.json.JSONException;
import org.json.JSONObject;

/*
 * Super class for all tile objects in our game
 * 
 * */
public class NamedLocation implements Identifiable, Locatable, JSONable, Typeable, Colourable {

	private final String identifier;
	private int location;
	private String type;
	private Color colour;

	public NamedLocation(String name){
		this.identifier = name;
		this.type = "named_location";
		this.colour = Color.RED;
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
		info.put("color", this.colour.getRGB());
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

	@Override
	public void setColour(Color colour) {
		this.colour = colour;
	}

	@Override
	public int getColour() {
		return this.colour.getRGB();
	}
}
