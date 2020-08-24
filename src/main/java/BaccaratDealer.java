import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.util.ArrayList;

public class BaccaratDealer extends BaccaratGame {

    ArrayList<Card> deck; // deck of cards

    // constructor
    BaccaratDealer(){
        this.deck = new ArrayList<>();
    }

    // makes a new deck
    public void generateDeck() {
        String suits[] = {"Hearts", "Diamonds", "Clubs", "Spades"};
        for(int i=0; i < suits.length; i++){
            for (int j=1; j<=13; j++){
                this.deck.add(new Card(suits[i],j));
            }
        }

        java.util.Collections.shuffle(deck);
    }

    // deals a hand of two cards
    public ArrayList<Card> dealHand() {
        ArrayList<Card> temp = new ArrayList<>();
        temp.add(drawOne());
        temp.add(drawOne());
        return temp;
    }

    // adds a card to a hand
    public Card drawOne() {
        Card temp = deck.get(deck.size()-1);
        deck.remove(deck.size()-1);

        return temp;
    }

    // makes a new deck
    public void shuffleDeck() {
        deck.clear();
        generateDeck();
    }

    // returns size of the deck
    public int deckSize() {
        return deck.size();
    }
}
