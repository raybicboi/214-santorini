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
    public boolean isValidTile(Tile t) {
        return true;
    }

    public boolean isValidBoard(Player p1, Player p2) {
        return true;
    }

    // helper methods
    private boolean playerStuck(Player a) {
        for (Worker w : a.getWorkerList()) {
            if (!w.isStuck(this)) return false;
        }
        return true;
    }

    private boolean winner (Player a) {
        for (Worker w : a.getWorkerList()) {
            if (w.reachedThird()) return true;
        }
        return false;
    }
}
