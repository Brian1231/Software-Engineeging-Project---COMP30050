package game;

import game_interfaces.Playable;
import game_interfaces.Taxable;

public class TaxSquare extends NamedLocation implements Taxable {

	int incomePercentage = 0;
	int flatAmount = 0;

	public TaxSquare(String name) {
		super(name);
	}

	@Override
	public int getIncomePercentage(Playable player) {
		try {
			return player.getNetWorth() / incomePercentage;
		} catch (Exception e) {
			System.out.println("Error income percentage not initialised" + e.getLocalizedMessage());
		}
		return 0;
	}

	@Override
	public void setIncomePercentage(int percentage) {
		this.incomePercentage = percentage;
	}

	@Override
	public int getFlatAmount(Playable player) {
		if(flatAmount != 0) {
			return flatAmount;
		} else {
			System.out.println("Error flat amount not initialised");
			return 0;
		}
	}

	@Override
	public void setFlatAmount(int flatAmount) {
		this.flatAmount = flatAmount;
	}
}
