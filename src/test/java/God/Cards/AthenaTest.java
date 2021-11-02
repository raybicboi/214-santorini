package God.Cards;

import GameBoard.Tile;
import God.Cards.Athena;
import Testing.GodSetupTest;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class AthenaTest {

    public GodSetupTest gst;
    public Athena ap0;

    @Before
    public void setUp() {
        gst = new GodSetupTest();
        gst.setUp();
        ap0 = new Athena(gst.game, gst.game.getP0());
    }

    @Test
    public void testIsHigher() {
        Tile oldTile = gst.game.getGameBoard().getTile(1,2);
        Tile newTile = gst.game.getGameBoard().getTile(1,3);
        assertFalse(ap0.isHigher(oldTile, newTile)); // both height of zero

        newTile.build(); // newTile has height of 1
        assertTrue(ap0.isHigher(oldTile, newTile));

        newTile.build(); // newTile has height of 2 (2 more than oldTile)
        assertTrue(ap0.isHigher(oldTile, newTile));

        oldTile.build(); // oldTile height 1, newTile height 2
        assertTrue(ap0.isHigher(oldTile, newTile));

        Tile thirdTile = gst.game.getGameBoard().getTile(1,4);
        assertFalse(ap0.isHigher(oldTile, thirdTile)); // old tile is taller than third tile
    }

    @Test
    public void testSameLevel1() {
        ap0.relocateWorker(0, 1, 0); // same level

        assertTrue(gst.dp1.relocateWorker(4, 3, 0)); // relocating ok
    }

    @Test
    public void testSameLevel2() {
        ap0.relocateWorker(0, 1, 0); // same level

        gst.dp1.buildTower(4, 3, 0);
        assertTrue(gst.dp1.relocateWorker(4, 3, 0)); // moving up okay
    }

    @Test
    public void testSameLevel3() {
        gst.dp1.buildTower(4, 3, 0);
        assertTrue(gst.dp1.relocateWorker(4, 3, 0));

        ap0.relocateWorker(0, 1, 0); // same level
        assertTrue(gst.dp1.relocateWorker(4, 4, 0)); // moving down okay
    }

    @Test
    public void testLowerLevel1() {
        ap0.buildTower(0, 1, 0);
        ap0.relocateWorker(0, 1, 0);
        ap0.relocateWorker(0, 0, 0); // going from level 1 to level 0

        assertTrue(gst.dp1.relocateWorker(4, 3, 0)); // relocating ok
    }

    @Test
    public void testLowerLevel2() {
        ap0.buildTower(0, 1, 0);
        ap0.relocateWorker(0, 1, 0);
//        gst.dp1.relocateWorker(3, 3, 0);
        ap0.relocateWorker(0, 0, 0); // going from level 1 to level 0

        gst.dp1.buildTower(4, 3, 0);
        assertTrue(gst.dp1.relocateWorker(4, 3, 0)); // moving up okay
    }

    @Test
    public void testLowerLevel3() {
        gst.dp1.buildTower(4, 3, 0);
        assertTrue(gst.dp1.relocateWorker(4, 3, 0));

        ap0.buildTower(0, 1, 0);
        ap0.relocateWorker(0, 1, 0);
        ap0.relocateWorker(0, 0, 0); // going from level 1 to level 0
        assertTrue(gst.dp1.relocateWorker(4, 4, 0)); // moving down okay
    }

    @Test
    public void testUpperLevel1() {
        ap0.buildTower(0, 1, 0);
        ap0.relocateWorker(0, 1, 0);// going from level 0 to level 1

        assertTrue(gst.dp1.relocateWorker(4, 3, 0)); // relocating ok
    }

    @Test
    public void testUpperLevel2() {
        ap0.buildTower(0, 1, 0);
        ap0.relocateWorker(0, 1, 0);// going from level 0 to level 1

        gst.dp1.buildTower(4, 3, 0);
        assertFalse(gst.dp1.relocateWorker(4, 3, 0)); // moving up NOT okay
        assertTrue(gst.dp1.relocateWorker(3, 4, 0));
        assertTrue(gst.dp1.relocateWorker(4, 3, 0)); // moving back up after another move is OK
    }

    @Test
    public void testUpperLevel3() {
        gst.dp1.buildTower(4, 3, 0);
        assertTrue(gst.dp1.relocateWorker(4, 3, 0));

        ap0.buildTower(0, 1, 0);
        ap0.relocateWorker(0, 1, 0);// going from level 0 to level 1

        assertTrue(gst.dp1.relocateWorker(4, 4, 0)); // moving back down is okay
    }

    @Test
    public void testBaseLogic() {
        ap0.buildTower(0, 1, 0);
        ap0.relocateWorker(0, 1, 0);// going from level 0 to level 1

        gst.gl1.buildTower(4, 3, 0);
        assertTrue(gst.gl1.relocateWorker(4, 3, 0)); // Athena powers does not apply to base logic
        assertTrue(gst.gl1.relocateWorker(3, 4, 0));
        assertTrue(gst.gl1.relocateWorker(4, 3, 0));
    }

    @Test
    public void testDoubleAthena() {
        ap0.buildTower(0, 1, 0);
        ap0.buildTower(1, 2, 1);
        ap0.relocateWorker(0, 1, 0);// going from level 0 to level 1
        assertFalse(ap0.relocateWorker(1, 2, 1));
        // implementation locks own player from moving up after a move up, but point moot as the game is turn based, so
        // the opposing player will make the move that triggers the athena flag to be false again

        gst.ap1.buildTower(4, 3, 0);
        assertFalse(gst.ap1.relocateWorker(4, 3, 0)); // Athena powers work with 2 Athena
        assertTrue(gst.ap1.relocateWorker(3, 4, 0));
        assertTrue(gst.ap1.relocateWorker(4, 3, 0));
    }
}
