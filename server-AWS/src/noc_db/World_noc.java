package noc_db;

import java.util.ArrayList;
import java.util.List;

public class World_noc {

	private String world;
	private List<String> types = new ArrayList<String>();
	
	public World_noc(String[] info){
		this.world = info[0];
		for(int i=1;i<info.length;i++){
			this.types.add(info[i]);
		}
	}
	
	public String getWorld(){
		return this.world;
	}
	
	public List<String> getTypes(){
		return this.types;
	}
}
