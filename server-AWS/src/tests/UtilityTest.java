package tests;

import game.Player;
import game.Utility;
import noc_db.Character_noc;
import noc_db.Vehicle_noc;
import org.junit.Test;

import static org.junit.Assert.*;


public class UtilityTest {

	private final Utility util1 = new Utility("UCD1", 100);
	private final Utility util2 = new Utility("UCD2", 100);
	private final String[] info = new String[24];
	private final Player player = new Player(1, "1.1.1.1",new Character_noc( info), new Vehicle_noc(info));

	@Test
	public void constructorTest() {
		assertNotNull(util1);
		assertEquals("Utility", util1.getType());
		assertEquals("UCD1", util1.getId());

		assertNotNull(util2);
		assertEquals("Utility", util2.getType());
		assertEquals("UCD2", util2.getId());
	}

	@Test
	public void rentTest() {
		int diceRoll = 7;
		player.addNewPropertyBought(util1);
		util1.setOwner(player);
		assertEquals(28, util1.getRentalAmount(diceRoll));

		player.addNewPropertyBought(util2);
		util2.setOwner(player);
		assertEquals(70, util1.getRentalAmount(diceRoll));
	}
}
