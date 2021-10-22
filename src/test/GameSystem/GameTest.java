package test.GameSystem;

import main.GameBoard.Board;
import main.GameSystem.Game;
import main.Player.Player;
import org.junit.Before;
import org.junit.Test;
import test.SetupTest;

import static junit.framework.TestCase.*;
import static org.junit.Assert.assertTrue;

public class GameTest {

    private SetupTest st;

    @Before
    public void setUp() {
        st = new SetupTest();
        st.setUp();
    }

    @Test
    public void testGetters() {
        assertEquals(st.game.getCurrentPlayer(), st.game.getP0());
        st.game.switchCurrentPlayer();
        assertEquals(st.game.getCurrentPlayer(), st.game.getP1());
    }

    @Test
    public void testIsValidGame() {
        // tested both is player stuck and is winner already
        st.game.getGameBoard().getTile(0, 1).build();
        st.game.getGameBoard().getTile(0, 1).build();
        st.game.getGameBoard().getTile(1, 1).build();
        st.game.getGameBoard().getTile(1, 1).build();
        st.game.getGameBoard().getTile(1, 0).build();
        st.game.getGameBoard().getTile(1, 0).build();
        st.game.getGameBoard().getTile(0, 2).build();
        st.game.getGameBoard().getTile(0, 2).build();
        st.game.getGameBoard().getTile(0, 3).build();
        st.game.getGameBoard().getTile(0, 3).build();
        st.game.getGameBoard().getTile(0, 4).build();
        st.game.getGameBoard().getTile(0, 4).build();
        st.game.getGameBoard().getTile(1, 4).build();
        st.game.getGameBoard().getTile(1, 4).build();
        st.game.getGameBoard().getTile(2, 4).build();
        st.game.getGameBoard().getTile(2, 4).build();
        st.game.getGameBoard().getTile(2, 3).build();
        st.game.getGameBoard().getTile(2, 3).build();
        st.game.getGameBoard().getTile(2, 2).build();
        st.game.getGameBoard().getTile(2, 2).build();
        st.game.getGameBoard().getTile(1, 2).build();
        assertTrue(st.game.isValidGame());
        assertNull(st.game.getWinner());
        st.game.getGameBoard().getTile(1, 2).build();
        assertFalse(st.game.isValidGame());
        assertEquals(st.game.getWinner(), st.game.getP1());
    }

    @Test
    public void isValidGame2() {
        st.game.relocateWorker(0, 1, 0); // current player 1, so no switch current player needed
        st.game.getGameBoard().getTile(0, 0).build();
        st.game.relocateWorker(0, 0, 0); // reached first level
        st.game.getGameBoard().getTile(0, 1).build();
        st.game.getGameBoard().getTile(0, 1).build();
        st.game.relocateWorker(0, 1, 0); // reached second level
        st.game.getGameBoard().getTile(0, 0).build();
        st.game.getGameBoard().getTile(0, 0).build();
        assertTrue(st.game.isValidGame());
        assertNull(st.game.getWinner());
        st.game.relocateWorker(0, 0, 0); // reached third level
        assertFalse(st.game.isValidGame());
        assertEquals(st.game.getWinner(), st.game.getP0());
    }

    @Test
    public void testResetGameBoard() {
        // board reset method already tested (just a setter method)
        st.game.getGameBoard().getTile(0, 1).build();
        st.game.getGameBoard().getTile(0, 1).build();
        st.game.getGameBoard().getTile(0, 2).build();
        st.game.switchCurrentPlayer(); // switch to player 1
        st.game.relocateWorker(3, 3, 1);

        st.game.resetGameBoard();
        assert(st.game.getGameBoard().getTile(0, 1).getCurrentLevel() == 0);
        assert(st.game.getGameBoard().getTile(0, 2).getCurrentLevel() == 0);
        assert(st.game.getGameBoard().getTile(0, 3).getCurrentLevel() == 0);
        assertFalse(st.game.getGameBoard().getTile(3, 3).getHasWorker());
        assertFalse(st.game.getGameBoard().getTile(0, 1).getHasWorker());

//        assertEquals(st.game.getPlayerList(), new ArrayList<Player>());
    }

    @Test
    public void testGameConstructor() {
        Game newGame = new Game();
        assertEquals(newGame.getGameBoard(), new Board());
        assertEquals(newGame.getP0(), new Player(0));
        assertEquals(newGame.getP1(), new Player(1));
        assertEquals(newGame.getCurrentPlayer(), newGame.getP0());
    }
}
