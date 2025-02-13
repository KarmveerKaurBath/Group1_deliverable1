package ca.sheridancollege.project;

/**
 * @author Gloria Kalsi
 * 
 * This class represents a card in the game of Go Fish.
 * It extends the Card class, inheriting its properties and methods.
 */
public class GoFishCard extends Card {
    // These variables hold the rank and suit of the card.
    private final String rank;
    private final String suit;

    // Constructor initializes the rank and suit of the card.
    public GoFishCard(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    // Getter method to return the rank of the card.
    public String getRank() {
        return rank;
    }

    // Getter method to return the suit of the card.
    public String getSuit() {
        return suit;
    }

    // This method converts the rank of the card to a numeric value.
    // It uses a switch statement to map the rank to a number.
    public int getRankAsNumber() {
        switch (rank) {
            case "2": return 0;
            case "3": return 1;
            case "4": return 2;
            case "5": return 3;
            case "6": return 4;
            case "7": return 5;
            case "8": return 6;
            case "9": return 7;
            case "10": return 8;
            case "Jack": return 9;
            case "Queen": return 10;
            case "King": return 11;
            case "Ace": return 12;
            default: throw new IllegalArgumentException("Invalid rank: " + rank);
        }
    }

    // This method returns a string representation of the card, combining its rank and suit.
    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}
