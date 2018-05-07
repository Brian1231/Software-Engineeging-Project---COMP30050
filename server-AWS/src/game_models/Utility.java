package game_models;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import game_interfaces.JSONable;
import main.Main;

/*
 * This class represents the Utility tiles in our game
 * 
 * */

public class Utility extends RentalProperty implements JSONable{

	private final int[] rentMultipliers;

	public Utility(String name, int price, int[] rentMultipliers) {
		super(name, price);
		this.setType("utility");
		this.rentMultipliers = rentMultipliers;
	}

	/*
	 * Rent is based on the number of utilities owned as well as the dice roll
	 * 
	 * */
	public int getRentalAmount(int diceRoll) {
		if(this.isOwned()){
			ArrayList<RentalProperty> properties = this.getOwner().getOwnedProperties();
			int numUtilitiesOwned = 0;
			for (RentalProperty p : properties) {
				if (p.getType().equals(this.getType()))
					numUtilitiesOwned++;
			}
			// If at least 1 utility owned
			if(numUtilitiesOwned > 0) {
				return diceRoll * rentMultipliers[numUtilitiesOwned-1];
			} else {
				return 0;
			}
		}
		return 0;
	}

	@Override
	public JSONObject getInfo() throws JSONException {
		JSONObject info =  super.getInfo();
		info.put("rent", this.getRentalAmount(Main.dice.getRollResult()));
		info.put("type", this.getType());
		return info;
	}
}
