package game_interfaces;

import game.Player;

public interface Trapable {

    int getTrapPrice();
    boolean hasTrap();

    String setTrap();

    String activateTrap(Player player);
}
