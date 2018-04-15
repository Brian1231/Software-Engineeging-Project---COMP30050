package game;

import java.util.Random;

import game_interfaces.Rollable;

public class Dice implements Rollable{

	private Random rand = new Random();
	@Override
	public int roll() {
		return (rand.nextInt(6) + 1) + (rand.nextInt(6) + 1);
	}

	public boolean rollDoubles(){
		return (rand.nextInt(6) + 1) == (rand.nextInt(6) + 1);
	}
	
	// non-standard dice rolls
	@Override
	public int rollDice(int numDice, int numSides) {
		int sum = 0;
		for (int i = 0; i < numDice; i++)
			sum += rand.nextInt(numSides)+1;
		return sum;
	}
	
}
