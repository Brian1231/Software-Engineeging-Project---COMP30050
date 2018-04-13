package game;

import java.util.ArrayList;

public class Station extends RentalProperty {

	public Station(String name, int price, int[] rentAmounts) {
		super(name, price);
		super.setRentAmounts(rentAmounts);
		super.setType("Station");
	}

	public int getRentalAmount() {
		if (!this.isMortgaged() && this.isOwned()) {
			ArrayList<PrivateProperty> properties = this.getOwner().getOwnedProperties();

			int numStationsOwned = 0;
			for (PrivateProperty p : properties) {
				if (p.getType().equals(this.getType())) {
					numStationsOwned++;
				}
			}
			return this.getAllRentAmounts()[numStationsOwned];
		} else {
			System.out.println("Cant claim rent on station that is mortgaged");
			return 0;
		}
	}
	
}
