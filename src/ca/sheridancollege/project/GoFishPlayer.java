package ca.sheridancollege.project;
/**
 * @author Gloria Kalsi
 */
import java.util.ArrayList;

public class GoFishPlayer extends Player {
    private ArrayList<GoFishCard> hand;
    private ArrayList<ArrayList<GoFishCard>> books;

    public GoFishPlayer(String name) {
        super(name);
        hand = new ArrayList<>();
        books = new ArrayList<>();
    }

    public void addCard(GoFishCard card) {
        hand.add(card);
    }

    public boolean hasRank(String rank) {
        return hand.stream().anyMatch(card -> card.getRank().equals(rank));
    }

    public ArrayList<GoFishCard> giveCards(String rank) {
        ArrayList<GoFishCard> givenCards = new ArrayList<>();
        hand.removeIf(card -> {
            if (card.getRank().equals(rank)) {
                givenCards.add(card);
                return true;
            }
            return false;
        });
        return givenCards;
    }

    public int getHandSize() {
        return hand.size();
    }

    public ArrayList<GoFishCard> getHand() {
        return hand;
    }

    public void addBook(ArrayList<GoFishCard> book) {
        books.add(book);
    }

    public int getBookCount() {
        return books.size();
    }

    @Override
    public void play() {
        // Game logic for player's turn
    }
}
