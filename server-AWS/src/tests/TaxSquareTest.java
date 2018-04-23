package tests;

import game.Player;
import game.TaxSquare;
import noc_db.Character_noc;
import noc_db.Vehicle_noc;
import org.junit.Test;
import static org.junit.Assert.*;

public class TaxSquareTest {
	private final TaxSquare tax = new TaxSquare("UCD");
	private final String[] info = new String[24];
	private final Player player = new Player(1, "1.1.1.1",new Character_noc( info), new Vehicle_noc(info));

	@Test
	public void constructorTest() {
		assertNotNull(tax);
		assertEquals("UCD", tax.getId());
	}

	@Test
	public void incomePercentTest() {
		double percentage = 0.05;
		assertEquals(50, tax.getIncomePercentage(player, percentage));
	}

	@Test
	public void flatAmountTest() {
		int taxAmount = tax.getFlatAmount();
		assertTrue(50<=taxAmount && 300>= taxAmount);
	}
}
