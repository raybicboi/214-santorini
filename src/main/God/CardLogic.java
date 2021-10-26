package main.God;

import main.Player.Player;

public interface CardLogic {

    boolean buildTower(int x, int y, int id);

    boolean relocateWorker(int x, int y, int workerId);

    Player getWinner();
}
