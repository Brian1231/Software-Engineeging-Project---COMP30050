package noc_db;

import java.util.Arrays;
import java.util.Random;

public class Character_noc {

    private final String character;
    private final String canName;
    private final String gender;
    private final String addr1;
    private final String addr2;
    private final String addr3;
    private final String politics;
    private final String maritalStatus;
    private final String Opponent;
    private final String TypicalActivity;
    private final String VehicleOfChoice;
    private final String WeaponOfChoice;
    private final String SeenWearing;
    private final String Domains;
    private final String Genres;
    private final String FictiveStatus;
    private final String PortrayedBy;
    private final String GroupAffiliation;
    private String[] PositiveTalkingPoints;
    private final String Creator;
    private final String Creation;
    private final String FictionalWorld;
    private final String Category;
    private Weapon_noc weapon;

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
        this.Opponent = opponents[0].trim();

        String[] activities = info[9].split(", ");
        Random random = new Random();
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
        if (info.length == 24)
            this.PositiveTalkingPoints = info[23].split(", ");
    }

    public String toString() {
        return ("Character : " + this.character + "\n") +
            "Canonical Name : " + this.canName + "\n" +
            "Gender : " + this.gender + "\n" +
            "Addr 1 : " + this.addr1 + "\n" +
            "Addr 2 : " + this.addr2 + "\n" +
            "Addr 3: " + this.addr3 + "\n" +
            "Politics : " + this.politics + "\n" +
            "Marital Status : " + this.maritalStatus + "\n" +
            "Opponent : " + this.Opponent + "\n" +
            "TypicalActivity : " + this.TypicalActivity + "\n" +
            "VehicleOfChoice : " + this.VehicleOfChoice + "\n" +
            "WeaponOfChoice : " + this.WeaponOfChoice + "\n" +
            "SeenWearing : " + this.SeenWearing + "\n" +
            "Domains : " + this.Domains + "\n" +
            "Genres : " + this.Genres + "\n" +
            "FictiveStatus : " + this.FictiveStatus + "\n" +
            "PortrayedBy : " + this.PortrayedBy + "\n" +
            "Creator :" + this.Creator + "\n" +
            "Creation : " + this.Creation + "\n" +
            "GroupAffiliation : " + this.GroupAffiliation + "\n" +
            "FictionalWorld :" + this.FictionalWorld + "\n" +
            "Category : " + this.Category + "\n" +
            "PositiveTalkingPoints : " + Arrays.toString(this.PositiveTalkingPoints) + "\n";
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

}
