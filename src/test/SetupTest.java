package test;

import main.GameSystem.Game;
import main.GameSystem.GameLogic;


public class SetupTest{

    public Game game;
    public GameLogic p0;
    public GameLogic p1;

    public void setUp() {
        game = new Game();
        game.dropWorker(0, 0, 0, this.game.getP0());
        game.dropWorker(1, 3, 1, this.game.getP0());
        game.dropWorker(4, 4, 0, this.game.getP1());
        game.dropWorker(3, 2, 1, this.game.getP1());

        p0 = new GameLogic(game, game.getP0());
        p1 = new GameLogic(game, game.getP1());
    }
}