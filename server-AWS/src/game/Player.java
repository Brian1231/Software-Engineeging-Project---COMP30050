package game;

import org.json.JSONException;
import org.json.JSONObject;

import game_interfaces.JSONable;
import game_interfaces.Playable;

import java.util.ArrayList;
import java.util.List;

public class Player implements Playable, JSONable {

	private int id;
	private int balance;
	private int position;
	private String ip;
	//private String type;
	private List<String> ownedProperties = new ArrayList<>();
	
	public Player(int playerId, String ipAddr){
		id = playerId;
		balance = 1000;
		position = 0;
		ip = ipAddr;
	}
	
	@Override
	public String toString(){
		return "ID: " + this.id;
	}
	
	@Override
	public String getIp(){
		return this.ip;
	}
	
	@Override
	public JSONObject getInfo() throws JSONException{
		JSONObject info = new JSONObject();
		
		info.put("id", this.id);
		info.put("balance", this.balance);
		info.put("position", this.position);
		return info;
		
	}
	
	@Override
	public int getPos(){
		return this.position;
	}
	
	@Override
	public int getID(){
		return this.id;
	}

	@Override
	public void moveForward(int spaces){
		this.position = (this.position + spaces)%40;
	}




	// do we need a string version of IDs? player name maybe?

	@Override
	public String getId() {
		return String.valueOf(this.id);
	}

	@Override
	public void setId(String id) {
		this.id = Integer.parseInt(id);
	}

	@Override
	public int getNetWorth() {
		return balance;// + value of properties
	}

	@Override
	public void setNetWorth(int netWorth) {
		this.balance = netWorth;
	}

	@Override
	public void payMoney(int paid) {
		this.balance -= paid;
	}

	@Override
	public void receiveMoney(int received) {
		this.balance += received;
	}

	@Override
	public List<String> getOwnedProperties() {
		return ownedProperties;
	}

	@Override
	public void addNewPropertyBought(String id) {
		ownedProperties.add(id);
	}
}
