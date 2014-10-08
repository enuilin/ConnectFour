package cosc405.bo;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import cosc405.main.Minimax;

public class Game {
	private int numAI;
	Board board;
	Player player1;
	Player player2;
	boolean gameOver = false;
	boolean ourTurn = true;
	int playerInput;
	ArrayList<Integer> validList;
	boolean validMove;
	//for AI testing -> starts off automatically at player #1
	int currentPlayer = 1;
	
	public Game(int numAI) {
		this.numAI = numAI;
		this.board = new Board();
		createPlayers();
		ourTurn = false;
		gameOver = false;
		
	}
	
	//will create AI vs AI if anything other than "1" is entered
	private void createPlayers(){
		player1 = new Player();
		
		if (numAI !=1) {
			player2 = new Player();
		} 
	}
	
	private void checkAIWinner() {
		gameOver = board.checkIfWinner();
//		if (board.getPreventFlag()) {
//			board.resetPreventFlag();
//		}
		if (gameOver == false) {
			gameOver = board.checkIfFull();
		}
	}
	public void start() {

		//if playing against human
		if (numAI ==1) {
			Scanner in = new Scanner(System.in);
			System.out.println("Would you like to move first? Enter Y or N.");	
			
			if (in.next().equals("Y")) {
				System.out.println("Please enter the column you wish to place your piece in (the board is 0 indexed): ");
				board.addPiece(in.nextInt() % 7, false);
				ourTurn = true;
				board.print();
			} else {
				board = player1.initialPlay(board);
				ourTurn = false;
				board.print();
			}
			
			while (gameOver == false) {
				if (ourTurn) {
					board = player1.play(board);
					ourTurn = false;
					board.print();
				} else {
					System.out.println("Enter number of column you wish to put your piece in");

					playerInput = in.nextInt();
					//this section checks if the user's move is valid
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
			
		//if playing AI against AI (particularly for testing purposes)
		} else {
			
			//start the game
			player1.initialPlay(board);
			currentPlayer = 2;
			
			while (gameOver == false) {
				//printNeutral replaces 5 and 1 with 1 and 2 respectively
				//to represent the player number
				board.printNeutral();
				
				//player #2's move
				board.flip();
				currentPlayer = 2;
				checkAIWinner();
				
				if (gameOver) {
					//player 2 has made a move
					board.flip();
					break;
				} else if (board.getPreventFlag()){
					board.resetPreventFlag();
				} else {
					player2.play(board);
				}

				board.flip();
				board.printNeutral();
				
				//actually checks for player #1
				currentPlayer = 1;
				checkAIWinner();
				
				if (gameOver == true) {
		
					break;
				} else if (board.getPreventFlag()){
					board.resetPreventFlag();
				} else {
					board = player1.play(board);
				}
				
			}
			
			board.printNeutral();
			System.out.printf("AI #%d wins!", currentPlayer);
		}
	}
	
	
	
}
