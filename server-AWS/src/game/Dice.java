package game;

import java.util.Random;

public class Dice implements Rollable{

	Random rand = new Random();
	@Override
	public int roll() {
		return (rand.nextInt(6) + 1) + (rand.nextInt(6) + 1);
	}

}
