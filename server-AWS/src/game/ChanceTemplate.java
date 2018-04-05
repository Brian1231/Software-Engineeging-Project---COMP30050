package game;

import main.Main;
import noc_db.Character_noc;
import noc_db.Vehicle_noc;
import noc_db.Weapon_noc;

public class ChanceTemplate {

	Character_noc char1;
	Weapon_noc weapon;
	Vehicle_noc vehicle;
	Character_noc opp;
	String pronoun;
	String them;

	public ChanceTemplate(Character_noc a){
		this.char1 = a;
		this.opp = Main.noc.getOpponent(a);
		this.weapon = Main.noc.getWeapon(a.getWeapon());
		this.vehicle = Main.noc.getVehicle(a.getVehicle());
		if(char1.getGender().equals("male")) {
			this.pronoun = "He";
			this.them = "Him";
		}
		else {
			this.pronoun = "She";
			this.them = "Her";
		}
	}

	public String template1(){
		StringBuilder sb = new StringBuilder();
		//sb.append("You are approached by " + char1.getName() + ". ");
		sb.append(char1.getName() + " appears " + vehicle.getAffordance() + " " + vehicle.getDeterminer() + " " + vehicle.getVehicle()+ ". ");

		sb.append(pronoun + " says that " + pronoun.toLowerCase() + " is tired of " + char1.getActivity() + " and wants some action. ");
		sb.append(pronoun + " starts " + weapon.getAffordanceWithTarget("you") + " " + weapon.getDeterminer() + " " + weapon.getWeapon() + ".");
		return sb.toString(); 
	}

	public String template2(){
		StringBuilder sb = new StringBuilder();
		//sb.append("You are approached by " + char1.getName() + ". ");
		String clothes = char1.getWearing();
		if(clothes.length()!=0)
			sb.append(char1.getName() + " appears wearing a " + char1.getWearing() + ". ");
		else 		
			sb.append(char1.getName() + " appears " + vehicle.getAffordance() + " " + vehicle.getDeterminer() + " " + vehicle.getVehicle()+ ". ");


		sb.append(pronoun + " says that " + pronoun.toLowerCase() + " just lost a fight to " + opp.getName() + " and wants revenge. ");
		sb.append(pronoun + " starts " + weapon.getAffordanceWithTarget("you") + " " + weapon.getDeterminer() + " " + weapon.getWeapon() + ".");
		return sb.toString(); 
	}
	
	public String template3(){
		StringBuilder sb = new StringBuilder();
		//sb.append("You are approached by " + char1.getName() + ". ");
		String clothes = char1.getWearing();
		if(clothes.length()!=0)
			sb.append(char1.getName() + " appears wearing a " + char1.getWearing() + ". ");
		else 		
			sb.append(char1.getName() + " appears " + vehicle.getAffordance() + " " + vehicle.getDeterminer() + " " + vehicle.getVehicle()+ ". ");

		String pos[] = char1.getPositives();
		sb.append(pronoun + " starts a long monologue about how ");
		if(pos.length>1) sb.append(pos[0] + " and " + pos[1] + " " + pronoun.toLowerCase() + " is. ");
		else sb.append(pos[0] + pronoun.toLowerCase() + "is. ");
		sb.append("You tell "+ them.toLowerCase() + " to shutup. ");
		sb.append(pronoun + " gets mad and starts " + weapon.getAffordanceWithTarget("you") + " " + weapon.getDeterminer() + " " + weapon.getWeapon() + ".");
		return sb.toString(); 
	}
}
