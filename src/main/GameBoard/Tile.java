package main.GameBoard;

public class Tile {

    private int x;
    private int y;
    private int currentLevel; // consider attaching a Tower object instead of current level
    private boolean hasWorker;

    // arg constructor
    public Tile(int x, int y) {
        assert x > -1 && x < 5;
        assert y > -1 && y < 5;
        // assert coordinates cannot be repeated?
        this.x = x;
        this.y = y;
        this.currentLevel = 0;
        this.hasWorker = false;
    }

    // setter methods
    public boolean build() {
        this.currentLevel += 1;
        return true;
    }

    public boolean jumped() {
        this.hasWorker = !this.hasWorker;
        return true;
    }

    // getter methods
    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getCurrentLevel() {
        return this.currentLevel;
    }

    public boolean getHasWorker() {
        return this.hasWorker;
    }

    // methods
    public boolean isLegalMoveTile(Tile other) {
        return this.tileCheck(other);
    }

    public boolean isLegalBuildTile(Tile other) {
        return this.tileBuildCheck(other);
    }

    public boolean isValidTile(Tile other) {
        return (this.isLegalBuildTile(other) && this.isLegalMoveTile(other));
    }

    public void resetTile() {
        this.currentLevel = 0;
        this.hasWorker = false;
    }

    // helper methods
    private boolean withinOne(int a, int b) {
        return (Math.abs(a - b) <= 1);
    }

    private boolean tileCheck(Tile other) {
        if (this.getCurrentLevel() < other.getCurrentLevel()) {
            if (!withinOne(this.getCurrentLevel(), other.getCurrentLevel())) return false;
        }
        if (other.currentLevel == 4) return false;
        return tileBuildCheck(other);
    }

    private boolean tileBuildCheck(Tile other) {
        if (this.getX() == other.getX() && this.getY() == other.getY()) return false;
        if (!withinOne(this.getX(), other.getX())) return false;
        if (!withinOne(this.getY(), other.getY())) return false;
        if (other.getHasWorker()) return false;
        return true;
    }
}
