package noc_db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import com.sun.org.apache.bcel.internal.util.ClassLoader;

public class NOC_Manager {

	ArrayList<Superlative_noc> superlatives;
	ArrayList<Character_noc> characters;
	public NOC_Manager(){
		this.superlatives = new ArrayList<Superlative_noc>();
		this.characters = new ArrayList<Character_noc>();
	}

	public void setup() throws IOException{

		BufferedReader br = new BufferedReader(
				new InputStreamReader( ClassLoader.getSystemResourceAsStream("superlatives.txt"))
				);
		String line = br.readLine();
		while (line != null) {
			String data[] = line.split("\t");
			superlatives.add(new Superlative_noc(data[0], data[1]));
			line = br.readLine();
		}
		br.close();
		
		br = new BufferedReader(
				new InputStreamReader( ClassLoader.getSystemResourceAsStream("Veale's The NOC List.txt"))
				);
		line = br.readLine();
		while (line != null) {
			String data[] = line.split("\t");
			characters.add(new Character_noc(data));
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
}
