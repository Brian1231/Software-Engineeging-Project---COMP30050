package noc_db;

import java.util.Random;

public class Clothes_noc {

	String determiner;
	String clothing;
	String covers;
	
	public Clothes_noc(String[] info){
		this.determiner = info[0];
		this.clothing = info[1];
		if(info.length>2)
		this.covers = info[2];
	}
	
	public String getDeterminer(){
		return this.determiner.trim();
	}
	
	public String getClothes(){
		return this.clothing;
	}

	public String getCovering() {
		String aff[] = this.covers.split(", ");
		Random random = new Random();
		return aff[random .nextInt(aff.length)].trim();
	}
}
