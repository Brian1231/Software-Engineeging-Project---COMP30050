package game_models;

import org.json.JSONException;
import org.json.JSONObject;

import game_interfaces.Improvable;


/*
 * Class to model improvable properties
 * 
 * */
public class InvestmentProperty extends RentalProperty implements Improvable {

	private int housePrice = 0;
	private int houseSellValue;

	private int numHouses = 0;
	private String buildDemolishError;

	// Must declare investment properties with the full array of rent prices
	public InvestmentProperty(String name) {
		super(name, 0);
		setType("investment");
	}

	@Override
	public int getNumHouses() {
		return this.numHouses;
	}

	@Override
	public boolean build(int numToBuild) {

		if (getNumHouses() == 5) {
			this.setBuildDemolishError("Hold Up! You already have the max number of upgrades!");
			return false;
		} else {
			numHouses+=numToBuild;
			this.setBuildDemolishError(null);
			return true;
		}
	}

	@Override
	public boolean demolish(int numToDemolish) {
		if (getNumHouses() == 0 ) {
			this.setBuildDemolishError("Careful! There is no upgrades left to remove!");
			return false;
		} else {
			this.numHouses -= numToDemolish;
			this.setBuildDemolishError(null);
			return true;
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
		this.houseSellValue = this.housePrice / 2;
	}

	@Override
	public int getHouseSellValue() {
		return this.houseSellValue;
	}

	public int getRentalAmount() {
		return this.getAllRentAmounts()[numHouses];
	}

	@Override
	public JSONObject getInfo() throws JSONException {
		JSONObject info =  super.getInfo();
		info.put("houses", this.getNumHouses());
		info.put("rent", this.getRentalAmount());
		info.put("type", this.getType());
		return info;
	}
}
