package tests;

import game_models.Player;
import game_models.VillainGang;
import noc_db.Character_noc;
import noc_db.NOC_Manager;
import noc_db.Weapon_noc;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.io.IOException;

import static org.junit.Assert.*;

public class VillainGangTest {

    private VillainGang villainGang;

    @Before
    public void setUp() {
        villainGang = new VillainGang();
    }

    @After
    public void tearDown() {
        villainGang = null;
    }

    @Test
    public void constructorTest() {
        assertNotNull(villainGang);
        assertEquals(10, villainGang.turnsToLive());
        assertEquals(0, villainGang.position());
    }

    @Test
    public void isActive() {
        assertFalse(villainGang.isActive());
    }

    @Test
    public void position() {
        assertEquals(0, villainGang.position());
    }

    @Test
    public void activate() {
        assertFalse(villainGang.isActive());

        villainGang.activate(15);

        assertTrue(villainGang.isActive());
        assertEquals(15, villainGang.position());
    }

    @Test
    public void deactivate() {
        villainGang.activate(10);
        assertTrue(villainGang.isActive());
        villainGang.deactivate();
        assertFalse(villainGang.isActive());
    }

    @Test
    public void update() {
        villainGang.activate(10);
        assertEquals(10, villainGang.position());

        villainGang.update();
        assertEquals(9, villainGang.turnsToLive());
        assertEquals(11, villainGang.position());
        assertTrue(villainGang.isActive());
    }

    @Test
    public void attackPlayer() throws IOException {
        NOC_Manager noc = NOC_Manager.getNocManager();
        noc.setup();

        Character_noc ch = noc.getRandomChar();

        Player player =  new Player(1,ch, noc.getVehicle(ch.getVehicle()), Color.BLUE);
        player.setVillain(noc.getOpponent(player.getCharacter()));
        Character_noc villain = player.getVillain();
        villain.setWeaponObject(noc.getWeapon(villain.getWeapon()));
        Weapon_noc villainWeapon = villain.getWeaponObject();



        villainGang.activate(10);

        String result = villainGang.attackPlayer(player);
        int amount = player.getDebt();

        String expected = villain.getName() + " steps forward from the gang of villains and immediately begins " +
            villainWeapon.getAffordanceWithTarget(player.getCharacter().getName()) + " " + villainWeapon.getDeterminer() + " " + villainWeapon.getWeapon() + ". "+
            player.getCharacter().getName() + " loses " + amount + " SHM. ";

        assertEquals(expected, result);
    }

    @Test
    public void getInfo() {
        villainGang.activate(10);

        try {
            JSONObject obj = villainGang.getInfo();
            assertTrue(obj.getBoolean("is_active"));
            assertEquals(10, obj.getInt("position"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}