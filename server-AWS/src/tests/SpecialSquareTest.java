package tests;

import game.SpecialSquare;
import org.junit.Test;
import static org.junit.Assert.*;

public class SpecialSquareTest {

	SpecialSquare specialSquare = new SpecialSquare("UCD");

	@Test
	public void constructorTest() {
		assertNotNull(specialSquare);
		assertEquals("UCD", specialSquare.getId());
	}
}
