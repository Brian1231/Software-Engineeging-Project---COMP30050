package game_models;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import game_interfaces.JSONable;

/*
 * Class for station tiles
 * 
 * */
public class Station extends RentalProperty implements JSONable{

	public Station(String name, int price, int[] rentAmounts) {
		super(name, price);
		super.setRentAmounts(rentAmounts);
		this.setType("station");
	}

	/*
	 * Rent is based on number of stations owned. Maximum of 4
	 * 
	 * */
	public int getRentalAmount() {
		if(this.isOwned()){
			ArrayList<RentalProperty> properties = this.getOwner().getOwnedProperties();
			int numStationsOwned = 0;
			for (RentalProperty p : properties) {
				if (p.getType().equals(this.getType()))
					numStationsOwned++;
			}
			if(numStationsOwned > 0)
				return this.getAllRentAmounts()[numStationsOwned-1];
			else
				return 0;
		}
		else
			return 0;
	}

	@Override
	public JSONObject getInfo() throws JSONException {
		JSONObject info =  super.getInfo();
		info.put("rent", this.getRentalAmount());
		info.put("type", this.getType());
		return info;
	}

}
