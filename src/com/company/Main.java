package com.company;

import main.GameBoard.Board;
import main.GameBoard.Tile;
import main.GameSystem.Game;
import main.Player.Player;
import main.Worker.Worker;

public class Main {

    /**
     * Helper method that sets up the santorini game.
     *
     */
    public static void setUp() {
        Tile t00 = new Tile(0, 0);
        Tile t01 = new Tile(0, 1);
        Tile t02 = new Tile(0, 2);
        Tile t03 = new Tile(0, 3);
        Tile t04 = new Tile(0, 4);
        Tile t10 = new Tile(1, 0);
        Tile t11 = new Tile(1, 1);
        Tile t12 = new Tile(1, 2);
        Tile t13 = new Tile(1, 3);
        Tile t14 = new Tile(1, 4);
        Tile t20 = new Tile(2, 0);
        Tile t21 = new Tile(2, 1);
        Tile t22 = new Tile(2, 2);
        Tile t23 = new Tile(2, 3);
        Tile t24 = new Tile(2, 4);
        Tile t30 = new Tile(3, 0);
        Tile t31 = new Tile(3, 1);
        Tile t32 = new Tile(3, 2);
        Tile t33 = new Tile(3, 3);
        Tile t34 = new Tile(3, 4);
        Tile t40 = new Tile(4, 0);
        Tile t41 = new Tile(4, 1);
        Tile t42 = new Tile(4, 2);
        Tile t43 = new Tile(4, 3);
        Tile t44 = new Tile(4, 4);

        Board board = new Board();
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

        Game game = new Game(board);

        Player p1 = new Player();
        Player p2 = new Player();
        game.addPlayer(p1);
        game.addPlayer(p2);

        Worker w11 = new Worker();
        Worker w12 = new Worker();
        Worker w21 = new Worker();
        Worker w22 = new Worker();

        p1.addNewWorker(w11);
        p1.addNewWorker(w12);
        p2.addNewWorker(w21);
        p2.addNewWorker(w22);

        w11.initDrop(t00, board);
        w12.initDrop(t13, board);
        w21.initDrop(t44, board);
        w22.initDrop(t32, board);
    }

    /**
     * Triggers the Santorini setup, but does not run as only the setup commands are initialized.
     *
     * @param args The {@link String} args used to run the main function.
     */
    public static void main(String[] args) {
        setUp();
    }
}
