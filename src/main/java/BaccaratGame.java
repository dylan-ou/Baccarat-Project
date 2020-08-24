import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

public class BaccaratGame extends Application {
	// All for scene 1
	public Text playerFirstCard, playerSecondCard, playerThirdCard;
	public Text bankerFirstCard, bankerSecondCard, bankerThirdCard;
	public Text playerResult, bankerResult, winnerResult;
	private Text text1, winnings, playerLabel, bankerLabel;
	private TextField playerInput;
	private Button playerBid, bankerBid, drawBid;
	private HBox buttons, currentWinningsText;
	private VBox bottom, playerSide, bankerSide;
	private VBox results;
	private BorderPane borderPane;
	private MenuBar menuBar;
	private MenuItem one, two;
	private PauseTransition pause;
	Scene scene;

	// All for scene 2
	Scene scene2;
	private Text title;
	private BorderPane borderPane2;
	private Button playButton;
	private VBox titleScreen;

	// variables for the entire game
	ArrayList<Card> playerHand;
	ArrayList<Card> bankerHand;
	BaccaratDealer theDealer;
	BaccaratGameLogic gameLogic;
	double currentBet;
	double totalWinnings;
	boolean betPlayer = false;
	boolean betTie = false;
	boolean betBanker = false;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	public double evaluateWinnings(){ // evaluates winnings
		double result = 0;
		if(gameLogic.whoWon(playerHand, bankerHand) == "Player!") { // if player wins
			winnerResult.setText("WINNER:\nPlayer");
			if(betPlayer == true) { // if player bet on player
				result = result + (currentBet * 2);
			} else {
				result = result - currentBet;
			}
		}
		else if(gameLogic.whoWon(playerHand, bankerHand) == "Banker!") { // if banker win
			winnerResult.setText("WINNER:\nBanker");
			if(betBanker == true) { // if player bet on banker
				result = result + (currentBet * 0.95);
			} else {
				result = result - currentBet;
			}
		}
		else if(gameLogic.whoWon(playerHand, bankerHand) == "Tie!") { // if tie
			winnerResult.setText("IT'S A TIE");
			if (betTie == true) { // if player bet on tie
				result = result + (currentBet * 9);
			}
			else {
				result = result - currentBet;
			}
		}

		betPlayer = false;
		betBanker = false;
		betTie = false;

		return result;
	}

	void startGame() { // does each round of baccarat
		gameLogic = new BaccaratGameLogic();
		theDealer = new BaccaratDealer();
		theDealer.generateDeck(); // makes a new deck

		// gets rid of textfields and buttons so player doesn't mess with the game
		playerInput.setVisible(false);
		playerBid.setVisible(false);
		bankerBid.setVisible(false);
		drawBid.setVisible(false);
		two.setVisible(false);

		// initializes all text in the game
		playerFirstCard.setText("");
		playerSecondCard.setText("");
		playerThirdCard.setText("");
		playerResult.setText("");
		bankerFirstCard.setText("");
		bankerSecondCard.setText("");
		bankerThirdCard.setText("");
		bankerResult.setText("");
		winnerResult.setText("");
		winnings.setText("Total Winnings: $" + totalWinnings);

		// player gets cards
		pause = new PauseTransition(Duration.seconds(1));
		playerHand = theDealer.dealHand();
		pause.setOnFinished(e->{
			winnerResult.setText("Drawing Cards\nfor Player");
			playerFirstCard.setText(playerHand.get(0).toString());
		});
		pause.play();
		pause = new PauseTransition(Duration.seconds(2));
		pause.setOnFinished(e->playerSecondCard.setText(playerHand.get(1).toString()));
		pause.play();
		pause = new PauseTransition(Duration.seconds(3));

		// banker gets cards
		bankerHand = theDealer.dealHand();
		pause.setOnFinished(e->{
			winnerResult.setText("Drawing Cards\nfor Banker");
			bankerFirstCard.setText(bankerHand.get(0).toString());
		});
		pause.play();
		pause = new PauseTransition(Duration.seconds(4));
		pause.play();
		pause.setOnFinished(e->bankerSecondCard.setText(bankerHand.get(1).toString()));
		pause.play();
		pause = new PauseTransition(Duration.seconds(5));

		thirdCard(); // determines if player and banker get a third card

		pause.setOnFinished(e->{
			winnerResult.setText("");
			playerResult.setText("\nPlayer Result: " + Integer.toString(gameLogic.handTotal(playerHand)));
			bankerResult.setText("\nBanker Result: " + Integer.toString(gameLogic.handTotal(bankerHand)));
		});
		pause.play();
		pause = new PauseTransition(Duration.seconds(8));

		// displays winner then distribute money
		pause.setOnFinished(e->{
			totalWinnings += evaluateWinnings();
			if(totalWinnings < 0){
				totalWinnings = 0;
			}
			winnings.setText("Total Winnings: $" + totalWinnings);
		});
		pause.play();
		pause = new PauseTransition(Duration.seconds(10));

		// make textfield and buttons visible again
		pause.setOnFinished(e->{
			playerInput.setVisible(true);
			playerBid.setVisible(true);
			bankerBid.setVisible(true);
			drawBid.setVisible(true);
			two.setVisible(true);
			text1.setText("");
		});
		pause.play();
	}

	void thirdCard() {
		if(gameLogic.evaluatePlayerDraw(playerHand)) { // determines if player needs a third card
			Card newCard = theDealer.drawOne();
			playerHand.add(newCard);
			pause.setOnFinished(e->{
				winnerResult.setText("Drawing Third Cards\n");
				playerThirdCard.setText(playerHand.get(2).toString());
			});
			pause.play();
			pause = new PauseTransition(Duration.seconds(6));

			if(gameLogic.evaluateBankerDraw(bankerHand, playerHand.get(2))) { // determines if banker needs third card
				bankerHand.add(theDealer.drawOne());
				pause.setOnFinished(e->bankerThirdCard.setText(bankerHand.get(2).toString()));
				pause.play();
				pause = new PauseTransition(Duration.seconds(7));
			}
		}
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Let's Play Baccarat!!!");

		// everything for scene1 (the actual game)
		winnings = new Text("Total Winnings: $" + totalWinnings);
		playerLabel = new Text("Player: ");
		playerFirstCard = new Text("");
		playerSecondCard = new Text("");
		playerThirdCard = new Text("");
		bankerLabel = new Text("Banker: ");
		bankerFirstCard = new Text("");
		bankerSecondCard = new Text("");
		bankerThirdCard = new Text("");
		playerResult = new Text("");
		bankerResult = new Text("");
		winnerResult = new Text("");

		playerInput = new TextField();
		playerBid = new Button("Bid on Player");
		bankerBid = new Button("Bid on Banker");
		drawBid = new Button("Bid on Draw");
		text1 = new Text("");

		totalWinnings = 0;

		// let's player bid on player's hand
		playerBid.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				String input = playerInput.getText();
				if (input.isEmpty()) {
					text1.setText("Input cash amount");
					return;
				} else {
					currentBet = Integer.parseInt(input);
					text1.setText("Player Betting $" + currentBet + " on Player");
					betPlayer = true;
					startGame();
				}
			}
		});

		// let's player bid on banker's hand
		bankerBid.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				String input = playerInput.getText();
				if (input.isEmpty()) {
					text1.setText("Input cash amount");
					return;
				} else {
					currentBet = Integer.parseInt(input);
					text1.setText("Player Betting $" + currentBet + " on Banker");
					betBanker = true;
					startGame();
				}
			}
		});

		// let's player bid on tie
		drawBid.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				String input = playerInput.getText();
				if (input.isEmpty()) {
					text1.setText("Input cash amount");
					return;
				} else {
					currentBet = Integer.parseInt(input);
					text1.setText("Player Betting $" + currentBet + " on Tie");
					betTie = true;
					startGame();
				}
			}
		});

		// adds the menu bar, menus, and menu items
		menuBar = new MenuBar();

		Menu menuOne = new Menu("Options");

		one = new MenuItem("Exit");
		two = new MenuItem("Fresh Start");

		menuOne.getItems().addAll(one,two);
		menuBar.getMenus().addAll(menuOne);

		// exits program
		one.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				System.exit(0);
			}
		});

		// initializes everything and start at title screen again
		two.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				currentBet = 0;
				totalWinnings = 0;
				playerFirstCard.setText("");
				playerSecondCard.setText("");
				playerThirdCard.setText("");
				playerResult.setText("");
				bankerFirstCard.setText("");
				bankerSecondCard.setText("");
				bankerThirdCard.setText("");
				bankerResult.setText("");
				winnerResult.setText("");
				text1.setText("");
				winnings.setText("Total Winnings: $" + totalWinnings);
				primaryStage.setScene(scene2);
			}
		});

		// fonts and colors for labels
		playerLabel.setFont(Font.font("Verdana", 20));
		bankerLabel.setFont(Font.font("Verdana", 20));
		playerLabel.setFill(Color.BLUE);
		bankerLabel.setFill(Color.RED);

		// everything for scene1
		currentWinningsText = new HBox(20, winnings, text1);
		buttons = new HBox(5, playerInput, playerBid, bankerBid, drawBid);
		bottom = new VBox(5, currentWinningsText, buttons);
		playerSide = new VBox(5, playerLabel, playerFirstCard, playerSecondCard, playerThirdCard, playerResult);
		bankerSide = new VBox(5, bankerLabel, bankerFirstCard, bankerSecondCard, bankerThirdCard, bankerResult);
		results = new VBox(5, winnerResult);
		results.setAlignment(Pos.CENTER);
		borderPane = new BorderPane();
		borderPane.setBottom(bottom);
		borderPane.setTop(menuBar);
		borderPane.setLeft(playerSide);
		borderPane.setRight(bankerSide);
		borderPane.setCenter(results);
		borderPane.setAlignment(results, Pos.BOTTOM_CENTER);
		borderPane.setMargin(results, new Insets(100,60,100,60));

		scene = new Scene(borderPane, 420, 350);

		// everything for title screen
		title = new Text("BACCARAT");
		title.setFont(Font.font("Verdana", 60));
		playButton = new Button("Play");
		playButton.setOnAction(e->primaryStage.setScene(scene));

		titleScreen = new VBox(10, title, playButton);
		titleScreen.setAlignment(Pos.CENTER);

		scene2 = new Scene(titleScreen, 420, 350);

		primaryStage.setScene(scene2);
		primaryStage.show();
	}
}
