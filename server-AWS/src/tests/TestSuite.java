package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
		DiceTest.class,
		PlayerTest.class,
		NamedLocationTest.class,
		RentalPropertyTest.class,
		StationTest.class,
		InvestmentPropertyTest.class,
		UtilityTest.class,
		TaxSquareTest.class,
		SpecialSquareTest.class,
		VillainGangTest.class
})

public class TestSuite {
}
