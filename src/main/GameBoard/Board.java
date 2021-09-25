package main.GameBoard;

import main.Player.Player;
import main.Worker.Worker;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private List<Tile> tileList;
    private boolean validBoard;

    // constructor
    public Board() {
        this.tileList = new ArrayList<Tile>(25);
        assert tileList.size() == 25;
    }

    // getters
    public List<Tile> getTileList() {
        return this.tileList;
    }

    // methods
    public void resetBoard() {
        for (Tile t : this.tileList) {
            t.resetTile();
        }
    }

    // helper methods

}
