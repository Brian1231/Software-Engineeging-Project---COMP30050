package game;

import java.util.Arrays;
import java.util.Random;

import game_interfaces.Rollable;

public class Dice implements Rollable{

	private Random rand;
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
		//int[] dice = {(rand.nextInt(6) + 1), (rand.nextInt(6) + 1)};
		this.diceValues = new int[]{0, 1};
	}
}
