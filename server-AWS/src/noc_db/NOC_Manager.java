package noc_db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import com.sun.org.apache.bcel.internal.util.ClassLoader;

public class NOC_Manager {

	Random random = new Random();
	ArrayList<Superlative_noc> superlatives;
	ArrayList<Character_noc> characters;
	ArrayList<Weapon_noc> weapons;
	ArrayList<Vehicle_noc> vehicles;
	ArrayList<Activity_noc> activities;
	ArrayList<World_noc> worlds;
	ArrayList<Location_noc> locations;
	ArrayList<Clothes_noc> clothes;

	public NOC_Manager(){
		this.superlatives = new ArrayList<Superlative_noc>();
		this.characters = new ArrayList<Character_noc>();
		this.weapons = new ArrayList<Weapon_noc>();
		this.vehicles = new ArrayList<Vehicle_noc>();
		this.activities = new ArrayList<Activity_noc>();
		this.worlds = new ArrayList<World_noc>();
		this.locations = new ArrayList<Location_noc>();
		this.clothes = new ArrayList<Clothes_noc>();
	}

	//Populate NOC List
	public void setup() throws IOException{

		/////////////////SUPERLATIVES//////////////////////
		InputStream in = ClassLoader.getSystemResourceAsStream("noc_files/superlatives.txt");
		InputStreamReader isr = new InputStreamReader(in);
		BufferedReader br = new BufferedReader(isr);

		String line = br.readLine();
		line = br.readLine();
		while (line != null) {
			String data[] = line.split("\t");
			superlatives.add(new Superlative_noc(data[0], data[1]));
			line = br.readLine();
		}
		br.close();

		/////////////////Characters//////////////////////
		in = ClassLoader.getSystemResourceAsStream("noc_files/Veale's The NOC List.txt");
		isr = new InputStreamReader(in);
		br = new BufferedReader(isr);

		line = br.readLine();
		line = br.readLine();
		while (line != null) {
			String data[] = line.split("\t");
			characters.add(new Character_noc(data));
			//System.out.println(line);
			line = br.readLine();
		}
		br.close();

		/////////////////Weapons//////////////////////
		in = ClassLoader.getSystemResourceAsStream("noc_files/Veale's weapon arsenal.txt");
		isr = new InputStreamReader(in);
		br = new BufferedReader(isr);

		line = br.readLine();
		line = br.readLine();
		while (line != null) {
			String data[] = line.split("\t");
			weapons.add(new Weapon_noc(data));
			//System.out.println(line);
			line = br.readLine();
		}
		br.close();

		/////////////////Vehicles//////////////////////
		in = ClassLoader.getSystemResourceAsStream("noc_files/Veale's vehicle fleet.txt");
		isr = new InputStreamReader(in);
		br = new BufferedReader(isr);

		line = br.readLine();
		line = br.readLine();
		while (line != null) {
			String data[] = line.split("\t");
			vehicles.add(new Vehicle_noc(data));
			//System.out.println(line);
			line = br.readLine();
		}
		br.close();

		/////////////////Activities//////////////////////
		in = ClassLoader.getSystemResourceAsStream("noc_files/Veale's Typical Activities.txt");
		isr = new InputStreamReader(in);
		br = new BufferedReader(isr);

		line = br.readLine();
		line = br.readLine();
		while (line != null) {
			String data[] = line.split("\t");
			this.activities.add(new Activity_noc(data));
			line = br.readLine();
		}
		br.close();

		/////////////////FICTIONAL WORLDS//////////////////////
		in = ClassLoader.getSystemResourceAsStream("noc_files/Veale's fictional worlds.txt");
		isr = new InputStreamReader(in);
		br = new BufferedReader(isr);

		line = br.readLine();
		line = br.readLine();
		while (line != null) {
			String data[] = line.split("\t");
			this.worlds.add(new World_noc(data));
			line = br.readLine();
		}
		br.close();

		/////////////////Locations//////////////////////
		in = ClassLoader.getSystemResourceAsStream("noc_files/Veale's location listing.txt");
		isr = new InputStreamReader(in);
		br = new BufferedReader(isr);

		line = br.readLine();
		line = br.readLine();
		while (line != null) {
			String data[] = line.split("\t");
			this.locations.add(new Location_noc(data));
			line = br.readLine();
		}
		br.close();

		/////////////////Clothing//////////////////////
		in = ClassLoader.getSystemResourceAsStream("noc_files/Veale's clothing line.txt");
		isr = new InputStreamReader(in);
		br = new BufferedReader(isr);

		line = br.readLine();
		line = br.readLine();
		while (line != null) {
			String data[] = line.split("\t");
			this.clothes.add(new Clothes_noc(data));
			line = br.readLine();
		}
		br.close();
	}

	public void printCharacterbyGender(String g){
		for(Character_noc c : this.characters){
			if(c.getGender().equals(g)){
				System.out.println(c.getName());
			}
		}
	}

	public Character_noc getRandomChar(){
		int i = random.nextInt(characters.size());
		return characters.get(i);
	}

	public Character_noc getOpponent(Character_noc ch){
		String opp = ch.getName();
		for(Character_noc other: this.characters){
			if(other.getOpponent().equals(opp)){
				return other;
			}
		}
		return this.getRandomChar();
	}

	public Weapon_noc getRandomWeapon(){
		int i = random.nextInt(weapons.size());
		return weapons.get(i);
	}

	public Weapon_noc getWeapon(String w){
		for(Weapon_noc weap : this.weapons){
			if(weap.getWeapon().equals(w)) return weap;
		}
		return this.getRandomWeapon();
	}

	public Vehicle_noc getRandomVehicle(){
		int i = random.nextInt(vehicles.size());
		return vehicles.get(i);
	}

	public Vehicle_noc getVehicle(String w){
		for(Vehicle_noc vehicle : this.vehicles){
			if(vehicle.getVehicle().equals(w)) return vehicle;
		}
		return this.getRandomVehicle();
	}

	public Activity_noc getRandomActivity(){
		int i = random.nextInt(activities.size());
		return activities.get(i);
	}

	public String getActivitySetting(String act){
		for(Activity_noc activity: this.activities){
			if(activity.getActivity().equals(act)) return activity.getSetting();
		}
		return "field";

	}

	public World_noc getRandomWorld(){
		int i = random.nextInt(worlds.size());
		return worlds.get(i);
	}

	public String getLocationDeterminer(String l){
		for(Location_noc loc : this.locations){
			if(loc.getLocation().equals(l)){
				return loc.getDeterminer();
			}
		}
		return "";
	}
	
	public String getClothingDeterminer(String c){
		for(Clothes_noc clo : this.clothes){
			if(clo.getClothes().equals(c)){
				return clo.getDeterminer();
			}
		}
		return "";
	}
	
	public String getClothingCovering(String c){
		for(Clothes_noc clo : this.clothes){
			if(clo.getClothes().equals(c)){
				return clo.getCovering();
			}
		}
		return "";
	}


}
