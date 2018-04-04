package noc_db;

public class Character_noc {

	private String character;
	private String canName;
	private String gender;
	private String addr1;	
	private String addr2;
	private String addr3;
	private String politics;	
	private String maritalStatus;
	private String Opponent;
	private String TypicalActivity;
	private String VehicleofChoice;
	private String WeaponofChoice;
	private String SeenWearing;
	private String DomainsGenres;
	private String FictiveStatus;
	private String PortrayedBy;
	private String CreatorCreation;
	private String GroupAffiliation;
	private String FictionalWorldCategory;
	private String NegativeTalkingPoints;
	private String PositiveTalkingPoints;

	public Character_noc(String[] info){
		this.character = info[0];
		this.canName = info[1];
		this.gender = info[2];
		this.addr1 = info[3];
		this.addr2 = info[4];
		this.addr3 = info[5];
		this.politics = info[6];	
		this.maritalStatus = info[7];
		this.Opponent = info[8];
		this.TypicalActivity = info[9];
		this.VehicleofChoice = info[10];
		this.WeaponofChoice = info[11];
		this.SeenWearing = info[12];
		this.DomainsGenres = info[13];
		this.FictiveStatus = info[14];
		this.PortrayedBy = info[15];
		this.CreatorCreation = info[16];
		this.GroupAffiliation = info[17];
		this.FictionalWorldCategory = info[18];
		this.NegativeTalkingPoints = info[19];
		this.PositiveTalkingPoints = info[20];
	}
	
	public String getGender(){
		return this.gender;
	}
	
	public String getName(){
		return this.character;
	}
}
