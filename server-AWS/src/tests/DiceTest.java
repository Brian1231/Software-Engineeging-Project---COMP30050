package tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import game_models.Dice;

public class DiceTest {
	private Dice dice;


	@Before
	public void setUp() {
		dice = new Dice();
	}

	@After
	public void tearDown(){
		dice = null;
	}

	@Test
	public void constructorTest() {
		assertNotNull(dice);
	}

	@Test
	public void roll() {

		int[] distribution = new int[11];
		for(int i=0;i<distribution.length;i++){
			distribution[i] = 0;
		}
		int TOTAL_ROLLS = 1000000;
		for(int i = 0; i< TOTAL_ROLLS; i++){
			dice.roll();
			int res = dice.getRollResult();
			distribution[res-2]++;
		}


		//Test 2 and 12 distribution
		int min = (int) (TOTAL_ROLLS /36 - 0.05* TOTAL_ROLLS /36);
		int max = (int) (TOTAL_ROLLS /36 + 0.05* TOTAL_ROLLS /36);
		assertTrue(min <= distribution[0] && distribution[0] <= max);
		assertTrue(min <= distribution[10] && distribution[10] <= max);

		//Test 3 and 11 distribution
		min = (int) (TOTAL_ROLLS /18 - 0.05* TOTAL_ROLLS /18);
		max = (int) (TOTAL_ROLLS /18 + 0.05* TOTAL_ROLLS /18);
		assertTrue(min <= distribution[1] && distribution[1] <= max);
		assertTrue(min <= distribution[9] && distribution[9] <= max);

		//Test 4 and 10 distribution
		min = (int) (TOTAL_ROLLS /12 - 0.05* TOTAL_ROLLS /12);
		max = (int) (TOTAL_ROLLS /12 + 0.05* TOTAL_ROLLS /12);
		assertTrue(min <= distribution[2] && distribution[2] <= max);
		assertTrue(min <= distribution[8] && distribution[8] <= max);

		//Test 5 and 9 distribution
		min = (int) (TOTAL_ROLLS /9 - 0.05* TOTAL_ROLLS /9);
		max = (int) (TOTAL_ROLLS /9 + 0.05* TOTAL_ROLLS /9);
		assertTrue(min <= distribution[3] && distribution[3] <= max);
		assertTrue(min <= distribution[7] && distribution[7] <= max);

		//Test 6 and 8 distribution
		min = (int) (TOTAL_ROLLS *5/36 - 0.05* TOTAL_ROLLS *5/36);
		max = (int) (TOTAL_ROLLS *5/36 + 0.05* TOTAL_ROLLS *5/36);
		assertTrue(min <= distribution[4] && distribution[4] <= max);
		assertTrue(min <= distribution[6] && distribution[6] <= max);

		//Test 7 distribution
		min = (int) (TOTAL_ROLLS /6 - 0.05* TOTAL_ROLLS /6);
		max = (int) (TOTAL_ROLLS /6 + 0.05* TOTAL_ROLLS /6);
		assertTrue(min <= distribution[5] && distribution[5] <= max);
	}
}
