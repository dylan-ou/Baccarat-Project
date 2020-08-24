import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.util.ArrayList;

public class BaccaratGameLogic extends BaccaratGame {
    // determines who won the game
    public String whoWon(ArrayList<Card> hand1, ArrayList<Card> hand2) {
        if((handTotal(hand1) <= 9) && (handTotal(hand1) > handTotal(hand2))) {
            return "Player!";
        }
        else if((handTotal(hand2) <= 9) && (handTotal(hand2) > handTotal(hand1))) {
            return "Banker!";
        }
        else {
            return "Tie!";
        }
    }

    // calculates the sum of cards in a certain hand
    public int handTotal(ArrayList<Card> hand) {
        int sum = 0;
        int temp;

        for (Card c : hand) {
            if(c.getValue() >= 10){
                temp = 0;
            } else {
                temp = c.getValue();
            }
            sum += temp;
        }

        return sum % 10;
    }

    // determines if banker needs to draw a third card (uses the chart on the baccarat rules diagram)
    public boolean evaluateBankerDraw(ArrayList<Card> hand, Card playerCard) {
        if((hand.size() < 3) && (handTotal(hand) < 3)) {
            return true;
        }
        else if((hand.size() < 3) && (handTotal(hand) == 3) && (playerCard.getValue() != 8)) {
            return true;
        }
        else if((hand.size() < 3) && (handTotal(hand) == 4) && (playerCard.getValue() != 0 ||
                playerCard.getValue() != 1 || playerCard.getValue() != 8 || playerCard.getValue() != 9)) {
            return true;
        }
        else if((hand.size() < 3) && (handTotal(hand) == 5) && (playerCard.getValue() == 4 ||
                playerCard.getValue() == 5 || playerCard.getValue() == 6 || playerCard.getValue() == 7)) {
            return true;
        }
        else if((hand.size() < 3) && (handTotal(hand) == 6) && (playerCard.getValue() == 6 || playerCard.getValue() == 7)) {
            return true;
        }
        else {
            return false;
        }
    }

    // determines if the player needs a third card (if sum of first two cards is less than 6)
    public boolean evaluatePlayerDraw(ArrayList<Card> hand) {
        if(hand.size() == 2 && handTotal(hand) <= 5){
            return true;
        } else {
            return false;
        }
    }
}
