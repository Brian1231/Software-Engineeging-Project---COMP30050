package client.java;

import javafx.beans.property.adapter.JavaBeanIntegerPropertyBuilder;
import javafx.beans.property.adapter.JavaBeanStringPropertyBuilder;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.paint.Color;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

public class Player implements Serializable {
    // Player data
    private String balance;
    private int id;
    private int position;
    private Color color;
    private String character;
    private int fuel;

    // Information Display objects
    public Label playerNameLabel = new Label();
    public Label playerBalanceLabel = new Label();
    public ProgressBar playerFuelBar = new ProgressBar();

    private final PropertyChangeSupport pcs ;

    public Player(String balance, int id, int position, Color colour, String character,int fuel) {
        this.balance = balance;
        this.id = id;
        this.position = position;
        this.color = colour;
        this.character = character;
        this.fuel = fuel;

        this.pcs = new PropertyChangeSupport(this);

        try {
            playerNameLabel.textProperty().bind(new JavaBeanStringPropertyBuilder().bean(this).name("character").build());
            playerBalanceLabel.textProperty().bind(new JavaBeanStringPropertyBuilder().bean(this).name("balance").build());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!Player.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final Player other = (Player) obj;

        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        String oldBalance = this.balance;
        this.balance = balance;
        pcs.firePropertyChange("balance",oldBalance,this.balance);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        String oldCharacter = this.character;
        this.character = character;
        pcs.firePropertyChange("character",oldCharacter,this.character);
    }

    public int getFuel() {
        return fuel;
    }

    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }
}
