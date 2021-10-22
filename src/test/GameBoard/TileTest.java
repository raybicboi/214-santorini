package test.GameBoard;

import main.GameBoard.Tile;
import org.junit.Before;
import org.junit.Test;
import test.SetupTest;

import static junit.framework.TestCase.*;
import static org.junit.Assert.assertNotEquals;

public class TileTest {

    private SetupTest st;

    @Before
    public void setUp() {
        st = new SetupTest();
        st.setUp();
    }

    @Test
    public void testGetters() {
        assertEquals(st.game.getGameBoard().getTile(0, 1).getX(), 0);
        assertEquals(st.game.getGameBoard().getTile(0, 1).getY(), 1);
        assertNotEquals(st.game.getGameBoard().getTile(0, 1).getX(), 3);
        assertNotEquals(st.game.getGameBoard().getTile(0, 1).getY(), 4);

        assertEquals(st.game.getGameBoard().getTile(0, 1).getCurrentLevel(), 0);
        st.game.getGameBoard().getTile(0, 1).build();
        assertEquals(st.game.getGameBoard().getTile(0, 1).getCurrentLevel(), 1);

        assertFalse(st.game.getGameBoard().getTile(2, 3).getHasWorker());
        assertTrue(st.game.getGameBoard().getTile(3, 2).getHasWorker());

        st.game.switchCurrentPlayer(); // switch to player 1
        st.game.relocateWorker(2, 3, 1);
        assertTrue(st.game.getGameBoard().getTile(2, 3).getHasWorker());
        assertFalse(st.game.getGameBoard().getTile(3, 2).getHasWorker());
    }

    @Test
    public void testSetters() {
        assertTrue(st.game.getGameBoard().getTile(3, 2).getHasWorker());
        st.game.getGameBoard().getTile(3, 2).jumped();
        assertFalse(st.game.getGameBoard().getTile(3, 2).getHasWorker());
        st.game.getGameBoard().getTile(3, 2).jumped();
        assertTrue(st.game.getGameBoard().getTile(3, 2).getHasWorker());

        assertEquals(st.game.getGameBoard().getTile(3, 3).getCurrentLevel(), 0);
        st.game.getGameBoard().getTile(3, 3).build();
        st.game.getGameBoard().getTile(3, 3).build();
        assertEquals(st.game.getGameBoard().getTile(3, 3).getCurrentLevel(), 2);

    }

    @Test // more extensive than is legal build tile
    public void testIsLegalMoveTile() {
        st.game.getGameBoard().setCurrentTile(0, 1);
        assertTrue(st.game.getGameBoard().isLegalMoveTile(st.game.getGameBoard().getTile(1, 2)));
        assertTrue(st.game.getGameBoard().isLegalMoveTile(st.game.getGameBoard().getTile(1, 0)));
        assertTrue(st.game.getGameBoard().isLegalMoveTile(st.game.getGameBoard().getTile(0, 2)));
        assertFalse(st.game.getGameBoard().isLegalMoveTile(st.game.getGameBoard().getTile(3, 3))); // not adjacent
        assertFalse(st.game.getGameBoard().isLegalMoveTile(st.game.getGameBoard().getTile(0, 1))); // own tile
        st.game.getGameBoard().setCurrentTile(1, 2);
        assertFalse(st.game.getGameBoard().isLegalMoveTile(st.game.getGameBoard().getTile(1, 3))); // already has another worker

        st.game.getGameBoard().setCurrentTile(4, 1);
        assertTrue(st.game.getGameBoard().isLegalMoveTile(st.game.getGameBoard().getTile(4, 2)));
        st.game.getGameBoard().getTile(4, 2).build();
        st.game.getGameBoard().getTile(4, 2).build();
        st.game.getGameBoard().getTile(4, 2).build();
        st.game.getGameBoard().getTile(4, 2).build();
        assertFalse(st.game.getGameBoard().isLegalMoveTile(st.game.getGameBoard().getTile(4, 2))); // tower already domed

        st.game.getGameBoard().getTile(4, 1).build();
        st.game.getGameBoard().getTile(4, 1).build();
        assertTrue(st.game.getGameBoard().isLegalMoveTile(st.game.getGameBoard().getTile(3, 1))); // can go down two levels
        st.game.getGameBoard().setCurrentTile(3, 1);
        assertTrue(st.game.getGameBoard().isLegalMoveTile(st.game.getGameBoard().getTile(4, 1))); // cannot go up two levels

        st.game.getGameBoard().getTile(3, 0).build();
        assertTrue(st.game.getGameBoard().isLegalMoveTile(st.game.getGameBoard().getTile(3, 0))); // can go up a single level
    }

    @Test
    public void testIsLegalBuildTile() {

        st.game.getGameBoard().setCurrentTile(0, 1);
        assertTrue(st.game.getGameBoard().isLegalBuildTile(st.game.getGameBoard().getTile(1, 2)));
        assertTrue(st.game.getGameBoard().isLegalBuildTile(st.game.getGameBoard().getTile(1, 0)));
        assertTrue(st.game.getGameBoard().isLegalBuildTile(st.game.getGameBoard().getTile(0, 2)));
        assertFalse(st.game.getGameBoard().isLegalBuildTile(st.game.getGameBoard().getTile(3, 3))); // not adjacent
        assertFalse(st.game.getGameBoard().isLegalBuildTile(st.game.getGameBoard().getTile(0, 1))); // own tile
        st.game.getGameBoard().setCurrentTile(1, 2);
        assertFalse(st.game.getGameBoard().isLegalBuildTile(st.game.getGameBoard().getTile(1, 3))); // already has a worker

        st.game.getGameBoard().setCurrentTile(4, 1);
        assertTrue(st.game.getGameBoard().isLegalBuildTile(st.game.getGameBoard().getTile(4, 2)));
        st.game.getGameBoard().getTile(4, 2).build();
        st.game.getGameBoard().getTile(4, 2).build();
        st.game.getGameBoard().getTile(4, 2).build();
        st.game.getGameBoard().getTile(4, 2).build();
        assertTrue(st.game.getGameBoard().isLegalBuildTile(st.game.getGameBoard().getTile(4, 2))); // tower is already domed
    }

    // for use case/naming convention purposes, isValidTile is a separate method
    @Test // legal move tile assertions suffice, so I will repeat the same tests
    public void testIsValidTile() {
        st.game.getGameBoard().setCurrentTile(0, 1);
        assertTrue(st.game.getGameBoard().isValidTile(st.game.getGameBoard().getTile(1, 2)));
        assertTrue(st.game.getGameBoard().isValidTile(st.game.getGameBoard().getTile(1, 0)));
        assertTrue(st.game.getGameBoard().isValidTile(st.game.getGameBoard().getTile(0, 2)));
        assertFalse(st.game.getGameBoard().isValidTile(st.game.getGameBoard().getTile(3, 3))); // not adjacent
        assertFalse(st.game.getGameBoard().isValidTile(st.game.getGameBoard().getTile(0, 1))); // own tile
        st.game.getGameBoard().setCurrentTile(1, 2);
        assertFalse(st.game.getGameBoard().isValidTile(st.game.getGameBoard().getTile(1, 3))); // already has another worker

        st.game.getGameBoard().setCurrentTile(4, 1);
        assertTrue(st.game.getGameBoard().isValidTile(st.game.getGameBoard().getTile(4, 2)));
        st.game.getGameBoard().getTile(4, 2).build();
        st.game.getGameBoard().getTile(4, 2).build();
        st.game.getGameBoard().getTile(4, 2).build();
        st.game.getGameBoard().getTile(4, 2).build();
        assertFalse(st.game.getGameBoard().isValidTile(st.game.getGameBoard().getTile(4, 2))); // tower already domed

        st.game.getGameBoard().getTile(4, 1).build();
        st.game.getGameBoard().getTile(4, 1).build();
        assertTrue(st.game.getGameBoard().isValidTile(st.game.getGameBoard().getTile(3, 1))); // can go down two levels
        st.game.getGameBoard().setCurrentTile(3, 1);
        assertTrue(st.game.getGameBoard().isValidTile(st.game.getGameBoard().getTile(4, 1))); // cannot go up two levels

        st.game.getGameBoard().getTile(3, 0).build();
        assertTrue(st.game.getGameBoard().isValidTile(st.game.getGameBoard().getTile(3, 0))); // can go up a single level
    }

    @Test
    public void testResetTile() {
        assertTrue(st.game.getGameBoard().getTile(3, 2).getHasWorker());
        assertFalse(st.game.getGameBoard().getTile(3, 3).getHasWorker());
        st.game.getGameBoard().getTile(3, 2).resetTile();
        st.game.getGameBoard().getTile(3, 3).resetTile();
        assertFalse(st.game.getGameBoard().getTile(3, 2).getHasWorker());
        assertFalse(st.game.getGameBoard().getTile(3, 3).getHasWorker());

        assertEquals(st.game.getGameBoard().getTile(4,2).getCurrentLevel(), 0);
        assertEquals(st.game.getGameBoard().getTile(4, 3).getCurrentLevel(), 0);
        st.game.getGameBoard().getTile(4, 2).build();
        st.game.getGameBoard().getTile(4, 2).build();
        st.game.getGameBoard().getTile(4, 2).build();
        assertEquals(st.game.getGameBoard().getTile(4, 2).getCurrentLevel(), 3);

        st.game.getGameBoard().getTile(4, 2).resetTile();
        st.game.getGameBoard().getTile(4, 3).resetTile();
        assertEquals(st.game.getGameBoard().getTile(4, 2).getCurrentLevel(), 0);
        assertEquals(st.game.getGameBoard().getTile(4, 3).getCurrentLevel(), 0);
    }

    @Test
    public void testTileConstructor() {
        Tile test = new Tile(5, 3);
        assertEquals(test.getX(), 5);
        assertEquals(test.getY(), 3);
        assertEquals(test.getCurrentLevel(), 0);
        assertFalse(test.getHasWorker());

    }
}
