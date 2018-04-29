package game_interfaces;

import game.RentalProperty;
import noc_db.Character_noc;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.*;
import java.util.ArrayList;

public interface Playable extends Identifiable {

	int getPos();
	int getID();
	int getFuel();
	int getBalance();
	int getNetWorth();

	boolean hasRolled();
	boolean hasBought();
	boolean hasBoosted();
	boolean incrementJailTurns();
	boolean ownsThree(Color color);
	boolean isInJail();
	boolean isInDebt();

	String getPossessive();
	String payDebt();
	String getCanName();
	String useBoost();
	String moveForward(int spaces);
	String getCharName();
	
	void reset();
	void useRoll();
	void topUpFuel();
	void sendToJail();
	void releaseFromJail();
	void changeDirection();
	void payMoney(int paid);
	void receiveMoney(int received);
	void addNewPropertyBought(RentalProperty property);
	void removePropertySold(RentalProperty property);
	void setDebt(int amount, Playable player);
	void setDebt(int amount);
	
	Character_noc getCharacter();
	ArrayList<RentalProperty> getOwnedProperties();
	JSONObject getInfo() throws JSONException;

}
