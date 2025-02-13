package ca.sheridancollege.project;

import java.util.ArrayList;
import java.util.Scanner;

public class GoFishGame extends Game {
    private final GoFishDeck deck;
    private final ArrayList<GoFishPlayer> players;

    public GoFishGame(String name, ArrayList<GoFishPlayer> players) {
        super(name);
        this.players = players;
        this.deck = new GoFishDeck();
    }

    // Deal initial cards to each player based on the number of players
    private void dealInitialCards() {
        int cardsToDeal = players.size() == 2 ? 7 : 5;
        for (GoFishPlayer player : players) {
            for (int i = 0; i < cardsToDeal; i++) {
                player.addCard(deck.drawCard());
            }
        }
    }

    // Check if a player has any books (four cards of the same rank)
    private void checkForBooks(GoFishPlayer player) {
        ArrayList<GoFishCard> hand = player.getHand();
        int[] rankCount = new int[13];

        // Count the occurrences of each rank in the player's hand
        for (GoFishCard card : hand) {
            int rankIndex = card.getRankAsNumber();
            if (rankIndex >= 0 && rankIndex < rankCount.length) {
                rankCount[rankIndex]++;
            } else {
                System.out.println("Error: rankIndex out of bounds: " + rankIndex);
            }
        }

        // Check for books and remove the cards that form a book from the player's hand
        for (int i = 0; i < rankCount.length; i++) {
            if (rankCount[i] == 4) {
                final int rankIndex = i;
                ArrayList<GoFishCard> book = new ArrayList<>();
                hand.removeIf(card -> {
                    if (card.getRankAsNumber() == rankIndex) {
                        book.add(card);
                        return true;
                    }
                    return false;
                });
                player.addBook(book);
                System.out.println(player.getName() + " completed a book of " + book.get(0).getRank() + "s!");
            }
        }
    }

    // Validate if the entered rank is valid (e.g., 2-10, J, Q, K, A)
    private boolean isValidRank(String rank) {
        return rank.matches("[2-9]|10|[JQKA]");
    }

    @Override
    public void play() {
        // Deal initial cards to all players
        dealInitialCards();
        Scanner scanner = new Scanner(System.in);
        boolean gameOver = false;

        // Main game loop
        while (!gameOver) {
            for (GoFishPlayer player : players) {
                System.out.println(player.getName() + ", it's your turn.");
                String rank;
                
                // Prompt the player for a valid rank
                do {
                    System.out.print("Enter a rank to ask for: ");
                    rank = scanner.nextLine();
                    if (!isValidRank(rank)) {
                        System.out.println("Invalid rank. Please enter a valid rank (2-10, J, Q, K, A).");
                    }
                } while (!isValidRank(rank));

                boolean found = false;
                
                // Check if any opponent has the requested rank
                for (GoFishPlayer opponent : players) {
                    if (!opponent.equals(player) && opponent.hasRank(rank)) {
                        System.out.println("You got cards from " + opponent.getName());
                        player.getHand().addAll(opponent.giveCards(rank));
                        found = true;
                        break;
                    }
                }

                // If no opponent has the requested rank, the player "goes fishing"
                if (!found) {
                    System.out.println("Go Fish!");
                    GoFishCard drawnCard = deck.drawCard();
                    if (drawnCard != null) {
                        player.addCard(drawnCard);
                        System.out.println("Player drew: " + drawnCard);
                    } else {
                        System.out.println("Deck is empty.");
                    }
                }

                // Check if the player has completed any books
                checkForBooks(player);

                // Check if the game is over (deck is empty and all players have no cards)
                if (deck.getCards().isEmpty() && players.stream().allMatch(p -> p.getHandSize() == 0)) {
                    gameOver = true;
                    break;
                }

                // Print the current game state (deck size, each player's hand size, and book count)
                System.out.println("\n-- Current Game State --");
                System.out.println("Deck size: " + deck.getCards().size());
                for (GoFishPlayer p : players) {
                    System.out.println(p.getName() + " hand size: " + p.getHandSize() + ", books: " + p.getBookCount());
                }
            }
        }
        scanner.close();
        declareWinner();
    }

    @Override
    public void declareWinner() {
        GoFishPlayer winner = players.get(0);
        int maxBooks = 0;
        
        // Determine the player with the most books
        for (GoFishPlayer player : players) {
            int playerBooks = player.getBookCount();
            if (playerBooks > maxBooks) {
                maxBooks = playerBooks;
                winner = player;
            }
        }
        System.out.println("Winner is " + winner.getName() + " with " + maxBooks + " books!");
    }
}
