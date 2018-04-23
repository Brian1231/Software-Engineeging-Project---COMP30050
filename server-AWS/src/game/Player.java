package game;

import game_interfaces.Colourable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import game_interfaces.JSONable;
import game_interfaces.Playable;
import noc_db.Character_noc;
import noc_db.Vehicle_noc;

import java.awt.*;
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
	private int jailTurnCount;
	private Playable playerOwed;
	private boolean hasRolled;
	private boolean hasBought;
	private boolean hasBoosted;
	private boolean isOnGo;
	private boolean isInDebt;
	private boolean isInJail;
	private boolean movingForward;
	private String colour;
	private Color rgbColour;

	public Player(int playerId, String ipAddr, Character_noc ch, Vehicle_noc vehicle){
		this.id = playerId;
		this.balance = 1000;
		this.position = 0;
		this.debt = 0;
		this.jailTurnCount = 0;
		this.ip = ipAddr;
		this.hasRolled = false;
		this.hasBought = false;
		this.hasBoosted = false;
		this.isOnGo = false;
		this.isInDebt = false;
		this.isInJail = false;
		this.playerOwed = null;
		this.fuel = 1;
		this.character = ch;
		this.vehicle = vehicle;
		this.movingForward = true;
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
	public boolean hasRolled(){
		return this.hasRolled;
	}

	@Override
	public void resetRoll(){
		this.hasRolled = false;
	}

	@Override
	public boolean hasBought(){
		return this.hasBought;
	}

	@Override
	public void useBuy(){
		this.hasBought = true;
	}

	@Override
	public boolean hasBoosted(){
		return this.hasBoosted;
	}

	@Override
	public void resetBoost(){
		this.hasBoosted = false;
	}

	@Override
	public void resetBought(){
		this.hasBought = false;
	}

	@Override
	public void useRoll(){
		this.hasRolled = true;
	}

	@Override
	public void topUpFuel(){
		this.fuel = 3;
	}

	@Override
	public int getFuel(){
		return this.fuel;
	}

	@Override
	public void sendToJail(){
		this.position = 29;
		this.isInJail = true;
	}
	@Override
	public void releaseFromJail(){
		this.jailTurnCount = 0;
		this.isInJail = false;
	}

	@Override
	public boolean isInJail(){
		return this.isInJail;
	}

	@Override
	public void changeDirection(){
		this.movingForward = !this.movingForward;
	}

	@Override
	public boolean incrementJailTurns(){
		this.jailTurnCount++;
		if(this.jailTurnCount==3){
			this.jailTurnCount = 0;
			this.isInJail = false;
			return true;
		}
		return false;
	}

	@Override
	public boolean ownsThree(Color color){
		int count = 0;
		for(PrivateProperty prop : this.ownedProperties){
			if(prop.getColor().equals(color))
				count++;
		}
		return count ==3;
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
		info.put("colour", this.rgbColour.getRGB());
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
	public int getBalance(){
		return this.balance;
	}

	@Override
	public String useBoost(){
		this.hasBoosted = true;
		this.fuel--;
		return this.moveForward(1); 
	}

	@Override
	public String moveForward(int spaces){
		if(this.movingForward){
			int oldPos = this.position;
			if(this.isOnGo){
				this.position = 19;
				this.isOnGo = false;
				this.position = (this.position + spaces)%39;
				return this.character.getName() + " travelled ahead " + spaces + " spaces " + this.vehicle.getAffordance() + " " + this.vehicle.getDeterminer() + " " + this.vehicle.getVehicle();

			}
			else{
				String res = "";
				this.position = (this.position + spaces)%39;

				//Check if we pass go
				if((oldPos<20 && this.position>19) || (oldPos>20 && this.position>=0 && this.position<20)){
					res+= this.getCharName() +" passed go and received $100.\n";
					this.receiveMoney(100);
				}

				//If we land on go going backwards
				if(this.position == 20) {
					this.position = 0;
					this.isOnGo = true;
				}

				//Allocating for extra space at go
				if(oldPos<20 && this.position>20){
					this.position--;
				}

				return res+this.character.getName() + " travelled " + spaces + " spaces " + this.vehicle.getAffordance() + " " + this.vehicle.getDeterminer() + " " + this.vehicle.getVehicle();
			}
		}
		else{
			//Moving backwards
			int oldPos = this.position;
			if(this.isOnGo){
				this.position = 20;
				this.isOnGo = false;
				if(this.position-spaces>=0)
					this.position = (this.position - spaces);
				else{
					int x = spaces - this.position;
					this.position = 39-x;
				}
				return this.character.getName() + " travelled ahead " + spaces + " spaces " + this.vehicle.getAffordance() + " " + this.vehicle.getDeterminer() + " " + this.vehicle.getVehicle();
			}
			else{
				String res = "";
				if(this.position-spaces>=0)
					this.position = (this.position - spaces);
				else{
					int x = spaces - this.position;
					this.position = 39-x;
				}

				//Check if we pass go
				if((oldPos>20 && this.position<=20) || (this.position>=20 && oldPos>0 && oldPos<20)){
					res+= this.getCharName() +" passed go and received $100.\n";
					this.receiveMoney(100);
				}

				//If we land on go going backwards
				if(this.position == 20) {
					this.position = 0;
					this.isOnGo = true;
				}

				//Allocating for extra space at go
				if((oldPos>20 && this.position<20)){
					this.position++;
				}
				
				return res+this.character.getName() + " travelled " + spaces + " spaces " + this.vehicle.getAffordance() + " " + this.vehicle.getDeterminer() + " " + this.vehicle.getVehicle();
			}
		}
	}

	@Override
	public String getCharName() {
		return this.character.getName();
	}
	@Override
	public String getCanName() {
		return this.character.getCanName();
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

	@Override
	public void setRGB(int r, int g, int b) {
		rgbColour = new Color(r,g,b);
	}

	@Override
	public void setRGB(Color colour) {
		rgbColour = colour;
	}

	@Override
	public Color getRGBColour() {
		return this.rgbColour;
	}

	@Override
	public Character_noc getCharacter() {
		return this.character;
	}

	@Override
	public boolean isInDebt(){
		return this.isInDebt;
	}

	@Override
	public void setDebt(int amount, Playable player){
		this.playerOwed = player;
		this.debt += amount;
		this.isInDebt = true;
	}

	@Override
	public void setDebt(int amount){
		this.debt += amount;
		this.isInDebt = true;
	}
	@Override
	public void removeDebt(){
		this.debt = 0;
		this.isInDebt = false;
		this.playerOwed = null;
	}


	@Override
	public String getPossessive(){
		if(this.character.getGender().equals("female")) return "Her";
		else return "His";
	}
	@Override
	public String payDebt(){
		if(!this.isInJail){
			if(this.isInDebt){
				if(this.balance >= this.debt){
					if(this.playerOwed != null){
						String res = this.getCharName() + " paid " + this.playerOwed.getCharName() + " " + debt + ".";
						this.playerOwed.receiveMoney(this.debt);
						this.payMoney(debt);
						this.debt = 0;
						this.isInDebt = false;
						this.playerOwed = null;
						return res;
					}
					else{
						String res = this.getCharName() + " paid "+this.getPossessive().toLowerCase()+" debt of " + debt + ".";
						this.payMoney(debt);
						this.debt = 0;
						this.isInDebt = false;
						return res;
					}
				}
				return "You don't have enough money to pay your debt!";
			}
			return "You're not in debt!";
		}
		if(this.balance >=500){
			this.payMoney(500);
			this.jailTurnCount = 0;
			this.isInJail = false;
			return this.getCharName() + " paid $500 and was released from jail.";
		}
		return "You can't afford to the fee of $500";
	}

}
