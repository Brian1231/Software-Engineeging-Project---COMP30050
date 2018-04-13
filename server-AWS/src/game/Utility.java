package game;

import java.util.ArrayList;

public class Utility extends RentalProperty {

	public Utility(String name, int price) {
		super(name, price);
		super.setType("Utility");
	}

	public int getRentalAmount(int diceRoll) {
		if (!this.isMortgaged() && this.isOwned()) {
			ArrayList<PrivateProperty> properties = this.getOwner().getOwnedProperties();

			int numUtilitiesOwned = 0;
			for (PrivateProperty p : properties) {
				if (p.getType().equals(this.getType())) {
					numUtilitiesOwned++;
				}
			}

			switch (numUtilitiesOwned) {
				case 1:
					return diceRoll * 4;
				case 2:
					return diceRoll * 10;
				default:
					return 0;
			}
		} else {
			System.out.println("Cant claim rent on utility that is mortgaged");
			return 0;
		}
	}
}
