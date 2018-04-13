package tests;

import static org.junit.Assert.*;

import game.NamedLocation;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

public class NamedLocationTest {

	@Test
	public void nameTest(){
		NamedLocation loc = new NamedLocation("UCD");
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
		NamedLocation loc = new NamedLocation("UCD");
		loc.setLocation(10);

		assertEquals(10, loc.getLocation());
	}

	@Test
	public void getInfoTest() {
		NamedLocation loc = new NamedLocation("UCD");

		try {
			JSONObject obj = loc.getInfo();
			assertEquals("RED", obj.get("color"));
			assertEquals(false, obj.get("is_mortgaged"));
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
}
