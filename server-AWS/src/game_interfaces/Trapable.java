package game_interfaces;

import game_models.Player;

public interface Trapable {

    int getTrapSetPrice();
    int getTrapFineAmount();
    boolean hasTrap();

    String setTrap();

    String activateTrap(Player player);
}
