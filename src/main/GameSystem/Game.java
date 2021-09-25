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

    // setter method
    public boolean addPlayer(Player p) {
        try {
            this.playerList.add(p);
            return true;
        } catch (Exception e) {
            System.out.println("Cannot add this player to the game.");
            return false;
        }
    }

    // getter method
    public List<Player> getPlayerList() {
        return this.playerList;
    }

    public Board getGameBoard() {
        return this.gameBoard;
    }

    // method
    public boolean isValidGame() {
        for (Player p : this.playerList) {
            if (p.isPlayerStuck(gameBoard)) {
                loser(p);
                return false;
            }
            if (p.isWinner()) {
                winner(p);
                return false;
            }
        }
        return true;
    }

    public void resetGameBoard() {
        this.gameBoard.resetBoard();
        this.playerList.clear();
    }

    // helper method
    private void loser(Player p) {
        System.out.println("Player " + p.toString() + "has lost the match due to being stuck!");
    }

    private void winner(Player p) {
        System.out.println("Player " + p.toString() + "has won the match due scaling the third level!");

    }
}
