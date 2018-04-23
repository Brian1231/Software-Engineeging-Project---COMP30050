package tests;

import game.InvestmentProperty;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.*;


public class InvestmentPropertyTest {

	private InvestmentProperty prop = new InvestmentProperty("UCD");

	@Test
	public void constructorTest(){
		assertNotNull(prop);
		assertEquals("UCD", prop.getId());
	}

	@Test
	public void housePriceTest() {
		assertEquals(0, prop.getHousePrice());
		prop.setHousePrice(50);
		assertEquals(50, prop.getHousePrice());
	}

	@Test
	public void hotelPriceTest() {
		assertEquals(0, prop.getHotelPrice());
		prop.setHotelPrice(50);
		assertEquals(50, prop.getHotelPrice());
	}

	@Test
	public void colourTest() {
		assertNull(prop.getColour());
		prop.setColour("RED");
		assertEquals("RED", prop.getColour());
	}

	@Test
	public void getInfoTest() {
		prop.setColour("BLUE");
		try {
			JSONObject obj = prop.getInfo();
			assertEquals("BLUE", obj.get("color"));
			assertEquals(false, obj.get("is_mortgaged"));
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void buildTest() {
		assertNull(prop.getBuildDemolishError());

		prop.build(1);
		assertNull(prop.getBuildDemolishError());
		assertEquals(1, prop.getNumHouses());

		prop.build(2);
		assertNull(prop.getBuildDemolishError());
		assertEquals(3, prop.getNumHouses());
	}

	@Test
	public void demolishTest() {
		prop.build(5);
		assertNull(prop.getBuildDemolishError());
		assertEquals(4, prop.getNumHouses());
		assertEquals(1, prop.getNumHotels());


		prop.demolish(3);
		assertEquals(2, prop.getNumHouses());
		assertEquals(0, prop.getNumHotels());

	}

	@Test
	public void buildDemolishErrorTest() {
		assertFalse(prop.build(6));
		assertNotNull(prop.getBuildDemolishError());

		prop.setBuildDemolishError(null);

		assertFalse(prop.build(-1));
		assertNotNull(prop.getBuildDemolishError());

		prop.setBuildDemolishError(null);

		assertFalse(prop.demolish(1));
		assertNotNull(prop.getBuildDemolishError());

		prop.setBuildDemolishError(null);

		prop.build(5);
		assertFalse(prop.demolish(6));
		assertNotNull(prop.getBuildDemolishError());


	}

}
