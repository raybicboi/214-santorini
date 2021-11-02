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
        Cell[] cells = getCells(game1);
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
        if (game1.getWinner(game2) == game1.getPlayer()) {
            instructions = "Player " + (game1.getPlayer().getPlayerId()) + " has won.";
        } else if (game2.getWinner(game1) == game2.getPlayer()) {
            instructions = "Player " + (game2.getPlayer().getPlayerId()) + " has won.";
        } else if (game1.getGame().getCurrentPlayer() == game1.getPlayer()) {
            instructions = "Next turn: Player " +  game1.getPlayer().getPlayerId() + ".";
        } else if ((game1.getGame().getCurrentPlayer() == game2.getPlayer())) {
            instructions = "Next turn: Player " +  game2.getPlayer().getPlayerId() + ".";
        }
        return instructions;
    }

    private static Cell[] getCells(CardLogic game1) {
        Cell cells[] = new Cell[25];
        Board board = game1.getGame().getGameBoard();
        for (int x = 0; x <= 4; x++) {
            for (int y = 0; y <= 4; y++) {
                String text = "";
                String link = "";
                String clazz = "";
                Tile tile = board.getTile(x, y);
//                Player curr = game1.getGame().getCurrentPlayer();
                if (tile.getHasWorker()) {
                    for (Worker w : game1.getPlayer().getWorkerList()) {
                        if (w.getCurrentTile() == tile) text = "player 0";
                    }
                    for (Worker w : game1.getOther().getWorkerList()) {
                        if (w.getCurrentTile() == tile) text = "player 1";
                    }
                } else if (tile.getCurrentLevel() == 1)  {
                    text = "1";
                    clazz = "playable";
                    link = "/play?x=" + x + "&y=" + y;
                } else if (tile.getCurrentLevel() == 2) {
                    text = "2";
                    clazz = "playable";
                    link = "/play?x=" + x + "&y=" + y;
                } else if (tile.getCurrentLevel() == 3) {
                    text = "3";
                    clazz = "playable";
                    link = "/play?x=" + x + "&y=" + y;
                } else if (tile.getCurrentLevel() == 4) {
                    text = "4";
                    clazz = "playable";
                    link = "/play?x=" + x + "&y=" + y;
                } else {
                    clazz = "playable";
                    link = "/play?x=" + x + "&y=" + y;
                }
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
