package main.Worker;

import main.GameBoard.Board;
import main.GameBoard.Tile;
import main.Player.Player;

public class Worker {

//    private Player playerAssociated; // can be final
    private Tile currentTile;

    // arg constructor
//    public Worker(Player playerAssociated) {
//        this.playerAssociated = playerAssociated;
//    }

    // no arg constructor (does it need player associated?)
    /**
     * Worker Constructor (no arg)
     *
     */
    public Worker() {}

    // getters methods
    /**
     * Getter method that returns the current tile the worker is on.
     *
     * @return Tile the tile associated with the worker
     */
    public Tile getCurrentTile() {
        return this.currentTile;
    }

//    public Player getPlayerAssociated() {
//        return this.playerAssociated;
//    }

    // methods
    /**
     * Allows the worker to be initially dropped onto a tile on the board before the game starts.
     *
     * @param t the tile to be dropped on
     * @param b the board associated with the game
     * @return boolean of whether the worker has been successfully dropped
     */
    public boolean initDrop(Tile t, Board b) {
        if (this.currentTile != null) {
            System.out.println("Worker is already on another tile");
            return false;
        }
        // make sure there are no buildings, no other players in that spot
        if (t.getHasWorker()) {
            System.out.println("This tile already has another worker");
            return false;
        }
        for (Tile tile : b.getTileList()) {
            if (tile.getCurrentLevel() != 0) {
                System.out.println("Game already started! Cannot drop worker");
                return false;
            }
        }
        this.currentTile = t;
        t.jumped();
        return true;
    }

    /**
     * Allows the worker to move tiles, if permitted.
     *
     * @param t the tile to be moved to
     * @return boolean of whether the worker has successfully moved
     */
    public boolean relocate(Tile t) {
        if (!currentTile.isLegalMoveTile(t)) {
            System.out.println("Cannot move to this tile!");
            return false;
        }
        this.currentTile.jumped();
        t.jumped();
        this.currentTile = t;
        return true;
    }

    /**
     * Allows the worker to build or add a tower level to a tile.
     *
     * @param t the tile to build on
     * @return boolean of whether the worker has successfully built on the tile
     */
    public boolean build(Tile t) {
        if (!currentTile.isLegalBuildTile(t)) {
            System.out.println("Cannot build on this tile!");
            return false;
        }
        t.build();
        return true;
    }

    /**
     * Helper function that determines if the worker cannot move to another space.
     *
     * @param b board representing the game
     * @return boolean of whether the worker can move
     */
    public boolean isStuck(Board b) {
        for (Tile t : b.getTileList()) {
            if (currentTile.isValidTile(t)) return false;
        }
        return true;
    }

    /**
     * Helper function that determines if the worker reached a level 3 tower.
     *
     * @return boolean of whether the worker is on a level 3 tower
     */
    public boolean reachedThird() {
        if (this.getCurrentTile().getCurrentLevel() == 3) return true;
        return false;
    }
}
