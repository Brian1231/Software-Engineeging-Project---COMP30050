package game;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import game_interfaces.JSONable;
import main.Main;

public class Utility extends RentalProperty implements JSONable{

	private int[] rentMultipliers;

	public Utility(String name, int price, int[] rentMultipliers) {
		super(name, price);
		super.setType("Utility");
		this.rentMultipliers = rentMultipliers;
	}

	public int getRentalAmount(int diceRoll) {
		if (!this.isMortgaged() && this.isOwned()) {
			ArrayList<RentalProperty> properties = this.getOwner().getOwnedProperties();

			int numUtilitiesOwned = 0;
			for (RentalProperty p : properties) {
				if (p.getType().equals(this.getType())) {
					numUtilitiesOwned++;
				}
			}

			// as long as at least 1 util owned
			if(numUtilitiesOwned > 0) {
				// -1 in index as index 0 will be when numUtils owned is 1
				return diceRoll * rentMultipliers[numUtilitiesOwned-1];
			} else {
				return 0;
			}

		} else {
			System.out.println("Cant claim rent on utility that is mortgaged");
			return 0;
		}
	}
	
	@Override
	public JSONObject getInfo() throws JSONException {
		JSONObject info =  super.getInfo();
		info.put("rent", this.getRentalAmount(Main.dice.getRollResult()));
		return info;
	}
}
