package tests;

import static org.junit.Assert.*;

import game.NamedLocation;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

public class NamedLocationTest {

	private NamedLocation loc;

	@Before
	public void setUp() {
		loc = new NamedLocation("UCD");
		loc.setLocation(10);
	}

	@After
	public void tearDown() {
		loc = null;
	}

	@Test
	public void constructorTest() {
		assertNotNull(loc);
	}


    @Test
    public void getId() {
		assertEquals("UCD", loc.getId());
    }

    @Test
    public void setId() {
		loc.setId("Trinity");
		assertEquals("Trinity", loc.getId());
    }

    @Test
    public void getColor() {
		assertEquals(Color.RED, loc.getColor());
    }

    @Test
    public void getInfo() {
		try {
			JSONObject obj = loc.getInfo();
			assertEquals(Color.RED.getRGB(), obj.get("color"));
			assertEquals(false, obj.get("is_mortgaged"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
    }

    @Test
    public void getLocation() {
		assertEquals(10, loc.getLocation());
    }

    @Test
    public void setLocation() {
		loc.setLocation(20);
		assertEquals(20, loc.getLocation());
    }
}
