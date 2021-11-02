import GameBoard.Board;
import GameBoard.Tile;
import GameSystem.GameLogic;
import God.CardLogic;
import Player.Player;
import Player.Worker;

import java.util.Arrays;

public class UIProxy {

    private final String instructions;
    private final Cell[] cells;

    private UIProxy(String instructions, Cell[] cells) {
        this.instructions = instructions;
        this.cells = cells;
    }

    public static UIProxy forGame(CardLogic game1, CardLogic game2) {
        Cell[] cells = getCells(game1, game2);
        String instructions = getInstructions(game1, game2);
        return new UIProxy(instructions, cells);
    }

    public String getInstructions() {
        return this.instructions;
    }

    public Cell[] getCells() {
        return this.cells;
    }

    @Override
    public String toString() {
        return "GameState[" +
                "instructions=" + this.instructions + ", " +
                "cells=" + Arrays.toString(this.cells) + ']';
    }

    private static String getInstructions(CardLogic game1, CardLogic game2) {
        String instructions;
        instructions = "";
        boolean tag1 = true;
        for (Worker w: game1.getGame().getP0().getWorkerList()) {
            if (null == w.getCurrentTile()) tag1 = false;
        }

        boolean tag2 = true;
        for (Worker w: game1.getGame().getP1().getWorkerList()) {
            if (null == w.getCurrentTile()) tag2 = false;
        }

        if (!tag1) {
            instructions = "Drop workers for Player " + (game1.getPlayer().getPlayerId());
        } else if (!tag2) {
            instructions = "Drop workers for Player " + (game1.getOther().getPlayerId());
        } else if (game1.getWinner(game2) == game1.getPlayer()) {
            instructions = "Player " + (game1.getPlayer().getPlayerId()) + " has won.";
        } else if (game2.getWinner(game1) == game2.getPlayer()) {
            instructions = "Player " + (game2.getPlayer().getPlayerId()) + " has won.";
        } else if (game1.getGame().getCurrentPlayer() == game1.getPlayer()) {
            if (game1.getCanMove()) instructions = "Next turn: Player " +  game1.getPlayer().getPlayerId() + " move.";
            else if (game1.getCanBuild()) instructions = "Next turn: Player " +  game1.getPlayer().getPlayerId() + " build.";

        } else if ((game1.getGame().getCurrentPlayer() == game2.getPlayer())) {
            if (game2.getCanMove()) instructions = "Next turn: Player " +  game2.getPlayer().getPlayerId() + " move.";
            else if (game2.getCanBuild()) instructions = "Next turn: Player " +  game2.getPlayer().getPlayerId() + " build.";
        }
        return instructions;
    }

    private static Cell[] getCells(CardLogic game1, CardLogic game2) {
        Cell cells[] = new Cell[25];
        Board board = game1.getGame().getGameBoard();
        int id;
        id = 0; // temp assignment
        for (int x = 0; x <= 4; x++) {
            for (int y = 0; y <= 4; y++) {
                int dropId = -1;
                Worker w00 = game2.getPlayer().getWorker(0);
                Worker w11 = game2.getPlayer().getWorker(1);
                if (w00.getCurrentTile() == null) dropId = 0;
                else if (w11.getCurrentTile() == null) dropId = 1;

                Worker w0 = game1.getPlayer().getWorker(0);
                Worker w1 = game1.getPlayer().getWorker(1);
                if (w0.getCurrentTile() == null) dropId = 0;
                else if (w1.getCurrentTile() == null) dropId = 1;

                String text = "";
                String link = "";
                String clazz = "";
                Tile tile = board.getTile(x, y);
                Player curr = game1.getGame().getCurrentPlayer();
                if (dropId != -1) {
                    id = dropId;
                    clazz = "playable";
                } else if (tile.getHasWorker()) {
                    for (Worker w : game1.getPlayer().getWorkerList()) {
                        if (w.getCurrentTile() == tile) {
                            if (w.getWorkerId() == 0) id = 0;
                            else id = 1;
                            text = "player 0 w-" + id;
                            clazz = "playable-player";
                        }
                    }
                    for (Worker w : game1.getOther().getWorkerList()) {
                        if (w.getCurrentTile() == tile) {
                            if (w.getWorkerId() == 0) id = 0;
                            else id = 1;
                            text = "player 1 w-" + id;
                            clazz = "playable-player";
                        }
                    }
                } else if (tile.getCurrentLevel() == 1)  {
                    if (curr == game1.getPlayer()) id = game1.getCloseWorker(x, y);
                    else id = game2.getCloseWorker(x, y);
                    text = "1";
                    clazz = "playable";
                } else if (tile.getCurrentLevel() == 2) {
                    if (curr == game1.getPlayer()) id = game1.getCloseWorker(x, y);
                    else id = game2.getCloseWorker(x, y);
                    text = "2";
                    clazz = "playable";
                } else if (tile.getCurrentLevel() == 3) {
                    if (curr == game1.getPlayer()) id = game1.getCloseWorker(x, y);
                    else id = game2.getCloseWorker(x, y);
                    text = "3";
                    clazz = "playable";
                } else if (tile.getCurrentLevel() == 4) {
                    if (curr == game1.getPlayer()) id = game1.getCloseWorker(x, y);
                    else id = game2.getCloseWorker(x, y);
                    text = "4";
                    clazz = "playable";
                } else { // tiles that have not been built and does not contain a worker
                    if (curr == game1.getPlayer()) id = game1.getCloseWorker(x, y);
                    else id = game2.getCloseWorker(x, y);
                    clazz = "playable";
                }
                link = "/play?x=" + x + "&y=" + y + "&id=" + id;
                cells[5 * x + y] = new Cell(text, clazz, link);
            }
        }
        return cells;
    }
}

class Cell {
    private final String text;
    private final String clazz;
    private final String link;

    Cell(String text, String clazz, String link) {
        this.text = text;
        this.clazz = clazz;
        this.link = link;
    }

    public String getText() {
        return this.text;
    }

    public String getClazz() {
        return this.clazz;
    }

    public String getLink() {
        return this.link;
    }

    @Override
    public String toString() {
        return "Cell[" +
                "text=" + this.text + ", " +
                "clazz=" + this.clazz + ", " +
                "link=" + this.link + ']';
    }
}
