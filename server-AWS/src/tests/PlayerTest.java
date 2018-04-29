package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import java.awt.Color;

import game.Player;
import game.RentalProperty;
import noc_db.Vehicle_noc;
import noc_db.Character_noc;

public class PlayerTest {
	private final String[] info = new String[24];
	private final Player player = new Player(1, new Character_noc( info), new Vehicle_noc(info), Color.BLUE);

	@Test
	public void constructorTest() {
		assertNotNull(player);
		assertEquals(1, player.getID());
		assertEquals(1000, player.getNetWorth());
		assertEquals(0, player.getPos());
	}
	
	@Test
	public void testBalance() {
		player.payMoney(500);
		assertEquals(500, player.getNetWorth());
		player.receiveMoney(1000);
		assertEquals(1500, player.getNetWorth());
	}

	@Test
	public void testBuy() {
		player.addNewPropertyBought(new RentalProperty("UCD", 200));
		assertEquals("UCD", player.getOwnedProperties().get(0).getId());
	}

	@Test
	public void testSell() {
		RentalProperty prop = new RentalProperty("UCD", 200);
		player.addNewPropertyBought(prop);

		player.removePropertySold(prop);
		// if property is removed from player correctly then the price of the property is added back to the player's balance
		assertEquals(1000, player.getNetWorth());
	}
}
