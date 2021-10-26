package main.GameSystem;

import main.GameBoard.Board;
import main.GameBoard.Tile;
import main.God.CardLogic;
import main.Player.Player;
import main.Player.Worker;

public class Game implements CardLogic {

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

    // method
    /**
     * Builds/Adds a "tower" onto the tile.
     *
     * @param x the x coordinate of the chosen tile
     * @param y the y coordinate of the chosen tile
     * @param id the id of the player
     * @return boolean of whether the build succeeded
     */
    @Override
    public boolean buildTower(int x, int y, int id) {
        if(this.gameBoard.build(x, y, id, currentPlayer)) {
            switchCurrentPlayer();
            return true;
        }
        return false;
    }

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
     * Moves a worker to another tile.
     *
     * @param x the x coordinate of the chosen tile
     * @param y the y coordinate of the chosen tile
     * @param workerId id of the worker to be moved
     * @return boolean of whether the move succeeded
     */
    @Override
    public boolean relocateWorker(int x, int y, int workerId) {
        if (this.gameBoard.relocate(x, y, workerId, currentPlayer)) {
            Worker w = this.currentPlayer.getWorker(workerId);
            Tile t = this.gameBoard.getTile(x, y);
            currentPlayer.changeWorkerTile(w, t);
            return true;
        }
        return false;
    }

    /**
     * At any given point in the game, determines the winner- or returns null if there is no winner.
     *
     * @return Player the winning player
     */
    @Override
    public Player getWinner() {
        if (isValidGame()) return null;
        Player p0 = this.getP0();
        Player p1 = this.getP1();
        if (p0.isPlayerStuck(this.gameBoard)) {
            loser(p0);
            return p1;
        } else if (p1.isPlayerStuck(this.gameBoard)) {
            loser(p1);
            return p0;
        }
        if (p0.isWinner()) {
            winner(p0);
            return p0;
        }
        winner(p1);
        return p1;
    }

    /**
     * Checks if the game state is valid.
     *
     * @return boolean of whether the game is still going
     */
    public boolean isValidGame() {
        Player p0 = this.getP0();
        Player p1 = this.getP1();
        return isValidHelp(p0) && isValidHelp(p1);
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
    /**
     * Private helper that prints a message for the losing player.
     *
     * @param p the player who lost
     */
    private void loser(Player p) {
        System.out.println("Player " + p.toString() + "has lost the match due to being stuck!");
    }

    /**
     * Private helper that prints a message for the winning player.
     *
     * @param p the player who won
     */
    private void winner(Player p) {
        System.out.println("Player " + p.toString() + "has won the match due scaling the third level!");
    }

    /**
     * Private helper that checks if a player is stuck or has won.
     *
     * @param p the player to be tested
     * @return boolean whether the player can make a move
     */
    private boolean isValidHelp(Player p) {
        if (p.isPlayerStuck(this.gameBoard)) {
            return false;
        }
        return !p.isWinner();
    }
}
