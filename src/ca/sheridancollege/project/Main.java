


package ca.sheridancollege.project;

import java.util.ArrayList;
/**
 *
 * @author Gloria Kalsi
 */

public class Main {
    public static void main(String[] args) {
        // Create a list of players
        ArrayList<GoFishPlayer> players = new ArrayList<>();
        players.add(new GoFishPlayer("Player 1"));
        players.add(new GoFishPlayer("Player 2"));
        // Add more players as needed

        // Create a new game
        GoFishGame game = new GoFishGame("Go Fish", players);

        // Play the game
        game.play();

        // Declare the winner
        game.declareWinner();
    }
}

