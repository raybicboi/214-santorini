package main.Player;

import main.GameBoard.Board;
import main.GameBoard.Tile;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private final List<Worker> workerList;
    private final int playerId;

    // constructor
    /**
     * Player Constructor
     *
     * @param playerId id of player
     */
    public Player(int playerId) {
        this.playerId = playerId;
//        this.workerList = new ArrayList<>(2);
        this.workerList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
//            this.workerList.set(i, new Worker(i));
            this.workerList.add(new Worker(i));
        }
    }

    // setter method
    
    // getter method
    /**
     * Getter method that returns the list of workers associated with the player.
     *
     * @return List<Worker> the list of workers
     */
    public List<Worker> getWorkerList() {
        return this.workerList;
    }

    /**
     * Getter method that allows access to a specific worker belonging to a player based on their worker ID.
     *
     * @params i the worker id to be passed
     * @return Worker the worker requested
     */
    public Worker getWorker(int i) {
        return this.workerList.get(i);
    }

    /**
     * Getter method that returns the id of the player.
     *
     * @return int the id of the player
     */
    public int getPlayerId() {
        return this.playerId;
    }

    // methods
    /**
     * Change the current worker's current tile spot.
     *
     * @param w the worker to be set
     * @param t the tile to set the worker on
     */
    public void changeWorkerTile(Worker w, Tile t) {
        w.setCurrentTile(t);
    }

    /**
     * Helper method that determines if a player cannot make a move.
     *
     * @param b the board associated with the game
     * @return boolean of whether the player can make a move
     */
    public boolean isPlayerStuck(Board b) {
        for (Worker w : this.getWorkerList()) {
            if (!w.isStuck(b)) return false;
        }
        return true;
    }

    /**
     * Helper method that determines if a player reached third tower level.
     *
     * @return boolean of whether the player reached the third level
     */
    public boolean isWinner() {
        for (Worker w : this.getWorkerList()) {
            if (w.reachedThird()) return true;
        }
        return false;
    }

    /**
     * Helper method that resets the current tile of both workers.
     *
     */
    public void clearPlayer() {
        for (Worker w : this.getWorkerList()) {
            w.setCurrentTile();
        }
    }

    /**
     * Helper method that finds if a worker is currently on a tile.
     *
     * @param id id of the worker
     * @return boolean whether the worker is currently on a tile
     */
    public boolean checkWorkerNull(int id) {
        Worker w = getWorker(id);
        if (w.getCurrentTile() == null) { return true; }
        return false;
    }
}
