package game_models;

import java.util.Random;

import main.Main;
import noc_db.Character_noc;
import noc_db.Vehicle_noc;
import noc_db.Weapon_noc;

@SuppressWarnings("Duplicates")
class ChanceTemplate {

	private final Character_noc char1;
	private final Weapon_noc weapon;
	private final Vehicle_noc vehicle;
	private final Character_noc opp;
	private final String pronoun;
	private final String them;
	private final String possesion;

	public ChanceTemplate(Character_noc a){
		this.char1 = a;
		this.opp = Main.noc.getOpponent(a);
		this.weapon = Main.noc.getWeapon(a.getWeapon());
		this.vehicle = Main.noc.getVehicle(a.getVehicle());
		if(char1.getGender().equals("male")) {
			this.pronoun = "He";
			this.them = "Him";
			this.possesion = "His";
		}
		else {
			this.pronoun = "She";
			this.them = "Her";
			this.possesion = "Her";
		}
	}

	private String template0(){
		StringBuilder sb = new StringBuilder();
		String act = char1.getActivity();
		String setting = Main.noc.getActivitySetting(act);
		String settingDeterminer = Main.noc.getLocationDeterminer(setting);
		sb.append("You see ").append(char1.getName()).append(" exiting").append(settingDeterminer).append(" ").append(setting).append(". ");
		sb.append(pronoun).append(" begins happily telling you about how much ").append(pronoun.toLowerCase()).append(" loves ").append(act).append(". ");
		sb.append(pronoun).append(" decides to give you a gift.");
		return sb.toString();
	}

	private String template1(){
		StringBuilder sb = new StringBuilder();
		String act = char1.getActivity();
		String setting = Main.noc.getActivitySetting(act);
		String settingDeterminer = Main.noc.getLocationDeterminer(setting);
		sb.append(char1.getName()).append(" appears stumbling out of").append(settingDeterminer).append(" ").append(setting).append(". ");
		sb.append(pronoun).append(" says ").append(pronoun.toLowerCase()).append(" just got 1st place in a ").append(act).append(" competition and wants to share some of the prize money with you.");
		sb.append(pronoun).append(" decides to give you some of ").append(pronoun.toLowerCase()).append(" winnings. ");
		return sb.toString();
	}
	
	private String template2(){
		StringBuilder sb = new StringBuilder();
		String clothes = char1.getWearing();
		String clothesDet = Main.noc.getClothingDeterminer(clothes);
		String clothesCover = Main.noc.getClothingCovering(clothes);
		if(clothes.length()!=0){
			if(!clothesCover.equals(""))
				sb.append(char1.getName()).append(" appears with").append(clothesDet).append(" ").append(char1.getWearing()).append(" covering ").append(possesion.toLowerCase()).append(" ").append(clothesCover).append(". ");
			else
				sb.append(char1.getName()).append(" appears wearing").append(clothesDet).append(" ").append(char1.getWearing()).append(". ");
		}
		else 		
			sb.append(char1.getName()).append(" appears ").append(vehicle.getAffordance()).append(" ").append(vehicle.getDeterminer()).append(" ").append(vehicle.getVehicle()).append(". ");

		String pos[] = char1.getPositives();
		sb.append(pronoun).append(" starts a long monologue about how ");
		if(pos.length>1) sb.append(pos[0]).append(" and ").append(pos[1]).append(" ").append(pronoun.toLowerCase()).append(" is. ");
		else sb.append(pos[0]).append(pronoun.toLowerCase()).append("is. ");
		sb.append("You tell ").append(them.toLowerCase()).append(" to shutup. ");
		sb.append(pronoun).append(" gets mad and starts ").append(weapon.getAffordanceWithTarget("you")).append(weapon.getDeterminer()).append(" ").append(weapon.getWeapon()).append(".");
		sb.append(pronoun).append(" refuses to stop  until you pay ").append(them.toLowerCase()).append(" some money!");
		return sb.toString(); 
	}
	
	private String template3(){
		return char1.getName() + " appears " + vehicle.getAffordance() + " " + vehicle.getDeterminer() + " " + vehicle.getVehicle() + ". " +
			pronoun + " says that " + pronoun.toLowerCase() + " is tired of " + char1.getActivity() + " and wants some action. " +
			pronoun + " quickly readys " + possesion.toLowerCase() + " " + weapon.getWeapon() + " and begins " + weapon.getAffordanceWithTarget("you") + " it. " +
			"You decide to pay " + them.toLowerCase() + " to stop.";
	}

	private String template4(){
		StringBuilder sb = new StringBuilder();
		String clothes = char1.getWearing();
		if(clothes.length()!=0)
			sb.append(char1.getName()).append(" appears wearing a ").append(char1.getWearing()).append(". ");
		else 		
			sb.append(char1.getName()).append(" appears ").append(vehicle.getAffordance()).append(" ").append(vehicle.getDeterminer()).append(" ").append(vehicle.getVehicle()).append(". ");
		sb.append(pronoun).append(" says that ").append(pronoun.toLowerCase()).append(" just lost a fight to ").append(opp.getName()).append(" and wants revenge. ");
		sb.append(pronoun).append(" refuses to let you pass. You decide to turn around. ");
		return sb.toString(); 
	}
	
	private String template5(){
		StringBuilder sb = new StringBuilder();
		String clothes = char1.getWearing();
		if(clothes.length()!=0)
			sb.append(char1.getName()).append(" shows up in a brand new ").append(char1.getWearing()).append(". ");
		else 		
			sb.append(char1.getName()).append(" arrives ").append(vehicle.getAffordance()).append(" ").append(vehicle.getDeterminer()).append(" ").append(vehicle.getVehicle()).append(". ");
		sb.append(pronoun).append(" warns you of dangerous areas up ahead and recommends you go back the way you came. ");
		sb.append("You decide to take ").append(possesion.toLowerCase()).append(" advise. ");
		return sb.toString(); 
	}


	public String getTemplateType(int type){
		Random random = new Random();
		switch(type){
		//Get money
		case 0:
			if(random.nextBoolean())
				return this.template0();
			else
				return this.template1();
		//Lose money
		case 1:
			if(random.nextBoolean()) 
				return this.template2();
			else
				return this.template3();
		//Change direction
		case 2:
			if(random.nextBoolean())
				return this.template4();
			else
				return this.template5();
		default: return "";
		}
	}
}
