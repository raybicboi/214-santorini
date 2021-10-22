package test.GameBoard;

import main.GameBoard.Board;
import main.GameBoard.Tile;
import org.junit.Before;
import org.junit.Test;
import test.SetupTest;

import java.util.ArrayList;

import static junit.framework.TestCase.*;

public class BoardTest {

    private SetupTest st;

    @Before
    public void setUp() {
        st = new SetupTest();
        st.setUp();
    }

    @Test
    public void testGetters() {
        assertNotSame(st.game.getGameBoard().getTileList(), new ArrayList<Tile>());
        assertEquals(st.game.getGameBoard().getTileList().size(), 25);
    }

    @Test
    public void testResetBoard() {
        st.game.buildTower(0, 1);
        st.game.buildTower(0, 1);
        st.game.buildTower(0, 2);
        st.game.switchCurrentPlayer(); // sets current player to p1
        st.game.relocateWorker(3, 3, 1);

        assert(st.game.getGameBoard().getTile(0, 1).getCurrentLevel() == 2);
        assert(st.game.getGameBoard().getTile(0, 2).getCurrentLevel() == 1);
        assert(st.game.getGameBoard().getTile(0, 3).getCurrentLevel() == 0);
        assertTrue(st.game.getGameBoard().getTile(3, 3).getHasWorker());
        assertEquals(st.game.getGameBoard().getTileList().size(), 25);

        st.game.getGameBoard().resetBoard();
        assert(st.game.getGameBoard().getTile(0, 1).getCurrentLevel() == 0);
        assert(st.game.getGameBoard().getTile(0, 2).getCurrentLevel() == 0);
        assert(st.game.getGameBoard().getTile(0, 3).getCurrentLevel() == 0);
        assertFalse(st.game.getGameBoard().getTile(3, 3).getHasWorker());
        assertFalse(st.game.getGameBoard().getTile(0, 1).getHasWorker());
    }

    @Test
    public void testConstructor() {
        Board test = new Board();
        assertNotSame(test.getTileList(), new ArrayList<Tile>());
        assertEquals(test.getTileList().size(), 25);
    }
}
