package game;

import org.json.JSONException;
import org.json.JSONObject;;

public class Player {

	private int id;
	private int balance;
	private int position;
	private String ip;
	
	public Player(int playerId, String ipAddr){
		id = playerId;
		balance = 1000;
		position = 0;
		ip = ipAddr;
	}
	
	public String toString(){
		return "ID: " + this.id;
	}
	
	public String getIp(){
		return this.ip;
	}
	
	public JSONObject getInfo() throws JSONException{
		JSONObject info = new JSONObject();
		
		info.put("id", this.id);
		info.put("balance", this.balance);
		info.put("position", this.position);
		return info;
		
	}
	
	public int getPos(){
		return this.position;
	}
	
	public int getID(){
		return this.id;
	}
	
	public void moveForward(int spaces){
		this.position = (this.position + spaces)%40;
	}
}
