package game;

import game_interfaces.Improvable;

public class InvestmentProperty extends RentalProperty implements Improvable {

	public InvestmentProperty(int location, String name, int price) {
		super(location, name, price);
	}

	// NOT IMPLEMENTED YET

	@Override
	public int getNumHouses() {
		return 0;
	}

	@Override
	public int getNumHotels() {
		return 0;
	}

	@Override
	public void build(int numToBuild) {

	}

	@Override
	public void demolish(int numToDemolish) {

	}

	@Override
	public int getHousePrice() {
		return 0;
	}

	@Override
	public void setHousePrice(int housePrice) {

	}

	@Override
	public int getHotelPrice() {
		return 0;
	}

	@Override
	public void setHotelPrice(int hotelPrice) {

	}
}
