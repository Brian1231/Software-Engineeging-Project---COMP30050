package tests;

import game.Player;
import game.PrivateProperty;
import noc_db.Character_noc;
import noc_db.NOC_Manager;
import noc_db.Vehicle_noc;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.io.IOException;

import static org.junit.Assert.*;

public class PrivatePropertyTest {

	private PrivateProperty prop;
	private NOC_Manager noc = new NOC_Manager();
	private Player player;

	@Before
	public void setUp() throws IOException {
		prop = new PrivateProperty("UCD", 100);
		noc.setup();

		Character_noc ch = noc.getRandomChar();
		player = new Player(1, "1.1.1.1",noc.getRandomChar(), noc.getVehicle(ch.getVehicle()));
	}

	@After
	public void tearDown() {
		prop = null;
		noc = null;
		player = null;
	}


	@Test
	public void constructorTest() {
		assertNotNull(prop);
		assertEquals("UCD", prop.getId());
	}


	@Test
	public void isOwned() {
		assertNull(prop.getOwner());
	}

	@Test
	public void getOwner() {
		prop.setOwner(player);
		assertEquals(player, prop.getOwner());
	}

	@Test
	public void setOwner() {
		assertNull(prop.getOwner());
		prop.setOwner(player);
		assertNotNull(prop.getOwner());
	}

	@Test
	public void setUnOwned() {
		prop.setOwner(player);
		assertNotNull(prop.getOwner());
		prop.setUnOwned();
		assertNull(prop.getOwner());
	}

	@Test
	public void getPrice() {
		assertEquals(100, prop.getPrice());
	}

	@Test
	public void setPrice() {
		prop.setPrice(200);
		assertEquals(200, prop.getPrice());
	}

	@Test
	public void getNumInGroup() {
		prop.setNumInGroup(3);
		assertEquals(3, prop.getNumInGroup());
	}

	@Test
	public void setNumInGroup() {
		assertEquals(0, prop.getNumInGroup());
		prop.setNumInGroup(3);
		assertEquals(3,prop.getNumInGroup());
	}

	@Test
	public void getType() {
		prop.setType("Private");
		assertEquals("Private", prop.getType());
	}

	@Test
	public void setType() {
		assertNull(prop.getType());
		prop.setType("Private");
		assertEquals("Private", prop.getType());
	}

	@Test
	public void getColor() {
		assertEquals(Color.GRAY, prop.getColor());
	}

	@Test
	public void getInfo() {
		prop.setPrice(200);
		prop.setOwner(player);
		prop.setLocation(20);
		try {
			JSONObject obj = this.prop.getInfo();
			assertEquals("UCD", obj.get("id"));
			assertEquals(200, obj.get("price"));
			assertEquals(20, obj.get("location"));
			assertEquals(Color.GRAY.getRGB(), obj.get("color"));
			assertFalse(obj.getBoolean("is_mortgaged"));
			assertEquals(player.getID(), obj.get("owner"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
