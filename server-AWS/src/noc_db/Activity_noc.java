package noc_db;

import java.util.Random;

public class Activity_noc {

	private final String activity;
	private final String[] settings;
	private final Random random = new Random();
	
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
