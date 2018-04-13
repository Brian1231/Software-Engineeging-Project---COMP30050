package game;

import game_interfaces.Colourable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import game_interfaces.JSONable;
import game_interfaces.Playable;
import main.Main;
import noc_db.Character_noc;
import noc_db.Vehicle_noc;

import java.util.ArrayList;

public class Player implements Playable, JSONable, Colourable {

	private int id;
	private int balance;
	private int position;
	private String ip;
	private ArrayList<PrivateProperty> ownedProperties = new ArrayList<>();

	private Character_noc character;
	private Vehicle_noc vehicle;
	private int fuel;
	private int debt;
	private Playable playerOwed;
	private boolean hasRolled;
	private boolean hasBought;
	private boolean hasBoosted;
	private boolean isOnGo;
	private boolean isInDebt;
	private String colour;

	public Player(int playerId, String ipAddr, Character_noc ch){
		this.id = playerId;
		this.balance = 1000;
		this.position = 0;
		this.debt = 0;
		this.ip = ipAddr;
		this.hasRolled = false;
		this.hasBought = false;
		this.hasBoosted = false;
		this.isOnGo = false;
		this.isInDebt = false;
		this.playerOwed = null;
		this.fuel = 1;
		this.character = ch;
		this.vehicle = Main.noc.getVehicle(ch.getVehicle());
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

	public boolean hasBought(){
		return this.hasBought;
	}

	public void useBuy(){
		this.hasBought = true;
	}

	public boolean hasBoosted(){
		return this.hasBoosted;
	}

	public void resetBoost(){
		this.hasBoosted = false;
	}

	public void resetBought(){
		this.hasBought = false;
	}

	public void useRoll(){
		this.hasRolled = true;
	}

	public void topUpFuel(){
		this.fuel = 3;
	}

	public int getFuel(){
		return this.fuel;
	}

	@Override
	public JSONObject getInfo() throws JSONException{
		JSONObject info = new JSONObject();

		JSONArray properties = new JSONArray();
		for(NamedLocation l : this.ownedProperties){
			properties.put(l.getInfo());
		}

		info.put("id", this.id);
		info.put("balance", this.balance);
		info.put("position", this.position);
		info.put("character", this.character.getName());
		info.put("properties", properties);
		info.put("fuel", this.fuel);
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

	public String useBoost(){
		this.hasBoosted = true;
		this.fuel--;
		return this.moveForward(1); 
	}

	@Override
	public String moveForward(int spaces){
		int oldPos = this.position;
		if(this.isOnGo){
			this.position = 19;
			this.isOnGo = false;
			this.position = (this.position + spaces)%39;
			return this.character.getName() + " travelled ahead " + spaces + " spaces " + this.vehicle.getAffordance() + " " + this.vehicle.getDeterminer() + " " + this.vehicle.getVehicle();

		}
		else{
			this.position = (this.position + spaces)%39;

			//If we land on go going backwards
			if(this.position == 20) {
				this.position = 0;
				this.isOnGo = true;
			}

			if(oldPos<20 && this.position>20){
				this.position--;
			}

			return this.character.getName() + " travelled ahead " + spaces + " spaces " + this.vehicle.getAffordance() + " " + this.vehicle.getDeterminer() + " " + this.vehicle.getVehicle();
		}
	}

	@Override
	public String getCharName() {
		return this.character.getName();
	}

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

	@Override
	public void setColour(String colour) {
		this.colour = colour;
	}

	@Override
	public String getColour() {
		return colour;
	}

	public boolean isInDebt(){
		return this.isInDebt;
	}

	public void setDebt(int amount, Playable player){
		this.playerOwed = player;
		this.debt = amount;
		this.isInDebt = true;
	}

	public String payDebt(){
		if(this.balance >= this.debt){
			this.playerOwed.receiveMoney(this.debt);
			this.payMoney(debt);
			this.debt = 0;
			this.isInDebt = false;
			return this.getCharName() + " paid " + this.playerOwed.getCharName() + " " + debt + ".";
		}
		return "You don't have enough money to pay your debt!";
	}
}
