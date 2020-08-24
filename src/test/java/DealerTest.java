import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class DealerTest {

	BaccaratGame g;
	BaccaratGameLogic l;
	BaccaratDealer d;

	@BeforeEach
	void setUp(){
		g = new BaccaratGame();
		l = new BaccaratGameLogic();
		d = new BaccaratDealer();
	}

	@Test
	void baccaratGameConstructor(){
		assertEquals("BaccaratGame", g.getClass().getName(), "did not initialize proper object");
	}

	/*
	These tests are unable to work due to a null pointer exception as evaluateWinnings need
	the actual game to work in our program

	@Test
	void evaluateWinnings(){
		d.generateDeck();
		g.playerHand = d.dealHand();
		g.bankerHand = d.dealHand();
		g.currentBet = 100;
		g.betPlayer = true;
		System.out.println(g.playerHand);
		System.out.println(g.bankerHand);

		System.out.println("Result" + g.evaluateWinnings());
		assertEquals(200, g.evaluateWinnings());
	}

	 */

	@Test
	void baccaratGameLogicConstructor(){
		assertEquals("BaccaratGameLogic", l.getClass().getName(), "did not initialize proper object");
	}

	@Test
	void whoWon1(){
		Card c1 = new Card("Hearts", 9);
		Card c2 = new Card("Hearts", 10);
		Card c3 = new Card("Spades", 1);
		Card c4 = new Card("Spades", 2);
		ArrayList<Card> hand1 = new ArrayList<Card>();
		hand1.add(c1);
		hand1.add(c2);
		ArrayList<Card> hand2 = new ArrayList<Card>();
		hand2.add(c3);
		hand2.add(c4);
		assertEquals("Player!", l.whoWon(hand1, hand2), "Wrong person won");
	}

	@Test
	void whoWon2(){
		Card c1 = new Card("Hearts", 9);
		Card c2 = new Card("Hearts", 10);
		Card c3 = new Card("Spades", 1);
		Card c4 = new Card("Spades", 2);
		ArrayList<Card> hand1 = new ArrayList<Card>();
		hand1.add(c3);
		hand1.add(c4);
		ArrayList<Card> hand2 = new ArrayList<Card>();
		hand2.add(c1);
		hand2.add(c2);
		assertEquals("Banker!", l.whoWon(hand1, hand2), "Wrong person won");
	}

	@Test
	void handTotal1(){
		Card c1 = new Card("Hearts", 9);
		Card c2 = new Card("Hearts", 10);
		ArrayList<Card> hand = new ArrayList<Card>();
		hand.add(c1);
		hand.add(c2);
		assertEquals(9, l.handTotal(hand), "Total number of cards is wrong");
	}

	@Test
	void handTotal2(){
		Card c1 = new Card("Hearts", 12);
		Card c2 = new Card("Hearts", 10);
		ArrayList<Card> hand = new ArrayList<Card>();
		hand.add(c1);
		hand.add(c2);
		assertEquals(0, l.handTotal(hand),"Total number of cards is wrong");
	}

	@Test
	void evaluatePlayerDraw1(){
		Card c1 = new Card("Hearts", 5);
		Card c2 = new Card("Hearts", 10);
		ArrayList<Card> hand = new ArrayList<Card>();
		hand.add(c1);
		hand.add(c2);
		assertEquals(true, l.evaluatePlayerDraw(hand), "Player drew card when they weren't supposed to");
	}

	@Test
	void evaluatePlayerDraw2(){
		Card c1 = new Card("Hearts", 6);
		Card c2 = new Card("Hearts", 10);
		ArrayList<Card> hand = new ArrayList<Card>();
		hand.add(c1);
		hand.add(c2);
		assertEquals(false, l.evaluatePlayerDraw(hand), "Player didn't draw a card when they were supposed to");
	}

	@Test
	void evaluateBankerDraw1(){
		Card c1 = new Card("Hearts", 1);
		Card c2 = new Card("Hearts", 10);
		ArrayList<Card> hand = new ArrayList<Card>();
		hand.add(c1);
		hand.add(c2);
		Card c3 = new Card("Spades", 2);
		assertEquals(true, l.evaluateBankerDraw(hand, c3), "Banker drew card when they weren't supposed to");
	}

	@Test
	void evaluateBankerDraw2(){
		Card c1 = new Card("Hearts", 5);
		Card c2 = new Card("Hearts", 10);
		ArrayList<Card> hand = new ArrayList<Card>();
		hand.add(c1);
		hand.add(c2);
		Card c3 = new Card("Spades", 2);
		assertEquals(false, l.evaluateBankerDraw(hand, c3), "Banker didn't draw a card when they were supposed to");
	}

	@Test
	void baccaratDealerConstructor(){
		assertEquals("BaccaratDealer", d.getClass().getName(), "did not initialize proper object");
	}

	@Test
	void generateDeck1(){
		d.generateDeck();
		assertEquals(52, d.deck.size(), "Deck didn't generate");
	}

	@Test
	void generateDeck2(){
		d.generateDeck();
		assertEquals("java.util.ArrayList", d.deck.getClass().getName(), "Deck didn't generate");
	}

	@Test
	void dealHand1(){
		d.generateDeck();
		ArrayList<Card> tempDeck = d.dealHand();
		assertEquals(2, tempDeck.size(), "dealHand() did not work");
	}

	@Test
	void dealHand2(){
		d.generateDeck();
		ArrayList<Card> tempDeck = d.dealHand();
		assertEquals("java.util.ArrayList", tempDeck.getClass().getName(), "dealHand() did not work");
	}

	@Test
	void drawOne1(){
		d.generateDeck();
		Card temp = d.drawOne();
		assertEquals("Card", temp.getClass().getName(), "drawOne didn't work");
	}

	@Test
	void drawOne2(){
		d.generateDeck();
		ArrayList<Card> hand = new ArrayList<>();
		for(int i = 0; i<10;i++){
			hand.add(d.drawOne());
		}
		assertEquals(42, d.deck.size(), "drawOne didn't work");
	}

	@Test
	void shuffleDeck1(){
		d.generateDeck();
		ArrayList<Card> oldDeck = new ArrayList<>(d.deck);
		d.shuffleDeck();
		assertNotEquals(oldDeck, d.deck, "Shuffle didn't work");
	}

	@Test
	void shuffleDeck2(){
		d.generateDeck();
		d.shuffleDeck();
		ArrayList<Card> oldDeck = new ArrayList<>(d.deck);
		d.shuffleDeck();
		assertNotEquals(oldDeck, d.deck, "Shuffle didn't work");
	}

	@Test
	void deckSize1(){
		d.generateDeck();
		assertEquals(52, d.deckSize(), "Deck size didn't work");
	}

	@Test
	void deckSize2(){
		d.generateDeck();
		for(int i=0;i<10;i++){
			d.deck.remove(d.deck.size()-1);
		}
		assertEquals(42, d.deckSize(), "Deck size didn't work");
	}

	@Test
	void CardConstructor(){
		Card c = new Card("Hearts", 12);
		assertEquals("Card", c.getClass().getName(), "did not initialize proper object");
	}

}
