package main.Tower;

import main.GameBoard.Tile;

public class Tower {

    private int level;

    // constructor
    public Tower() {
        this.level = 0;
    }

    // getter method
    public int getLevel() {
        return this.level;
    }

    // methods
    public boolean scalable() {
        return this.getLevel() < 4;
    }

    public boolean build() {
        this.level += 1;
        return true;
    }

}
