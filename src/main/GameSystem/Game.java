package main.GameSystem;

import main.GameBoard.Board;
import main.Player.Player;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private List<Player> playerList;
    private Board gameBoard;

    // arg constructor
    /**
     * Game Constructor
     *
     * @param gameBoard the board associated with the game
     */
    public Game(Board gameBoard) {
        this.playerList = new ArrayList<Player>();
        this.gameBoard = gameBoard;
    }

    // setter method
    /**
     * Adds a new player to the game.
     *
     * @param p the player to be added to the game
     * @return boolean denoting the success or failure of the addition
     */
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
    /**
     * Getter method for retrieving the player list.
     *
     * @return List<Player> the list of players
     */
    public List<Player> getPlayerList() {
        return this.playerList;
    }

    /**
     * Getter method for retrieving the board associated with the game.
     *
     * @return Board the board
     */
    public Board getGameBoard() {
        return this.gameBoard;
    }

    // method
    /**
     * At any given point in the game, determines the winner- or returns null if there is no winner.
     *
     * @param p1 the first player in the game
     * @param p2 the second player in the game
     * @return Player the winning player
     */
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

    /**
     * Checks if the game state is valid.
     *
     * @return boolean of whether the game is still going
     */
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

    /**
     * Resets the game board to its original state.
     *
     */
    public void resetGameBoard() {
        this.gameBoard.resetBoard();
        this.playerList.clear();
    }

    // helper method
    /**
     * Private helper that prints a message for the losing player.
     *
     * @param p the player who lost
     */
    private void loser(Player p) {
        System.out.println("Player " + p.toString() + "has lost the match due to being stuck!");
    }

    /**
     * Private helper that prints a message for the winning player.
     *
     * @param p the player who won
     */
    private void winner(Player p) {
        System.out.println("Player " + p.toString() + "has won the match due scaling the third level!");

    }
}
