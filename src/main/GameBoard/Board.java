package main.GameBoard;

import main.Player.Player;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private final List<Tile> tileList;

    // constructor
    /**
     * Game Board Constructor (no arg)
     *
     */
    public Board() {
        this.tileList = new ArrayList<>();
        for (int i = 0; i < 5 ; i++) {
            for (int j = 0; j < 5; j++) {
                this.tileList.add(new Tile(i, j));
            }
        }
    }

    /**
     * Builds/Adds a "tower" onto the tile.
     *
     * @param x the x coordinate of the chosen tile
     * @param y the y coordinate of the chosen tile
     * @param id the id of the player
     * @param p the player initiating that action
     * @return boolean of whether the build succeeded
     */
    public boolean build(int x, int y, int id, Player p) {
        Tile t = getTile(x, y);
        if (!this.isLegalBuildTile(t, id, p)) {
            System.out.println("Cannot build on this tile!");
            return false;
        }
        t.build();
        return true;
    }

    // getters
    /**
     * Getter method for retrieving tiles on the current board.
     *
     * @return A list of the tiles currently on the board
     */
    public List<Tile> getTileList() {
        return this.tileList;
    }

    /**
     * Getter method for retrieving a single tile on the current board.
     *
     * @param x the x coordinate of the tile to be retrieved
     * @param y the y coordinate of the tile to be retrieved
     * @return the tile found based on the coordinates given
     */
    public Tile getTile(int x, int y) {
        return this.tileList.get(translate(x, y));
    }

    // methods

    /**
     * Sees if a worker can move from their current tile to a select tile.
     *
     * @param other - the other tile to be tested
     * @param id the id of the player
     * @param p the player initiating that action
     * @return boolean of whether 'other' is a legal move
     */
    public boolean isLegalMoveTile(Tile other, int id, Player p) {
        Tile t = p.findCurrentTile(id);
        if (t != null) { return this.tileCheck(other, id, p); }
        return false;
    }

    /**
     * Sees if a worker can build on a select tile from its current location.
     *
     * @param other - the other tile to be tested
     * @param id the id of the player
     * @param p the player initiating that action
     * @return boolean of whether 'other' is a legal build
     */
    public boolean isLegalBuildTile(Tile other, int id, Player p) {
        Tile t = p.findCurrentTile(id);
        if (t != null) { return this.tileBuildCheck(other, id, p); }
        return false;
    }

    /**
     * Sees if a worker can perform either move or build operation on a select tile.
     *
     * @param other - the other tile to be tested
     * @param id the id of the player
     * @param p the player initiating that action
     * @return boolean of whether 'other' is a legal move or build
     */
    public boolean isValidTile(Tile other, int id, Player p) {
        Tile t = p.findCurrentTile(id);
        if (t != null) {return (this.isLegalBuildTile(other, id, p) && this.isLegalMoveTile(other, id, p));}
        return false;
    }

    /**
     * Resets the game board by resetting every tile.
     *
     */
    public void resetBoard() {
        for (Tile t : this.tileList) {
            t.resetTile();
        }
    }

    /**
     * Drops a worker on a select tile prior to the start of the game
     *
     * @param x the x coordinate of the test tile
     * @param y the y coordinate of the test tile
     * @return boolean of whether the worker has been successfully dropped on the selected tile
     */
    public boolean initDrop(int x, int y) {
        Tile t = getTile(x, y);
        // make sure there are no buildings, no other players in that spot
        if (t.getHasWorker()) {
            System.out.println("This tile already has another worker");
            return false;
        }
        for (Tile tile : this.getTileList()) {
            if (tile.getCurrentLevel() != 0) {
                System.out.println("Game already started! Cannot drop worker");
                return false;
            }
        }
        t.jumped();
        return true;
    }

    /**
     * Allows the worker to move tiles, if permitted.
     *
     * @param x the x coordinate of the test tile
     * @param y the y coordinate of the test tile
     * @param id the id of the player
     * @param p the player initiating that action
     * @return boolean of whether the worker has successfully moved
     */
    public boolean relocate(int x, int y, int id, Player p) {
        Tile t = getTile(x, y);
        if (!this.isLegalMoveTile(t, id, p)) {
            System.out.println("Cannot move to this tile!");
            return false;
        }
        Tile old = p.findCurrentTile(id);
        old.jumped();
        t.jumped();
        return true;
    }

    // helpers
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
     * @param id the id of the player
     * @param p the player initiating that action
     * @return boolean of whether 'other' is a legal move
     */
    private boolean tileCheck(Tile other, int id, Player p) {
        Tile t = p.findCurrentTile(id);
        if (t.getCurrentLevel() < other.getCurrentLevel()) {
            if (!withinOne(t.getCurrentLevel(), other.getCurrentLevel())) return false;
        }
//        if (other.currentLevel == 4) return false;
        return tileBuildCheck(other, id, p);
    }

    /**
     * Private helper method that tests if another tile can be built on.
     *
     * @param other - the other tile to be tested
     * @param id the id of the player
     * @param p the player initiating that action
     * @return boolean of whether 'other' is a legal move
     */
    private boolean tileBuildCheck(Tile other, int id, Player p) {
        Tile t = p.findCurrentTile(id);
        if (t.getX() == other.getX() &&
                t.getY() == other.getY()) return false;
        if (!withinOne(t.getX(), other.getX())) return false;
        if (!withinOne(t.getY(), other.getY())) return false;
        if (other.getHasWorker()) return false;
        return other.getCurrentLevel() < 4;
    }

    /**
     * Private helper method that gets the tile from the tile list.
     *
     * @param x the x value in the array
     * @param y the y value in the array
     * @return int- the index position of that tile in the tile array
     */
    private int translate(int x, int y) { return x * 5 + y; }

}
