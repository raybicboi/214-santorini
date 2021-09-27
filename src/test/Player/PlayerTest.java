package test.Player;

import junit.framework.TestCase;
import main.Player.Player;
import main.Worker.Worker;
import org.junit.Before;
import org.junit.Test;
import test.SetupTest;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class PlayerTest {

    private SetupTest st;

    @Before
    public void setUp() {
        st = new SetupTest();
        st.setUp();
    }

    @Test
    public void testSetters() {
        Player p3 = new Player();
        assertTrue(p3.addNewWorker(st.w11));
        assertFalse(p3.addNewWorker(st.w11)); // cannot add same worker twice
        assertTrue(p3.addNewWorker(st.w12));
        assertFalse(p3.addNewWorker(st.w21)); // cannot add a third worker
        p3.removeWorker(st.w12);
        p3.removeWorker(st.w21); // cannot remove a worker that has not been added
        assertTrue(p3.addNewWorker(st.w21)); // unless...
    }

    @Test
    public void testGetters() {
        Player p3 = new Player();
        assertEquals(p3.getWorkerList(), new ArrayList<Worker>());

        List<Worker> testList = new ArrayList<Worker>();
        testList.add(st.w11);
        testList.add(st.w12);
        assertEquals(st.p1.getWorkerList(), testList);
    }

    @Test
    public void testIsPlayerStuck() {
        // already tested is stuck, so I just create a scenario where both workers are stuck
        assertFalse(st.p1.isPlayerStuck(st.board));
        st.t01.build();
        st.t01.build();
        st.t11.build();
        st.t11.build();
        st.t10.build();
        st.t10.build();
        assertFalse(st.p1.isPlayerStuck(st.board)); // only one worker is stuck
        st.t02.build();
        st.t02.build();
        st.t03.build();
        st.t03.build();
        st.t04.build();
        st.t04.build();
        st.t14.build();
        st.t14.build();
        st.t24.build();
        st.t24.build();
        st.t23.build();
        st.t23.build();
        st.t22.build();
        st.t22.build();
        st.t12.build();
        st.t12.build();
        assertTrue(st.p1.isPlayerStuck(st.board)); // both workers of player 1 is stuck
    }

    @Test
    public void isWinner() {
        // already tested reached third
        st.w11.relocate(st.t01);
        st.w11.build(st.t00);
        st.w11.relocate(st.t00); // reached first level
        st.w11.build(st.t01);
        st.w11.build(st.t01);
        st.w11.relocate(st.t01); // reached second level
        st.w11.build(st.t00);
        st.w11.build(st.t00);
        assertFalse(st.p1.isWinner());
        st.w11.relocate(st.t00); // reached third level
        assertTrue(st.p1.isWinner());
    }

    @Test
    public void testPlayerConstructor() {
        Player newPlayer = new Player();
        assertEquals(newPlayer.getWorkerList(), new ArrayList<Worker>());
    }
}
