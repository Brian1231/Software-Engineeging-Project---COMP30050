package tests;

import static org.junit.Assert.*;

import game.NamedLocation;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

public class NamedLocationTest {

	private NamedLocation loc = new NamedLocation("UCD");

	@Test
	public void constructorTest() {
		assertNotNull(loc);
		assertEquals("UCD", loc.getId());
	}

	@Test
	public void nameTest() {
		assertEquals("UCD", loc.getId());
	}

	@Test
	public void setNameTest() {
		NamedLocation loc = new NamedLocation("Trinity");
		loc.setId("UCD");
		assertEquals("UCD", loc.getId());
	}

	@Test
	public void locationTest() {
		loc.setLocation(10);
		assertEquals(10, loc.getLocation());
	}

	@Test
	public void getInfoTest() {
		try {
			JSONObject obj = loc.getInfo();
			assertEquals("RED", obj.get("color"));
			assertEquals(false, obj.get("is_mortgaged"));
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
}
