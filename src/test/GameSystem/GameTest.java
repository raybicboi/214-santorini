package test.GameSystem;

import junit.framework.TestCase;
import main.GameSystem.Game;
import org.junit.Before;
import org.junit.Test;
import test.SetupTest;

import static junit.framework.TestCase.*;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class GameTest {

    private SetupTest st;

    @Before
    public void setUp() {
        st = new SetupTest();
        st.setUp();
    }

    // switch current player is also tested here
    @Test
    public void testGetters() {
        assertEquals(st.game.getCurrentPlayer(), st.game.getP0());
        st.game.switchCurrentPlayer();
        assertEquals(st.game.getCurrentPlayer(), st.game.getP1());
        st.game.switchCurrentPlayer();
        assertEquals(st.game.getCurrentPlayer(), st.game.getP0());
    }

    @Test
    public void testResetGameBoard() {
        // board reset method already tested (just a setter method)
        st.game.getGameBoard().getTile(0, 1).build();
        st.game.getGameBoard().getTile(0, 1).build();
        st.game.getGameBoard().getTile(0, 2).build();
//        st.game.switchCurrentPlayer(); // switch to player 1
        st.p1.relocateWorker(3, 3, 1);

        st.game.resetGameBoard();
        assert(st.game.getGameBoard().getTile(0, 1).getCurrentLevel() == 0);
        assert(st.game.getGameBoard().getTile(0, 2).getCurrentLevel() == 0);
        assert(st.game.getGameBoard().getTile(0, 3).getCurrentLevel() == 0);
        assertFalse(st.game.getGameBoard().getTile(3, 3).getHasWorker());
        assertFalse(st.game.getGameBoard().getTile(0, 1).getHasWorker());
    }

    @Test
    public void testInitDrop() {
        st.game.resetGameBoard();

        TestCase.assertTrue(st.game.dropWorker(0, 0, 0, st.game.getP1())); // first drop
        assertFalse(st.game.dropWorker(0, 0, 0, st.game.getP0())); // another worker occupies that spot
        assertFalse(st.game.dropWorker(1, 1, 0, st.game.getP1())); // worker already dropped in setup

        assertFalse(st.game.getGameBoard().getTile(1, 1).getHasWorker());
        TestCase.assertTrue(st.game.dropWorker(1, 1, 0, st.game.getP0()));
        TestCase.assertTrue(st.game.getGameBoard().getTile(1, 1).getHasWorker()); // dropped tile now has worker

        st.game.getGameBoard().getTile(3, 1).build();
        assertFalse(st.game.dropWorker(1, 2, 1, st.game.getP0())); // cannot drop player after game has started

        // ensuring current location of workers from setup
        assertEquals(st.game.getP0().getWorker(0).getCurrentTile(), st.game.getGameBoard().getTile(1, 1));
        assertEquals(st.game.getP1().getWorker(0).getCurrentTile(), st.game.getGameBoard().getTile(0, 0));
        assertNotEquals(st.game.getP0().getWorker(0).getCurrentTile(), st.game.getGameBoard().getTile(1, 3));
    }

    @Test
    public void testGameConstructor() {
        Game newGame = new Game();
        assert(newGame.getGameBoard() != null);
        assert(newGame.getP0() != null);
        assert(newGame.getP1() != null);
        assertEquals(newGame.getCurrentPlayer(), newGame.getP0());
    }
}
