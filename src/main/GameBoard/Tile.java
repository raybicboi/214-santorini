package main.GameBoard;

public class Tile {

    private int x;
    private int y;
    private int currentLevel; // consider attaching a Tower object instead of current level
    private boolean hasWorker;

    // arg constructor
    /**
     * Game Board Constructor (no arg)
     *
     * @param x coordinate of the tile
     * @param y coordinate of the tile
     */
    public Tile(int x, int y) {
//        assert x > -1 && x < 5;
//        assert y > -1 && y < 5;
        // assert coordinates cannot be repeated?
        this.x = x;
        this.y = y;
        this.currentLevel = 0;
        this.hasWorker = false;
    }

    // setter methods
    /**
     * Builds/Adds a "tower" onto the tile.
     *
     * @return boolean of whether the build succeeded
     */
    public boolean build() {
        this.currentLevel += 1;
        return true;
    }

    /**
     * Toggles the has worker flag.
     *
     * @return boolean of whether the flag has been changed
     */
    public boolean jumped() {
        this.hasWorker = !this.hasWorker;
        return true;
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
     * Sees if a worker can move from their current tile to a select tile.
     *
     * @param other - the other tile to be tested
     * @return boolean of whether 'other' is a legal move
     */
    public boolean isLegalMoveTile(Tile other) {
        return this.tileCheck(other);
    }

    /**
     * Sees if a worker can build on a select tile from its current location.
     *
     * @param other - the other tile to be tested
     * @return boolean of whether 'other' is a legal build
     */
    public boolean isLegalBuildTile(Tile other) {
        return this.tileBuildCheck(other);
    }

    /**
     * Sees if a worker can perform either move or build operation on a select tile.
     *
     * @param other - the other tile to be tested
     * @return boolean of whether 'other' is a legal move or build
     */
    public boolean isValidTile(Tile other) {
        return (this.isLegalBuildTile(other) && this.isLegalMoveTile(other));
    }

    /**
     * Resets the tile to its initial state.
     *
     */
    public void resetTile() {
        this.currentLevel = 0;
        this.hasWorker = false;
    }

    // helper methods
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

    /**
     * Private helper method that tests if another tile can be relocated to.
     *
     * @param other - the other tile to be tested
     * @return boolean of whether 'other' is a legal move
     */
    private boolean tileCheck(Tile other) {
        if (this.getCurrentLevel() < other.getCurrentLevel()) {
            if (!withinOne(this.getCurrentLevel(), other.getCurrentLevel())) return false;
        }
//        if (other.currentLevel == 4) return false;
        return tileBuildCheck(other);
    }

    /**
     * Private helper method that tests if another tile can be built on.
     *
     * @param other - the other tile to be tested
     * @return boolean of whether 'other' is a legal move
     */
    private boolean tileBuildCheck(Tile other) {
        if (this.getX() == other.getX() && this.getY() == other.getY()) return false;
        if (!withinOne(this.getX(), other.getX())) return false;
        if (!withinOne(this.getY(), other.getY())) return false;
        if (other.getHasWorker()) return false;
        if (other.currentLevel >= 4) return false;
        return true;
    }
}
