package main.God.Cards;

import main.GameBoard.Tile;
import main.GameSystem.Game;
import main.God.AbstractGod;
import main.God.CardLogic;
import main.Player.Player;
import main.Player.Worker;

public class Athena extends AbstractGod {

    // constructor
    /**
     * Athena Constructor (arg)
     *
     */
    public Athena(Game game, Player p) {
        super(game, p);
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
    @Override
    public boolean buildTower(int x, int y, int id) {
        if(this.buildHelper(x, y, id)) {
            game.switchCurrentPlayer();
            return true;
        }
        return false;
    }

    // MOVE MODIFIED
    /**
     * Moves a worker to another tile.
     *
     * @param x the x coordinate of the chosen tile
     * @param y the y coordinate of the chosen tile
     * @param workerId id of the worker to be moved
     * @return boolean of whether the move succeeded
     */
    @Override
    public boolean relocateWorker(int x, int y, int workerId) { // what if there are two athena?
        if (this.relocateHelper(x, y, workerId)) {
            Tile original = p.findCurrentTile(workerId);

            Worker w = p.getWorker(workerId);
            Tile t = game.retrieveTile(x, y);
            p.changeWorkerTile(w, t);

            if (isHigher(original, t)) athena = true;
            else if (!isHigher(original, t)) athena = false;
            return true;
        }
        return false;
    }

    /**
     * Helper method that determines if Athena moved up.
     *
     * @param oldTile the original Tile
     * @param newTile the tile moved to
     * @return boolean of whether the worker moved to a higher level
     */
    // NEW METHOD
    public boolean isHigher(Tile oldTile, Tile newTile) {
        int oldLevel = oldTile.getCurrentLevel();
        int newLevel = newTile.getCurrentLevel();
        return newLevel > oldLevel;
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
     * @param cl the opposing card logic
     * @return Player the winning player
     */
    @Override
    public Player getWinner(CardLogic cl) {
        if (isValidGame(cl)) return null;
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
