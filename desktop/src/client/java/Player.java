package client.java;

import javafx.scene.paint.Color;

public class Player {
    private int balance;
    private int id;
    private int position;
    private Color color;
    private String character;
    private int fuel;

    public Player(int balance, int id, int position, Color colour, String character,int fuel) {
        this.balance = balance;
        this.id = id;
        this.position = position;
        this.color = colour;
        this.character = character;
        this.fuel = fuel;
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

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
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
}
