package game;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;;

public class Player {

	private int id;
	private int balance;
	
	public Player(int playerId){
		id = playerId;
	}
	
	public String toString(){
		return "ID: " + this.id;
	}
	
	public JSONObject getInfo() throws JSONException{
		JSONObject info = new JSONObject();
		
		info.put("id", this.id);
		info.put("balance", this.balance);
		return info;
		
	}
}
