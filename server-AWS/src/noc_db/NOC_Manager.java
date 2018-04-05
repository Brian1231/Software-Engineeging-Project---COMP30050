package noc_db;

import java.io.BufferedReader;
import java.io.IOException;
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

	public NOC_Manager(){
		this.superlatives = new ArrayList<Superlative_noc>();
		this.characters = new ArrayList<Character_noc>();
		this.weapons = new ArrayList<Weapon_noc>();
		this.vehicles = new ArrayList<Vehicle_noc>();
	}

	//Populate NOC List
	public void setup() throws IOException{

		/////////////////SUPERLATIVES//////////////////////
		BufferedReader br = new BufferedReader(
				new InputStreamReader(ClassLoader.getSystemResourceAsStream("superlatives.txt"))
				);
		String line = br.readLine();
		line = br.readLine();
		while (line != null) {
			String data[] = line.split("\t");
			superlatives.add(new Superlative_noc(data[0], data[1]));
			line = br.readLine();
		}
		br.close();

		/////////////////Characters//////////////////////
		br = new BufferedReader(
				new InputStreamReader(ClassLoader.getSystemResourceAsStream("Veale's The NOC List.txt"))
				);
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
		br = new BufferedReader(
				new InputStreamReader(ClassLoader.getSystemResourceAsStream("Veale's weapon arsenal.txt"))
				);
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
		br = new BufferedReader(
				new InputStreamReader(ClassLoader.getSystemResourceAsStream("Veale's vehicle fleet.txt"))
				);
		line = br.readLine();
		line = br.readLine();
		while (line != null) {
			String data[] = line.split("\t");
			vehicles.add(new Vehicle_noc(data));
			//System.out.println(line);
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
}
