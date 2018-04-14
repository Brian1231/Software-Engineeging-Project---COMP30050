package game;

import game_interfaces.JSONable;
import game_interfaces.Playable;
import game_interfaces.Taxable;
import noc_db.Character_noc;

public class TaxSquare extends NamedLocation implements Taxable, JSONable {

	private float incomePercentage = 0.04f;
	private int flatAmount = 200;

	public TaxSquare(String name) {
		super(name);
	}

	@Override
	public int getIncomePercentage(Playable player) {
			return (int) (player.getNetWorth() * incomePercentage);
	}

	public String getText(Character_noc ch){
		return new TaxTemplate(ch).getRandomTemplate();
	}
	
	@Override
	public int getFlatAmount() {
		if(flatAmount != 0) {
			return flatAmount;
		} else {
			System.out.println("Error flat amount not initialised");
			return 0;
		}
	}
}
