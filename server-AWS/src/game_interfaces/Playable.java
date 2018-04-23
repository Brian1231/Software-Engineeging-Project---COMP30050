package game_interfaces;

import game.PrivateProperty;
import noc_db.Character_noc;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.*;
import java.util.ArrayList;

public interface Playable extends Identifiable {

    String getIp();

    boolean hasRolled();

    void resetRoll();

    boolean hasBought();

    void useBuy();

    boolean hasBoosted();

    void resetBoost();

    void resetBought();

    void useRoll();

    void topUpFuel();

    int getFuel();

    void sendToJail();

    void releaseFromJail();

    boolean isInJail();

    void changeDirection();

    boolean incrementJailTurns();

    boolean ownsThree(Color color);

    JSONObject getInfo() throws JSONException;

    int getPos();

    int getID();

    String getCanName();

    int getBalance();

    String useBoost();

    String moveForward(int spaces);

    String getCharName();

    int getNetWorth();

    void setNetWorth(int netWorth);

    void payMoney(int paid);

    void receiveMoney(int received);

    ArrayList<PrivateProperty> getOwnedProperties();

    void addNewPropertyBought(PrivateProperty property);

    void removePropertySold(PrivateProperty property);

    void setColour(String colour);

    String getColour();

    void setRGB(int r, int g, int b);

    void setRGB(Color colour);

    Color getRGBColour();

    Character_noc getCharacter();

    boolean isInDebt();

    void setDebt(int amount, Playable player);

    void setDebt(int amount);

    void removeDebt();

    String getPossessive();

    String payDebt();
}
