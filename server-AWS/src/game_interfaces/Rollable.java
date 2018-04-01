package game_interfaces;

public interface Rollable {

	int roll();

	// additional roll method for use with multiple die or diff number of sides on the die - portal dice maybe??
	int rollDice(int numDice, int numSides);
}
