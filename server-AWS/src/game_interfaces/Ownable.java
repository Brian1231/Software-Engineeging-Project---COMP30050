package game_interfaces;

public interface Ownable extends Identifiable {

    // all ownable tiles have an owner
    Playable getOwner();
    void setOwner(Playable player);

    // all ownable tiles have a price
    int getPrice();
    int setPrice();

    // all ownable tiles are in groups
    int getNumInGroup();
    void setNumInGroup();

    // bool to check is tile owned or not
    boolean isOwned = false;

    // all game components have a type
    // type of location = property/jail/station/utility/tax etc
    String getType();
    void setType(String type);
}
