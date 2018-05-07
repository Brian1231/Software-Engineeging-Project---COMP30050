package tests;


import game_models.Player;
import game_models.RentalProperty;
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


public class RentalPropertyTest {

	private RentalProperty prop;
	private Player player;
	private NOC_Manager noc;


    @Before
    public void setUp() throws IOException {
        prop = new RentalProperty("UCD", 100);
        prop.setRentAmounts(new int[]{10,20,30,40});
        prop.setMortgageAmount(50);


        noc = NOC_Manager.getNocManager();
        noc.setup();
        Character_noc ch = noc.getRandomChar();
        player = new Player(1,ch, noc.getVehicle(ch.getVehicle()), Color.BLUE);
        prop.setOwner(player);

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
		assertEquals("rental", prop.getType());
	}



    @Test
    public void getMortgageAmount() {
        assertEquals(50, prop.getMortgageAmount());
    }

    @Test
    public void setMortgageAmount() {
        prop.setMortgageAmount(110);
        assertEquals(110, prop.getMortgageAmount());
    }

    @Test
    public void mortgage() {
        prop.mortgage(player);
        assertTrue(prop.isMortgaged());
    }

    @Test
    public void redeem() {
        prop.redeem(player);
        assertFalse(prop.isMortgaged());
    }

    @Test
    public void getRedeemAmount() {
        assertEquals(55, prop.getRedeemAmount());
    }

    @Test
    public void isMortgaged() {
        assertFalse(prop.isMortgaged());
        prop.mortgage(player);
        assertTrue(prop.isMortgaged());
    }

    @Test
    public void setMortgageStatus() {
        assertFalse(prop.isMortgaged());
        prop.setMortgageStatus(true);
        assertTrue(prop.isMortgaged());
    }

    @Test
    public void getBaseRentAmount() {
        assertEquals(10, prop.getBaseRentAmount());
    }

    @Test
    public void setRentAmounts() {
        prop.setRentAmounts(new int[]{50,60,70,80});
        assertEquals(50, prop.getBaseRentAmount());
    }

    @Test
    public void getAllRentAmounts() {
        int rents[] = {10,20,30,40};
        assertEquals(rents[0], prop.getAllRentAmounts()[0]);
        assertEquals(rents[1], prop.getAllRentAmounts()[1]);
        assertEquals(rents[2], prop.getAllRentAmounts()[2]);
        assertEquals(rents[3], prop.getAllRentAmounts()[3]);
    }

    @Test
    public void hasTrap() {
        assertFalse(prop.hasTrap());
        prop.setTrap();
        assertTrue(prop.hasTrap());
    }

    @Test
    public void setTrap() {
        String result = prop.getOwner().getCharName() + " paid " + (prop.getPrice()/5) + " SHM and set a trap at " + prop.getId() + ". ";
        assertEquals(result, prop.setTrap());
    }

    @Test
    public void activateTrap() {
        Character_noc ch = noc.getRandomChar();
        Player player2 = new Player(2,noc.getRandomChar(), noc.getVehicle(ch.getVehicle()), Color.BLUE);
        String result = player2.getCharName() + " activated " + prop.getOwner().getCharName() + "'s trap and now owes them an additional " + prop.getTrapFineAmount() + " SHM. ";
        assertEquals(result, prop.activateTrap(player2));
    }

    @Test
    public void getInfo() {

        prop.setPrice(150);
        prop.setOwner(player);
        prop.setLocation(15);
        prop.setColour(Color.GRAY);

        try {
            JSONObject obj = this.prop.getInfo();
            assertEquals("UCD", obj.get("id"));
            assertEquals(150, obj.get("price"));
            assertEquals(15, obj.get("location"));
            assertEquals(Color.GRAY.getRGB(), obj.get("color"));
            assertEquals(false, obj.get("is_mortgaged"));
            assertEquals(player.getID(), obj.get("owner"));
            assertFalse(obj.getBoolean("hasTrap"));
            assertFalse(obj.getBoolean("is_mortgaged"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
