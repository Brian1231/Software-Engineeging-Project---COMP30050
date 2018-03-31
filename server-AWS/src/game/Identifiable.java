package game;

public interface Identifiable {


    // do we want ID as string or int??
    String getId();

    // do we need a setter - or set via constructor as they wont change throughout game?
    void setId(String id);

}
