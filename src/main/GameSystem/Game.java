package main.GameSystem;

import main.GameBoard.Board;
import main.Player.Player;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private List<Player> playerList;
    private Board gameBoard;

    // arg constructor
    public Game(Board gameBoard) {
        this.playerList = new ArrayList<Player>();
        this.gameBoard = gameBoard;
    }
}
