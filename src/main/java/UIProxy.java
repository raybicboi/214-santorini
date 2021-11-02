import GameBoard.Board;
import GameBoard.Tile;
import GameSystem.GameLogic;
import God.CardLogic;

import java.util.Arrays;

public class UIProxy {

    private final String instructions;
    private final Cell[] cells;

    private UIProxy(String instructions, Cell[] cells) {
        this.instructions = instructions;
        this.cells = cells;
    }

    public static UIProxy forGame(GameLogic game1, GameLogic game2) {
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
        if (game1.getWinner(game2) == game1.getPlayer()) {
            instructions = "Player " + (game1.getPlayer().getPlayerId()) + " has won.";
        } else if (game2.getWinner(game1) == game2.getPlayer()) {
            instructions = "Player " + (game2.getPlayer().getPlayerId()) + " has won.";
        } else {
            instructions = "Next turn: Player " +  ".";
        }
//      (game.getPlayer() == Player.PLAYER0 ? "0" : "1")
//        instructions = "Next turn: Player " +  ".";
        return instructions;
    }

    private static Cell[] getCells(GameLogic game1) {
        Cell cells[] = new Cell[25];
        Board board = game1.getGame().getGameBoard();
        for (int x = 0; x <= 4; x++) {
            for (int y = 0; y <= 4; y++) {
                String text = "";
                String link = "";
                String clazz = "";
                Tile tile = board.getTile(x, y);
//                Player p = something idk
                if (tile.getHasWorker() && game1.getPlayer().getPlayerId() == 0) { // add both conditions
                    text = "player";
                    clazz = "playable";
                    link = "/play?x=" + x + "&y=" + y;
                }
                else if (tile.getCurrentLevel() == 1)  {
                    text = "1";
                    clazz = "playable";
                    link = "/play?x=" + x + "&y=" + y;
                }
                else if (tile.getCurrentLevel() == 2) {
                    text = "2";
                    clazz = "playable";
                    link = "/play?x=" + x + "&y=" + y;
                }
                else if (tile.getCurrentLevel() == 3) {
                    text = "3";
                    clazz = "playable";
                    link = "/play?x=" + x + "&y=" + y;
                }
                else if (tile.getCurrentLevel() == 4) {
                    text = "4";
                    clazz = "playable";
                    link = "/play?x=" + x + "&y=" + y;
                }
                else if (tile == null) {
                    text = "other";
                }
                cells[5 * y + x] = new Cell(text, clazz, link);
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
