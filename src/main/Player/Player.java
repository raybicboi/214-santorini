package main.Player;

import main.GameBoard.Board;
import main.Worker.Worker;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private List<Worker> workerList;

    // constructor
    /**
     * Player Constructor (no arg)
     *
     */
    public Player() {
        this.workerList = new ArrayList<>(2);
    }

    // setter method
    /**
     * Adds a new worker to belong to the player.
     *
     * @param w the worker to be added
     * @return boolean of whether the worker has been successfully added
     */
    public boolean addNewWorker(Worker w) {
        if (this.workerList.size() >= 2) {
            System.out.println("This player already has two workers");
            return false;
        }
        if (this.workerList.contains(w)) {
            System.out.println("This player already has this worker");
            return false;
        }
        this.workerList.add(w);
        return true;
    }

    /**
     * Removes a worker so that it no longer belongs to this player.
     *
     * @param w worker to be removed
     * @return boolean of whether the remove was successful
     */
    public boolean removeWorker(Worker w) {
        if (!this.workerList.contains(w)) {
            System.out.println("Cannot remove worker that does not exist");
            return false;
        }
        this.workerList.remove(w);
        return true;
    }

    // getter method
    /**
     * Getter method that returns the list of workers associated with the player.
     *
     * @return List<Worker> the list of workers
     */
    public List<Worker> getWorkerList() {
        return this.workerList;
    }

    // methods
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
}
