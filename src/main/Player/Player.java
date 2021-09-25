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
    public void addNewWorker(Worker w) {
        this.workerList.add(w);
    }

    public void removeWorker(Worker w) {
        this.workerList.remove(w);
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
