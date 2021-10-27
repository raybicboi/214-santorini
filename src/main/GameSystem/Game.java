package main.GameSystem;

import main.GameBoard.Board;
import main.GameBoard.Tile;
import main.God.CardLogic;
import main.Player.Player;
import main.Player.Worker;

public class Game {

    private Player currentPlayer;
    private final Board gameBoard;
    private final Player p0;
    private final Player p1;

    // arg constructor
    /**
     * Game Constructor (no arg)
     *
     */
    public Game() {
        this.p0 = new Player(0);
        this.p1 = new Player(1);
        this.gameBoard = new Board();
        this.currentPlayer = this.getP0();
    }

    // setter method
    /**
     * Switches the current player when the turn ends.
     *
     */
    public void switchCurrentPlayer() {
        if (this.currentPlayer == this.p0) { this.currentPlayer = this.p1; }
        else { this.currentPlayer = this.p0; }
    }

    // getter method
    /**
     * Getter method for retrieving the board associated with the game.
     *
     * @return Board the board
     */
    public Board getGameBoard() {
        return this.gameBoard;
    }

    /**
     * Getter method for retrieving player 0.
     *
     * @return Player the player
     */
    public Player getP0() { return this.p0; }

    /**
     * Getter method for retrieving player 1.
     *
     * @return Player the player
     */
    public Player getP1() { return this.p1; }

    /**
     * Getter method for retrieving the current player whose turn it is to move.
     *
     * @return Player the player
     */
    public Player getCurrentPlayer() { return this.currentPlayer; }

    /**
     * Drops a worker onto the game board.
     *
     * @param x the x coordinate of the chosen tile
     * @param y the y coordinate of the chosen tile
     * @param workerId id of the worker to be dropped
     * @param p the player associated with the worker to be dropped
     * @return boolean of whether the drop succeeded
     */
    public boolean dropWorker(int x, int y, int workerId, Player p) {
        if (!p.checkWorkerNull(workerId)) {
            System.out.println("Worker is already on another tile");
            return false;
        }
        if (this.gameBoard.initDrop(x, y)) {
            Tile t = this.gameBoard.getTile(x, y);
            Worker w;
            if (p == this.p0) {
                w = this.p0.getWorker(workerId);
                p0.changeWorkerTile(w, t);
            } else if (p == this.p1) {
                w = this.p1.getWorker(workerId);
                p1.changeWorkerTile(w, t);
            }
            return true;
        }
        return false;
    }

    /**
     * Getter method for retrieving a single tile from the current Game.
     *
     * @param x the x coordinate of the tile to be retrieved
     * @param y the y coordinate of the tile to be retrieved
     * @return the tile found based on the coordinates given
     */
    public Tile retrieveTile(int x, int y) {
        return this.getGameBoard().getTile(x, y);
    }

    /**
     * Resets the game board to its original state.
     *
     */
    public void resetGameBoard() {
        this.gameBoard.resetBoard();
        this.p0.clearPlayer();
        this.p1.clearPlayer();
    }

    // helper method

}
