package test.Player;

import main.GameBoard.Tile;
import main.Player.Worker;
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
        assertEquals(st.game.getP0().getWorker(0).getWorkerId(), 0);
        assertNotEquals(st.game.getP0().getWorker(0).getWorkerId(), 1);
    }

    @Test
    public void testSetters() {
        Tile t = st.game.getGameBoard().getTile(0, 1);
        st.game.getP0().getWorker(0).setCurrentTile(t);
        assertEquals(st.game.getP0().getWorker(0).getCurrentTile(), t);
        st.game.getP0().getWorker(0).setCurrentTile();
        assertNull(st.game.getP0().getWorker(0).getCurrentTile());
    }

    @Test
    public void testReachThird() {
        assertFalse(st.game.getP0().getWorker(0).reachedThird());
        st.p0.relocateWorker(0, 1, 0);
        st.p0.buildTower(0, 0, 0);
//        st.game.switchCurrentPlayer();
        st.p0.relocateWorker(0, 0, 0); // reached first level
        assertFalse(st.game.getP0().getWorker(0).reachedThird());
        st.p0.buildTower(0, 1, 0);
//        st.game.switchCurrentPlayer();
        st.p0.buildTower(0, 1, 0);
//        st.game.switchCurrentPlayer();
        st.p0.relocateWorker(0, 1, 0); // reached second level
        assertFalse(st.game.getP0().getWorker(0).reachedThird());
        st.p0.buildTower(0, 0, 0);
//        st.game.switchCurrentPlayer();
        st.p0.buildTower(0, 0, 0);
//        st.game.switchCurrentPlayer();
        st.p0.relocateWorker(0, 0, 0); // reached third level
        assertTrue(st.game.getP0().getWorker(0).reachedThird());
    }

    @Test
    public void testWorkerConstructor() {
        Worker w = new Worker(3);
        assertNull(w.getCurrentTile());
        assertEquals(w.getWorkerId(), 3);
    }
}
