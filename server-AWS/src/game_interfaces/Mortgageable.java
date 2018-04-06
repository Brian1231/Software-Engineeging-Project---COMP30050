package game_interfaces;

public interface Mortgageable extends Ownable {
    // get Mortgage amount
    int getMortgageAmount();

    // mortgage a property
    void mortgage(Playable player);

    // redeem a mortgage property
    void redeem(Playable player);

    // get redeem amount
    int getRedeemAmount();
}
