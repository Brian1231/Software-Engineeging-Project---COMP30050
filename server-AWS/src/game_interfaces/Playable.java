package game_interfaces;

import game.Player;
import game.RentalProperty;
import noc_db.Character_noc;

import java.awt.Color;
import java.util.ArrayList;

public interface Playable extends Identifiable{

	int getNetWorth();
	int getFuel();
	int getPos();
	int getID();
	int getBalance();
	
	boolean hasRolled();
	boolean ownsThree(Color color);
	boolean incrementJailTurns();
	boolean isInJail();
	boolean hasBought();
	boolean hasBoosted();
	boolean isInDebt();

	void setDebt(int amount, Player player);
	void reset();
	void useRoll();
	void topUpFuel();
	void sendToJail();
	void releaseFromJail();
	void addNewPropertyBought(RentalProperty property);
	void removePropertySold(RentalProperty property);
	void payMoney(int paid);
	void receiveMoney(int received);
	void changeDirection();
	
	String useBoost();
	String moveForward(int spaces);
	String getCharName();
	String getCanName();
	String getPossessive();
	String payDebt();

	ArrayList<RentalProperty> getOwnedProperties();
	Character_noc getCharacter();
}
