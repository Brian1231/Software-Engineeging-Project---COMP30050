package game_interfaces;

public interface Rollable {

	// get array of the dice values;
	int[] getDiceValues();

	// get the sum of the dice values
	int getRollResult();

	// roll the dice
	void roll();
}
