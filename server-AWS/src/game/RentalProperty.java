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
	private int mortgageAmount;

	public RentalProperty(String name, int price) {
		super(name, price);
		this.hasTrap = false;
	}

	@Override
	public int getMortgageAmount() {
		return this.mortgageAmount;
	}

	@Override
	public void setMortgageAmount(int mortgageAmount) {
		this.mortgageAmount = mortgageAmount;
	}

	@Override
	public void mortgage(Playable player) {
		player.receiveMoney(this.mortgageAmount);
		setMortgageStatus(true);
	}

	@Override
	public void redeem(Playable player) {
		player.payMoney(getRedeemAmount());
		setMortgageStatus(false);
	}

	@Override
	public int getRedeemAmount() {
		return Math.round(this.mortgageAmount + (this.mortgageAmount * 0.1f));
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
			return player.getCharName() + " activated " + this.getOwner().getCharName() + "'s trap and now owes them an additional " + trapAmount + ". ";
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
