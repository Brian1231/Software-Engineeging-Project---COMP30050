package game_interfaces;

import org.json.JSONException;
import org.json.JSONObject;

public interface JSONable {

	public JSONObject getInfo() throws JSONException;
}
