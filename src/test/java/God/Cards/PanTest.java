package God.Cards;

import Testing.GodSetupTest;
import GameSystem.Game;
import God.Cards.Pan;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.*;

public class PanTest {

    public GodSetupTest gst;
    public Pan pp0;

    @Before
    public void setUp() {
        gst = new GodSetupTest();
        gst.setUp();
        pp0 = new Pan(gst.game, gst.game.getP0());
    }

    // No need to test relocate/winner logic that overlaps with base game logic since logic is built on top of that
    @Test
    public void testMoveUp() {
        pp0.buildTower(1, 1, 0); // tile height 1
        pp0.relocateWorker(1, 1, 0); // move up 1 level
        assertFalse(pp0.getPanFlag());
        assertNull(pp0.getWinner(gst.gl1)); // use base game logic as general case (but worker for all god cards)

        pp0.relocateWorker(1, 0, 0);
        pp0.buildTower(1, 1, 0);
        pp0.relocateWorker(1, 1, 0); // move up 2 levels
        assertFalse(pp0.getPanFlag());
        assertNull(pp0.getWinner(gst.gl1));
    }

    @Test
    public void testMoveLat() {
        pp0.relocateWorker(1, 1, 0); // move same level
        assertFalse(pp0.getPanFlag());
        assertNull(pp0.getWinner(gst.gl1));
    }

    @Test
    public void testMoveDown() {
        pp0.buildTower(1, 1, 0); // tile height 1
        pp0.relocateWorker(1, 1, 0); // move to level 1
        pp0.relocateWorker(1, 0, 0); // move from 1 to 0
        assertFalse(pp0.getPanFlag());
        assertNull(pp0.getWinner(gst.gl1)); // use base game logic as general case (but worker for all god cards)

        pp0.relocateWorker(0, 0, 0);
        pp0.buildTower(1, 1, 0); // level 2
        pp0.buildTower(1, 0, 0); // level 1
        pp0.relocateWorker(1, 0, 0);
        pp0.relocateWorker(1, 1, 0);
        pp0.relocateWorker(0, 0, 0); // move from 2 to 0

        assertTrue(pp0.getPanFlag());
        assertEquals(pp0.getWinner(gst.dp1), gst.game.getP0());
    }

    @Test
    public void testConstructor() {
        Game newGame = new Game();
        Pan testPan = new Pan(newGame, newGame.getP1());
        assertFalse(testPan.getPanFlag());
    }
}
