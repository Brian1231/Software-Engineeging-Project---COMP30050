package game;

import java.util.Arrays;
import java.util.Random;

import game_interfaces.Rollable;
import main.Main;

public class Dice implements Rollable{

	private Random rand = new Random();
	
	@Override
	public int roll() {
		int[] dice = {(rand.nextInt(6) + 1), (rand.nextInt(6) + 1)};
		Main.clientUpdater.updateActionDice(dice);
		return (Arrays.stream(dice).sum());
	}

	public boolean rollDoubles(){
		int[] dice = {(rand.nextInt(6) + 1), (rand.nextInt(6) + 1)};
		Main.clientUpdater.updateActionDice(dice);
		return (dice[0] == dice[1]);
	}
	
}
