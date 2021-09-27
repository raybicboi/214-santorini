package main.GameBoard;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private List<Tile> tileList;
    private boolean validBoard;

    // constructor
    /**
     * Game Board Constructor (no arg)
     *
     */
    public Board() {
        this.tileList = new ArrayList<Tile>(25);
//        assert this.tileList.size() == 25;
    }

    // setters
    /**
     * Adds a tile to the game board.
     *
     * @param t the tile to be added to the board
     */
    public void addTile(Tile t) {
        this.tileList.add(t);
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
}
