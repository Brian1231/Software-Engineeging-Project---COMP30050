package client.java.tests;

import client.java.gameObjects.VillainGang;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class VillainGangTest {

    private VillainGang villainGang;

    @Before
    public void setUp() throws Exception {
        villainGang = new VillainGang();
    }

    @After
    public void tearDown() throws Exception {
        villainGang = null;
    }

    @Test
    public void setPosition() {
        assertEquals(0, villainGang.getPosition());
        villainGang.setPosition(10);
        assertEquals(10, villainGang.getPosition());
    }

    @Test
    public void getPosition() {
        assertEquals(0, villainGang.getPosition());
    }

    @Test
    public void setState() {
        assertFalse(villainGang.isActive());
        villainGang.setState(true);
        assertTrue(villainGang.isActive());
    }

    @Test
    public void isActive() {
        assertFalse(villainGang.isActive());
    }
}