package ca.sheridancollege.project;

/**
 * @author Gloria Kalsi
 * 
 * This class represents a deck of Go Fish cards.
 * It extends the GroupOfCards class, inheriting its properties and methods.
 */
public class GoFishDeck extends GroupOfCards {

    // Constructor initializes the deck with 52 cards.
    public GoFishDeck() {
        super(52); // Call the parent constructor with the default size of the deck (52 cards).
        
        // Arrays representing the ranks and suits of the cards.
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        
        // Nested loops to create the full deck of cards.
        for (String suit : suits) {
            for (String rank : ranks) {
                // Add a new GoFishCard to the deck for each combination of rank and suit.
                cards.add(new GoFishCard(rank, suit));
            }
        }
        
        // Shuffle the deck to randomize the order of the cards.
        shuffle();
    }

    // This method draws a card from the deck.
    // If the deck is empty, it returns null. Otherwise, it removes and returns the last card in the deck.
    public GoFishCard drawCard() {
        return cards.isEmpty() ? null : (GoFishCard) cards.remove(cards.size() - 1);
    }
}
