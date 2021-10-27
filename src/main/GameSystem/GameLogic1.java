package main.GameSystem;

import main.GameBoard.Tile;
import main.God.CardLogic;
import main.Player.Player;
import main.Player.Worker;

public class GameLogic1 implements CardLogic {

    private final Player p;
    private final Game game;

    // constructor
    /**
     * GameLogic Constructor (arg)
     *
     */
    public GameLogic1(Game game) {
        this.game = game;
        this.p = game.getP1();
    }


    // methods

    // BUILD
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
        if(this.build(x, y, id, p)) {
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
     * @param id the id of the player
     * @param p the player initiating that action
     * @return boolean of whether the build succeeded
     */
    public boolean build(int x, int y, int id, Player p) {
        Tile t = game.retrieveTile(x, y);
        if (!this.isLegalBuildTile(t, id, p)) {
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
     * @param id the id of the player
     * @param p the player initiating that action
     * @return boolean of whether 'other' is a legal build
     */
    public boolean isLegalBuildTile(Tile other, int id, Player p) {
        Tile t = p.findCurrentTile(id);
        if (t != null) { return this.tileBuildCheck(other, id, p); }
        return false;
    }

    /**
     * Private helper method that tests if another tile can be built on.
     *
     * @param other - the other tile to be tested
     * @param id the id of the player
     * @param p the player initiating that action
     * @return boolean of whether 'other' is a legal move
     */
    private boolean tileBuildCheck(Tile other, int id, Player p) {
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
        if (this.relocate(x, y, workerId, p)) {
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
     * @param id the id of the player
     * @param p the player initiating that action
     * @return boolean of whether the worker has successfully moved
     */
    public boolean relocate(int x, int y, int id, Player p) {
        Tile t = game.retrieveTile(x, y);
        if (!this.isLegalMoveTile(t, id, p)) {
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
     * @param id the id of the player
     * @param p the player initiating that action
     * @return boolean of whether 'other' is a legal move
     */
    public boolean isLegalMoveTile(Tile other, int id, Player p) {
        Tile t = p.findCurrentTile(id);
        if (t != null) { return this.tileCheck(other, id, p); }
        return false;
    }

    /**
     * Private helper method that tests if another tile can be relocated to.
     *
     * @param other - the other tile to be tested
     * @param id the id of the player
     * @param p the player initiating that action
     * @return boolean of whether 'other' is a legal move
     */
    private boolean tileCheck(Tile other, int id, Player p) {
        Tile t = p.findCurrentTile(id);
        if (t.getCurrentLevel() < other.getCurrentLevel()) {
            if (!withinOne(t.getCurrentLevel(), other.getCurrentLevel())) return false;
        }
//        if (other.currentLevel == 4) return false;
        return tileBuildCheck(other, id, p);
    }

    // GAME STATE
    /**
     * At any given point in the game, determines the winner- or returns null if there is no winner.
     *
     * @return Player the winning player
     */
    @Override
    public Player getWinner() {
        if (isValidGame()) return null;
        Player p0 = game.getP0();
        if (p0.isPlayerStuck(game.getGameBoard())) {
            loser(p0);
            return p;
        } else if (p.isPlayerStuck(game.getGameBoard())) {
            loser(p);
            return p0;
        }
        if (p0.isWinner()) {
            winner(p0);
            return p0;
        }
        winner(p);
        return p;
    }

    /**
     * Checks if the game state is valid.
     *
     * @return boolean of whether the game is still going
     */
    public boolean isValidGame() {
        Player p0 = game.getP0();
        return isValidHelp(p) && isValidHelp(p0);
    }

    /**
     * Private helper that checks if a player is stuck or has won.
     *
     * @param p the player to be tested
     * @return boolean whether the player can make a move
     */
    private boolean isValidHelp(Player p) {
        if (p.isPlayerStuck(game.getGameBoard())) {
            return false;
        }
        return !p.isWinner();
    }

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

    // OTHER HELPERS
    /**
     * Private helper method that test if two values are within one.
     *
     * @param a - First int
     * @param b - Second int
     * @return boolean of whether a and b is within 1
     */
    private boolean withinOne(int a, int b) {
        return (Math.abs(a - b) <= 1);
    }

    // method not in use
    /**
     * Sees if a worker can perform either move or build operation on a select tile.
     *
     * @param other - the other tile to be tested
     * @param id the id of the player
     * @param p the player initiating that action
     * @return boolean of whether 'other' is a legal move or build
     */
    public boolean isValidTile(Tile other, int id, Player p) {
        Tile t = p.findCurrentTile(id);
        if (t != null) {return (this.isLegalBuildTile(other, id, p) && this.isLegalMoveTile(other, id, p));}
        return false;
    }
}
