package game;

import org.json.JSONException;
import org.json.JSONObject;

import game_interfaces.Improvable;
import game_interfaces.JSONable;

public class InvestmentProperty extends RentalProperty implements Improvable, JSONable {

	private int housePrice = 0;
	private int hotelPrice = 0;

	private int numHouses = 0;
	private int numHotels = 0;
	private String buildDemolishError;

	// Must declare investment properties with the full array of rent prices
	public InvestmentProperty(String name) {
		super(name, 0);
		this.setType("Investment");
	}

	@Override
	public int getNumHousesAndHotels() {
		return this.numHotels + this.numHouses;
	}

	@Override
	public boolean build(int numToBuild) {
		switch (numToBuild) {
		case 0:
			this.setBuildDemolishError("Error, cant build 0 houses/hotels");
			return false;
		case 1:
			if (numHouses == 4 && numHotels == 0) {
				numHotels = 1;
				return true;
			} else if (numHouses < 4) {
				numHouses += numToBuild;
				return true;
			} else {
				this.setBuildDemolishError("Error, cant build that many houses/hotels");
				return false;
			}
		case 2:
			if (numHouses == 3 && numHotels == 0) {
				numHotels = 1;
				numHouses += 1;
				return true;
			} else if (numHouses < 3) {
				numHouses += numToBuild;
				return true;
			} else {
				this.setBuildDemolishError("Error, cant build that many houses/hotels");
				return false;
			}
		case 3:
			if (numHouses == 2 && numHotels == 0) {
				numHotels = 1;
				numHouses += 2;
				return true;
			} else if (numHouses < 2) {
				numHouses += numToBuild;
				return true;
			} else {
				this.setBuildDemolishError("Error, cant build that many houses/hotels");
				return false;
			}
		case 4:
			if (numHouses == 1 && numHotels == 0) {
				numHotels = 1;
				numHouses += 3;
				return true;
			} else if (numHouses < 1) {
				numHouses += numToBuild;
				return true;
			} else {
				this.setBuildDemolishError("Error, cant build that many houses/hotels");
				return false;
			}
		case 5:
			if (numHouses == 0 && numHotels == 0) {
				numHotels = 1;
				numHouses += 4;
				return true;
			} else {
				this.setBuildDemolishError("Error, cant build that many houses/hotels");
				return false;
			}
		default:
			this.setBuildDemolishError("Error, given wrong input");
			return false;
		}
	}

	@Override
	public boolean demolish(int numToDemolish) {
		if (numToDemolish == 0) {
			this.setBuildDemolishError("Error, cant demolish 0 houses/hotels");
			return false;

		} else if (numToDemolish >= 1 && numToDemolish <=5) {

			if (numHotels == 1) {
				numHotels = 0;
				numHouses -= numToDemolish - 1;
				return true;

			} else if (numHouses <= 4 && numToDemolish <= numHouses) {
				numHouses -= numToDemolish;
				return true;

			} else {
				this.setBuildDemolishError("Error, cant demolish that many houses/hotels");
				return false;
			}

		} else {
			this.setBuildDemolishError("Error, given wrong input");
			return false;
		}
	}

	@Override
	public String getBuildDemolishError() {
		return this.buildDemolishError;
	}

	@Override
	public void setBuildDemolishError(String error) {
		this.buildDemolishError = error;
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
		return this.getAllRentAmounts()[numHotels+numHouses];
	}

	@Override
	public JSONObject getInfo() throws JSONException {
		JSONObject info =  super.getInfo();
		info.put("houses", this.getNumHousesAndHotels());
		info.put("rent", this.getRentalAmount());
		return info;
	}
}
