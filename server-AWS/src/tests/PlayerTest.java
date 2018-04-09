package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import game.Player;
import noc_db.Character_noc;

public class PlayerTest {

	@Test
	public void testCreatePlayer() {
		String[] info = new String[24];
		Player player = new Player(1, "1.1.1.1",new Character_noc( info));

		assertEquals(1, player.getID());
		assertEquals("1.1.1.1", player.getIp());
		assertEquals(1000, player.getNetWorth());
		assertEquals(0, player.getPos());
	}
	
	@Test
	public void testBalance() {
		String[] info = new String[24];
		Player player = new Player(1, "1.1.1.1",new Character_noc( info));
		
		player.payMoney(500);
		assertEquals(500, player.getNetWorth());
		player.receiveMoney(1000);
		assertEquals(1500, player.getNetWorth());
	}
}
