package main.God.Cards;

import main.GameBoard.Tile;
import main.GameSystem.Game;
import main.God.AbstractGod;
import main.God.CardLogic;
import main.Player.Player;
import main.Player.Worker;

public class Minotaur extends AbstractGod {

    // constructor
    /**
     * Minotaur Constructor (arg)
     *
     */
    public Minotaur(Game game, Player p) {
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
     * Private helper method that tests if another tile can be relocated to.
     *
     * @param other - the other tile to be tested
     * @param id the id of the worker
     * @return boolean of whether 'other' is a legal move
     */
    @Override // MODIFIED
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
        return tileMinCheck(other, id);
    }

    /**
     * Private helper method that tests if another tile can be moved to.
     *
     * @param other - the other tile to be tested
     * @param id the id of the worker
     * @return boolean of whether 'other' is a legal move
     */
    private boolean tileMinCheck(Tile other, int id) { // NEW METHOD
        Tile t = p.findCurrentTile(id);
        if (t.getX() == other.getX() &&
                t.getY() == other.getY()) return false;
        if (!withinOne(t.getX(), other.getX())) return false;
        if (!withinOne(t.getY(), other.getY())) return false;
        for (Worker w : this.other.getWorkerList()) {
            if (w.getCurrentTile() == other) {
                int wid = w.getWorkerId();
                int otherX = other.getX();
                int otherY = other.getY();
                int thisX = t.getX();
                int thisY = t.getY();

                if (otherX - thisX == 1 && otherY == thisY && otherX <= 3) {
                    return relocateOpposite(otherX + 1, otherY, wid);
                } else if (thisX - otherX == 1 && otherY == thisY && otherX >= 1) {
                    return relocateOpposite(otherX - 1, otherY, wid);
                } else if (otherY - thisY == 1 && otherX == thisX && otherY <= 3) {
                    return relocateOpposite(otherX, otherY + 1, wid);
                } else if (thisY - otherY == 1 && otherX == thisX && otherY >= 1) {
                    return relocateOpposite(otherX, otherY - 1, wid);
                }
            }
        }
        if (other.getHasWorker()) return false;
        return other.getCurrentLevel() < 4;
    }

    /**
     * Private helper method that tests if another tile can be moved to.
     *
     * @param x - the x coordinate of the tile
     * @param y - the y coordinate of the tile
     * @param workerId the id of the worker
     * @return boolean of whether the minotaur "push" has succeeded
     */
    private boolean relocateOpposite(int x, int y, int workerId) { // NEW METHOD
        if (this.relocateHelperOpposite(x, y, workerId)) {
            Worker w = other.getWorker(workerId);
            Tile t = game.retrieveTile(x, y);
            other.changeWorkerTile(w, t);
            return true;
        }
        return false;
    }

    /**
     * Allows the opposite worker to move tiles, if permitted.
     *
     * @param x the x coordinate of the test tile
     * @param y the y coordinate of the test tile
     * @param id the id of the worker
     * @return boolean of whether the worker has successfully moved
     */
    private boolean relocateHelperOpposite(int x, int y, int id) { // NEW METHOD
        Tile t = game.retrieveTile(x, y);
        if (!this.isLegalMoveTileOpposite(t, id)) {
            System.out.println("Cannot move to this tile!");
            return false;
        }
        Tile old = other.findCurrentTile(id);
        old.jumped();
        t.jumped();
        return true;
    }

    /**
     * Sees if the worker to be pushed can move from their current tile to a select tile.
     *
     * @param other - the other tile to be tested
     * @param id the id of the worker
     * @return boolean of whether 'other' is a legal move
     */
    private boolean isLegalMoveTileOpposite(Tile other, int id) { // NEW METHOD
        Tile t = this.other.findCurrentTile(id);
        if (t == null) { return false; }
        if (other.getHasWorker()) { return false; }
        return other.getCurrentLevel() < 4;
    }

    // GAME STATE
    /**
     * At any given point in the game, determines the winner- or returns null if there is no winner.
     *
     * @param cl the opposing god card logic
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
