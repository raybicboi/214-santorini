package main.God.Cards;

import main.GameBoard.Tile;
import main.GameSystem.Game;
import main.God.AbstractGod;
import main.Player.Player;
import main.Player.Worker;

public class Demeter extends AbstractGod {

    private int firstX;
    private int firstY;

    // constructor
    /**
     * Demeter Constructor (arg)
     *
     */
    public Demeter(Game game, Player p) {
        super(game, p);
        firstX = -1;
        firstY = -1;
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
    @Override // MODIFIED
    public boolean buildTower(int x, int y, int id) {
        if(this.buildHelper(x, y, id)) {
            firstX = x;
            firstY = y;
            return true;
        }
        return false;
    }

    /**
     * Builds/Adds a "tower" onto the tile for the second time in the same turn.
     *
     * @param x the x coordinate of the chosen tile
     * @param y the y coordinate of the chosen tile
     * @param id the id of the worker
     * @return boolean of whether the build succeeded
     */
    // NEW METHOD
    public boolean buildTower2(int x, int y, int id) {
        if (x == -1 && y == -1) {
            game.switchCurrentPlayer();
            return true;
        }
        if (x == firstX && y == firstY) return false;
        if(this.buildHelper(x, y, id)) {
            firstX = -1;
            firstY = -1;
            game.switchCurrentPlayer();
            return true;
        }
        return false;
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
            athena = false;
            return true;
        }
        return false;
    }

    /**
     * Sees if a worker can move from their current tile to a select tile.
     *
     * @param other - the other tile to be tested
     * @param id the id of the worker
     * @return boolean of whether 'other' is a legal move
     */
    @Override
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
    @Override
    public boolean tileCheck(Tile other, int id) {
        Tile t = p.findCurrentTile(id);
        if (athena && other.getCurrentLevel() > t.getCurrentLevel()) {
            System.out.println("Cannot traverse to higher level due to Athena perk");
            return false;
        }
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
    public Player getWinner() {
        if (isValidGame()) return null;
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
}
