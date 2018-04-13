package game;

import java.util.Random;

import main.Main;
import noc_db.Character_noc;
import noc_db.Vehicle_noc;
import noc_db.Weapon_noc;

@SuppressWarnings("Duplicates")
public class ChanceTemplate {

	private Character_noc char1;
	private Weapon_noc weapon;
	private Vehicle_noc vehicle;
	private Character_noc opp;
	private String pronoun;
	private String them;
	private String possesion;

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
		sb.append(char1.getName()).append(" appears ").append(vehicle.getAffordance()).append(" ").append(vehicle.getDeterminer()).append(" ").append(vehicle.getVehicle()).append(". ");
		sb.append(pronoun + " says that " + pronoun.toLowerCase() + " is tired of " + char1.getActivity() + " and wants some action. ");
		sb.append(pronoun + " starts " + weapon.getAffordanceWithTarget("you") + weapon.getDeterminer() + " " + weapon.getWeapon() + ".");
		return sb.toString(); 
	}

	private String template1(){
		StringBuilder sb = new StringBuilder();
		String clothes = char1.getWearing();
		if(clothes.length()!=0)
			sb.append(char1.getName() + " appears wearing a " + char1.getWearing() + ". ");
		else 		
			sb.append(char1.getName() + " appears " + vehicle.getAffordance() + " " + vehicle.getDeterminer() + " " + vehicle.getVehicle()+ ". ");
		sb.append(pronoun + " says that " + pronoun.toLowerCase() + " just lost a fight to " + opp.getName() + " and wants revenge. ");
		sb.append(pronoun + " starts " + weapon.getAffordanceWithTarget("you") + weapon.getDeterminer() + " " + weapon.getWeapon() + ".");
		return sb.toString(); 
	}

	private String template2(){
		StringBuilder sb = new StringBuilder();
		String clothes = char1.getWearing();
		String clothesDet = Main.noc.getClothingDeterminer(clothes);
		String clothesCover = Main.noc.getClothingCovering(clothes);
		if(clothes.length()!=0){
			if(!clothesCover.equals(""))
				sb.append(char1.getName() + " appears with" + clothesDet + " " + char1.getWearing() + " covering " + possesion.toLowerCase() + " " + clothesCover + ". ");
			else
				sb.append(char1.getName() + " appears wearing" + clothesDet + " " + char1.getWearing() + ". ");
		}
		else 		
			sb.append(char1.getName() + " appears " + vehicle.getAffordance() + " " + vehicle.getDeterminer() + " " + vehicle.getVehicle()+ ". ");

		String pos[] = char1.getPositives();
		sb.append(pronoun + " starts a long monologue about how ");
		if(pos.length>1) sb.append(pos[0] + " and " + pos[1] + " " + pronoun.toLowerCase() + " is. ");
		else sb.append(pos[0] + pronoun.toLowerCase() + "is. ");
		sb.append("You tell "+ them.toLowerCase() + " to shutup. ");
		sb.append(pronoun + " gets mad and starts " + weapon.getAffordanceWithTarget("you") + weapon.getDeterminer() + " " + weapon.getWeapon() + ".");
		return sb.toString(); 
	}

	private String template3(){
		StringBuilder sb = new StringBuilder();
		String act = char1.getActivity();
		String setting = Main.noc.getActivitySetting(act);
		String settingDeterminer = Main.noc.getLocationDeterminer(setting);
		sb.append("You see " + char1.getName() + " exiting" + settingDeterminer + " " + setting + ". ");
		sb.append(pronoun + " begins happily telling you about how much " + pronoun.toLowerCase() + " loves " + act + ". ");
		sb.append(pronoun + " decides to give you a gift.");
		return sb.toString();
	}
	
	public String getRandomTemplate(){
		Random random = new Random();
		int i = random.nextInt(4);
		switch(i){
		case 0:
			return this.template0();
		case 1:
			return this.template1();
		case 2:
			return this.template2();
		case 3:
			return this.template3();
		default:
			return "";
		
		}
	}
}
