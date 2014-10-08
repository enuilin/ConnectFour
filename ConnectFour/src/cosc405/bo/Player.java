package cosc405.bo;

import java.util.Random;

import cosc405.main.Minimax;

public class Player {

	Minimax minimax;
	int[][] testState;

	public Player() {
		this.minimax = new Minimax();
	}

	public Board initialPlay(Board board) {
		Random rand = new Random();
		board.addPiece(Math.abs(rand.nextInt()) % 7, true);
		return board;
	}

	public Board play(Board board) {
		testState = board.cloneArray();
		int decision = minimax.minimax(testState, 0, -1).getDecision();
		//it's always *our turn" in the eyes of the player
		board.addPiece(decision, true);
		return board;

	}

}
