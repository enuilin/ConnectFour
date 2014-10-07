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
				//gameOver = board.checkIfWinner();
				testState = board.cloneArray();
				int decision = minimax.minimax(testState, 0, -1).getDecision();
				//System.out.println("decision is " + decision);
				
				board.addPiece(decision, ourTurn);
				ourTurn = false;
				board.print();
			} else {
				System.out.println("Enter number of column you wish to put your piece in");

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
				gameOver = board.checkIfWinner();
				if (board.getPreventFlag()) {
					ourTurn = false;
					board.resetPreventFlag();
					board.print();
				}
			}
			if (gameOver == false) {
				gameOver = board.checkIfFull();
			}
			
		}
		System.out.println(board.getWinner() + " wins!!");
		board.print();
		
	}

}

