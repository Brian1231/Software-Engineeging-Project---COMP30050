package tests;

import game_models.Player;
import game_models.SpecialSquare;
import noc_db.Character_noc;
import noc_db.NOC_Manager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.io.IOException;

import static org.junit.Assert.*;

public class SpecialSquareTest {

    private SpecialSquare specialSquare;
    private NOC_Manager noc;
    private Player player;


    @Before
    public void setUp() throws IOException {
        specialSquare = new SpecialSquare("UCD");

        noc = NOC_Manager.getNocManager();
        noc.setup();

        Character_noc ch = noc.getRandomChar();
        player = new Player(1,ch, noc.getVehicle(ch.getVehicle()), Color.BLUE);

    }

    @After
    public void tearDown() {
        specialSquare = null;
        noc = null;
    }

    @Test
    public void constructorTest() {
        assertNotNull(specialSquare);
        assertEquals("UCD", specialSquare.getId());
        assertEquals("special", specialSquare.getType());
    }

    @Test
    public void activateTestLoc0() {

        specialSquare.setLocation(0);
        String result = "\n"+player.getCharName() + " arrived at the galactic core." +
            "\nThe fuel for " + player.getPossessive().toLowerCase() + " " + player.getCharacter().getVehicle()+" was topped up.";
        assertEquals(result,specialSquare.activate(player));
    }


    @Test
    public void activateTestLoc10() {

        specialSquare.setLocation(10);
        String result = "\n" + player.getCharName() + " was sent to intergalactic prison!\n"+
            "Attempt to break free by rolling doubles or pay the fee of 50 SHM.";
        assertEquals(result, specialSquare.activate(player));
    }

    @Test
    public void activateTestLoc29() {

        specialSquare.setLocation(29);
        String result = "You arrive at intergalactic prison and decide to have a relaxing stroll around.";
        assertEquals(result ,specialSquare.activate(player));
    }



}