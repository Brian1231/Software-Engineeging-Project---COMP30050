package game_models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import game_interfaces.JSONable;
import game_interfaces.Playable;
import noc_db.Character_noc;
import noc_db.Vehicle_noc;

import java.awt.*;
import java.util.ArrayList;

/*
 * Class to represent player object in the game
 * Contains all information relevant to individual player
 * 
 * */
public class Player implements Playable, JSONable{

	private final int id;
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

	private final Color rgbColour;
	private Playable playerOwed;
	private final ArrayList<RentalProperty> ownedProperties = new ArrayList<>();
	private final Character_noc character;
	private Character_noc villain;
	private final Vehicle_noc vehicle;

	public Player(int playerId, Character_noc ch, Vehicle_noc vehicle, Color color){
		this.id = playerId;
		this.balance = 1500; //Initial balance
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
		this.movingForward = true;
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

	//Has the player rolled this turn?
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

	//Has the player bought this turn?
	@Override
	public boolean hasBought(){
		return this.hasBought;
	}

	//Has the player boosted this turn?
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

	//Assign player a villain character
	@Override
	public void setVillain(Character_noc villain) {
		this.villain = villain;
	}

	//Player can only stay in jail for 3 turns
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

	//Check if the player owns a full color group
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

	//Get location of player on the board
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

	//Move Player around the board by a certain number of spaces
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
					res.append(this.getCharName()).append(" passed the Galactic Core and received 100 SHM.\n");
					this.receiveMoney(100);
				}
				//If we land on go going backwards
				if(this.position == 20) {
					this.position = 0;
					this.isOnGo = true;
				}
				//Allocating for extra space at go
				if(oldPos<20 && this.position>20) this.position--;
				res.append(this.character.getName()).append(" travelled ").append(spaces).append(" spaces ").append(this.vehicle.getAffordance()).append(" ").append(this.vehicle.getDeterminer()).append(" ").append(this.vehicle.getVehicle());
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
				if((oldPos>=20 && this.position<20) || (this.position>=20 && oldPos>0 && oldPos<20)){
					res.append(this.getCharName()).append(" passed the Galactic Core and received 100 SHM.\n");
					this.receiveMoney(100);
				}
				if(oldPos>=20 && this.position<20) this.position++;
				//If we land on go going backwards
				if(this.position == 19) {
					this.position = 0;
					this.isOnGo = true;
				}
				res.append(this.character.getName()).append(" travelled ").append(spaces).append(" spaces ").append(this.vehicle.getAffordance()).append(" ").append(this.vehicle.getDeterminer()).append(" ").append(this.vehicle.getVehicle());
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
		if(this.balance >=50){
			this.payMoney(50);
			this.jailTurnCount = 0;
			this.isInJail = false;
			return this.getCharName() + " paid 50 SHM and was released from jail.";
		}
		return "You can't afford to the fee of 50 SHM";
	}

}
