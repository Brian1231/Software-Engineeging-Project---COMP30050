package game_models;

import java.util.Arrays;
import java.util.Random;

import game_interfaces.Rollable;

/*
 * Dice object to simulate players rolling a pair of dice
 * */
public class Dice implements Rollable{

	private final Random rand;
	private int[] diceValues;

	public int[] getDiceValues() {
		return diceValues;
	}

	public int getRollResult() {
		return Arrays.stream(diceValues).sum();
	}

	public Dice() {
		this.rand = new Random();
		diceValues = new int[]{0, 0};
	}

	@Override
	public void roll() {
		this.diceValues = new int[]{(rand.nextInt(6) + 1), (rand.nextInt(6) + 1)};

		// un-comment below line to set dice to 1 for testing
		//this.diceValues = new int[]{0, 1};
	}
}
