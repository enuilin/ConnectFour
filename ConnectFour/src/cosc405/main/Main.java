package cosc405.main;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import cosc405.bo.Board;

public class Main {
	
	public static void main(String[] args) {
		
		Board board = new Board();
		boolean ourTurn = true;
		boolean gameOver = false;
		Minimax minimax = new Minimax();
		int playerInput;
		ArrayList<Integer> validList;
		boolean validMove;
		int[][] testState;
		
		System.out.println("Would you like to move first? Enter Y or N.");
		
		Scanner in = new Scanner(System.in);
		
		if (in.next().equals("Y")) {
			System.out.println("Please enter the column you wish to place your piece in (the board is 0 indexed): ");
			board.addPiece(in.nextInt() % 7, false);
			board.print();
		} else {
			Random rand = new Random();
			board.addPiece(Math.abs(rand.nextInt()) % 7, true);
			ourTurn = false;
			board.print();
		}
		
		while (gameOver == false) {
			if (ourTurn) {
				testState = board.cloneArray(); // so we don't mess with the active board state
				int decision = minimax.minimax(testState, 0, -1).getDecision();
				
				board.addPiece(decision, ourTurn);
				ourTurn = false;
				board.print();
			} else {
				System.out.println("Enter number of column you wish to put your piece in");

				// take player input, generate list of valid moves,
				// make sure player move falls within list
				playerInput = in.nextInt();
				validList = board.validPlay();
				validMove = false;
				while (validMove == false) {
					for (int x:validList) {
						if (x == playerInput) {
							validMove = true;
						}
					}
					if (validMove == false) {
						System.out.println("That column is full. Please choose another column.");
						playerInput = in.nextInt();
					}
				}
				board.addPiece(playerInput, ourTurn);
				ourTurn = true;
				board.print();
				// check for notable conditions
				gameOver = board.checkIfWinner(); // if player wins or cpu can win, sets as true
				// if checkIfWinner() triggers player win prevention by cpu, makes sure cpu doesn't take double turn
				if (board.getPreventFlag()) {
					ourTurn = false;
					board.resetPreventFlag();
					board.print();
				}
			}
			// checks if tie has been reached
			if (gameOver == false) {
				gameOver = board.checkIfFull();
			}
		}
		// declares winner and prints final game state
		System.out.println(board.getWinner() + " wins!!");
		board.print();		
	}
}

