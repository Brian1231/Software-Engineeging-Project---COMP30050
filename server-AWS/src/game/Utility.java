package game;

import game_interfaces.Playable;

import java.util.ArrayList;

public class Utility extends RentalProperty {

	public Utility(int location, String name, int price) {
		super(location, name, price);
		super.setType("Utility");
	}

	public int getRentalAmount(Playable player, int diceRoll) {
		if (!this.getMortgageStatus()) {
			ArrayList<PrivateProperty> properties = player.getOwnedProperties();

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
