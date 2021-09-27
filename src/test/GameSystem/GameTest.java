package test.GameSystem;

import junit.framework.TestCase;
import main.GameBoard.Board;
import main.GameSystem.Game;
import main.Player.Player;
import org.junit.Before;
import org.junit.Test;
import test.SetupTest;

import java.util.ArrayList;
import java.util.List;

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
    public void testAddPlayer() {
        Player p3 = new Player();
        Player p4 = new Player();
        assertFalse(st.game.addPlayer(p3)); // cannot add more than two players

        Game newGame = new Game(st.board);
        assertTrue(newGame.addPlayer(p3));
        assertFalse(newGame.addPlayer(p3)); // cannot add same player twice
        assertTrue(newGame.addPlayer(p4));
    }

    @Test
    public void testGetters() {
        assertEquals(st.game.getGameBoard(), st.board);

        List<Player> test = new ArrayList<Player>();
        test.add(st.p1);
        test.add(st.p2);
        assertEquals(st.game.getPlayerList(), test);

    }

    @Test
    public void testIsValidGame() {
        // tested both is player stuck and is winner already
        st.t01.build();
        st.t01.build();
        st.t11.build();
        st.t11.build();
        st.t10.build();
        st.t10.build();
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
        assertTrue(st.game.isValidGame());
        assertEquals(st.game.getWinner(st.p1, st.p2), null);
        st.t12.build();
        assertFalse(st.game.isValidGame());
        assertEquals(st.game.getWinner(st.p1, st.p2), st.p2);
    }

    @Test
    public void isValidGame2() {
        st.w11.relocate(st.t01);
        st.w11.build(st.t00);
        st.w11.relocate(st.t00); // reached first level
        st.w11.build(st.t01);
        st.w11.build(st.t01);
        st.w11.relocate(st.t01); // reached second level
        st.w11.build(st.t00);
        st.w11.build(st.t00);
        assertTrue(st.game.isValidGame());
        assertEquals(st.game.getWinner(st.p1, st.p2), null);
        st.w11.relocate(st.t00); // reached third level
        assertFalse(st.game.isValidGame());
        assertEquals(st.game.getWinner(st.p1, st.p2), st.p1);
    }

    @Test
    public void testGetWinner() {
        // integration test done in valid game
        // just testing preconditions here
        Player p3 = new Player();
        assertEquals(st.game.getWinner(st.p1, p3), null); // p3 is not in the game
    }

    @Test
    public void testResetGameBoard() {
        // board reset method already tested (just a setter method)
        st.t01.build();
        st.t01.build();
        st.t02.build();
        st.w22.relocate(st.t33);

        st.game.resetGameBoard();
        assert(st.t01.getCurrentLevel() == 0);
        assert(st.t02.getCurrentLevel() == 0);
        assert(st.t03.getCurrentLevel() == 0);
        assertFalse(st.t33.getHasWorker());
        assertFalse(st.t01.getHasWorker());

        assertEquals(st.game.getPlayerList(), new ArrayList<Player>());
    }

    @Test
    public void testGameConstructor() {
        Board newBoard = new Board();
        Game newGame = new Game(newBoard);
        assertEquals(newGame.getGameBoard(), newBoard);
        assertEquals(newGame.getPlayerList(), new ArrayList<Player>());
    }
}
