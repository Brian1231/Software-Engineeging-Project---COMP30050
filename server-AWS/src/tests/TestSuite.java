package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	DiceTest.class,
	InvestmentPropertyTest.class,
	NamedLocationTest.class,
	NOC_ManagerTest.class,
	PlayerTest.class,
	RentalPropertyTest.class,
	SpecialSquareTest.class,
	StationTest.class,
	TaxSquareTest.class,
	UtilityTest.class,
	VillainGangTest.class
})

public class TestSuite {
}
