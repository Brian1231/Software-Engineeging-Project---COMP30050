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
		int amount = getMortgageAmount();
		float interest = (float) amount * 10.0f/100.0f;
		int redeemAmount = Math.round(interest) + amount;
		return redeemAmount;
	}

	@Override
	public boolean getMortgageStatus() {
		return isMortgaged;
	}

	@Override
	public void setMortgageStatus(boolean mortgageStatus) {
		this.isMortgaged = mortgageStatus;
	}

	@Override
	public int getBaseRentAmount() {
		if (!this.getMortgageStatus()) {
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
}
