package game_interfaces;

import org.json.JSONException;
import org.json.JSONObject;

public interface JSONable {

	JSONObject getInfo() throws JSONException;
}
