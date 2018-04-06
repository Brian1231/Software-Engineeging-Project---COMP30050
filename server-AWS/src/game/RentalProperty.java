package game;

import game_interfaces.Mortgageable;
import game_interfaces.Playable;
import game_interfaces.Rentable;

public class RentalProperty extends PrivateProperty implements Rentable, Mortgageable {

	private boolean isMortgaged = false;
	private int[] rentAmounts;

	public RentalProperty(int location, String name, int price) {
		super(location, name, price);
	}

	@Override
	public int getMortgageAmount() {
		float amount = ((float) super.getPrice())/2.0f;
		int mortgageAmount = Math.round(amount);
		return mortgageAmount;
	}


	@Override
	public void mortgage(Playable player) {
		isMortgaged = true;
		player.receiveMoney(getMortgageAmount());
	}

	@Override
	public void redeem(Playable player) {
		isMortgaged = false;
		player.payMoney(getRedeemAmount());
	}

	@Override
	public int getRedeemAmount() {
		int amount = getMortgageAmount();
		float interest = (float) amount * 10.0f/100.0f;
		int redeemAmount = Math.round(interest) + amount;
		return redeemAmount;
	}

	@Override
	public int getBaseRentAmount() {
		return rentAmounts[0];
	}

	@Override
	public void setRentAmounts(int[] rentAmounts) {
		this.rentAmounts = rentAmounts;
	}

	@Override
	public int[] getAllRentAmounts() {
		return rentAmounts;
	}
}
