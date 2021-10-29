package main.God;

import main.GameBoard.Tile;
import main.GameSystem.Game;
import main.Player.Player;
import main.Player.Worker;

public abstract class AbstractGod implements CardLogic {

    public static boolean athena;
    public final Player p;
    public final Player other;
    public final Game game;

    // constructor
    /**
     * GameLogic Constructor (arg)
     *
     */
    public AbstractGod(Game game, Player p) {
        this.game = game;
        athena = false;
        if (p == game.getP0()) {
            this.p = game.getP0();
            this.other = game.getP1();
        } else {
            this.p = game.getP1();
            this.other = game.getP0();
        }
    }

    // methods

    // BUILD
    /**
     * Builds/Adds a "tower" onto the tile.
     *
     * @param x the x coordinate of the chosen tile
     * @param y the y coordinate of the chosen tile
     * @param id the id of the worker
     * @return boolean of whether the build succeeded
     */
    public abstract boolean buildTower(int x, int y, int id); // DEMETER, ATHENA

    /**
     * Builds/Adds a "tower" onto the tile.
     *
     * @param x the x coordinate of the chosen tile
     * @param y the y coordinate of the chosen tile
     * @param id the id of the worker
     * @return boolean of whether the build succeeded
     */
    public boolean buildHelper(int x, int y, int id) {
        Tile t = game.retrieveTile(x, y);
        if (!this.isLegalBuildTile(t, id)) {
            System.out.println("Cannot build on this tile!");
            return false;
        }
        t.build();
        return true;
    }

    /**
     * Sees if a worker can build on a select tile from its current location.
     *
     * @param other - the other tile to be tested
     * @param id the id of the worker
     * @return boolean of whether 'other' is a legal build
     */
    public boolean isLegalBuildTile(Tile other, int id) {
        Tile t = p.findCurrentTile(id);
        if (t != null) { return this.tileBuildCheck(other, id); }
        return false;
    }

    /**
     * Private helper method that tests if another tile can be built on.
     *
     * @param other - the other tile to be tested
     * @param id the id of the worker
     * @return boolean of whether 'other' is a legal move
     */
    public boolean tileBuildCheck(Tile other, int id) {
        Tile t = p.findCurrentTile(id);
        if (t.getX() == other.getX() &&
                t.getY() == other.getY()) return false;
        if (!withinOne(t.getX(), other.getX())) return false;
        if (!withinOne(t.getY(), other.getY())) return false;
        if (other.getHasWorker()) return false;
        return other.getCurrentLevel() < 4;
    }

    // MOVE
    /**
     * Moves a worker to another tile.
     *
     * @param x the x coordinate of the chosen tile
     * @param y the y coordinate of the chosen tile
     * @param workerId id of the worker to be moved
     * @return boolean of whether the move succeeded
     */
    public abstract boolean relocateWorker(int x, int y, int workerId); // PAN

    /**
     * Allows the worker to move tiles, if permitted.
     *
     * @param x the x coordinate of the test tile
     * @param y the y coordinate of the test tile
     * @param id the id of the worker
     * @return boolean of whether the worker has successfully moved
     */
    public boolean relocateHelper(int x, int y, int id) {
        Tile t = game.retrieveTile(x, y);
        if (!this.isLegalMoveTile(t, id)) {
            System.out.println("Cannot move to this tile!");
            return false;
        }
        Tile old = p.findCurrentTile(id);
        old.jumped();
        t.jumped();
        return true;
    }

    /**
     * Sees if a worker can move from their current tile to a select tile.
     *
     * @param other - the other tile to be tested
     * @param id the id of the worker
     * @return boolean of whether 'other' is a legal move
     */
    public abstract boolean isLegalMoveTile(Tile other, int id); // MINOTAUR

    /**
     * Private helper method that tests if another tile can be relocated to.
     *
     * @param other - the other tile to be tested
     * @param id the id of the worker
     * @return boolean of whether 'other' is a legal move
     */
    public abstract boolean tileCheck(Tile other, int id); // MINOTAUR

    // GAME STATE
    /**
     * At any given point in the game, determines the winner- or returns null if there is no winner.
     *
     * @return Player the winning player
     */
    public abstract Player getWinner(); // PAN

    /**
     * Checks if the game state is valid.
     *
     * @return boolean of whether the game is still going
     */
    public boolean isValidGame() {
        if (isPlayerStuck()) return false;
        if (isOtherStuck()) return false;
        return !p.isWinner();
    }

    /**
     * Helper method that determines if a player cannot make a move.
     *
     * @return boolean of whether the player can make a move
     */
    public boolean isPlayerStuck() {
        for (Worker w : p.getWorkerList()) {
            if (!isStuck(w.getWorkerId())) return false;
        }
        return true;
    }

    /**
     * Helper method that determines if the opposing player cannot make a move.
     *
     * @return boolean of whether the player can make a move
     */
    public boolean isOtherStuck() {
        for (Worker w : other.getWorkerList()) {
            if (!isStuck(w.getWorkerId())) return false;
        }
        return true;
    }

    /**
     * Helper function that determines if the worker cannot move to another space.
     *     * @param id the id of the worker
     * @return boolean of whether the worker can move
     */
    public boolean isStuck(int id) {
        for (Tile t : game.getGameBoard().getTileList()) {
            if (isValidTile(t, id)) return false;
        }
        return true;
    }

    /**
     * Sees if a worker can perform either move or build operation on a select tile.
     *
     * @param other - the other tile to be tested
     * @param id the id of the worker
     * @return boolean of whether 'other' is a legal move or build
     */
    public boolean isValidTile(Tile other, int id) {
        Tile t = p.findCurrentTile(id);
        if (t != null) {return (this.isLegalBuildTile(other, id) && this.isLegalMoveTile(other, id));}
        return false;
    }

    // OTHER HELPERS
    /**
     * Private helper that prints a message for the losing player.
     *
     */
    public void loser() {
        System.out.println("Player " + p.toString() + "has lost the match due to being stuck!");
    }

    /**
     * Private helper that prints a message for the winning player.
     *
     */
    public void winner() {
        System.out.println("Player " + p.toString() + "has won the match due scaling the third level!");
    }

    /**
     * Private helper method that test if two values are within one.
     *
     * @param a - First int
     * @param b - Second int
     * @return boolean of whether a and b is within 1
     */
    public boolean withinOne(int a, int b) {
        return (Math.abs(a - b) <= 1);
    }
}
