package God.Cards;

import Testing.GodSetupTest;
import GameBoard.Tile;
import God.Cards.Minotaur;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.*;

public class MinotaurTest {

    public GodSetupTest gst;
    public Minotaur mp0;

    @Before
    public void setUp() {
        gst = new GodSetupTest();
        gst.setUp();
        mp0 = new Minotaur(gst.game, gst.game.getP0());
    }

    // not testing shared logic to the base game logic (ie: relocation without pushing)
    // also will not test private helper methods
    @Test
    public void testRelocateFailed() {
        mp0.relocateWorker(1, 1, 0);
        mp0.relocateWorker(1, 2, 0);
        assertFalse(mp0.relocateWorker(1, 3, 0)); // cannot push worker belonging to the same player

        mp0.relocateWorker(2, 2, 0);
        mp0.relocateWorker(3, 3, 0);
        mp0.relocateWorker(3, 4, 0);
        assertFalse(mp0.relocateWorker(4, 4, 0)); // cannot push opposing player off the board

        mp0.relocateWorker(2, 3, 1);
        assertFalse(mp0.relocateWorker(3, 2, 1)); // cannot push opposing worker diagonally

        gst.gl1.relocateWorker(3, 3, 0);
        mp0.relocateWorker(3, 1, 1);
        assertFalse(mp0.relocateWorker(3, 2, 1)); // cannot push worker into another worker
    }

    @Test
    public void testRelocateVert() {
        mp0.relocateWorker(2, 3, 1);
        mp0.relocateWorker(3, 3, 1);
        assertTrue(mp0.relocateWorker(3, 2, 1)); // pushing opposing worker north/south

        assertTrue(gst.game.getGameBoard().getTile(3, 2).getHasWorker());
        Tile pushedTile = gst.game.getP1().findCurrentTile(1);
        assertEquals(pushedTile.getX(), 3);
        assertEquals(pushedTile.getY(), 1);
        assertTrue(gst.game.getGameBoard().getTile(3, 1).getHasWorker());
    }

    @Test
    public void testRelocateHor() {
        mp0.relocateWorker(2, 3, 1);
        mp0.relocateWorker(2, 2, 1);
        assertTrue(mp0.relocateWorker(3, 2, 1)); // pushing opposing worker east/west

        assertTrue(gst.game.getGameBoard().getTile(3, 2).getHasWorker());
        Tile pushedTile = gst.game.getP1().findCurrentTile(1);
        assertEquals(pushedTile.getX(), 4);
        assertEquals(pushedTile.getY(), 2);
        assertTrue(gst.game.getGameBoard().getTile(4, 2).getHasWorker());
    }

    // pushing should also work regardless of level (except for a domed tile)
    @Test
    public void testRelocateLevel() {
        mp0.relocateWorker(2, 3, 1);
        mp0.relocateWorker(2, 2, 1);
        Tile arrival = gst.game.retrieveTile(4, 2);
        arrival.build();
        arrival.build();

        assertTrue(mp0.relocateWorker(3, 2, 1)); // pushing opposing worker east/west onto level 2

        assertTrue(gst.game.getGameBoard().getTile(3, 2).getHasWorker());
        Tile pushedTile = gst.game.getP1().findCurrentTile(1);
        assertEquals(pushedTile.getX(), 4);
        assertEquals(pushedTile.getY(), 2);
        assertTrue(gst.game.getGameBoard().getTile(4, 2).getHasWorker());
    }

    // pushing should also work regardless of level (except for a domed tile)
    @Test
    public void testRelocateDome() {
        mp0.relocateWorker(2, 3, 1);
        mp0.relocateWorker(2, 2, 1);
        Tile arrival = gst.game.retrieveTile(4, 2);
        arrival.build();
        arrival.build();
        arrival.build();
        arrival.build(); // tile built do level 4

        assertFalse(mp0.relocateWorker(3, 2, 1)); // cannot push opposing workers to level 4
    }
}
