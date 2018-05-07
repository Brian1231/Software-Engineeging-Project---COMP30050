package noc_db;

public class World_noc {

	private final String world;

	public World_noc(String[] info){
		this.world = info[0];
	}
	
	public String getWorld(){
		return this.world;
	}

}
