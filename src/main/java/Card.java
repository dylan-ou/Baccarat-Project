public class Card extends BaccaratDealer{
    private String suit; // suit of card
    private int value; // value of card

    // makes a new card
    public Card(String theSuit, int theValue) {
        this.suit = theSuit;
        this.value = theValue;
    }

    // gets the suit since suit is private
    public String getSuit() {
        return suit;
    }

    // gets the value of the card
    public int getValue() {
        return value;
    }

    // returns what card it is
    // (any value that isn't a number is presented correctly)
    public String toString() {
        if(value == 1){
            return "Ace of " + suit;
        } else if (value == 11){
            return "Jack of " + suit;
        } else if (value == 12){
            return "Queen of "+ suit;
        } else if (value == 13){
            return "King of " + suit;
        } else {
            return value + " of " + suit;
        }
    }

}

