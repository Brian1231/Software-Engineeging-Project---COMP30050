package tests;

import game_models.Dice;
import game_models.Player;

import game_models.Utility;
import noc_db.Character_noc;
import noc_db.NOC_Manager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
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
		assertEquals("utility", util1.getType());
		assertEquals("UCD1", util1.getId());
		assertEquals("utility", util1.getType());

		assertNotNull(util2);
		assertEquals("utility", util2.getType());
		assertEquals("UCD2", util2.getId());
	}


    @Before
    public void setUp() throws IOException {
		util1 = new Utility("UCD1", 100, new int[]{4,10});
		util2 = new Utility("UCD2", 100, new int[]{4,10});
		dice = new Dice();

		noc = NOC_Manager.getNocManager();
		noc.setup();
		Character_noc ch = noc.getRandomChar();
		player = new Player(1,ch, noc.getVehicle(ch.getVehicle()), Color.BLUE);
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
		dice.roll();
		int diceRoll = dice.getRollResult();

		player.addNewPropertyBought(util1, util1.getPrice());
		util1.setOwner(player);
		assertEquals(diceRoll*4, util1.getRentalAmount(diceRoll));

		player.addNewPropertyBought(util2, util2.getPrice());
		util2.setOwner(player);
		assertEquals(diceRoll*10, util1.getRentalAmount(diceRoll));
    }
}
