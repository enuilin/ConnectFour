package cosc405.main;

import cosc405.bo.Board;

public class Test {

	public static void main(String[] args) {
		Board board = new Board();
		boolean ourTurn = true;
		board.addPiece(0, ourTurn);
		
		board.print();
	}
}
