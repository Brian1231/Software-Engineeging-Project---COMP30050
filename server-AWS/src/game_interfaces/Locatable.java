package game_interfaces;

public interface Locatable {

    //forward and back not left and right to make more sense in gameplay
    Locatable goForward();
    Locatable goBack();

    // got to tile of location
    Locatable goToLocation(int location);

    // get and set current location
    int getBoardLocation();
    void setBoardLocation(int location);
}
