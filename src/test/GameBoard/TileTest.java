package test.GameBoard;

import main.GameBoard.Tile;
import org.junit.Before;
import org.junit.Test;
import test.SetupTest;

import static junit.framework.TestCase.*;
import static org.junit.Assert.assertNotEquals;

public class TileTest {

    private SetupTest st;

    @Before
    public void setUp() {
        st = new SetupTest();
        st.setUp();
    }

    @Test
    public void testGetters() {
        assertEquals(st.t01.getX(), 0);
        assertEquals(st.t01.getY(), 1);
        assertNotEquals(st.t01.getX(), 3);
        assertNotEquals(st.t01.getY(), 4);

        assertEquals(st.t01.getCurrentLevel(), 0);
        st.t01.build();
        assertEquals(st.t01.getCurrentLevel(), 1);

        assertFalse(st.t23.getHasWorker());
        assertTrue(st.t32.getHasWorker());

        st.w22.relocate(st.t23);
        assertTrue(st.t23.getHasWorker());
        assertFalse(st.t32.getHasWorker());
    }

    @Test
    public void testSetters() {
        assertTrue(st.t32.getHasWorker());
        st.t32.jumped();
        assertFalse(st.t32.getHasWorker());
        st.t32.jumped();
        assertTrue(st.t32.getHasWorker());

        assertEquals(st.t33.getCurrentLevel(), 0);
        st.t33.build();
        st.t33.build();
        assertEquals(st.t33.getCurrentLevel(), 2);

    }

    @Test // more extensive than is legal build tile
    public void testIsLegalMoveTile() {
        assertTrue(st.t01.isLegalMoveTile(st.t12));
        assertTrue(st.t01.isLegalMoveTile(st.t10));
        assertTrue(st.t01.isLegalMoveTile(st.t02));
        assertFalse(st.t01.isLegalMoveTile(st.t33)); // not adjacent
        assertFalse(st.t01.isLegalMoveTile(st.t01)); // own tile
        assertFalse(st.t12.isLegalMoveTile(st.t13)); // already has a worker

        assertTrue(st.t41.isLegalMoveTile(st.t42));
        st.t42.build();
        st.t42.build();
        st.t42.build();
        st.t42.build();
        assertFalse(st.t41.isLegalMoveTile(st.t42)); // tower already domed

        st.t41.build();
        st.t41.build();
        assertTrue(st.t41.isLegalMoveTile(st.t31)); // can go down two levels
        assertFalse(st.t31.isLegalMoveTile(st.t41)); // cannot go up two levels

        st.t30.build();
        assertTrue(st.t31.isLegalMoveTile(st.t30)); // can go up a single level
    }

    @Test
    public void testIsLegalBuildTile() {
        assertTrue(st.t01.isLegalBuildTile(st.t12));
        assertTrue(st.t01.isLegalBuildTile(st.t10));
        assertTrue(st.t01.isLegalBuildTile(st.t02));
        assertFalse(st.t01.isLegalBuildTile(st.t33)); // not adjacent
        assertFalse(st.t01.isLegalBuildTile(st.t01)); // own tile
        assertFalse(st.t12.isLegalBuildTile(st.t13)); // already has a worker

        assertTrue(st.t41.isLegalBuildTile(st.t42));
        st.t42.build();
        st.t42.build();
        st.t42.build();
        st.t42.build();
        assertFalse(st.t41.isLegalBuildTile(st.t42)); // tower already domed
    }

    // for use case/naming convention purposes, isValidTile is a separate method
    @Test // legal move tile assertions suffice, so I will repeat the same tests
    public void testIsValidTile() {
        assertTrue(st.t01.isValidTile(st.t12));
        assertTrue(st.t01.isValidTile(st.t10));
        assertTrue(st.t01.isValidTile(st.t02));
        assertFalse(st.t01.isValidTile(st.t33)); // not adjacent
        assertFalse(st.t01.isValidTile(st.t01)); // own tile
        assertFalse(st.t12.isValidTile(st.t13)); // already has a worker

        assertTrue(st.t41.isValidTile(st.t42));
        st.t42.build();
        st.t42.build();
        st.t42.build();
        st.t42.build();
        assertFalse(st.t41.isValidTile(st.t42)); // tower already domed

        st.t41.build();
        st.t41.build();
        assertTrue(st.t41.isValidTile(st.t31)); // can go down two levels
        assertFalse(st.t31.isValidTile(st.t41)); // cannot go up two levels

        st.t30.build();
        assertTrue(st.t31.isValidTile(st.t30)); // can go up a single level
    }

    @Test
    public void testResetTile() {
        assertTrue(st.t32.getHasWorker());
        assertFalse(st.t33.getHasWorker());
        st.t32.resetTile();
        st.t33.resetTile();
        assertFalse(st.t32.getHasWorker());
        assertFalse(st.t33.getHasWorker());

        assertEquals(st.t42.getCurrentLevel(), 0);
        assertEquals(st.t43.getCurrentLevel(), 0);
        st.t42.build();
        st.t42.build();
        st.t42.build();
        assertEquals(st.t42.getCurrentLevel(), 3);

        st.t42.resetTile();
        st.t43.resetTile();
        assertEquals(st.t42.getCurrentLevel(), 0);
        assertEquals(st.t43.getCurrentLevel(), 0);
    }

    @Test
    public void testTileConstructor() {
        Tile test = new Tile(5, 3);
        assertEquals(test.getX(), 5);
        assertEquals(test.getY(), 3);
        assertEquals(test.getCurrentLevel(), 0);
        assertFalse(test.getHasWorker());

    }
}
