package game;

public interface Improvable extends Ownable {

    // get number of built houses and hotels on a property
    int getNumHouses();
    int getNumHotels();

    // build or demolish a house/s or property/ies
    void build(int numToBuild);
    void demolish(int numToDemolish);

    // get and set the price of building a house
    int getHousePrice();
    void setHousePrice(int housePrice);

    // get and set the price of building a hotel
    int getHotelPrice();
    void setHotelPrice(int hotelPrice);
}
