package game_interfaces;

import game.Player;

public interface Trapable {

    boolean hasTrap();

    String setTrap();

    String activateTrap(Player player);
}
