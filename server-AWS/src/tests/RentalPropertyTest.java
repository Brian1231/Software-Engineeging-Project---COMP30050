package tests;


import game.Player;
import game.RentalProperty;
import noc_db.Character_noc;
import noc_db.Vehicle_noc;
import org.junit.Test;
import static org.junit.Assert.*;


public class RentalPropertyTest {

	private String[] info = new String[24];
	private Player player = new Player(1, "1.1.1.1",new Character_noc( info), new Vehicle_noc(info));
	private RentalProperty prop = new RentalProperty("UCD", 100);

	@Test
	public void constructorTest() {
		assertNotNull(prop);
		assertEquals("UCD", prop.getId());
	}
	@Test
	public void mortgageAmountTest() {
		assertEquals(90, prop.getMortgageAmount());

	}

	@Test
	public void mortgageTest() {
		prop.mortgage(player);
		// if mortgaged then player receives 90% of price into balance
		assertEquals(1090, player.getNetWorth());
		assertTrue(prop.isMortgaged());
	}

	@Test
	public void redeemAmountTest() {
		assertEquals(110, prop.getRedeemAmount());
	}

	@Test
	public void redeemTest() {
		prop.redeem(player);
		assertEquals(890, player.getNetWorth());
		assertFalse(prop.isMortgaged());
	}

	@Test
	public void rentTest() {
		prop.setRentAmounts(new int[] {10,20,30,40});
		assertEquals(10, prop.getBaseRentAmount());
		assertEquals(40, prop.getAllRentAmounts()[3]);
	}
}
