package noc_db;

import java.util.Random;

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
    private String VehicleOfChoice;
    private String WeaponOfChoice;
    private String SeenWearing;
    private String Domains;
    private String Genres;
    private String FictiveStatus;
    private String PortrayedBy;
    private String GroupAffiliation;
    private String[] NegativeTalkingPoints;
    private String[] PositiveTalkingPoints;
    private String Creator;
    private String Creation;
    private String FictionalWorld;
    private String Category;
    private Weapon_noc weapon;

    private Random random = new Random();

    public Character_noc(String[] info) {
        this.character = info[0];
        this.canName = info[1];
        this.gender = info[2];
        this.addr1 = info[3];
        this.addr2 = info[4];
        this.addr3 = info[5];
        this.politics = info[6];
        this.maritalStatus = info[7];

        String[] opponents = info[8].split(", ");
        this.Opponent = opponents[random.nextInt(opponents.length)].trim();

        String[] activities = info[9].split(", ");
        this.TypicalActivity = activities[random.nextInt(activities.length)].trim();

        String[] vehicles = info[10].split(", ");
        this.VehicleOfChoice = vehicles[random.nextInt(vehicles.length)].trim();

        String[] weapons = info[11].split(", ");
        this.WeaponOfChoice = weapons[random.nextInt(weapons.length)].trim();

        String[] clothes = info[12].split(", ");
        this.SeenWearing = clothes[random.nextInt(clothes.length)].trim();

        this.Domains = info[13];
        this.Genres = info[14];
        this.FictiveStatus = info[15];
        this.PortrayedBy = info[16];
        this.Creator = info[17];
        this.Creation = info[18];
        this.GroupAffiliation = info[19];
        this.FictionalWorld = info[20];
        this.Category = info[21];
        this.NegativeTalkingPoints = info[22].split(", ");;
        if (info.length == 24)
            this.PositiveTalkingPoints = info[23].split(", ");
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Character : " + this.character + "\n");
        sb.append("Canonical Name : " + this.canName + "\n");
        sb.append("Gender : " + this.gender + "\n");
        sb.append("Addr 1 : " + this.addr1 + "\n");
        sb.append("Addr 2 : " + this.addr2 + "\n");
        sb.append("Addr 3: " + this.addr3 + "\n");
        sb.append("Politics : " + this.politics + "\n");
        sb.append("Marital Status : " + this.maritalStatus + "\n");
        sb.append("Opponent : " + this.Opponent + "\n");
        sb.append("TypicalActivity : " + this.TypicalActivity + "\n");
        sb.append("VehicleOfChoice : " + this.VehicleOfChoice + "\n");
        sb.append("WeaponOfChoice : " + this.WeaponOfChoice + "\n");
        sb.append("SeenWearing : " + this.SeenWearing + "\n");
        sb.append("Domains : " + this.Domains + "\n");
        sb.append("Genres : " + this.Genres + "\n");
        sb.append("FictiveStatus : " + this.FictiveStatus + "\n");
        sb.append("PortrayedBy : " + this.PortrayedBy + "\n");
        sb.append("Creator :" + this.Creator + "\n");
        sb.append("Creation : " + this.Creation + "\n");
        sb.append("GroupAffiliation : " + this.GroupAffiliation + "\n");
        sb.append("FictionalWorld :" + this.FictionalWorld + "\n");
        sb.append("Category : " + this.Category + "\n");
        sb.append("NegativeTalkingPoints : " + this.NegativeTalkingPoints + "\n");
        sb.append("PositiveTalkingPoints : " + this.PositiveTalkingPoints + "\n");
        return sb.toString();
    }

    public String getGender() {
        return this.gender;
    }

    public String getName() {
        return this.character;
    }

    public String getCanName() {
        return this.canName;
    }

    public String getWearing() {
        return SeenWearing;
    }

    public String getOpponent() {
        return this.Opponent;
    }

    public String getWeapon() {
        return this.WeaponOfChoice;
    }

    public void setWeaponObject(Weapon_noc weapon) { this.weapon = weapon; }
    public Weapon_noc getWeaponObject() { return this.weapon; }

    public String getVehicle() {
        return this.VehicleOfChoice;
    }

    public String getActivity() {
        return  this.TypicalActivity;
    }

    public String[] getPositives() {
        return this.PositiveTalkingPoints;
    }

    public String[] getNegatives() {
        return this.NegativeTalkingPoints;
    }

}
