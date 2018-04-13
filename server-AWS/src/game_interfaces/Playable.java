package game_interfaces;

import game.PrivateProperty;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public interface Playable extends Identifiable {
    String toString();

    String getIp();

    JSONObject getInfo() throws JSONException;

    int getPos();

    // do we want ID as string or int??
    int getID();

    String getId();

    String moveForward(int spaces);

    int getNetWorth();
    void setNetWorth(int netWorth);

    void payMoney(int paid);
    void receiveMoney(int received);

    // returns list of ID of owned properties
    ArrayList<PrivateProperty> getOwnedProperties();

    // add new property to list of properties owned
    void addNewPropertyBought(PrivateProperty property);

    // remove property player sold
    void removePropertySold(PrivateProperty property);
}
