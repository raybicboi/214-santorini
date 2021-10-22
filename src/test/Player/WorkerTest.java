package test.Player;

import main.Player.Player;
import org.junit.Before;
import org.junit.Test;
import test.SetupTest;

import static junit.framework.TestCase.*;
import static org.junit.Assert.assertNotEquals;

public class WorkerTest {

    private SetupTest st;

    @Before
    public void setUp() {
        st = new SetupTest();
        st.setUp();
    }

    @Test
    public void testGetters() {
        assertEquals(st.game.getP0().getWorker(0).getCurrentTile(), st.game.getGameBoard().getTile(0, 0));
        assertNotEquals(st.game.getP0().getWorker(0).getCurrentTile(), st.game.getGameBoard().getTile(0, 1));
    }

    @Test
    public void testInitDrop() {
        st.game.resetGameBoard();

        assertTrue(st.game.dropWorker(0, 0, 0, st.game.getP1())); // first drop
        assertFalse(st.game.dropWorker(0, 0, 0, st.game.getP0())); // another worker occupies that spot
        assertFalse(st.game.dropWorker(1, 1, 0, st.game.getP1())); // worker already dropped in setup

        assertFalse(st.game.getGameBoard().getTile(1, 1).getHasWorker());
        assertTrue(st.game.dropWorker(1, 1, 0, st.game.getP0()));
        assertTrue(st.game.getGameBoard().getTile(1, 1).getHasWorker()); // dropped tile now has worker

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
        System.out.println(st.game.getGameBoard().currentTile.getX());
        System.out.println(st.game.getGameBoard().currentTile.getY());
        assertTrue(st.game.getGameBoard().getTile(4, 4).getHasWorker());
        assertFalse(st.game.getGameBoard().getTile(4, 3).getHasWorker());
        st.game.switchCurrentPlayer(); // switch player 1
        assertTrue(st.game.relocateWorker(4, 3, 0));
        assertTrue(st.game.getGameBoard().getTile(4, 3).getHasWorker());

        System.out.println(st.game.getGameBoard().getTile(4, 4));

        assertFalse(st.game.getGameBoard().getTile(4, 4).getHasWorker());

        assertFalse(st.game.relocateWorker(2, 2, 0));
        assertFalse(st.game.relocateWorker(4, 3, 0));
    }

    @Test
    public void testBuild() {
        // legal build tile already tested, so I don't need to unit test this
        assertEquals(st.game.getGameBoard().getTile(3, 3).getCurrentLevel(), 0);
        assertFalse(st.game.buildTower(3, 3));
        assertEquals(st.game.getGameBoard().getTile(3, 3).getCurrentLevel(), 0);

        assertEquals(st.game.getGameBoard().getTile(0, 1).getCurrentLevel(), 0);
        assertTrue(st.game.buildTower(0, 1));
        st.game.switchCurrentPlayer();
        assertEquals(st.game.getGameBoard().getTile(0, 1).getCurrentLevel(), 1);
    }

    @Test
    public void testIsStuck() {
        // is valid tile is already tested, so I will just create a scenario
        // where I either surround the worker with tall towers or other workers
        assertFalse(st.game.getP0().getWorker(0).isStuck(st.game.getGameBoard()));
        st.game.getGameBoard().getTile(0, 1).build();
        st.game.getGameBoard().getTile(0, 1).build();
        st.game.getGameBoard().getTile(1, 0).build();
        st.game.getGameBoard().getTile(1, 0).build();
        st.game.getGameBoard().getTile(1, 1).build();
        st.game.getGameBoard().getTile(1, 1).build();
        assertTrue(st.game.getP0().getWorker(0).isStuck(st.game.getGameBoard()));
    }

    @Test
    public void testIsStuck2() {
        // is valid tile is already tested, so I will just create a scenario
        // where I either surround the worker with tall towers or other workers
        assertFalse(st.game.getP0().getWorker(0).isStuck(st.game.getGameBoard()));
        st.game.relocateWorker(1, 2, 1); // player 1
        st.game.relocateWorker(1, 1, 1);
        st.game.relocateWorker(0, 1, 1);
        st.game.switchCurrentPlayer();
        st.game.relocateWorker(3, 3, 0); // switch to player 2
        st.game.relocateWorker(2, 2, 0);
        st.game.relocateWorker(1, 1, 0);
        st.game.relocateWorker(2, 1, 1);
        st.game.relocateWorker(1, 0, 1);
        assertTrue(st.game.getP0().getWorker(0).isStuck(st.game.getGameBoard()));
    }

    @Test
    public void testReachThird() {
        assertFalse(st.game.getP0().getWorker(0).reachedThird());
        st.game.relocateWorker(0, 1, 0);
        st.game.buildTower(0, 0);
        st.game.switchCurrentPlayer();
        st.game.relocateWorker(0, 0, 0); // reached first level
        assertFalse(st.game.getP0().getWorker(0).reachedThird());
        st.game.buildTower(0, 1);
        st.game.switchCurrentPlayer();
        st.game.buildTower(0, 1);
        st.game.switchCurrentPlayer();
        st.game.relocateWorker(0, 1, 0); // reached second level
        assertFalse(st.game.getP0().getWorker(0).reachedThird());
        st.game.buildTower(0, 0);
        st.game.switchCurrentPlayer();
        st.game.buildTower(0, 0);
        st.game.switchCurrentPlayer();
        st.game.relocateWorker(0, 0, 0); // reached third level
        assertTrue(st.game.getP0().getWorker(0).reachedThird());
    }
}
