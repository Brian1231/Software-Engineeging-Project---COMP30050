package game;

import java.util.ArrayList;

public class Utility extends RentalProperty {

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
}
