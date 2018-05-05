package game;

import java.util.Random;

import game_interfaces.Activatable;
import main.Main;
import noc_db.Character_noc;

public class ChanceSquare extends NamedLocation implements Activatable{

	
	public ChanceSquare(String s){
		super(s);
		this.setType("chance");
	}
	
	public String getChance(Character_noc ch, int type){
		return new ChanceTemplate(ch).getTemplateType(type);
	}
	
	@Override
	public String activate(Player player){
		Random random = new Random();
		int chanceType = random.nextInt(4);
		String res = this.getChance(Main.noc.getOpponent(player.getCharacter()), chanceType);
		switch(chanceType){
		//Get money
		case 0:
			int amount = 50 + 10*random.nextInt(26);
			player.receiveMoney(amount);
			res+="\n" + player.getCharName() + " receives " + amount + " SHM.";
			break;
			//Lose money
		case 1:
			amount = 50 + 10*random.nextInt(26);
			player.setDebt(amount, null);
			res+="\n" + player.getCharName() + " owes " + amount + " SHM.";
			break;
			//Change direction
		case 2:
			player.changeDirection();
			res+="\n" + player.getCharName() + " has changed direction!.";
			break;
		case 3:
			if(Main.gameState.villainGangIsActive())
				res += "\n" + player.getCharName() + " startled the gang of villains! They quickly charge to you. ";
			else
				res+="\n" + player.getCharName() + " awoke a gang of evil villains!";
			Main.gameState.activateVillainGang(player.getPos());
		}
		return res;
	}
}
