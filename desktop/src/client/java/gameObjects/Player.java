package client.java.gameObjects;

import javafx.beans.property.adapter.JavaBeanDoublePropertyBuilder;
import javafx.beans.property.adapter.JavaBeanStringPropertyBuilder;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

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
    private double fuel;
    private boolean movingForward = true;

    // Information Display objects
    private Label playerNameLabel = new Label();
    private HBox money = new HBox(5);
    private HBox gas = new HBox(5);
    private Image currency = new Image("client/resources/images/Schmeckles.png");
    private Image fuelIcon = new Image("client/resources/images/gasoline.png");
    private Label playerBalanceLabel = new Label();
    private ProgressBar playerFuelBar = new ProgressBar();
    public VBox stats = new VBox();

    // Token
    public Circle playerToken = new Circle(10);

    private final PropertyChangeSupport pcs;

    public Player(String balance, int id, int position, Color colour, String character,double fuel, boolean movingForward) {
        this.balance = balance;
        this.id = id;
        this.position = position;
        this.color = colour;
        this.character = character;
        this.fuel = fuel;
        this.movingForward = movingForward;

        this.pcs = new PropertyChangeSupport(this);

        try {
            playerNameLabel.textProperty().bind(new JavaBeanStringPropertyBuilder().bean(this).name("character").build().concat(" Player: " + id));
            playerBalanceLabel.textProperty().bind(new JavaBeanStringPropertyBuilder().bean(this).name("balance").build());
            playerFuelBar.progressProperty().bind(new JavaBeanDoublePropertyBuilder().bean(this).name("fuel").build().divide(3.0));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        playerNameLabel.setTextFill(colour);
        playerBalanceLabel.setTextFill(colour);

        ImageView c = new ImageView();
        c.setImage(currency);
        c.setFitHeight(20);
        c.setFitWidth(20);
        money.getChildren().addAll(c,playerBalanceLabel);

        ImageView f = new ImageView();
        f.setImage(fuelIcon);
        f.setFitHeight(20);
        f.setFitWidth(20);
        gas.getChildren().addAll(f,playerFuelBar);

        stats.setSpacing(5.0);
        stats.setId("stats");
        stats.setPadding(new Insets(5,5,5,5));
        stats.getChildren().add(playerNameLabel);
        stats.getChildren().add(money);
        stats.getChildren().add(gas);
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

        return this.id == other.id;
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

    public double getFuel() {
        return fuel;
    }

    public void setFuel(double fuel) {
        double oldFuel = this.fuel;
        this.fuel = fuel;
        pcs.firePropertyChange("fuel",oldFuel,this.fuel);
    }

    public boolean isMovingForward() {
        return movingForward;
    }

    public void setMovingForward(boolean movingForward) {
        this.movingForward = movingForward;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }
}
