package main.Player;

import main.GameBoard.Board;
import main.Worker.Worker;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private List<Worker> workerList;

    // constructor
    public Player() {
        this.workerList = new ArrayList<>(2);
    }

    // setter method
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

    public boolean removeWorker(Worker w) {
        if (!this.workerList.contains(w)) {
            System.out.println("Cannot remove worker that does not exist");
            return false;
        }
        this.workerList.remove(w);
        return true;
    }

    // getter method
    public List<Worker> getWorkerList() {
        return this.workerList;
    }

    // methods
    public boolean isPlayerStuck(Board b) {
        for (Worker w : this.getWorkerList()) {
            if (!w.isStuck(b)) return false;
        }
        return true;
    }

    public boolean isWinner() {
        for (Worker w : this.getWorkerList()) {
            if (w.reachedThird()) return true;
        }
        return false;
    }
}
