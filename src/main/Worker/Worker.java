package main.Worker;

import main.GameBoard.Board;
import main.GameBoard.Tile;
import main.Player.Player;

public class Worker {

//    private Player playerAssociated; // can be final
    private Tile currentTile;

    // arg constructor
//    public Worker(Player playerAssociated) {
//        this.playerAssociated = playerAssociated;
//    }

    // no arg constructor (does it need player associated?)
    public Worker() {}

    // getters methods
    public Tile getCurrentTile() {
        return this.currentTile;
    }

//    public Player getPlayerAssociated() {
//        return this.playerAssociated;
//    }

    // methods
    public boolean initDrop(Tile t, Board b) {
        if (this.currentTile != null) {
            System.out.println("Worker is already on another tile");
            return false;
        }
        // make sure there are no buildings, no other players in that spot
        if (t.getHasWorker()) {
            System.out.println("This tile already has another worker");
            return false;
        }
        for (Tile tile : b.getTileList()) {
            if (tile.getCurrentLevel() != 0) {
                System.out.println("Game already started! Cannot drop worker");
                return false;
            }
        }
        this.currentTile = t;
        t.jumped();
        return true;
    }

    public boolean relocate(Tile t) {
        if (!currentTile.isLegalMoveTile(t)) {
            System.out.println("Cannot move to this tile!");
            return false;
        }
        this.currentTile.jumped();
        t.jumped();
        this.currentTile = t;
        return true;
    }

    public boolean build(Tile t) {
        if (!currentTile.isLegalBuildTile(t)) {
            System.out.println("Cannot build on this tile!");
            return false;
        }
        t.build();
        return true;
    }

    public boolean isStuck(Board b) {
        for (Tile t : b.getTileList()) {
            if (currentTile.isValidTile(t)) return false;
        }
        return true;
    }

    public boolean reachedThird() {
        if (this.getCurrentTile().getCurrentLevel() == 3) return true;
        return false;
    }
}
