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

    @Test(expected=IndexOutOfBoundsException.class)
    public void testGetters() {
        assertNotSame(st.game.getGameBoard().getTileList(), new ArrayList<Tile>());
        assertEquals(st.game.getGameBoard().getTileList().size(), 25);

        assertNotNull(st.game.getGameBoard().getTile(1, 2));
        assertNull(st.game.getGameBoard().getTile(6, 6));
    }

    @Test
    public void testBuild() {
        assertEquals(st.game.getGameBoard().getTile(1, 2).getCurrentLevel(), 0);
        assertTrue(st.game.getGameBoard().isLegalBuildTile(st.game.getGameBoard().getTile(1, 1), 0, st.game.getP0()));
        st.game.getGameBoard().build(1, 1, 0, st.game.getP0());
        assertEquals(st.game.getGameBoard().getTile(1, 1).getCurrentLevel(), 1);
        st.game.getGameBoard().build(1, 1, 0, st.game.getP0());
        assertEquals(st.game.getGameBoard().getTile(1, 1).getCurrentLevel(), 2);

        assertFalse(st.game.getGameBoard().build(1, 2, 0, st.game.getP0())); // cannot build here
    }

    @Test // more extensive than is legal build tile
    public void testIsLegalMoveTile() {
        st.game.relocateWorker(0, 1, 0);
        assertTrue(st.game.getGameBoard().isLegalMoveTile(st.game.getGameBoard().getTile(1, 2), 0, st.game.getP0()));
        assertTrue(st.game.getGameBoard().isLegalMoveTile(st.game.getGameBoard().getTile(1, 0), 0, st.game.getP0()));
        assertTrue(st.game.getGameBoard().isLegalMoveTile(st.game.getGameBoard().getTile(0, 2), 0, st.game.getP0()));
        assertFalse(st.game.getGameBoard().isLegalMoveTile(st.game.getGameBoard().getTile(3, 3), 0, st.game.getP0())); // not adjacent
        assertFalse(st.game.getGameBoard().isLegalMoveTile(st.game.getGameBoard().getTile(0, 1), 0, st.game.getP0())); // own tile
        st.game.relocateWorker(1, 2, 0);
        assertFalse(st.game.getGameBoard().isLegalMoveTile(st.game.getGameBoard().getTile(1, 3), 0, st.game.getP0())); // already has another worker

        st.game.switchCurrentPlayer();
        st.game.relocateWorker(4, 1, 1);
        assertTrue(st.game.getGameBoard().isLegalMoveTile(st.game.getGameBoard().getTile(4, 2), 1, st.game.getP1()));
        st.game.getGameBoard().getTile(4, 2).build();
        st.game.getGameBoard().getTile(4, 2).build();
        st.game.getGameBoard().getTile(4, 2).build();
        st.game.getGameBoard().getTile(4, 2).build();
        assertFalse(st.game.getGameBoard().isLegalMoveTile(st.game.getGameBoard().getTile(4, 2), 1, st.game.getP1())); // tower already domed

        st.game.getGameBoard().getTile(4, 1).build();
        st.game.getGameBoard().getTile(4, 1).build();
        assertTrue(st.game.getGameBoard().isLegalMoveTile(st.game.getGameBoard().getTile(3, 1), 1, st.game.getP1())); // can go down two levels
        st.game.relocateWorker(3, 1, 1);
        assertFalse(st.game.getGameBoard().isLegalMoveTile(st.game.getGameBoard().getTile(4, 1), 1, st.game.getP1())); // cannot go up two levels

        st.game.getGameBoard().getTile(3, 0).build();
        assertTrue(st.game.getGameBoard().isLegalMoveTile(st.game.getGameBoard().getTile(3, 0), 1, st.game.getP1())); // can go up a single level
    }

    @Test
    public void testIsLegalBuildTile() {

        st.game.relocateWorker(0, 1, 0);
        assertTrue(st.game.getGameBoard().isLegalBuildTile(st.game.getGameBoard().getTile(1, 2), 0, st.game.getP0()));
        assertTrue(st.game.getGameBoard().isLegalBuildTile(st.game.getGameBoard().getTile(1, 0), 0, st.game.getP0()));
        assertTrue(st.game.getGameBoard().isLegalBuildTile(st.game.getGameBoard().getTile(0, 2), 0, st.game.getP0()));
        assertFalse(st.game.getGameBoard().isLegalBuildTile(st.game.getGameBoard().getTile(3, 3), 0, st.game.getP0())); // not adjacent
        assertFalse(st.game.getGameBoard().isLegalBuildTile(st.game.getGameBoard().getTile(0, 1), 0, st.game.getP0())); // own tile
        st.game.relocateWorker(1, 2, 0);
        assertFalse(st.game.getGameBoard().isLegalBuildTile(st.game.getGameBoard().getTile(1, 3), 0, st.game.getP0())); // already has another worker

        st.game.switchCurrentPlayer();
        st.game.relocateWorker(4, 1, 1);
        assertTrue(st.game.getGameBoard().isLegalBuildTile(st.game.getGameBoard().getTile(4, 2), 1, st.game.getP1()));
        st.game.getGameBoard().getTile(4, 2).build();
        st.game.getGameBoard().getTile(4, 2).build();
        st.game.getGameBoard().getTile(4, 2).build();
        st.game.getGameBoard().getTile(4, 2).build();
        assertFalse(st.game.getGameBoard().isLegalBuildTile(st.game.getGameBoard().getTile(4, 2), 1, st.game.getP1())); // tower already domed
    }

    // for use case/naming convention purposes, isValidTile is a separate method
    @Test // legal move tile assertions suffice, so I will repeat the same tests
    public void testIsValidTile() {
        st.game.relocateWorker(0, 1, 0);
        assertTrue(st.game.getGameBoard().isValidTile(st.game.getGameBoard().getTile(1, 2), 0, st.game.getP0()));
        assertTrue(st.game.getGameBoard().isValidTile(st.game.getGameBoard().getTile(1, 0), 0, st.game.getP0()));
        assertTrue(st.game.getGameBoard().isValidTile(st.game.getGameBoard().getTile(0, 2), 0, st.game.getP0()));
        assertFalse(st.game.getGameBoard().isValidTile(st.game.getGameBoard().getTile(3, 3), 0, st.game.getP0())); // not adjacent
        assertFalse(st.game.getGameBoard().isValidTile(st.game.getGameBoard().getTile(0, 1), 0, st.game.getP0())); // own tile
        st.game.relocateWorker(1, 2, 0);
        assertFalse(st.game.getGameBoard().isValidTile(st.game.getGameBoard().getTile(1, 3), 0, st.game.getP0())); // already has another worker

        st.game.switchCurrentPlayer();
        st.game.relocateWorker(4, 1, 1);
        assertTrue(st.game.getGameBoard().isValidTile(st.game.getGameBoard().getTile(4, 2), 1, st.game.getP1()));
        st.game.getGameBoard().getTile(4, 2).build();
        st.game.getGameBoard().getTile(4, 2).build();
        st.game.getGameBoard().getTile(4, 2).build();
        st.game.getGameBoard().getTile(4, 2).build();
        assertFalse(st.game.getGameBoard().isValidTile(st.game.getGameBoard().getTile(4, 2), 1, st.game.getP1())); // tower already domed

        st.game.getGameBoard().getTile(4, 1).build();
        st.game.getGameBoard().getTile(4, 1).build();
        assertTrue(st.game.getGameBoard().isValidTile(st.game.getGameBoard().getTile(3, 1), 1, st.game.getP1())); // can go down two levels
        st.game.relocateWorker(3, 1, 1);
        assertFalse(st.game.getGameBoard().isValidTile(st.game.getGameBoard().getTile(4, 1), 1, st.game.getP1())); // cannot go up two levels

        st.game.getGameBoard().getTile(3, 0).build();
        assertTrue(st.game.getGameBoard().isValidTile(st.game.getGameBoard().getTile(3, 0), 1, st.game.getP1())); // can go up a single level
    }

    @Test
    public void testResetBoard() {
        st.game.buildTower(0, 1, 0);
        st.game.switchCurrentPlayer(); // switches current player back to 0
        st.game.buildTower(0, 1, 0);
        st.game.switchCurrentPlayer(); // switches current player back to 0
        st.game.buildTower(0, 2, 1);
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

    // relocate and initDrop tested when calling dropWorker and relocateWorker (both in here and in Game test)

    @Test
    public void testConstructor() {
        Board test = new Board();
        assertNotSame(test.getTileList(), new ArrayList<Tile>());
        assertEquals(test.getTileList().size(), 25);
    }
}
