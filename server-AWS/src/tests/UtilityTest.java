package tests;

import game.Dice;
import game.Player;
import game.Station;
import game.Utility;
import noc_db.Character_noc;
import noc_db.NOC_Manager;
import noc_db.Vehicle_noc;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;


public class UtilityTest {

	private Utility util1;
	private Utility util2;
	private Player player;
	private NOC_Manager noc;
	private Dice dice;

	@Test
	public void constructorTest() {
		assertNotNull(util1);
		assertEquals("Utility", util1.getType());
		assertEquals("UCD1", util1.getId());

		assertNotNull(util2);
		assertEquals("Utility", util2.getType());
		assertEquals("UCD2", util2.getId());
	}


    @Before
    public void setUp() throws IOException {
		util1 = new Utility("UCD1", 100);
		util2 = new Utility("UCD2", 100);
		dice = new Dice();

		noc = new NOC_Manager();
		noc.setup();
		Character_noc ch = noc.getRandomChar();
		player = new Player(1, "1.1.1.1",noc.getRandomChar(), noc.getVehicle(ch.getVehicle()));
    }

    @After
    public void tearDown() {
		util1 = util2 = null;
		player = null;
		noc = null;
		dice = null;
    }

    @Test
    public void getRentalAmount() {
		int diceRoll = dice.rollDice(2,6);

		player.addNewPropertyBought(util1);
		util1.setOwner(player);
		assertEquals(diceRoll*4, util1.getRentalAmount(diceRoll));

		player.addNewPropertyBought(util2);
		util2.setOwner(player);
		assertEquals(diceRoll*10, util1.getRentalAmount(diceRoll));
    }
}
