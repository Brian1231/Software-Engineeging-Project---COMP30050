package tests;

import static org.junit.Assert.*;

import game.NamedLocation;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.awt.*;

public class NamedLocationTest {

	private final NamedLocation loc = new NamedLocation("UCD");

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
			assertEquals(Color.RED.getRGB(), obj.get("color"));
			assertEquals(false, obj.get("is_mortgaged"));
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
}
