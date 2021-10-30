package test.GameSystem;

import org.junit.Before;
import org.junit.Test;
import test.SetupTest;

import static org.junit.Assert.*;

public class GameLogicTest {

    private SetupTest st;

    @Before
    public void setUp() {
        st = new SetupTest();
        st.setUp();
    }

    @Test
    public void testBuild() {
        assertEquals(st.game.getGameBoard().getTile(1, 2).getCurrentLevel(), 0);
        assertTrue(st.p0.isLegalBuildTile(st.game.getGameBoard().getTile(1, 1), 0));
        st.p0.buildTower(1, 1, 0);
        assertEquals(st.game.getGameBoard().getTile(1, 1).getCurrentLevel(), 1);
        st.p0.buildTower(1, 1, 0);
        assertEquals(st.game.getGameBoard().getTile(1, 1).getCurrentLevel(), 2);

        assertFalse(st.p0.buildTower(1, 2, 0)); // cannot build here
    }

    // more extensive than is legal build tile
    @Test
    public void testIsLegalMoveTile() {
        st.p0.relocateWorker(0, 1, 0);
        assertTrue(st.p0.isLegalMoveTile(st.game.getGameBoard().getTile(1, 2), 0));
        assertTrue(st.p0.isLegalMoveTile(st.game.getGameBoard().getTile(1, 0), 0));
        assertTrue(st.p0.isLegalMoveTile(st.game.getGameBoard().getTile(0, 2), 0));
        assertFalse(st.p0.isLegalMoveTile(st.game.getGameBoard().getTile(3, 3), 0)); // not adjacent
        assertFalse(st.p0.isLegalMoveTile(st.game.getGameBoard().getTile(0, 1), 0)); // own tile
        st.p0.relocateWorker(1, 2, 0);
        assertFalse(st.p0.isLegalMoveTile(st.game.getGameBoard().getTile(1, 3), 0)); // already has another worker

//        st.game.switchCurrentPlayer();
        st.p1.relocateWorker(4, 1, 1);
        assertTrue(st.p1.isLegalMoveTile(st.game.getGameBoard().getTile(4, 2), 1));
        st.game.getGameBoard().getTile(4, 2).build();
        st.game.getGameBoard().getTile(4, 2).build();
        st.game.getGameBoard().getTile(4, 2).build();
        st.game.getGameBoard().getTile(4, 2).build();
        assertFalse(st.p1.isLegalMoveTile(st.game.getGameBoard().getTile(4, 2), 1)); // tower already domed

        st.game.getGameBoard().getTile(4, 1).build();
        st.game.getGameBoard().getTile(4, 1).build();
        assertTrue(st.p1.isLegalMoveTile(st.game.getGameBoard().getTile(3, 1), 1)); // can go down two levels
        st.p1.relocateWorker(3, 1, 1);
        assertFalse(st.p1.isLegalMoveTile(st.game.getGameBoard().getTile(4, 1), 1)); // cannot go up two levels

        st.game.getGameBoard().getTile(3, 0).build();
        assertTrue(st.p1.isLegalMoveTile(st.game.getGameBoard().getTile(3, 0), 1)); // can go up a single level
    }

    @Test
    public void testIsLegalBuildTile() {

        st.p0.relocateWorker(0, 1, 0);
        assertTrue(st.p0.isLegalBuildTile(st.game.getGameBoard().getTile(1, 2), 0));
        assertTrue(st.p0.isLegalBuildTile(st.game.getGameBoard().getTile(1, 0), 0));
        assertTrue(st.p0.isLegalBuildTile(st.game.getGameBoard().getTile(0, 2), 0));
        assertFalse(st.p0.isLegalBuildTile(st.game.getGameBoard().getTile(3, 3), 0)); // not adjacent
        assertFalse(st.p0.isLegalBuildTile(st.game.getGameBoard().getTile(0, 1), 0)); // own tile
        st.p0.relocateWorker(1, 2, 0);
        assertFalse(st.p0.isLegalBuildTile(st.game.getGameBoard().getTile(1, 3), 0)); // already has another worker

//        st.game.switchCurrentPlayer();
        st.p1.relocateWorker(4, 1, 1);
        assertTrue(st.p1.isLegalBuildTile(st.game.getGameBoard().getTile(4, 2), 1));
        st.game.getGameBoard().getTile(4, 2).build();
        st.game.getGameBoard().getTile(4, 2).build();
        st.game.getGameBoard().getTile(4, 2).build();
        st.game.getGameBoard().getTile(4, 2).build();
        assertFalse(st.p1.isLegalBuildTile(st.game.getGameBoard().getTile(4, 2), 1)); // tower already domed
    }

    // for use case/naming convention purposes, isValidTile is a separate method
    // legal move tile assertions suffice, so I will repeat the same tests
    @Test
    public void testIsValidTile() {
        st.p0.relocateWorker(0, 1, 0);
        assertTrue(st.p0.isValidTile(st.game.getGameBoard().getTile(1, 2), 0));
        assertTrue(st.p0.isValidTile(st.game.getGameBoard().getTile(1, 0), 0));
        assertTrue(st.p0.isValidTile(st.game.getGameBoard().getTile(0, 2), 0));
        assertFalse(st.p0.isValidTile(st.game.getGameBoard().getTile(3, 3), 0)); // not adjacent
        assertFalse(st.p0.isValidTile(st.game.getGameBoard().getTile(0, 1), 0)); // own tile
        st.p0.relocateWorker(1, 2, 0);
        assertFalse(st.p0.isValidTile(st.game.getGameBoard().getTile(1, 3), 0)); // already has another worker

//        st.game.switchCurrentPlayer();
        st.p1.relocateWorker(4, 1, 1);
        assertTrue(st.p1.isValidTile(st.game.getGameBoard().getTile(4, 2), 1));
        st.game.getGameBoard().getTile(4, 2).build();
        st.game.getGameBoard().getTile(4, 2).build();
        st.game.getGameBoard().getTile(4, 2).build();
        st.game.getGameBoard().getTile(4, 2).build();
        assertFalse(st.p1.isValidTile(st.game.getGameBoard().getTile(4, 2), 1)); // tower already domed

        st.game.getGameBoard().getTile(4, 1).build();
        st.game.getGameBoard().getTile(4, 1).build();
        assertTrue(st.p1.isValidTile(st.game.getGameBoard().getTile(3, 1), 1)); // can go down two levels
        st.p1.relocateWorker(3, 1, 1);
        assertFalse(st.p1.isValidTile(st.game.getGameBoard().getTile(4, 1), 1)); // cannot go up two levels

        st.game.getGameBoard().getTile(3, 0).build();
        assertTrue(st.p1.isValidTile(st.game.getGameBoard().getTile(3, 0), 1)); // can go up a single level
    }


    @Test
    public void testIsPlayerStuck() {
        // already tested is stuck, so I just create a scenario where both workers are stuck
        assertFalse(st.p0.isPlayerStuck());
        st.game.getGameBoard().getTile(0, 1).build();
        st.game.getGameBoard().getTile(0, 1).build();
        st.game.getGameBoard().getTile(1, 1).build();
        st.game.getGameBoard().getTile(1, 1).build();
        st.game.getGameBoard().getTile(1, 0).build();
        st.game.getGameBoard().getTile(1, 0).build();
        assertFalse(st.p0.isPlayerStuck()); // only one worker is stuck
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
        assertTrue(st.p0.isPlayerStuck()); // both workers of player 1 is stuck
    }

    @Test
    public void testIsStuck() {
        // is valid tile is already tested, so I will just create a scenario
        // where I either surround the worker with tall towers or other workers
        assertFalse(st.p0.isStuck(0));
        st.game.getGameBoard().getTile(0, 1).build();
        st.game.getGameBoard().getTile(0, 1).build();
        st.game.getGameBoard().getTile(1, 0).build();
        st.game.getGameBoard().getTile(1, 0).build();
        st.game.getGameBoard().getTile(1, 1).build();
        st.game.getGameBoard().getTile(1, 1).build();
        assertTrue(st.p0.isStuck(0));
    }

    @Test
    public void testIsStuck2() {
        // is valid tile is already tested, so I will just create a scenario
        // where I either surround the worker with tall towers or other workers
        assertFalse(st.p0.isStuck(0));
        st.p0.relocateWorker(1, 2, 1); // player 0
        st.p0.relocateWorker(1, 1, 1);
        st.p0.relocateWorker(0, 1, 1);
//        st.game.switchCurrentPlayer();
        st.p1.relocateWorker(3, 3, 0); // switch to player 1
        st.p1.relocateWorker(2, 2, 0);
        st.p1.relocateWorker(1, 1, 0);
        st.p1.relocateWorker(2, 1, 1);
        st.p1.relocateWorker(1, 0, 1);
        assertTrue(st.p0.isStuck(0));
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
        assertTrue(st.p0.isValidGame(st.p1));
        assertTrue(st.p1.isValidGame(st.p0));
        assertNull(st.p0.getWinner(st.p1));
        st.game.getGameBoard().getTile(1, 2).build();
        assertFalse(st.p0.isValidGame(st.p1));
        assertFalse(st.p1.isValidGame(st.p0));
        assertEquals(st.p0.getWinner(st.p1), st.game.getP1());
    }

    @Test
    public void isValidGame2() {
        st.p0.relocateWorker(0, 1, 0); // current player 0, so no switch current player needed
        st.game.getGameBoard().getTile(0, 0).build();
        st.p0.relocateWorker(0, 0, 0); // reached first level
        st.game.getGameBoard().getTile(0, 1).build();
        st.game.getGameBoard().getTile(0, 1).build();
        st.p0.relocateWorker(0, 1, 0); // reached second level
        st.game.getGameBoard().getTile(0, 0).build();
        st.game.getGameBoard().getTile(0, 0).build();
        assertTrue(st.p0.isValidGame(st.p1));
        assertNull(st.p0.getWinner(st.p1));
        st.p0.relocateWorker(0, 0, 0); // reached third level
        assertFalse(st.p0.isValidGame(st.p1));
        assertEquals(st.p0.getWinner(st.p1), st.game.getP0());
    }

    @Test
    public void testRelocate() {
        // legal move tile already tested, so I don't need to unit test this
        assertTrue(st.game.getGameBoard().getTile(4, 4).getHasWorker());
        assertFalse(st.game.getGameBoard().getTile(4, 3).getHasWorker());
        st.game.switchCurrentPlayer(); // switch player 1
        assertTrue(st.p1.relocateWorker(4, 3, 0));
        assertTrue(st.game.getGameBoard().getTile(4, 3).getHasWorker());

        System.out.println(st.game.getGameBoard().getTile(4, 4));

        assertFalse(st.game.getGameBoard().getTile(4, 4).getHasWorker());

        assertFalse(st.p1.relocateWorker(2, 2, 0));
        assertFalse(st.p1.relocateWorker(4, 3, 0));
    }

    @Test
    public void testBuildTower() {
        assertEquals(st.game.getCurrentPlayer(), st.game.getP0());
        assertEquals(st.game.getGameBoard().getTile(1, 0).getCurrentLevel(), 0);
        assertTrue(st.p0.buildTower(1, 0, 0)); // this tile can be built
        assertEquals(st.game.getCurrentPlayer(), st.game.getP1()); // make sure current player is switched after a build
        assertEquals(st.game.getGameBoard().getTile(1, 0).getCurrentLevel(), 1); // tile level increased

//        st.game.switchCurrentPlayer();
        assertFalse(st.p0.buildTower(3, 3, 0)); // tile not adjacent
        assertFalse(st.p0.buildTower(0, 0, 0)); // cannot build on own tile

        st.p0.relocateWorker(1, 1, 0);
        st.p0.relocateWorker(1, 2, 0);
        assertFalse(st.p0.buildTower(1, 3, 0)); // cannot build a tile with another worker

        st.p0.relocateWorker(1, 1, 0);
        assertTrue(st.p0.buildTower(1, 0, 0));
        assertFalse(st.p1.buildTower(1, 0, 0)); // player 2 cannot build due to proximity
//        st.game.switchCurrentPlayer();
        assertTrue(st.p0.buildTower(1, 0, 0));
//        st.game.switchCurrentPlayer();
        assertTrue(st.p0.buildTower(1, 0, 0)); // tower level is now 4
//        st.game.switchCurrentPlayer();
        assertFalse(st.p0.buildTower(1, 0, 0)); // cannot build on a domed tower
    }
}
