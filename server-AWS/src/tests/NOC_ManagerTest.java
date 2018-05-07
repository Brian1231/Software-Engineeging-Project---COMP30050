package tests;

import noc_db.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class NOC_ManagerTest {

    private NOC_Manager noc;

    @Before
    public void setUp() throws IOException {
        noc = NOC_Manager.getNocManager();
        noc.setup();
    }

    @After
    public void tearDown() {
        noc = null;
    }

    @Test
    public void getNocManager() {
        assertNotNull(noc);
    }


    @Test
    public void getRandomChar() {
        assertEquals(Character_noc.class, noc.getRandomChar().getClass());
        Character_noc character = noc.getRandomChar();
        assertNotNull(character);
        assertNotNull(character.getName());
    }

    @Test
    public void getRandomWeapon() {
        assertEquals(Weapon_noc.class, noc.getRandomWeapon().getClass());
        Weapon_noc weapon = noc.getRandomWeapon();
        assertNotNull(weapon);
        assertNotNull(weapon.getWeapon());
    }

    @Test
    public void getWeapon() {
        Character_noc character = noc.getRandomChar();
        assertEquals(character.getWeapon(), noc.getWeapon(character.getWeapon()).getWeapon());
    }

    @Test
    public void getRandomVehicle() {
        assertEquals(Vehicle_noc.class, noc.getRandomVehicle().getClass());
        Vehicle_noc vehicle = noc.getRandomVehicle();
        assertNotNull(vehicle);
        assertNotNull(vehicle.getVehicle());
    }

    @Test
    public void getVehicle() {
        Character_noc character = noc.getRandomChar();
        assertEquals(character.getVehicle(), noc.getVehicle(character.getVehicle()).getVehicle());
    }

    @Test
    public void getRandomActivity() {
        assertEquals(Activity_noc.class, noc.getRandomActivity().getClass());
        Activity_noc activity = noc.getRandomActivity();
        assertNotNull(activity);
        assertNotNull(activity.getActivity());
    }


    @Test
    public void getRandomWorld() {
        assertEquals(World_noc.class, noc.getRandomWorld().getClass());
        World_noc world = noc.getRandomWorld();
        assertNotNull(world);
        assertNotNull(world.getWorld());
    }


    @Test
    public void getRandomClothes() {
        assertEquals(Clothes_noc.class, noc.getRandomClothes().getClass());
        Clothes_noc clothes = noc.getRandomClothes();
        assertNotNull(clothes);
        assertNotNull(clothes.getClothes());
    }
}