package game;

import org.json.JSONException;
import org.json.JSONObject;

import game_interfaces.Colorable;
import game_interfaces.Improvable;
import game_interfaces.JSONable;

public class InvestmentProperty extends RentalProperty implements Improvable, Colorable, JSONable {

	private int housePrice = 0;
	private int hotelPrice = 0;

	private int numHouses = 0;
	private int numHotels = 0;
	private String color;

	// Must declare investment propertys with the full array of rent prices
	public InvestmentProperty(String name, int price, int[] rentalAmounts) {
		super(name, price);
		super.setRentAmounts(rentalAmounts);
	}


	@Override
	public int getNumHouses() {
		return numHouses;
	}

	@Override
	public int getNumHotels() {
		return numHotels;
	}

	@Override
	public void build(int numToBuild) {
		switch (numToBuild) {
			case 0:
				System.out.println("Error, cant build 0 houses/hotels");
				break;
			case 1:
				if (numHouses == 4 && numHotels == 0) {
					numHotels = 1;
				} else if (numHouses < 4) {
					numHouses += numToBuild;
				} else {
					System.out.println("Error, cant build that many houses/hotels");
				}
				break;
			case 2:
				if (numHouses == 3 && numHotels == 0) {
					numHotels = 1;
					numHouses += 1;
				} else if (numHouses < 3) {
					numHouses += numToBuild;
				} else {
					System.out.println("Error, cant build that many houses/hotels");
				}
				break;
			case 3:
				if (numHouses == 2 && numHotels == 0) {
					numHotels = 1;
					numHouses += 2;
				} else if (numHouses < 2) {
					numHouses += numToBuild;
				} else {
					System.out.println("Error, cant build that many houses/hotels");
				}
				break;
			case 4:
				if (numHouses == 1 && numHotels == 0) {
					numHotels = 1;
					numHouses += 3;
				} else if (numHouses < 1) {
					numHouses += numToBuild;
				} else {
					System.out.println("Error, cant build that many houses/hotels");
				}
				break;
			case 5:
				if (numHouses == 0 && numHotels == 0) {
					numHotels = 1;
					numHouses += 4;
				} else {
					System.out.println("Error, cant build that many houses/hotels");
				}
				break;
			default:
				System.out.println("Error, given wrong input");
				break;
		}
	}

	@Override
	public void demolish(int numToDemolish) {
		if (numToDemolish == 0) {
			System.out.println("Error, cant demolish 0 houses/hotels");

		} else if (numToDemolish >= 1 && numToDemolish <=5) {

			if (numHotels == 1) {
				numHotels = 0;
				numHouses -= numToDemolish - 1;

			} else if (numHouses <= 4 && numToDemolish <= numHouses) {
				numHouses -= numToDemolish;

			} else {
				System.out.println("Error, cant demolish that many houses/hotels");
			}

		} else {
			System.out.println("Error, given wrong input");
		}
	}

	@Override
	public int getHousePrice() {
		return housePrice;
	}

	@Override
	public void setHousePrice(int housePrice) {
		this.housePrice = housePrice;
	}

	@Override
	public int getHotelPrice() {
		return hotelPrice;
	}

	@Override
	public void setHotelPrice(int hotelPrice) {
		this.hotelPrice = hotelPrice;
	}

	public int getRentalAmount() {
		if (!this.isMortgaged()) {
			return this.getAllRentAmounts()[numHotels+numHouses];
		} else {
			System.out.println("Cant claim rent on investment property that is mortgaged");
			return 0;
		}
	}


	@Override
	public void setColor(String color) {
		this.color = color;
		
	}

	@Override
	public String getColor() {
		return this.color;
	}
	
	@Override
	public JSONObject getInfo() throws JSONException {
		JSONObject info =  super.getInfo();
		info.put("color", this.getColor());
		return info;
	}
}
