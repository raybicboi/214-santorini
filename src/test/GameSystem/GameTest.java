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

    // get winner is also tested here
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
    public void testRelocate() {
        // legal move tile already tested, so I don't need to unit test this
        TestCase.assertTrue(st.game.getGameBoard().getTile(4, 4).getHasWorker());
        assertFalse(st.game.getGameBoard().getTile(4, 3).getHasWorker());
        st.game.switchCurrentPlayer(); // switch player 1
        TestCase.assertTrue(st.game.relocateWorker(4, 3, 0));
        TestCase.assertTrue(st.game.getGameBoard().getTile(4, 3).getHasWorker());

        System.out.println(st.game.getGameBoard().getTile(4, 4));

        assertFalse(st.game.getGameBoard().getTile(4, 4).getHasWorker());

        assertFalse(st.game.relocateWorker(2, 2, 0));
        assertFalse(st.game.relocateWorker(4, 3, 0));
    }

    @Test
    public void testBuildTower() {
        assertEquals(st.game.getCurrentPlayer(), st.game.getP0());
        assertEquals(st.game.getGameBoard().getTile(1, 0).getCurrentLevel(), 0);
        assertTrue(st.game.buildTower(1, 0, 0)); // this tile can be built
        assertEquals(st.game.getCurrentPlayer(), st.game.getP1()); // make sure current player is switched after a build
        assertEquals(st.game.getGameBoard().getTile(1, 0).getCurrentLevel(), 1); // tile level increased

        st.game.switchCurrentPlayer();
        assertFalse(st.game.buildTower(3, 3, 0)); // tile not adjacent
        assertFalse(st.game.buildTower(0, 0, 0)); // cannot build on own tile

        st.game.relocateWorker(1, 1, 0);
        st.game.relocateWorker(1, 2, 0);
        assertFalse(st.game.buildTower(1, 3, 0)); // cannot build a tile with another worker

        st.game.relocateWorker(1, 1, 0);
        assertTrue(st.game.buildTower(1, 0, 0));
        assertFalse(st.game.buildTower(1, 0, 0)); // player 2 cannot build due to proximity
        st.game.switchCurrentPlayer();
        assertTrue(st.game.buildTower(1, 0, 0));
        st.game.switchCurrentPlayer();
        assertTrue(st.game.buildTower(1, 0, 0)); // tower level is now 4
        st.game.switchCurrentPlayer();
        assertFalse(st.game.buildTower(1, 0, 0)); // cannot build on a domed tower
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
