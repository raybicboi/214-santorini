package test.GameBoard;

import main.GameBoard.Board;
import main.GameBoard.Tile;
import org.junit.Before;
import org.junit.Test;
import test.SetupTest;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.*;

public class BoardTest {

    private SetupTest st;

    @Before
    public void setUp() {
        st = new SetupTest();
        st.setUp();
    }

    @Test
    public void testSetters() {
        Tile sample = new Tile(6, 6);
        st.board.addTile(sample);
        assertEquals(st.board.getTileList().size(), 26);
    }

    @Test
    public void testGetters() {
        assertNotSame(st.board.getTileList(), new ArrayList<Tile>());
        assertEquals(st.board.getTileList().size(), 25);
    }

    @Test
    public void testResetBoard() {
        st.t01.build();
        st.t01.build();
        st.t02.build();
        st.w22.relocate(st.t33);

        assert(st.t01.getCurrentLevel() == 2);
        assert(st.t02.getCurrentLevel() == 1);
        assert(st.t03.getCurrentLevel() == 0);
        assertTrue(st.t33.getHasWorker());
        assertEquals(st.board.getTileList().size(), 25);

        st.board.resetBoard();
        assert(st.t01.getCurrentLevel() == 0);
        assert(st.t02.getCurrentLevel() == 0);
        assert(st.t03.getCurrentLevel() == 0);
        assertFalse(st.t33.getHasWorker());
        assertFalse(st.t01.getHasWorker());
    }

    @Test
    public void testConstructor() {
        Board test = new Board();
        assertEquals(test.getTileList(), new ArrayList<Tile>());
    }
}
