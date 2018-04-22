package game;

import java.util.Random;

import game_interfaces.JSONable;
import game_interfaces.Playable;
import game_interfaces.Taxable;
import main.Main;
import noc_db.Character_noc;

public class TaxSquare extends NamedLocation implements Taxable, JSONable {

	Random random = new Random();

	public TaxSquare(String name) {
		super(name);
	}

	public String activate(Player player){
		int type = random.nextInt(2);
		String res = "\n"+this.getText(Main.noc.getOpponent(player.getCharacter()), type);
		
		switch (type){
		case 0:
			player.setDebt(this.getFlatAmount());
			res+="\n"+player.getCharName()+" owes the flat amount of $"+this.getFlatAmount()+".";
			return res;
		case 1:
			//Percent in range 5% - 30%
			double percentage = (0.05 + (random.nextInt(26)*0.01));
			int t = this.getIncomePercentage(player, percentage);
			res+="\n"+player.getCharName()+" owes "+percentage*100+"% of their net worth. Thats $"+t+".";
			player.setDebt(t);
			return res;
		}
		return res;
	}
	
	@Override
	public int getIncomePercentage(Playable player, double percentage) {
		return (int) (player.getNetWorth() * percentage);
	}

	public String getText(Character_noc ch, int taxType){
		return new TaxTemplate(ch, taxType).getTemplate();
	}
	
	@Override
	public int getFlatAmount() {
		return 50 + 10*random.nextInt(26);
	}
}
