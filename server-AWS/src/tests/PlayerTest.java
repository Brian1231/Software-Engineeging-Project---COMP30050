package tests;

import static org.junit.Assert.assertEquals;

import game.PrivateProperty;
import noc_db.Vehicle_noc;
import org.junit.Test;

import game.Player;
import noc_db.Character_noc;

public class PlayerTest {

	@Test
	public void testCreatePlayer() {
		String[] info = new String[24];
		Player player = new Player(1, "1.1.1.1",new Character_noc( info), new Vehicle_noc(info));

		assertEquals(1, player.getID());
		assertEquals("1.1.1.1", player.getIp());
		assertEquals(1000, player.getNetWorth());
		assertEquals(0, player.getPos());
	}
	
	@Test
	public void testBalance() {
		String[] info = new String[24];
		Player player = new Player(1, "1.1.1.1",new Character_noc( info), new Vehicle_noc(info));
		
		player.payMoney(500);
		assertEquals(500, player.getNetWorth());
		player.receiveMoney(1000);
		assertEquals(1500, player.getNetWorth());
	}

	@Test
	public void testBuy() {
		String[] info = new String[24];
		Player player = new Player(1, "1.1.1.1", new Character_noc(info), new Vehicle_noc(info));

		player.addNewPropertyBought(new PrivateProperty("UCD", 200));
		assertEquals("UCD", player.getOwnedProperties().get(0).getId());
	}

	@Test
	public void testSell() {
		String[] info = new String[24];
		Player player = new Player(1, "1.1.1.1", new Character_noc(info), new Vehicle_noc(info));

		PrivateProperty prop = new PrivateProperty("UCD", 200);
		player.addNewPropertyBought(prop);

		player.removePropertySold(prop);
		// if property is removed from player correctly then the price of the property is added back to the player's balance
		assertEquals(1000, player.getNetWorth());
	}
}
