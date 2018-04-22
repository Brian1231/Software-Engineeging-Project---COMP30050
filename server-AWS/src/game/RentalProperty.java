package game;

import org.json.JSONException;
import org.json.JSONObject;

import game_interfaces.Mortgageable;
import game_interfaces.Playable;
import game_interfaces.Rentable;

public class RentalProperty extends PrivateProperty implements Rentable, Mortgageable {

	private boolean isMortgaged = false;
	private int[] rentAmounts;
	private boolean hasTrap;

	public RentalProperty(String name, int price) {
		super(name, price);
		this.hasTrap = false;
	}

	@Override
	public int getMortgageAmount() {
		float amount = ((float) super.getPrice()*0.9f);
		return Math.round(amount);
	}

	@Override
	public void mortgage(Playable player) {
		player.receiveMoney(getMortgageAmount());
		setMortgageStatus(true);
	}

	@Override
	public void redeem(Playable player) {
		player.payMoney(getRedeemAmount());
		setMortgageStatus(false);
	}

	@Override
	public int getRedeemAmount() {
		int amount = super.getPrice();
		float interest = (float) amount * 0.1f;
		return Math.round(interest) + amount;
	}

	@Override
	public boolean isMortgaged() {
		return isMortgaged;
	}

	@Override
	public void setMortgageStatus(boolean mortgageStatus) {
		this.isMortgaged = mortgageStatus;
	}

	@Override
	public int getBaseRentAmount() {
		if (!this.isMortgaged()) {
			return rentAmounts[0];
		} else {
			System.out.println("Cant claim rent on rental property that is mortgaged");
			return 0;
		}
	}

	@Override
	public void setRentAmounts(int[] rentAmounts) {
		this.rentAmounts = rentAmounts;
	}

	@Override
	public int[] getAllRentAmounts() {
		return rentAmounts;
	}

	public boolean hasTrap(){
		return this.hasTrap;
	}

	public String setTrap(){
		int trapCost = this.getPrice()/5;
		if(this.getOwner().getBalance() > trapCost){
			this.hasTrap = true;
			this.getOwner().payMoney(trapCost);
			return this.getOwner().getCharName() + " paid " + trapCost + " and set a trap at " + this.getId() + ". ";
		}
		else
			return "You can't afford the cost of " + trapCost + " to set a trap.";
	}
	
	public String activateTrap(Player player){
		if(!player.equals(this.getOwner())){
			int trapAmount =  this.getPrice()/3;
			player.setDebt(trapAmount, this.getOwner());
			return player.getCharName() + " activated " + this.getOwner().getCharName() + "'s trap and now owes them an aditional " + trapAmount + ". ";
		}
		return "";
	}
	
	
	public JSONObject getInfo() throws JSONException {
		JSONObject info = super.getInfo();
		info.put("hasTrap", false);
		info.put("is_mortgaged", this.isMortgaged());
		return info;
	}
}
