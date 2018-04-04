package client.java;

public class Location {

    private int rent;
    private int position;
    private String name;

    public Location(int rent, int position, String name) {
        this.rent = rent;
        this.position = position;
        this.name = name;
    }

    public int getRent() {
        return rent;
    }

    public void setRent(int rent) {
        this.rent = rent;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
