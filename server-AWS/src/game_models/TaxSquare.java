package game_models;

import java.util.Random;

import game_interfaces.Activatable;
import game_interfaces.Playable;
import game_interfaces.Taxable;
import main.Main;
import noc_db.Character_noc;

/*
 * This class represents the Tax tiles in our game.
 * These tiles are represented by a fictional world from te NOC list
 * 
 * */

public class TaxSquare extends NamedLocation implements Taxable, Activatable {

	private final Random random = new Random();

	public TaxSquare(String name) {
		super(name);
		this.setType("tax");
	}

	@Override
	public String activate(Player player){
		int type = random.nextInt(2);
		String res = "\n"+this.getText(Main.noc.getOpponent(player.getCharacter()), type);
		
		switch (type){
		case 0:
			int flatAmount = this.getFlatAmount();
			player.setDebt(flatAmount, null);
			res+="\n"+player.getCharName()+" owes the flat amount of "+flatAmount+" SHM.";
			return res;
		case 1:
			//Percent in range 5% - 10%
			double percentage = (0.05 + (random.nextInt(6)*0.01));
			int amount = this.getIncomePercentage(player, percentage);
			res+="\n"+player.getCharName()+" owes "+Math.round(percentage*100)+"% of their net worth. That's "+amount+" SHM.";
			player.setDebt(amount, null);
			return res;
		}
		return res;
	}
	
	@Override
	public int getIncomePercentage(Playable player, double percentage) {
		return (int) (player.getNetWorth() * percentage);
	}

	private String getText(Character_noc ch, int taxType){
		return new TaxTemplate(ch, taxType).getTemplate();
	}
	
	@Override
	public int getFlatAmount() {
		return 20 + 10*random.nextInt(9);
	}
}
