package client.java.tests;

import client.java.gameObjects.Player;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import static org.junit.Assert.*;

@RunWith(JfxTestRunner.class)
public class PlayerTest {

    private Player player;

    @Before
    public void setUp() throws Exception {
        player = new Player("1000", 1, 0, Color.BLUE, "Batman", 1.0, true);
    }

    @After
    public void tearDown() throws Exception {
        player = null;
    }

    @Test
    public void equals() {
        Player player2 = new Player("1000", 1, 0, Color.BLUE, "Batman", 1.0, true);
        assertTrue(player.equals(player2));
    }

    @Test
    public void getBalance() {
        assertEquals("1000", player.getBalance());
    }

    @Test
    public void setBalance() {
        assertEquals("1000", player.getBalance());
        player.setBalance("1500");
        assertEquals("1500", player.getBalance());
    }

    @Test
    public void getId() {
        assertEquals(1, player.getId());
    }

    @Test
    public void setId() {
        assertEquals(1, player.getId());
        player.setId(2);
        assertEquals(2, player.getId());
    }

    @Test
    public void getPosition() {
        assertEquals(0, player.getPosition());
    }

    @Test
    public void setPosition() {
        assertEquals(0, player.getPosition());
        player.setPosition(10);
        assertEquals(10, player.getPosition());
    }

    @Test
    public void getColor() {
        assertEquals(Color.BLUE, player.getColor());
    }

    @Test
    public void setColor() {
        assertEquals(Color.BLUE, player.getColor());
        player.setColor(Color.RED);
        assertEquals(Color.RED, player.getColor());
    }

    @Test
    public void getCharacter() {
        assertEquals("Batman", player.getCharacter());
    }

    @Test
    public void setCharacter() {
        assertEquals("Batman", player.getCharacter());
        player.setCharacter("Spiderman");
        assertEquals("Spiderman", player.getCharacter());
    }

    @Test
    public void getFuel() {
        assertEquals(1.0, player.getFuel(),0.0);
    }

    @Test
    public void setFuel() {
        assertEquals(1.0, player.getFuel(),0.0);
        player.setFuel(2.0);
        assertEquals(2.0, player.getFuel(),0.0);
    }

    @Test
    public void isMovingForward() {
        assertTrue(player.isMovingForward());
    }

    @Test
    public void setMovingForward() {
        assertTrue(player.isMovingForward());
        player.setMovingForward(false);
        assertFalse(player.isMovingForward());
    }
}