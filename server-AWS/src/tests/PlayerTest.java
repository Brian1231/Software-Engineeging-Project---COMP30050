package tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import game.Player;

public class PlayerTest {

	@Test
	public void testCreatePlayer() {
		Player player = new Player(1, "1.1.1.1");
		
		assertTrue(player.getID() == 1);
		assertTrue(player.getIp().equals("1.1.1.1"));
		assertTrue(player.getNetWorth() == 1000);
		assertTrue(player.getPos() == 0);
	}
	
	@Test
	public void testBalance() {
		Player player = new Player(1, "1.1.1.1");
		
		player.payMoney(500);
		assertTrue(player.getNetWorth() == 500);
		player.receiveMoney(1000);
		assertTrue(player.getNetWorth() == 1500);
	}
}
