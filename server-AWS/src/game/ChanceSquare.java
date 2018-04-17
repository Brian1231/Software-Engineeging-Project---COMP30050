package game;

import noc_db.Character_noc;

public class ChanceSquare extends NamedLocation{

	
	public ChanceSquare(String s){
		super(s);
	}
	
	public String getChance(Character_noc ch, int type){
		return new ChanceTemplate(ch).getTemplateType(type);
	}
}
