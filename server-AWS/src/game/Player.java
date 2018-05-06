package game;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import game_interfaces.JSONable;
import game_interfaces.Playable;
import noc_db.Character_noc;
import noc_db.Vehicle_noc;

import java.awt.*;
import java.util.ArrayList;

public class Player implements Playable, JSONable{

	private int id;
	private int fuel;
	private int debt;
	private int jailTurnCount;
	private int balance;
	private int position;

	private boolean hasRolled;
	private boolean hasBought;
	private boolean hasBoosted;
	private boolean isOnGo;
	private boolean isInDebt;
	private boolean isInJail;
	private boolean movingForward;


	private Color rgbColour;
	private Playable playerOwed;
	private ArrayList<RentalProperty> ownedProperties = new ArrayList<>();
	private Character_noc character;
	private Character_noc villain;
	private Vehicle_noc vehicle;

	public Player(int playerId, Character_noc ch, Vehicle_noc vehicle, Color color){
		this.id = playerId;
		this.balance = 1000;
		this.position = 0;
		this.debt = 0;
		this.jailTurnCount = 0;
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
		this.movingForward = false;
		this.rgbColour = color;
	}

	@Override
	public boolean equals(Object obj){
		if(obj instanceof Player){
			Player other = (Player) obj;
			return this.id == other.getID();
		}
		return false;
	}

	@Override
	public String toString(){
		return "ID: " + this.id;
	}

	@Override
	public boolean hasRolled(){
		return this.hasRolled;
	}

	@Override
	public void reset(){
		this.hasRolled = false;
		this.hasBoosted = false;
		this.hasBought = false;
	}

	@Override
	public boolean hasBought(){
		return this.hasBought;
	}

	@Override
	public boolean hasBoosted(){
		return this.hasBoosted;
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
	public void setVillain(Character_noc villain) {
		this.villain = villain;
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
	public boolean ownsThree(int color){
		int count = 0;
		for(RentalProperty prop : this.ownedProperties){
			if(prop instanceof InvestmentProperty){
				InvestmentProperty investment = (InvestmentProperty) prop;
				if(investment.getColour() == color)
					count++;
			}
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
		info.put("moving_forward", this.movingForward);
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
	public int getDebt() {
		return this.debt;
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
				StringBuilder res = new StringBuilder();
				this.position = (this.position + spaces)%39;

				//Check if we pass go
				if((oldPos<20 && this.position>19) || (oldPos>20 && this.position>=0 && this.position<20)){
					res.append(this.getCharName() +" passed the Galactic Core and received 100 SHM.\n");
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
				res.append(this.character.getName() + " travelled " + spaces + " spaces " + this.vehicle.getAffordance() + " " + this.vehicle.getDeterminer() + " " + this.vehicle.getVehicle());
				return res.toString();
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
				StringBuilder res = new StringBuilder();
				if(this.position-spaces>=0)
					this.position = (this.position - spaces);
				else{
					int x = spaces - this.position;
					this.position = 39-x;
				}

				//Check if we pass go
				if((oldPos>20 && this.position<=20) || (this.position>=20 && oldPos>0 && oldPos<20)){
					res.append(this.getCharName() +" passed the Galactic Core and received 100 SHM.\n");
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
				res.append(this.character.getName() + " travelled " + spaces + " spaces " + this.vehicle.getAffordance() + " " + this.vehicle.getDeterminer() + " " + this.vehicle.getVehicle());
				return res.toString();
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
	public int getNetWorth() {
		int worth = balance;
		//Sum price of all owned properties
		for (RentalProperty p: ownedProperties) {
			if(!p.isMortgaged()) {
				worth += p.getPrice();
			} else {
				worth += p.getPrice()/2;
			}
			if (p instanceof InvestmentProperty) {
				InvestmentProperty investmentProperty = (InvestmentProperty) p;
				worth += investmentProperty.getNumHouses() * investmentProperty.getHousePrice();
			}
		}
		return worth;
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
	public ArrayList<RentalProperty> getOwnedProperties() {
		return ownedProperties;
	}

	@Override
	public void addNewPropertyBought(RentalProperty property, int price) {
		ownedProperties.add(property);
		// pay money out
		payMoney(price);
		if(price == property.getPrice())
			this.hasBought = true;
	}

	@Override
	public void removePropertySold(RentalProperty property, int price) {
		if(ownedProperties.contains(property)){
			ownedProperties.remove(property);
			// receive money in
			receiveMoney(price);
		}
	}

	@Override
	public Character_noc getCharacter() {
		return this.character;
	}

	@Override
	public Character_noc getVillain() {
		return this.villain;
	}

	@Override
	public boolean isInDebt(){
		return this.isInDebt;
	}

	@Override
	public void setDebt(int amount, Player player){
		this.playerOwed = player;
		this.debt += amount;
		this.isInDebt = true;
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
						String res = this.getCharName() + " paid " + this.playerOwed.getCharName() + " " + debt + " SHM.";
						this.playerOwed.receiveMoney(this.debt);
						this.payMoney(debt);
						this.debt = 0;
						this.isInDebt = false;
						//Main.portAllocator.updatePlayer(playerOwed.getID());
						this.playerOwed = null;
						return res;
					}
					else{
						String res = this.getCharName() + " paid "+this.getPossessive().toLowerCase()+" debt of " + debt + " SHM.";
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
			return this.getCharName() + " paid 500 SHM and was released from jail.";
		}
		return "You can't afford to the fee of 500 SHM";
	}

}
