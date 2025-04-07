package ca.sheridancollege.project;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class GoFishGameTest {

    private GoFishGame game;
    private ArrayList<GoFishPlayer> players;

    public GoFishGameTest() {}

    @BeforeClass
    public static void setUpClass() {
        System.out.println("Beginning unit tests\n");
    }

    @AfterClass
    public static void tearDownClass() {
        System.out.println("\nUnit Tests Completed");
    }

    @Before
    public void setUp() {
        players = new ArrayList<>();
        players.add(new GoFishPlayer("Alice"));
        players.add(new GoFishPlayer("Bob"));
        game = new GoFishGame("Go Fish", players);
    }

    @After
    public void tearDown() {
        System.out.println("-------------------");
    }

    // -------------------------------
    // isValidRank() Tests
    // -------------------------------
    @Test
    public void testIsValidRankDefault() {
        System.out.println("Default test: isValidRank with empty string");
        String rank = "";
        boolean expResult = false;
        boolean result = game.isValidRank(rank);
        assertEquals(expResult, result);
    }

    @Test
    public void testIsValidRankGood() {
        System.out.println("Good test: isValidRank with 'A'");
        String rank = "A";
        boolean expResult = true;
        boolean result = game.isValidRank(rank);
        assertEquals(expResult, result);
    }

    @Test
    public void testIsValidRankBad() {
        System.out.println("Bad test: isValidRank with 'Z'");
        String rank = "Z";
        boolean expResult = false;
        boolean result = game.isValidRank(rank);
        assertEquals(expResult, result);
    }

    @Test
    public void testIsValidRankBoundary() {
        System.out.println("Boundary test: isValidRank with '10'");
        String rank = "10";
        boolean expResult = true;
        boolean result = game.isValidRank(rank);
        assertEquals(expResult, result);
    }

    // -------------------------------
    // declareWinner() Tests
    // -------------------------------
    @Test
    public void testDeclareWinnerDefault() {
        System.out.println("Default test: declareWinner with 0 books for all");
        game.declareWinner();
        for (GoFishPlayer player : players) {
            assertEquals(0, player.getBookCount());
        }
    }

    @Test
    public void testDeclareWinnerGood() {
        System.out.println("Good test: declareWinner with 1 player having more books");
        GoFishPlayer alice = players.get(0);
        ArrayList<GoFishCard> book = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            book.add(new GoFishCard("Hearts", "5"));
        }
        alice.addBook(book);
        game.declareWinner();
        assertEquals(1, alice.getBookCount());
    }

    @Test
    public void testDeclareWinnerBad() {
        System.out.println("Bad test: declareWinner when both players have 0 books");
        game.declareWinner();
        boolean allZero = players.stream().allMatch(p -> p.getBookCount() == 0);
        assertTrue(allZero);
    }

    @Test
    public void testDeclareWinnerBoundary() {
        System.out.println("Boundary test: declareWinner with tie");
        GoFishPlayer alice = players.get(0);
        GoFishPlayer bob = players.get(1);
        ArrayList<GoFishCard> book1 = new ArrayList<>();
        ArrayList<GoFishCard> book2 = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            book1.add(new GoFishCard("Spades", "7"));
            book2.add(new GoFishCard("Diamonds", "8"));
        }
        alice.addBook(book1);
        bob.addBook(book2);
        game.declareWinner(); // Should still identify one (first max), no crash
        assertEquals(1, alice.getBookCount());
        assertEquals(1, bob.getBookCount());
    }

    // -------------------------------
    // dealInitialCards() Tests
    // -------------------------------
    @Test
    public void testDealInitialCardsDefault() {
        System.out.println("Default test: dealInitialCards with 2 players");
        game.dealInitialCards(); 
        int handSize = players.get(0).getHandSize();
        assertTrue(handSize == 7);
    }

    @Test
    public void testDealInitialCardsGood() {
        System.out.println("Good test: dealInitialCards with 4 players");
        players.clear();
        for (int i = 1; i <= 4; i++) {
            players.add(new GoFishPlayer("Player" + i));
        }
        game = new GoFishGame("Go Fish", players);
        game.dealInitialCards();
        for (GoFishPlayer p : players) {
            assertEquals(5, p.getHandSize());
        }
    }

    @Test
    public void testDealInitialCardsBoundary() {
        System.out.println("Boundary test: dealInitialCards with 3 players");
        players.clear();
        for (int i = 1; i <= 3; i++) {
            players.add(new GoFishPlayer("P" + i));
        }
        game = new GoFishGame("Go Fish", players);
        game.dealInitialCards();
        for (GoFishPlayer p : players) {
            assertEquals(5, p.getHandSize());
        }
    }

    // -------------------------------
    // checkForBooks() Tests
    // -------------------------------
    @Test
    public void testCheckForBooksDefault() {
        System.out.println("Default test: checkForBooks with empty hand");
        GoFishPlayer player = new GoFishPlayer("Test");
        game.checkForBooks(player);
        assertEquals(0, player.getBookCount());
    }

   @Test
public void testCheckForBooksGood() {
    System.out.println("Good test: checkForBooks with 4 matching ranks");
    GoFishPlayer player = new GoFishPlayer("Tester");
    // Ensure rank is "Jack" and suit is correct (Hearts, Diamonds, Clubs, Spades)
    player.addCard(new GoFishCard("Jack", "Hearts"));
    player.addCard(new GoFishCard("Jack", "Diamonds"));
    player.addCard(new GoFishCard("Jack", "Clubs"));
    player.addCard(new GoFishCard("Jack", "Spades"));
    game.checkForBooks(player);
    assertEquals(1, player.getBookCount());  // Since all are "Jack", it should count as a book
}


 @Test
public void testCheckForBooksBad() {
    System.out.println("Bad test: checkForBooks with 3 matching cards only");
    GoFishPlayer player = new GoFishPlayer("Tester");
    // Add cards with the same rank but different suits
    player.addCard(new GoFishCard("9", "Hearts"));
    player.addCard(new GoFishCard("9", "Clubs"));
    player.addCard(new GoFishCard("9", "Spades"));
    game.checkForBooks(player);
    assertEquals(0, player.getBookCount());  // Since there are only 3 cards, it should not form a book
}


   @Test
public void testCheckForBooksBoundary() {
    System.out.println("Boundary test: checkForBooks with exactly 4 but mixed suits");
    GoFishPlayer player = new GoFishPlayer("Tester");
    // Ensure the rank is "3" and the suits are different
    player.addCard(new GoFishCard("3", "Spades"));
    player.addCard(new GoFishCard("3", "Hearts"));
    player.addCard(new GoFishCard("3", "Clubs"));
    player.addCard(new GoFishCard("3", "Diamonds"));
    game.checkForBooks(player);
    assertEquals(1, player.getBookCount());  // Since all are rank "3", it should count as a book
}

}
