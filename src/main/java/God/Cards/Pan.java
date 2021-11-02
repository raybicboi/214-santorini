package God.Cards;

import GameBoard.Tile;
import GameSystem.Game;
import God.AbstractGod;
import God.CardLogic;
import Player.Player;
import Player.Worker;

public class Pan extends AbstractGod {

    private boolean panFlag;

    // constructor
    /**
     * Pan Constructor (arg)
     *
     */
    public Pan(Game game, Player p) {
        super(game, p);
        this.panFlag = false;
    }

    // Getters
    /**
     * Getter method for the pan flag.
     *
     * @return boolean value of the pan flag
     */
    public boolean getPanFlag() {
        return this.panFlag;
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
        if(this.buildHelper(x, y, id)) {
            game.switchCurrentPlayer();
            canMove = true;
            canBuild = false;
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
    @Override // MODIFIED
    public boolean relocateWorker(int x, int y, int workerId) {
        Tile oldTile = p.findCurrentTile(workerId);
        int oldLevel = oldTile.getCurrentLevel();
        if (this.relocateHelper(x, y, workerId)) {
            Worker w = p.getWorker(workerId);
            Tile t = game.retrieveTile(x, y);
            p.changeWorkerTile(w, t);
            canMove = false;
            canBuild = true;
            int tLevel = t.getCurrentLevel();
            if (oldLevel - tLevel >= 2) { this.panFlag = true; }
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
    @Override // MODIFIED
    public Player getWinner(CardLogic cl) {
        if (this.panFlag) {
            System.out.println("Player " + p.toString() + " has won due to dropping 2 levels with the Pan perk!");
            System.out.println("Player " + other.toString() + " has lost the match :(");
            return p;
        }
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
