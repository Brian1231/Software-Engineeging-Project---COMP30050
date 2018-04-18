package client.java;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class Location {

    // Data
    private String name;
    private int position;
    private int price;
    private int rent;
    private int ownerID;
    private Color colour;
    private boolean isMortgaged;
    private Image image;
    // Display objects.
    Circle Tile = new Circle();


    public Location(String name, int position, int price, int rent, int owner, Color c,boolean isMortgaged) {
        this.name = name;
        this.position = position;
        this.price = price;
        this.rent = rent;
        this.ownerID = owner;
        this.colour = c;
        this.isMortgaged = isMortgaged;
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

        if (this.position != other.position){
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public Color getColour() {
        return colour;
    }

    public void setColour(Color colour) {
        this.colour = colour;
    }

    public void setMortgaged(boolean mortgaged) {
        isMortgaged = mortgaged;
    }

    public boolean isMortgaged() {
        return isMortgaged;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
