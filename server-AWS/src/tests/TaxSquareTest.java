package tests;

import game.Player;
import game.TaxSquare;
import noc_db.Character_noc;
import noc_db.Vehicle_noc;
import org.junit.Test;
import static org.junit.Assert.*;

public class TaxSquareTest {
	private TaxSquare tax = new TaxSquare("UCD");
	private String[] info = new String[24];
	private Player player = new Player(1, "1.1.1.1",new Character_noc( info), new Vehicle_noc(info));

	@Test
	public void constructorTest() {
		assertNotNull(tax.getId());
	}

	@Test
	public void incomePercentTest() {
		assertEquals(0, tax.getIncomePercentage(player));

	}

	@Test
	public void flatAmountTest() {
		assertEquals(0, tax.getFlatAmount());

		assertEquals(200, tax.getFlatAmount());
	}
}
