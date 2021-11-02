package GameBoard;

import Testing.SetupTest;
import GameBoard.Tile;
import org.junit.Before;
import org.junit.Test;

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
        st.p1.relocateWorker(2, 3, 1);
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

    @Test
    public void testJumped() {
        assertFalse(st.game.getGameBoard().getTile(0, 4).getHasWorker());
        st.game.getGameBoard().getTile(0, 4).jumped();
        assertTrue(st.game.getGameBoard().getTile(0, 4).getHasWorker());
        st.game.getGameBoard().getTile(0, 4).jumped();
        assertFalse(st.game.getGameBoard().getTile(0, 4).getHasWorker());
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
    public void testBuild() {
        // legal build tile already tested, so I don't need to unit test this
        assertEquals(st.game.getGameBoard().getTile(3, 3).getCurrentLevel(), 0);
        st.game.getGameBoard().getTile(3, 3).build();
        assertEquals(st.game.getGameBoard().getTile(3, 3).getCurrentLevel(), 1);
        st.game.getGameBoard().getTile(3, 3).build();
        assertEquals(st.game.getGameBoard().getTile(3, 3).getCurrentLevel(), 2);
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
