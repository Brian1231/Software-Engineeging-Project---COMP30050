package game;

import main.Main;
import noc_db.Character_noc;
import noc_db.Weapon_noc;

public class ChanceTemplate {

	Character_noc char1;
	Weapon_noc weapon;
	Character_noc opp;
	public ChanceTemplate(Character_noc a){
		this.char1 = a;
		this.opp = Main.noc.getOpponent(a);
		this.weapon = Main.noc.getWeapon(a.getWeapon());
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("You are approached by " + char1.getName() + ". ");
		if(char1.getGender().equals("male")) sb.append("He ");
		else sb.append("She ");
		sb.append("takes a break from " + char1.getActivity());
				
		sb.append( " and starts " + weapon.getAffordanceWithTarget("you") + " " + weapon.getDeterminer() + " " + weapon.getWeapon() + ".");
		return sb.toString(); 
	}
}
