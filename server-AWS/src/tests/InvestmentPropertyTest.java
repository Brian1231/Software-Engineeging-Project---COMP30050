package tests;

import game.InvestmentProperty;
import game.Player;
import noc_db.Character_noc;
import noc_db.NOC_Manager;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.io.IOException;

import static org.junit.Assert.*;


public class InvestmentPropertyTest {

	private InvestmentProperty prop;
	private Player player;
	private NOC_Manager noc;



	@Before
	public void setUp() throws IOException {
		prop = new InvestmentProperty("UCD");
		prop.setRentAmounts(new int[]{10,20,30,40});
		prop.setPrice(200);
		prop.setHousePrice(prop.getPrice()/2);
		prop.setHotelPrice(prop.getPrice()/2);
		prop.setRGB(Color.RED);
		prop.setColour("RED");



		noc = new NOC_Manager();
		noc.setup();
		Character_noc ch = noc.getRandomChar();
		player = new Player(1, "1.1.1.1",noc.getRandomChar(), noc.getVehicle(ch.getVehicle()));
		prop.setOwner(player);
	}

	@After
	public void tearDown() {
		prop = null;
		player = null;
		noc = null;
	}


	@Test
	public void constructorTest(){
		assertNotNull(prop);
		assertEquals("UCD", prop.getId());
	}


	@Test
	public void getNumHouses() {
		prop.build(5);
		assertEquals(4, prop.getNumHouses());
	}

	@Test
	public void getNumHotels() {
		prop.build(5);
		assertEquals(1,prop.getNumHotels());
	}

	@Test
	public void getNumHousesAndHotels() {
		prop.build(5);
		assertEquals(5, prop.getNumHousesAndHotels());
	}

	@Test
	public void build() {
		assertNull(prop.getBuildDemolishError());

		prop.build(1);
		assertNull(prop.getBuildDemolishError());
		assertEquals(1, prop.getNumHouses());

		prop.build(2);
		assertNull(prop.getBuildDemolishError());
		assertEquals(3, prop.getNumHouses());
	}


	@Test
	public void demolish() {
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



	@Test
	public void getBuildDemolishError() {
		assertNull(prop.getBuildDemolishError());

		prop.build(-1);
		assertEquals("Error, given wrong input", prop.getBuildDemolishError());
	}

	@Test
	public void setBuildDemolishError() {
		assertNull(prop.getBuildDemolishError());

		prop.build(-1);
		assertNotNull(prop.getBuildDemolishError());
	}

	@Test
	public void getHousePrice() {
		assertEquals(100, prop.getHousePrice());
	}

	@Test
	public void setHousePrice() {
		prop.setHousePrice(150);
		assertEquals(150, prop.getHousePrice());
	}

	@Test
	public void getHotelPrice() {
		assertEquals(100, prop.getHotelPrice());
	}

	@Test
	public void setHotelPrice() {
		prop.setHotelPrice(150);
		assertEquals(150, prop.getHotelPrice());
	}

	@Test
	public void getRentalAmount() {
		assertEquals(10, prop.getRentalAmount());

		prop.build(2);
		assertEquals(30, prop.getRentalAmount());

		prop.demolish(1);
		assertEquals(20, prop.getRentalAmount());
	}

	@Test
	public void setColour() {
		prop.setColour("GRAY");
		assertEquals("GRAY", prop.getColour());
	}

	@Test
	public void getColour() {
		assertEquals("RED", prop.getColour());
	}

	@Test
	public void setRGB() {
		Color col = Color.BLUE;
		prop.setRGB(col.getRed(), col.getGreen(), col.getBlue());
		assertEquals(Color.BLUE, prop.getRGBColour());
	}

	@Test
	public void setRGB1() {
		prop.setRGB(Color.BLUE);
		assertEquals(Color.BLUE, prop.getRGBColour());
	}

	@Test
	public void getRGBColour() {
		assertEquals(Color.RED, prop.getRGBColour());
	}

	@Test
	public void getInfo() {
		prop.setRGB(Color.BLUE);
		try {
			JSONObject obj = prop.getInfo();
			assertEquals(Color.BLUE.getRGB(), obj.get("color"));
			assertEquals(false, obj.get("is_mortgaged"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
