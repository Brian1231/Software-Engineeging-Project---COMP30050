package game_models;

import java.util.Random;

import game_interfaces.Activatable;
import main.Main;
import noc_db.Character_noc;


/*
 * This class represents the Interdimensional TV tiles in our game
 * 
 * */
public class ChanceSquare extends NamedLocation implements Activatable{

	public ChanceSquare(String s){
		super(s);
		this.setType("chance");
	}
	
	/*
	 * Get the text generated from the chance templates
	 * */
	private String getChance(Character_noc ch, int type){
		return new ChanceTemplate(ch).getTemplateType(type);
	}
	
	/*
	 * Trigger the chance card when a player lands on it
	 * */
	@Override
	public String activate(Player player){
		Random random = new Random();
		int chanceType = random.nextInt(4);
		String res = this.getChance(Main.noc.getOpponent(player.getCharacter()), chanceType);
		switch(chanceType){
		//Get money
		case 0:
			int amount = 20 + 10*random.nextInt(9);
			player.receiveMoney(amount);
			res+="\n" + player.getCharName() + " receives " + amount + " SHM.";
			break;
			//Lose money
		case 1:
			amount = 20 + 10*random.nextInt(9);
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
