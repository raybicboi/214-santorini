package God.Cards;

import Testing.GodSetupTest;
import God.Cards.Demeter;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.*;

public class DemeterTest {

    public GodSetupTest gst;
    public Demeter dp0;

    @Before
    public void setUp() {
        gst = new GodSetupTest();
        gst.setUp();
        dp0 = new Demeter(gst.game, gst.game.getP0());
    }

    @Test
    public void testBuildTower() {
        assertTrue(dp0.buildTower(1, 0, 0));
        assertEquals(dp0.getFirstX(), 1);
        assertEquals(dp0.getFirstY(), 0);

        assertTrue(dp0.buildTower(0, 1, 0));
        assertEquals(dp0.getFirstX(), 0);
        assertEquals(dp0.getFirstY(), 1);
    }

    @Test
    public void testBuildTower2() {
        assertTrue(dp0.buildTower2(-1, -1, 0)); // skips the second build

        dp0.buildTower(1, 0, 0);
        assertEquals(dp0.getFirstX(), 1);
        assertEquals(dp0.getFirstY(), 0);
        assertFalse(dp0.buildTower2(1, 0, 0)); // second build cannot be the same level as the first

        assertTrue(dp0.buildTower2(0, 1, 0));
        assertEquals(gst.game.getGameBoard().getTile(0, 1).getCurrentLevel(), 1); // make sure build succeeded
        assertEquals(dp0.getFirstX(), -1); // not that x and y resets
        assertEquals(dp0.getFirstY(), -1);

        // rest of the building behavior is same as base logic, so no need to re-test build tower 2 as extensively as
        // in the game logic test class
    }

    @Test
    public void testConstructor() {
        assertEquals(dp0.getFirstX(), -1);
        assertEquals(dp0.getFirstY(), -1);
    }
}
