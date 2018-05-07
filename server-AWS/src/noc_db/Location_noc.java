package noc_db;

class Location_noc {

	private final String location;
	//private String type;
	private final String determiner;
//	private String preposition;
//	private String size;
//	private String ambience;
//	private String interactions;
//	private String props;
	
	public Location_noc(String[] info){
		this.location = info[0];
		//this.type = info[1];
		this.determiner = info[2];
//		this.preposition = info[3];
//		this.size = info[4];
//		this.ambience = info[5];
//		if(info.length > 6) {
//			this.interactions = info[6];
//		}
//		if(info.length > 7) {
//			this.props = info[7];
//		}
	}
	
	public String getLocation(){
		return this.location;
	}
	
	public String getDeterminer(){
		return this.determiner.trim();
	}
}
