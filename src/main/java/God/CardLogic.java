package God;

import GameBoard.Tile;
import GameSystem.Game;
import Player.Player;

public interface CardLogic {

    // GETTER

    Player getOther();

    Player getPlayer();

    Game getGame();

    // BUILD
    boolean buildTower(int x, int y, int id);

    boolean buildHelper(int x, int y, int id);

    boolean isLegalBuildTile(Tile other, int id);

    boolean tileBuildCheck(Tile other, int id);

    // MOVE
    boolean relocateWorker(int x, int y, int workerId);

    boolean relocateHelper(int x, int y, int id);

    boolean isLegalMoveTile(Tile other, int id);

    boolean tileCheck(Tile other, int id);

    // GAME STATE
    Player getWinner(CardLogic gl);

    boolean isValidGame(CardLogic other);

    boolean isPlayerStuck();

    boolean isStuck(int id);

    boolean isValidTile(Tile other, int id);

    // OTHER
    void loser();

    void winner();

    boolean withinOne(int a, int b);
}
