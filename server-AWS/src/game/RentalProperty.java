package game;

import game_interfaces.Mortgageable;
import game_interfaces.Playable;
import game_interfaces.Rentable;

public class RentalProperty extends PrivateProperty implements Rentable, Mortgageable {

	private boolean isMortgaged = false;
	private int[] rentAmounts;

	public RentalProperty(String name, int price) {
		super(name, price);
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
}
