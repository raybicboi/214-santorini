package test;

import main.GameBoard.Board;
import main.GameBoard.Tile;
import main.GameSystem.Game;
import main.Player.Player;
import main.Worker.Worker;
import org.junit.Before;

public class SetupTest {

    // init tiles
    public Tile t00;
    public Tile t01;
    public Tile t02;
    public Tile t03;
    public Tile t04;
    public Tile t10;
    public Tile t11;
    public Tile t12;
    public Tile t13;
    public Tile t14;
    public Tile t20;
    public Tile t21;
    public Tile t22;
    public Tile t23;
    public Tile t24;
    public Tile t30;
    public Tile t31;
    public Tile t32;
    public Tile t33;
    public Tile t34;
    public Tile t40;
    public Tile t41;
    public Tile t42;
    public Tile t43;
    public Tile t44;

    // init game and board
    public Board board;
    public Game game;

    // init players and workers
    public Player p1;
    public Player p2;

    public Worker w11;
    public Worker w12;

    public Worker w21;
    public Worker w22;

    @Before
    public void setUp() {
        t00 = new Tile(0, 0);
        t01 = new Tile(0, 1);
        t02 = new Tile(0, 2);
        t03 = new Tile(0, 3);
        t04 = new Tile(0, 4);
        t10 = new Tile(1, 0);
        t11 = new Tile(1, 1);
        t12 = new Tile(1, 2);
        t13 = new Tile(1, 3);
        t14 = new Tile(1, 4);
        t20 = new Tile(2, 0);
        t21 = new Tile(2, 1);
        t22 = new Tile(2, 2);
        t23 = new Tile(2, 3);
        t24 = new Tile(2, 4);
        t30 = new Tile(3, 0);
        t31 = new Tile(3, 1);
        t32 = new Tile(3, 2);
        t33 = new Tile(3, 3);
        t34 = new Tile(3, 4);
        t40 = new Tile(4, 0);
        t41 = new Tile(4, 1);
        t42 = new Tile(4, 2);
        t43 = new Tile(4, 3);
        t44 = new Tile(4, 4);

        board = new Board();
        board.addTile(t00);
        board.addTile(t01);
        board.addTile(t02);
        board.addTile(t03);
        board.addTile(t04);
        board.addTile(t10);
        board.addTile(t11);
        board.addTile(t12);
        board.addTile(t13);
        board.addTile(t14);
        board.addTile(t20);
        board.addTile(t21);
        board.addTile(t22);
        board.addTile(t23);
        board.addTile(t24);
        board.addTile(t30);
        board.addTile(t31);
        board.addTile(t32);
        board.addTile(t33);
        board.addTile(t34);
        board.addTile(t40);
        board.addTile(t41);
        board.addTile(t42);
        board.addTile(t43);
        board.addTile(t44);

        game = new Game(board);

        p1 = new Player();
        p2 = new Player();
        game.addPlayer(p1);
        game.addPlayer(p2);

        w11 = new Worker();
        w12 = new Worker();
        w21 = new Worker();
        w22 = new Worker();

        p1.addNewWorker(w11);
        p1.addNewWorker(w12);
        p2.addNewWorker(w21);
        p2.addNewWorker(w22);
    }
}
