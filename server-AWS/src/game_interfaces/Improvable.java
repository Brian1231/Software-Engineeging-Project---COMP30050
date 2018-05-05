package game_interfaces;

public interface Improvable extends Ownable {

    // get number of built houses and hotels on a property
    int getNumHouses();

    // build or demolish a house/s or property/ies
    boolean build(int numToBuild);
    boolean demolish(int numToDemolish);

    String getBuildDemolishError();
    void setBuildDemolishError(String error);

    // get and set the price of building a house
    int getHousePrice();
    void setHousePrice(int housePrice);

    int getHouseSellValue();
}
