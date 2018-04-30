package client.java.tests;

import client.java.gameObjects.Location;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LocationTest {

    private Location loc;
    private Image img;

    @Before
    public void setUp() throws Exception {
        loc = new Location("UCD", 0, 150, 50, 1, javafx.scene.paint.Color.RED, false, 0);
    }

    @After
    public void tearDown() throws Exception {
        loc = null;
    }

    @Test
    public void equals() {
    }

    @Test
    public void getRent() {
        assertEquals(50 , loc.getRent());
    }

    @Test
    public void setRent() {
        assertEquals(50, loc.getRent());
        loc.setRent(20);
        assertEquals(20, loc.getRent());
    }

    @Test
    public void getPosition() {
        assertEquals(0, loc.getPosition());
    }

    @Test
    public void setPosition() {
        assertEquals(0, loc.getPosition());
        loc.setPosition(10);
        assertEquals(10, loc.getPosition());
    }

    @Test
    public void getName() {
        assertEquals("UCD", loc.getName());
    }

    @Test
    public void setName() {
        assertEquals("UCD", loc.getName());
        loc.setName("Trinity");
        assertEquals("Trinity", loc.getName());
    }

    @Test
    public void getPrice() {
        assertEquals(150, loc.getPrice());
    }

    @Test
    public void setPrice() {
        assertEquals(150, loc.getPrice());
        loc.setPrice(200);
        assertEquals(200, loc.getPrice());
    }

    @Test
    public void getOwnerID() {
        assertEquals(1, loc.getOwnerID());
    }

    @Test
    public void setOwnerID() {
        assertEquals(1, loc.getOwnerID());
        loc.setOwnerID(2);
        assertEquals(2, loc.getOwnerID());
    }

    @Test
    public void getColour() {
        assertEquals(Color.RED, loc.getColour());
    }

    @Test
    public void setColour() {
        assertEquals(Color.RED, loc.getColour());
        loc.setColour(Color.BLUE);
        assertEquals(Color.BLUE, loc.getColour());
    }

    @Test
    public void setMortgaged() {
        assertFalse(loc.isMortgaged());
        loc.setMortgaged(true);
        assertTrue(loc.isMortgaged());
    }

    @Test
    public void isMortgaged() {
        assertFalse(loc.isMortgaged());
    }

    @Test
    public void getImage() {
        getImageFromFile(loc);
        loc.setImage(img);
        assertEquals(img, loc.getImage());
    }

    @Test
    public void setImage() {
        getImageFromFile(loc);

        assertNull(loc.getImage());
        loc.setImage(img);
        assertEquals(img, loc.getImage());
    }

    @Test
    public void getHouses() {
        assertEquals(0, loc.getHouses());
    }

    @Test
    public void setHouses() {
        assertEquals(0, loc.getHouses());
        loc.setHouses(2);
        assertEquals(2, loc.getHouses());
    }


    private void getImageFromFile(Location location){

        StringBuilder sb = new StringBuilder();
        sb.append("/client/resources/images/worlds/");
        sb.append(location.getName().trim().replace(":", ""));
        sb.append(".jpg");

        try{
            img = new Image( sb.toString() );
        }catch(Exception e) {
        }
    }
}