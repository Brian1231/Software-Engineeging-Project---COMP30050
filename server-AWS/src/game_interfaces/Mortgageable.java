package game_interfaces;

public interface Mortgageable extends Ownable {
    // get Mortgage amount
    int getMortgageAmount();

    //set Mortgage amount
    void setMortgageAmount(int mortgageAmount);

    // mortgage a property
    void mortgage(Playable player);

    // redeem a mortgage property
    void redeem(Playable player);

    // get redeem amount
    int getRedeemAmount();

    // get and set mortgage status
    boolean isMortgaged();
    void setMortgageStatus(boolean mortgageStatus);
}
