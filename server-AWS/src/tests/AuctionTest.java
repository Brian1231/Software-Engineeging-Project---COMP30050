package tests;

import game.Auction;
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

import static org.junit.Assert.*;

public class AuctionTest {

    private Auction auction;
    private Player player1, player2;
    private NOC_Manager noc;
    private RentalProperty prop;

    @Before
    public void setUp() throws Exception {
        auction = new Auction();

        prop = new RentalProperty("UCD", 100);
        prop.setLocation(7);

        noc = NOC_Manager.getNocManager();
        noc.setup();
        Character_noc ch = noc.getRandomChar();
        player1 = new Player(1, ch, noc.getVehicle(ch.getVehicle()), Color.BLUE);
        prop.setOwner(player1);


        ch = noc.getRandomChar();
        player2 = new Player(2, ch, noc.getVehicle(ch.getVehicle()), Color.RED);

        auction.auction(prop,player2,150);
    }

    @After
    public void tearDown() {
        auction = null;
        player1 = null;
        player2 = null;
        noc = null;
    }

    @Test
    public void constructorTest() {
        assertNotNull(auction);
    }

    @Test
    public void auctionInProgress() {
        assertTrue(auction.auctionInProgress());
    }

    @Test
    public void update() {
        assertTrue(auction.update(player2, 200));
    }

    @Test
    public void getInfo() {
        auction.update(player2, 200);
        try {
            JSONObject obj = this.auction.getInfo();
            assertEquals(player1.getID(), obj.getInt("player_selling"));
            assertEquals(player2.getID(), obj.getInt("player_buying"));
            assertEquals(200, obj.getInt("price"));
            assertEquals(7, obj.getInt("location"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void isValidBid() {
        assertTrue(auction.isValidBid(player2));
    }
}