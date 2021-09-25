package main.Player;

import main.Worker.Worker;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private List<Worker> workerList;

    // constructor
    public Player() {
        this.workerList = new ArrayList<>(2);
    }

    // getter method
    public List<Worker> getWorkerList() {
        return this.workerList;
    }
}
