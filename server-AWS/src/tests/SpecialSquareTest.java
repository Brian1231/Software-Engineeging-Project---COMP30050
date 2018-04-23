package tests;

import game.Player;
import game.SpecialSquare;
import noc_db.Character_noc;
import noc_db.NOC_Manager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class SpecialSquareTest {

    private SpecialSquare specialSquare;
    private final NOC_Manager noc = new NOC_Manager();

    
    @Before
    public void setUp() {
        specialSquare = new SpecialSquare("UCD");
    }

    @After
    public void tearDown() {
        specialSquare = null;
    }

    @Test
    public void constructorTest() {
        assertNotNull(specialSquare);
        assertEquals("UCD", specialSquare.getId());
    }

    @Test
    public void activateTestLoc0() throws IOException {
        noc.setup();

        Character_noc ch = noc.getRandomChar();
        Player player = new Player(1, "1.1.1.1",noc.getRandomChar(), noc.getVehicle(ch.getVehicle()));

        specialSquare.setLocation(0);
        String result = "\n"+player.getCharName() + " arrived at the galactic core." +
            "\nThe fuel for " + player.getPossessive().toLowerCase() + " " + player.getCharacter().getVehicle()+" was topped up.";
        assertEquals(result,specialSquare.activate(player));
    }


    @Test
    public void activateTestLoc10() throws IOException {
        noc.setup();

        Character_noc ch = noc.getRandomChar();
        Player player = new Player(1, "1.1.1.1",noc.getRandomChar(), noc.getVehicle(ch.getVehicle()));


        specialSquare.setLocation(10);
        String result = "\n" + player.getCharName() + " was sent to intergalactic prison!\n"+
            "Attempt to break free by rolling doubles or pay the fee of $1000.";
        assertEquals(result, specialSquare.activate(player));
    }

    @Test
    public void activateTestLoc29() throws IOException {
        noc.setup();

        Character_noc ch = noc.getRandomChar();
        Player player = new Player(1, "1.1.1.1",noc.getRandomChar(), noc.getVehicle(ch.getVehicle()));

        specialSquare.setLocation(29);
        String result = "You arrive at intergalactic prison and decide to have a relaxing stroll around.";
        assertEquals(result ,specialSquare.activate(player));
    }



}