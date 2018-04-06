package noc_db;

import java.util.Random;

public class Weapon_noc {

	private String determiner;
	private String weapon;
	private String affordances;

	private Random random = new Random();

	public Weapon_noc(String[] info){
		switch(info.length){
		case 3:
			this.determiner = info[0];
			this.weapon = info[1];
			this.affordances = info[2];
			break;
		case 2:
			this.weapon = info[0];
			this.affordances = info[1];
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
		String aff[] = this.affordances.split(", ");
		return aff[random.nextInt(aff.length)].trim();
	}

	public String getAffordanceWithTarget(String target){
		if(this.affordances != null){
			String aff[] = this.affordances.split(", ");
			String afford =  aff[random.nextInt(aff.length)];
			String s[] = afford.split(" ");
			switch (s.length){
			case 1:
				return afford;
			case 2:
				return s[0] + " " + target + " " + s[1];
			case 3:
				return s[0] + " " + s[1]  + " " + target + " " + s[2];

			}
			return afford.trim();
		}
		return "";

	}
}

