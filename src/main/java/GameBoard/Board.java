package GameBoard;

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

    // helpers
    /**
     * Private helper method that gets the tile from the tile list.
     *
     * @param x the x value in the array
     * @param y the y value in the array
     * @return int- the index position of that tile in the tile array
     */
    private int translate(int x, int y) { return x * 5 + y; }

}
