package noc_db;

import java.util.Random;

public class Activity_noc {

	private String activity;
	private String[] settings;
	private Random random = new Random();
	
	public Activity_noc(String[] info){
		this.activity = info[0];
		this.settings = info;
	}
	
	public String getActivity(){
		return this.activity;
	}
	
	public String getSetting(){
		return settings[random.nextInt(settings.length-1) + 1];
	}
}
