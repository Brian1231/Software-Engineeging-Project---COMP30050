package game_interfaces;

import game_models.Player;
import game_models.RentalProperty;
import noc_db.Character_noc;

import java.util.ArrayList;

public interface Playable extends Identifiable{

	int getNetWorth();
	int getFuel();
	int getPos();
	int getID();
	int getBalance();
	int getDebt();
	
	boolean hasRolled();
	boolean ownsThree(int color);
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
	void addNewPropertyBought(RentalProperty property, int price);
	void removePropertySold(RentalProperty property, int price);
	void payMoney(int paid);
	void receiveMoney(int received);
	void changeDirection();
	void setVillain(Character_noc villain);
	
	String useBoost();
	String moveForward(int spaces);
	String getCharName();
	String getCanName();
	String getPossessive();
	String payDebt();

	ArrayList<RentalProperty> getOwnedProperties();
	Character_noc getCharacter();
	Character_noc getVillain();

}
