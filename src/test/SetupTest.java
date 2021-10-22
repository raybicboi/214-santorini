package test;

import main.GameSystem.Game;


public class SetupTest{

    public Game game;

    public void setUp() {
        game = new Game();
        game.dropWorker(0, 0, 0, this.game.getP0());
        game.dropWorker(1, 3, 1, this.game.getP0());
        game.dropWorker(4, 4, 0, this.game.getP1());
        game.dropWorker(3, 2, 1, this.game.getP1());
    }
}