package prototypes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class Card {
    private String suit;
    private String rank;

    public Card(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public int getValue() {
        if (rank.equals("Ace")) return 1;
        if (rank.equals("Jack") || rank.equals("Queen") || rank.equals("King")) return 0;
        return Integer.parseInt(rank);
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}

class Deck {
    private List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] ranks = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};

        for (String suit : suits) {
            for (String rank : ranks) {
                cards.add(new Card(suit, rank));
            }
        }
    }
    //used to shuffle the deck
    public void shuffle() {
        Collections.shuffle(cards);
    }
    //used to deal cards to player and dealer
    public Card dealCard() {
        return cards.remove(cards.size() - 1);
    }
}

public class Lucky9Prototype{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Deck deck = new Deck();
        deck.shuffle();

        
        List<Card> playerHand = new ArrayList<>();
        List<Card> dealerHand = new ArrayList<>();

        System.out.println("===== WELCOME TO LUCKY9 =====");
        // draw cards for Player and Dealer
        playerHand.add(deck.dealCard());
        playerHand.add(deck.dealCard());

        dealerHand.add(deck.dealCard());
        dealerHand.add(deck.dealCard());

        // calculate scores
        int playerScore = calculateScore(playerHand);
        int dealerScore = calculateScore(dealerHand);

        // display initial hands
        System.out.println("Player's Hand: " + playerHand);
        System.out.println("Dealer's Hand: " + dealerHand);

        System.out.println("Player: " + playerScore);
        System.out.println("Dealer: " + dealerScore);
        
        //if dealer's hand has a score less than or equal to 4
        //they draw another card
        if (dealerScore <= 4){
            System.out.println("Dealer score is less than or equal to 4!\nThey draw another card!");
            
            //add new card to dealer's hand
            dealerHand.add(deck.dealCard());
            
            //recalculate dealer's score
            dealerScore = calculateScore(dealerHand);
            
            System.out.println("Player's Hand: " + playerHand);
            System.out.println("Dealer's New Hand: " + dealerHand);
            
            System.out.println("Player: " + playerScore);
            System.out.println("Dealer: " + dealerScore);
        }
        

        //if Dealer or Player gets a 9 on the first hand, its a natural 9 and wins automatically. exit immediately
        // ask player if he wants to add another card to his hand
        if (playerScore == 9 && dealerScore == 9){
            System.out.println("It's a tie!");
            System.exit(0);
        } else if (playerScore == 9){
            System.out.println("Player Natural 9! PLAYER WINS!");
            System.exit(0);
        } else if (dealerScore == 9){
            System.out.println("Dealer Natural 9! DEALER WINS!");
            System.exit(0);
        }
        System.out.print("Do you want to draw another card? (Yes/No): ");
        String choice = scanner.nextLine();
        if (choice.equalsIgnoreCase("yes")) {
            playerHand.add(deck.dealCard());
            playerScore = calculateScore(playerHand);
            System.out.println("Player's New Hand: " + playerHand);
            System.out.println("Dealer's Hand: " + dealerHand);
        }

        // calculate score 
        System.out.println("\nFinal Scores:");
        System.out.println("Player: " + playerScore);
        System.out.println("Dealer: " + dealerScore);
        
        //display winner
        if (playerScore > 9) {
            System.out.println("Player busts! Dealer wins.");
        } else if (dealerScore > 9) {
            System.out.println("Dealer busts! Player wins.");
        } else if (playerScore > dealerScore) {
            System.out.println("Player wins!");
        } else if (dealerScore > playerScore) {
            System.out.println("Dealer wins!");
        } else {
            System.out.println("It's a tie!");
        }

        scanner.close();
    }
    //this is to calculate the dealer and player score
    private static int calculateScore(List<Card> hand) {
        int total = 0;
        for (Card card : hand) {
            total += card.getValue();
        }
        return total % 10; // Modulo 10 for Lucky 9 scoring
    }
}