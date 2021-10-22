package main.Player;

import main.GameBoard.Tile;

public class Worker {

    private Tile currentTile;
    private final int workerId;

    /**
     * Worker Constructor
     *
     * @param workerId id of worker
     */
    public Worker(int workerId) {
        this.workerId = workerId;
    }

    // setter methods
    /**
     * Setter method to set the current tile the worker is on
     *
     * @param t the tile to be set
     */
    public void setCurrentTile(Tile t) {
        this.currentTile = t;
    }

    /**
     * Setter method to set the current tile the worker is on (no arg default setter)
     *
     */
    public void setCurrentTile() {
        this.currentTile = null;
    }

    // getter methods
    /**
     * Getter method that returns the current tile the worker is on.
     *
     * @return Tile the tile associated with the worker
     */
    public Tile getCurrentTile() {
        return this.currentTile;
    }

    /**
     * Getter method that returns the id of the worker.
     *
     * @return int the id of the worker
     */
    public int getWorkerId() {
        return this.workerId;
    }

    // helper methods
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
