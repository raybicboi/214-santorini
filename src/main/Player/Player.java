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
        this.workerList = new ArrayList<>(2);
        for (int i = 0; i < 2; i++) {
            this.workerList.set(i, new Worker(i));
        }
    }

    // setter method
//    /**
//     * Adds a new worker to belong to the player.
//     *
//     * @param w the worker to be added
//     * @return boolean of whether the worker has been successfully added
//     */
//    public boolean addNewWorker(Worker w) {
//        if (this.workerList.size() >= 2) {
//            System.out.println("This player already has two workers");
//            return false;
//        }
//        if (this.workerList.contains(w)) {
//            System.out.println("This player already has this worker");
//            return false;
//        }
//        this.workerList.add(w);
//        return true;
//    }

//    /**
//     * Removes a worker so that it no longer belongs to this player.
//     *
//     * @param w worker to be removed
//     * @return boolean of whether the remove was successful
//     */
//    public boolean removeWorker(Worker w) {
//        if (!this.workerList.contains(w)) {
//            System.out.println("Cannot remove worker that does not exist");
//            return false;
//        }
//        this.workerList.remove(w);
//        return true;
//    }

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
        Worker w = this.workerList.get(i);
        return w;
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
}
