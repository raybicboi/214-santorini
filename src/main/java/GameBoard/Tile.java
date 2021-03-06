package GameBoard;

public class Tile {

    private final int x;
    private final int y;
    private int currentLevel; // consider attaching a Tower object instead of current level
    private boolean hasWorker;

    // arg constructor
    /**
     * Tile Constructor
     *
     * @param x coordinate of the tile
     * @param y coordinate of the tile
     */
    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        this.currentLevel = 0;
        this.hasWorker = false;
    }

    // setter methods
    /**
     * Builds/Adds a "tower" onto the tile.
     *
     */
    public void build() {
        this.currentLevel += 1;
//        return true;
    }

    /**
     * Toggles the has worker flag.
     *
     */
    public void jumped() {
        this.hasWorker = !this.hasWorker;
//        return true;
    }

    // getter methods
    /**
     * Getter method for the x coordinate.
     *
     * @return the x coordinate of the tile
     */
    public int getX() {
        return this.x;
    }

    /**
     * Getter method for the y coordinate.
     *
     * @return the y coordinate of the tile
     */
    public int getY() {
        return this.y;
    }

    /**
     * Getter method for the current tower height.
     *
     * @return the tower height
     */
    public int getCurrentLevel() {
        return this.currentLevel;
    }

    /**
     * Getter method for the has worker flag.
     *
     * @return whether the tile current has a worker
     */
    public boolean getHasWorker() {
        return this.hasWorker;
    }

    // methods
    /**
     * Resets the tile to its initial state.
     *
     */
    public void resetTile() {
        this.currentLevel = 0;
        this.hasWorker = false;
    }

    // helper methods
}
