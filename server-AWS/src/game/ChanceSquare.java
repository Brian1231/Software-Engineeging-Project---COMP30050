package game;

import java.util.Random;

import main.Main;
import noc_db.Character_noc;

public class ChanceSquare extends NamedLocation{

	
	public ChanceSquare(String s){
		super(s);
	}
	
	public String getChance(Character_noc ch, int type){
		return new ChanceTemplate(ch).getTemplateType(type);
	}
	
	public String activate(Player player){
		Random random = new Random();
		int chanceType = random.nextInt(3);
		String res = this.getChance(Main.noc.getOpponent(player.getCharacter()), chanceType);
		switch(chanceType){
		//Get money
		case 0:
			int amount = 50 + 10*random.nextInt(26);
			player.receiveMoney(amount);
			res+="\n" + player.getCharName() + " receives $" + amount + ".";
			break;
			//Lose money
		case 1:
			amount = 50 + 10*random.nextInt(26);
			player.setDebt(amount);
			res+="\n" + player.getCharName() + " owes $" + amount + ".";
			break;
			//Change direction
		case 2:
			player.changeDirection();
			res+="\n" + player.getCharName() + " has changed direction!.";
			break;
		}
		return res;
	}
}
