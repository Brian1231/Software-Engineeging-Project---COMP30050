package client.java;

public class Location {

    private String name;
    private int position;
    private int price;
    private int rent;
    private int ownerID;

    public Location(String name, int position, int price, int rent, int owner) {
        this.name = name;
        this.position = position;
        this.price = price;
        this.rent = rent;
        this.ownerID = owner;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!Location.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final Location other = (Location) obj;

        if (!this.name.equals(other.name)){
            return false;
        }
        return true;
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
