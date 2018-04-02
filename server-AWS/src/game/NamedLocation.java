package game;



import org.json.JSONException;
import org.json.JSONObject;

import game_interfaces.Identifiable;
import game_interfaces.JSONable;

public class NamedLocation implements Identifiable, JSONable{

	private String identifier;

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
		return info;
	}
}
