package noc_db;

import java.util.Random;

public class Vehicle_noc {

	private String determiner;
	private String vehicle;
	private String affordances;

	private Random random = new Random();

	public Vehicle_noc(String[] info){
		switch(info.length){
		case 3:
			this.determiner = info[0];
			this.vehicle = info[1];
			this.affordances = info[2];
			break;
		case 2:
			this.vehicle = info[0];
			this.affordances = info[1];
			break;
		case 1:
			this.vehicle = info[0];
			this.affordances = null;

		}
	}

	public String getDeterminer(){
		return this.determiner;
	}

	public String getVehicle(){
		return this.vehicle.trim();
	}

	public String getAffordance(){
		if(this.affordances!=null){
			String aff[] = this.affordances.split(", ");
			return aff[random.nextInt(aff.length)].trim();
		}
		else
			return "";
	}
}
