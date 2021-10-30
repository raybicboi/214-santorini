package main.GameSystem;

import main.GameBoard.Tile;
import main.God.CardLogic;
import main.Player.Player;
import main.Player.Worker;

public class GameLogic implements CardLogic {

    private final Player p;
    private final Player other;
    private final Game game;

    // constructor
    /**
     * GameLogic Constructor (arg)
     *
     */
    public GameLogic(Game game, Player p) {
        this.game = game;
        if (p == game.getP0()) {
            this.p = game.getP0();
            this.other = game.getP1();
        } else {
            this.p = game.getP1();
            this.other = game.getP0();
        }
    }

    public Player getOther() {
        return this.other;
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
    public boolean buildTower(int x, int y, int id) {
        if(this.buildHelper(x, y, id)) {
            game.switchCurrentPlayer();
            return true;
        }
        return false;
    }

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
    @Override
    public boolean relocateWorker(int x, int y, int workerId) {
        if (this.relocateHelper(x, y, workerId)) {
            Worker w = p.getWorker(workerId);
            Tile t = game.retrieveTile(x, y);
            p.changeWorkerTile(w, t);
            return true;
        }
        return false;
    }

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
    public boolean isLegalMoveTile(Tile other, int id) {
        Tile t = p.findCurrentTile(id);
        if (t != null) { return this.tileCheck(other, id); }
        return false;
    }

    /**
     * Private helper method that tests if another tile can be relocated to.
     *
     * @param other - the other tile to be tested
     * @param id the id of the worker
     * @return boolean of whether 'other' is a legal move
     */
    public boolean tileCheck(Tile other, int id) {
        Tile t = p.findCurrentTile(id);
        if (t.getCurrentLevel() < other.getCurrentLevel()) {
            if (!withinOne(t.getCurrentLevel(), other.getCurrentLevel())) return false;
        }
//        if (other.currentLevel == 4) return false;
        return tileBuildCheck(other, id);
    }

    // GAME STATE
    /**
     * At any given point in the game, determines the winner- or returns null if there is no winner.
     *
     * @return Player the winning player
     */
    @Override
    public Player getWinner(GameLogic gl) {
        if (isValidGame(gl)) return null;
        if (isPlayerStuck()) {
            loser();
            return other;
        }
        if (p.isWinner()) {
            winner();
            return p;
        }
        return null;
    }

    /**
     * Checks if the game state is valid.
     *
     * @return boolean of whether the game is still going
     */
    public boolean isValidGame(GameLogic gl) {
        if (isPlayerStuck()) return false;
        if (gl.isPlayerStuck()) return false;
        return !p.isWinner() && !other.isWinner();
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
     * Helper function that determines if the worker cannot move to another space.
     *
     * @param id the id of the worker
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
        System.out.println("Player " + other.toString() + "has won the match :)");
    }

    /**
     * Private helper that prints a message for the winning player.
     *
     */
    public void winner() {
        System.out.println("Player " + p.toString() + "has won the match due scaling the third level!");
        System.out.println("Player " + other.toString() + "has lost the match :(");
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
