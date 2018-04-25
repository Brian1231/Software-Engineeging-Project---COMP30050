package tests;

import game.Player;
import game.TaxSquare;
import noc_db.Character_noc;
import noc_db.NOC_Manager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class TaxSquareTest {
	private TaxSquare tax;
	private Player player;
	private NOC_Manager noc;
	private Character_noc ch;



    @Before
	public void setUp() throws IOException {
		tax = new TaxSquare("UCD");

		noc = new NOC_Manager();
		noc.setup();
		ch = noc.getRandomChar();
		player = new Player(1, "1.1.1.1",noc.getRandomChar(), noc.getVehicle(ch.getVehicle()));
	}

	@After
	public void tearDown() {
		tax = null;
		player = null;
		noc = null;
		ch = null;
	}


	@Test
	public void constructorTest() {
		assertNotNull(tax);
		assertEquals("UCD", tax.getId());
	}

	@Test
	public void activate() {
 //       assertNotNull(tax.activate(player));
	}

	@Test
	public void getIncomePercentage() {
        double percentage = 0.05;
        assertEquals(50, tax.getIncomePercentage(player, percentage));
	}

	@Test
	public void getText() {
//	    assertNotNull(tax.getText(ch, 1));
	}

	@Test
	public void getFlatAmount() {
        int taxAmount = tax.getFlatAmount();
        assertTrue(50<=taxAmount && 300>= taxAmount);
	}

}
