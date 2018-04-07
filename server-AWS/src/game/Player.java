package game;

import org.json.JSONException;
import org.json.JSONObject;

import game_interfaces.JSONable;
import game_interfaces.Playable;

import java.util.ArrayList;

public class Player implements Playable, JSONable {

	private int id;
	private int balance;
	private int position;
	private String ip;
	private ArrayList<PrivateProperty> ownedProperties = new ArrayList<>();
	private boolean hasRolled;
	
	public Player(int playerId, String ipAddr){
		id = playerId;
		balance = 1000;
		position = 0;
		ip = ipAddr;
		hasRolled = false;
	}
	
	@Override
	public String toString(){
		return "ID: " + this.id;
	}
	
	@Override
	public String getIp(){
		return this.ip;
	}
	
	public boolean hasRolled(){
		return this.hasRolled;
	}
	
	public void resetRoll(){
		this.hasRolled = false;
	}
	
	public void useRoll(){
		this.hasRolled = true;
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
	
	public int getBalance(){
		return this.balance;
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
		int worth = balance;
		// add on price of all owned properties
		for (PrivateProperty p: ownedProperties
		     ) {
			worth += p.getPrice();
		}
		return worth;
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
	public ArrayList<PrivateProperty> getOwnedProperties() {
		return ownedProperties;
	}

	@Override
	public void addNewPropertyBought(PrivateProperty property) {
		ownedProperties.add(property);
		// pay money out
		payMoney(property.getPrice());
	}

	@Override
	public void removePropertySold(PrivateProperty property) {
		ownedProperties.remove(property);
		// receive money in
		receiveMoney(property.getPrice());
	}
}
