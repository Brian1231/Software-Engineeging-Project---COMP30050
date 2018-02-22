package client.java;

import javafx.scene.paint.Color;

public class Player {
    private int balance;
    private int id;
    private int position;
    private Color color;

    public Player(int balance, int id, int position) {
        this.balance = balance;
        this.id = id;
        this.position = position;
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


}
