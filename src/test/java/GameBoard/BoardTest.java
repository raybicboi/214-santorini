package GameBoard;

import GameBoard.Board;
import GameBoard.Tile;
import Testing.SetupTest;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.*;

public class BoardTest {

    private SetupTest st;

    @Before
    public void setUp() {
        st = new SetupTest();
        st.setUp();
    }

    @Test(expected=IndexOutOfBoundsException.class)
    public void testGetters() {
        assertNotSame(st.game.getGameBoard().getTileList(), new ArrayList<Tile>());
        assertEquals(st.game.getGameBoard().getTileList().size(), 25);

        assertNotNull(st.game.getGameBoard().getTile(1, 2));
        assertNull(st.game.getGameBoard().getTile(6, 6));
    }

    @Test
    public void testResetBoard() {
        st.p0.buildTower(0, 1, 0);
//        st.game.switchCurrentPlayer(); // switches current player back to 0
        st.p0.buildTower(0, 1, 0);
//        st.game.switchCurrentPlayer(); // switches current player back to 0
        st.p0.buildTower(0, 2, 1);
        st.p1.relocateWorker(3, 3, 1);

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

    // relocate and initDrop tested when calling dropWorker and relocateWorker (both in here and in Game/Gamelogic test)

    @Test
    public void testConstructor() {
        Board test = new Board();
        assertNotSame(test.getTileList(), new ArrayList<Tile>());
        assertEquals(test.getTileList().size(), 25);
    }
}
