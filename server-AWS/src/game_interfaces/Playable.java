package game_interfaces;

import game.RentalProperty;
import noc_db.Character_noc;

import java.awt.Color;
import java.util.ArrayList;

public interface Playable extends Identifiable{

    boolean hasRolled();

    void reset();

    boolean hasBought();

    boolean hasBoosted();

    void useRoll();

    void topUpFuel();

    int getFuel();

    void sendToJail();

    void releaseFromJail();

    boolean isInJail();

    void changeDirection();

    boolean incrementJailTurns();

    boolean ownsThree(Color color);

    int getPos();

    int getID();

    int getBalance();

    String useBoost();

    String moveForward(int spaces);

    String getCharName();

    String getCanName();

    int getNetWorth();

    void payMoney(int paid);

    void receiveMoney(int received);

    ArrayList<RentalProperty> getOwnedProperties();

    void addNewPropertyBought(RentalProperty property);

    void removePropertySold(RentalProperty property);

    Character_noc getCharacter();

    boolean isInDebt();

    void setDebt(int amount, Playable player);

    void setDebt(int amount);

    String getPossessive();

    String payDebt();
}
