package game_interfaces;

public interface Rentable extends Ownable {

    // get and set Rent amounts
    int getBaseRentAmount();
    void setRentAmounts(int[] rentAmounts);

    int[] getAllRentAmounts();
}
