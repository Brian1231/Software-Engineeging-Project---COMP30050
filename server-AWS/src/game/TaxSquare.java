package game;

import java.util.Random;

import game_interfaces.JSONable;
import game_interfaces.Playable;
import game_interfaces.Taxable;
import noc_db.Character_noc;

public class TaxSquare extends NamedLocation implements Taxable, JSONable {

	Random random = new Random();

	public TaxSquare(String name) {
		super(name);
	}

	@Override
	public int getIncomePercentage(Playable player, double percentage) {
		return (int) (player.getNetWorth() * percentage);
	}

	public String getText(Character_noc ch){
		return new TaxTemplate(ch).getRandomTemplate();
	}

	//Tax in range 50-300
	@Override
	public int getFlatAmount() {
		return 50 + 10*random.nextInt(26);
	}
}
