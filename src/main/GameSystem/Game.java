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
            if (this.playerList.size() == 2) {
                System.out.println("Can only have two players in this game");
                return false;
            }
            if (this.playerList.contains(p)) {
                System.out.println("Player is already in the game");
                return false;
            }
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
    public Player getWinner(Player p1, Player p2) {
        if (isValidGame()) return null;
        if (!this.playerList.contains(p1)) return null;
        if (!this.playerList.contains(p2)) return null;
        if (p1.isPlayerStuck(this.gameBoard)) {
            loser(p1);
            return p2;
        } else if (p2.isPlayerStuck(this.gameBoard)) {
            loser(p2);
            return p1;
        }
        if (p1.isWinner()) {
            winner(p1);
            return p1;
        }
        winner(p2);
        return p2;
    }

    public boolean isValidGame() {
        for (Player p : this.playerList) {
            if (p.isPlayerStuck(this.gameBoard)) {
                return false;
            }
            if (p.isWinner()) {
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
