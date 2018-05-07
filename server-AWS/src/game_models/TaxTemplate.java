package game_models;

import java.util.Random;

import main.Main;
import noc_db.Character_noc;
import noc_db.Vehicle_noc;
import noc_db.Weapon_noc;

@SuppressWarnings({"ALL", "Duplicates"})
public class TaxTemplate {

	private Character_noc char1;
	private Weapon_noc weapon;
	private Vehicle_noc vehicle;
	private Character_noc opp;
	private String pronoun;
	private String them;
	private String possesion;
	private int taxType;

	public TaxTemplate(Character_noc a, int type){
		this.char1 = a;
		this.taxType = type;
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
		sb.append(pronoun + " demands money!");
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
		sb.append(pronoun + " says " + pronoun.toLowerCase() + " won't stop until you pay " + them.toLowerCase() + " some money!");
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
		sb.append(pronoun + " starts ranting about about how jealous " + pronoun.toLowerCase() + " is of your newly found success in buying interdimensional property.");
		sb.append("You feel morally obliged to share some of your vast wealth.");
		return sb.toString(); 
	}
	
	private String template3(){
		StringBuilder sb = new StringBuilder();
		sb.append("In the distance you see " + char1.getCanName() + " fast approaching " + vehicle.getAffordance() + " " + vehicle.getDeterminer() + " " + vehicle.getVehicle()+ ". ");
		sb.append("You can see that " + pronoun.toLowerCase() + " has " + weapon.getDeterminer() + " " + weapon.getWeapon() + " and looks ready to use it. ");
		sb.append("Rather than face them,  you decide to throw some money at " + them.toLowerCase() + " as a distraction. It's going to take a lot to pull this off. ");
		return sb.toString(); 
	}
	
	public String getTemplate(){
		Random random = new Random();
		switch(this.taxType){
		case 0:
			if(random.nextBoolean())
				return this.template0();
			else
				return this.template1();
		case 1:
			if(random.nextBoolean())
				return this.template2();
			else
				return this.template3();
		default:
			return "";
		
		}
	}
}
