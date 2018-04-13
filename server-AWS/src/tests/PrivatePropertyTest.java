package tests;

import game.Player;
import game.PrivateProperty;
import noc_db.Character_noc;
import noc_db.Vehicle_noc;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

public class PrivatePropertyTest {

	private PrivateProperty prop = new PrivateProperty("UCDPrivate", 100);
	private String[] info = new String[24];
	private Player player = new Player(1, "1.1.1.1",new Character_noc( info), new Vehicle_noc(info));

	@Test
	public void constructorTest() {
		assertNotNull(prop.getId());
		assertNotNull(prop.getPrice());
	}

	@Test
	public void ownedTest() {
		assertEquals(false, prop.isOwned());
		prop.setOwner(player);
		assertEquals(true, prop.isOwned());
	}

	@Test
	public void unownedTest(){
		prop.setOwner(player);
		assertEquals(true, prop.isOwned());
		prop.setUnOwned();
		assertEquals(false, prop.isOwned());
	}

	@Test
	public void ownerTest() {
		assertNull(prop.getOwner());
		prop.setOwner(player);
		assertEquals(player, prop.getOwner());
	}

	@Test
	public void priceTest() {
		assertEquals(100, prop.getPrice());
	}

	@Test
	public void numInGroupTest() {
		assertEquals(0,prop.getNumInGroup());
		prop.setNumInGroup(3);
		assertEquals(3,prop.getNumInGroup());
	}

	@Test
	public void typeTest() {
		assertNull(prop.getType());
		prop.setType("private property");
		assertEquals("private property", prop.getType());
	}

	@Test
	public void getInfoTest() {
		try {
			JSONObject obj = prop.getInfo();
			assertEquals("UCDPrivate", obj.get("id"));
			assertEquals("GRAY", obj.get("color"));
			assertEquals(false, obj.get("is_mortgaged"));
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
}
