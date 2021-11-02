package Player;

import GameBoard.Tile;
import Player.Player;
import Player.Worker;
import Testing.SetupTest;
import org.junit.Before;
import org.junit.Test;

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
    public void isWinner() {
        // already tested reached third
        st.p0.relocateWorker(0,1, 0); // player 0
        st.game.getGameBoard().getTile(0, 0).build();
        st.p0.relocateWorker(0,0, 0); // reached first level
        st.game.getGameBoard().getTile(0, 1).build();
        st.game.getGameBoard().getTile(0, 1).build();
        st.p0.relocateWorker(0,1, 0); // reached second level
        st.game.getGameBoard().getTile(0, 0).build();
        st.game.getGameBoard().getTile(0, 0).build();
        assertFalse(st.game.getP0().isWinner());
        st.p0.relocateWorker(0,0, 0); // reached third level
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

        st.p0.relocateWorker(0, 1, 0);

        Tile two = st.game.getGameBoard().getTile(0, 1);
        assertEquals(st.game.getP0().findCurrentTile(0), two);

        st.game.getP0().clearPlayer();
        assertEquals(st.game.getP0().findCurrentTile(0), null);
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
