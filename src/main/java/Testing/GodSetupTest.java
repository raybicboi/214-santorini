package Testing;

import GameSystem.Game;
import GameSystem.GameLogic;
import God.Cards.Athena;
import God.Cards.Demeter;
import God.Cards.Minotaur;
import God.Cards.Pan;

public class GodSetupTest {

    public Game game;
    public Demeter dp1;
    public Minotaur md1;
    public Pan pd1;
    public Athena ap1;
    public GameLogic gl1;

    public void setUp() {
        game = new Game();

        game.dropWorker(0, 0, 0, this.game.getP0());
        game.dropWorker(1, 3, 1, this.game.getP0());
        game.dropWorker(4, 4, 0, this.game.getP1());
        game.dropWorker(3, 2, 1, this.game.getP1());

        dp1 = new Demeter(game, game.getP1());
        md1 = new Minotaur(game, game.getP1());
        pd1 = new Pan(game, game.getP1());
        ap1 = new Athena(game, game.getP1());
        gl1 = new GameLogic(game, game.getP1());
    }
}
