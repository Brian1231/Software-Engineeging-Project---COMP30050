package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import game.Player;

public class PlayerTest {

	@Test
	public void testCreatePlayer() {
		Player player = new Player(1, "1.1.1.1");

		assertEquals(1, player.getID());
		assertEquals("1.1.1.1", player.getIp());
		assertEquals(1000, player.getNetWorth());
		assertEquals(0, player.getPos());
	}
	
	@Test
	public void testBalance() {
		Player player = new Player(1, "1.1.1.1");
		
		player.payMoney(500);
		assertEquals(500, player.getNetWorth());
		player.receiveMoney(1000);
		assertEquals(1500, player.getNetWorth());
	}
}
