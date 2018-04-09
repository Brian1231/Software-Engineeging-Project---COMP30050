package game;

import noc_db.Character_noc;

public class ChanceSquare extends NamedLocation{

	
	public ChanceSquare(String s){
		super(s);
	}
	
	public String getChance(Character_noc ch){
		return new ChanceTemplate(ch).getRandomTemplate();
	}
}
