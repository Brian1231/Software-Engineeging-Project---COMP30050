package noc_db;

import java.util.Random;

public class Weapon_noc {

	private String determiner;
	private String weapon;
	private String affordance;

	private Random random = new Random();

	public Weapon_noc(String[] info){
		switch(info.length){
		case 3:
			this.determiner = info[0];
			this.weapon = info[1];
			String[] affordances = info[2].split(", ");
			this.affordance = affordances[random.nextInt(affordances.length)].trim();
			break;
		case 2:
			this.weapon = info[0];
			affordances = info[1].split(", ");
			this.affordance = affordances[random.nextInt(affordances.length)].trim();
			break;
		case 1:
			this.weapon = info[0];
		}
	}

	public String getDeterminer(){
		if(this.determiner.equals("")) 
			return this.determiner;
		else
			return " " + this.determiner;
	}

	public String getWeapon(){
		return this.weapon.trim();
	}

	
	public String getAffordance(){
		return this.affordance;
	}

	public String getAffordanceWithTarget(String target){
		if(this.affordance != null){
			String s[] = this.affordance.split(" ");
			switch (s.length){
			case 1:
				return affordance;
			case 2:
				return s[0] + " " + target + " " + s[1];
			case 3:
				return s[0] + " " + s[1]  + " " + target + " " + s[2];

			}
			return affordance.trim();
		}
		return "";

	}
}

