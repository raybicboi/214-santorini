package test.Player;

import junit.framework.TestCase;
import main.GameBoard.Tile;
import main.Player.Player;
import main.Player.Worker;
import org.junit.Before;
import org.junit.Test;
import test.SetupTest;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class PlayerTest {

    private SetupTest st;

    @Before
    public void setUp() {
        st = new SetupTest();
        st.setUp();
    }

    @Test
    public void testGetters() {
        List<Worker> testList = new ArrayList<>();
        testList.add(st.game.getP0().getWorker(0));
        testList.add(st.game.getP0().getWorker(1));

        assertEquals(st.game.getP0().getWorkerList().size(), 2);
        assertEquals(st.game.getP0().getWorkerList(), testList);

        assertEquals(st.game.getP0().getPlayerId(), 0);
        assertEquals(st.game.getP1().getPlayerId(), 1);
    }

    @Test
    public void testChangeWorkerTile() {
        Tile one = st.game.getGameBoard().getTile(0, 0);
        assertEquals(st.game.getP0().getWorker(0).getCurrentTile(), one);
        Worker w1 = st.game.getP0().getWorker(0);
        Tile two = st.game.getGameBoard().getTile(1, 1);
        st.game.getP0().changeWorkerTile(w1, two);
        assertEquals(st.game.getP0().getWorker(0).getCurrentTile(), two);
    }

    @Test
    public void testIsPlayerStuck() {
        // already tested is stuck, so I just create a scenario where both workers are stuck
        assertFalse(st.game.getP0().isPlayerStuck(st.game.getGameBoard()));
        st.game.getGameBoard().getTile(0, 1).build();
        st.game.getGameBoard().getTile(0, 1).build();
        st.game.getGameBoard().getTile(1, 1).build();
        st.game.getGameBoard().getTile(1, 1).build();
        st.game.getGameBoard().getTile(1, 0).build();
        st.game.getGameBoard().getTile(1, 0).build();
        assertFalse(st.game.getP0().isPlayerStuck(st.game.getGameBoard())); // only one worker is stuck
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
        st.game.getGameBoard().getTile(1, 2).build();
        assertTrue(st.game.getP0().isPlayerStuck(st.game.getGameBoard())); // both workers of player 1 is stuck
    }

    @Test
    public void isWinner() {
        // already tested reached third
        st.game.relocateWorker(0,1, 0); // player 0
        st.game.getGameBoard().getTile(0, 0).build();
        st.game.relocateWorker(0,0, 0); // reached first level
        st.game.getGameBoard().getTile(0, 1).build();
        st.game.getGameBoard().getTile(0, 1).build();
        st.game.relocateWorker(0,1, 0); // reached second level
        st.game.getGameBoard().getTile(0, 0).build();
        st.game.getGameBoard().getTile(0, 0).build();
        assertFalse(st.game.getP0().isWinner());
        st.game.relocateWorker(0,0, 0); // reached third level
        assertTrue(st.game.getP0().isWinner());
    }

    @Test
    public void testClearPlayer() {
        Tile one = st.game.getGameBoard().getTile(0, 0);
        assertEquals(st.game.getP0().getWorker(0).getCurrentTile(), one);

        Tile two = st.game.getGameBoard().getTile(1, 3);
        assertEquals(st.game.getP0().getWorker(1).getCurrentTile(), two);

        st.game.getP0().clearPlayer();
        assertNull(st.game.getP0().getWorker(0).getCurrentTile());
        assertNull(st.game.getP0().getWorker(1).getCurrentTile());
    }

    @Test
    public void testWorkerNull() {
        assertFalse(st.game.getP0().checkWorkerNull(0));
        st.game.getP0().getWorker(0).setCurrentTile();
        assertTrue(st.game.getP0().checkWorkerNull(0));
    }

    @Test
    public void testFindCurrentTile() {
        Tile one = st.game.getGameBoard().getTile(0, 0);
        assertEquals(st.game.getP0().findCurrentTile(0), one);

        st.game.relocateWorker(0, 1, 0);

        Tile two = st.game.getGameBoard().getTile(0, 1);
        assertEquals(st.game.getP0().findCurrentTile(0), two);

        st.game.getP0().clearPlayer();
        assertEquals(st.game.getP0().findCurrentTile(0), null);
    }

    @Test
    public void testIsStuck() {
        // is valid tile is already tested, so I will just create a scenario
        // where I either surround the worker with tall towers or other workers
        assertFalse(st.game.getP0().isStuck(st.game.getGameBoard(), 0));
        st.game.getGameBoard().getTile(0, 1).build();
        st.game.getGameBoard().getTile(0, 1).build();
        st.game.getGameBoard().getTile(1, 0).build();
        st.game.getGameBoard().getTile(1, 0).build();
        st.game.getGameBoard().getTile(1, 1).build();
        st.game.getGameBoard().getTile(1, 1).build();
        TestCase.assertTrue(st.game.getP0().isStuck(st.game.getGameBoard(), 0));
    }

    @Test
    public void testIsStuck2() {
        // is valid tile is already tested, so I will just create a scenario
        // where I either surround the worker with tall towers or other workers
        assertFalse(st.game.getP0().isStuck(st.game.getGameBoard(), 0));
        st.game.relocateWorker(1, 2, 1); // player 1
        st.game.relocateWorker(1, 1, 1);
        st.game.relocateWorker(0, 1, 1);
        st.game.switchCurrentPlayer();
        st.game.relocateWorker(3, 3, 0); // switch to player 2
        st.game.relocateWorker(2, 2, 0);
        st.game.relocateWorker(1, 1, 0);
        st.game.relocateWorker(2, 1, 1);
        st.game.relocateWorker(1, 0, 1);
        TestCase.assertTrue(st.game.getP0().isStuck(st.game.getGameBoard(), 0));
    }

    @Test
    public void testPlayerConstructor() {
        Player newPlayer = new Player(2);

        List<Worker> testList = new ArrayList<>();
        testList.add(newPlayer.getWorker(0));
        testList.add(newPlayer.getWorker(1));

        assertEquals(newPlayer.getWorkerList(), testList);
        assertEquals(newPlayer.getPlayerId(), 2);
    }
}
