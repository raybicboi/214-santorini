package test.Player;

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
        assertEquals(st.w11.getCurrentTile(), st.t00);
        assertNotEquals(st.w11.getCurrentTile(), st.t01);
    }

    @Test
    public void testInitDrop() {
        Worker testWorker = new Worker();
        assertFalse(testWorker.initDrop(st.t00, st.board)); // another worker occupies that spot
        assertFalse(st.w11.initDrop(st.t11, st.board)); // w11 already dropped in setup

        assertFalse(st.t11.getHasWorker());
        assertTrue(testWorker.initDrop(st.t11, st.board));
        assertTrue(st.t11.getHasWorker());

        Worker testWorker2 = new Worker();
        st.t31.build();
        assertFalse(testWorker2.initDrop(st.t12, st.board)); // cannot drop player after game has started
    }

    @Test
    public void testRelocate() {
        // legal move tile already tested, so I don't need to unit test this
        assertTrue(st.t44.getHasWorker());
        assertFalse(st.t43.getHasWorker());
        assertTrue(st.w21.relocate(st.t43));
        assertTrue(st.t43.getHasWorker());
        assertFalse(st.t44.getHasWorker());

        assertFalse(st.w21.relocate(st.t22));
        assertFalse(st.w21.relocate(st.t43));

    }

    @Test
    public void testBuild() {
        // legal build tile already tested, so I don't need to unit test this
        assertEquals(st.t33.getCurrentLevel(), 0);
        assertFalse(st.w11.build(st.t33));
        assertEquals(st.t33.getCurrentLevel(), 0);

        assertEquals(st.t01.getCurrentLevel(), 0);
        assertTrue(st.w11.build(st.t01));
        assertEquals(st.t01.getCurrentLevel(), 1);
    }

    @Test
    public void testIsStuck() {
        // is valid tile is already tested, so I will just create a scenario
        // where I either surround the worker with tall towers or other workers
        assertFalse(st.w11.isStuck(st.board));
        st.t01.build();
        st.t01.build();
        st.t10.build();
        st.t10.build();
        st.t11.build();
        st.t11.build();
        assertTrue(st.w11.isStuck(st.board));
    }

    @Test
    public void testIsStuck2() {
        // is valid tile is already tested, so I will just create a scenario
        // where I either surround the worker with tall towers or other workers
        assertFalse(st.w11.isStuck(st.board));
        st.w12.relocate(st.t12);
        st.w12.relocate(st.t11);
        st.w12.relocate(st.t01);
        st.w21.relocate(st.t33);
        st.w21.relocate(st.t22);
        st.w21.relocate(st.t11);
        st.w22.relocate(st.t21);
        st.w22.relocate(st.t10);
        assertTrue(st.w11.isStuck(st.board));
    }

    @Test
    public void testReachThird() {
        assertFalse(st.w11.reachedThird());
        st.w11.relocate(st.t01);
        st.w11.build(st.t00);
        st.w11.relocate(st.t00); // reached first level
        assertFalse(st.w11.reachedThird());
        st.w11.build(st.t01);
        st.w11.build(st.t01);
        st.w11.relocate(st.t01); // reached second level
        assertFalse(st.w11.reachedThird());
        st.w11.build(st.t00);
        st.w11.build(st.t00);
        st.w11.relocate(st.t00); // reached third level
        assertTrue(st.w11.reachedThird());
    }
}
