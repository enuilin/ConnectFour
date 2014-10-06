package cosc405.bo;

import java.util.ArrayList;

public class Board {

	private int[][] state;
	private String winner;
	
	public Board() {
		state = new int[6][7];
		winner = "CPU";
	}
	
	public void addPiece(int place, boolean ourTurn) {
		//be sure to utilize validPlay() first
		//due to usage, ourTurn can be passed as level%2 in minmax procedures
		int row = 5;
		boolean placed = false; //should not be relevant.  usage should be precluded by validPlay()
		while (row >= 0 && placed == false) {
			if (state[row][place] == 0) {
				if (ourTurn) {
					state[row][place] = 5;
					placed = true;
				} else if (!ourTurn) {
					state[row][place] = 1;
					placed = true;
				}
			} else {
				row--;
			}
			
		}
		if (placed == false) {
			System.out.println("Something has gone wrong in Board.addPiece.");
		}
	}
	
	public int[][] getState() {
		return state;
	}
	
	public String getWinner() {
		return winner;
	}
	
	public ArrayList<Integer> validPlay() {
		//should be used before requesting input from human.
		//should be used to determine which minmax branches to explore, as
		//there is no point exploring a branch for a decision that is an illegal move
		ArrayList<Integer> valid = new ArrayList<Integer>();
		for (int x = 0; x < 7; x++) {
			if (state[0][x] == 0){
				valid.add(x);
			}
		}
		return valid;
	}
	
	public void print() {
		for (int i = 0; i<6; i++) {
			System.out.print("| ");
			for (int j = 0; j<7; j++) {
				System.out.print(state[i][j] + " ");
			}
			System.out.println("| ");
			
		}
		
	}
	
	//replaces all numerical values with 1 and 2 (for player numbers)
	public void printNeutral() {
		//5s will always represent p1
		//1 will always represent p2
		for (int i = 0; i<6; i++) {
			System.out.print("| ");
			for (int j = 0; j<7; j++) {
				if (state[i][j] == 5) {
					System.out.print(1 + " ");
				} else if (state[i][j] == 1) {
					System.out.print(2 + " ");
				} else if (state[i][j] == 0) {
					System.out.print(0 + " ");
				}
				
			}
			System.out.println("| ");
			
		}
		
		
	}
	//for AI-- flips around values in the board
	public void flip() {
		for (int i =0; i<6; i++) {
			for (int j = 0; j<7; j++) {
				if (state[i][j] == 5) {
					state[i][j] = 1;
				} else if (state[i][j] == 1) {
					state[i][j] = 5;
				}
					
			}
		}
	}

	public boolean checkIfWinner() {
		int winningMove = -1;
		int testSum;
		boolean canWin = false;
		int row;
		
		int[] test = new int[4];
		//check horizontals
		for (int x = 0; x <= 3; x++) {
			for (int y = 0; y <= 5; y++) {
				//gather test block of size 4
				for (int z = 0; z <= 3; z++) {
					test[z] = state[y][x+z];
				}
				testSum = 0;
				for (int w = 0; w <= 3; w++) {
					testSum = testSum + test[w];
				}
				if (testSum == 15) {
					for (int i:test) {
						if (i == 0) {
							if (y == 5 || state[y+1][x+i] != 0) {
								winningMove = x+i;
								canWin = true;
							}
							
							//maybeWinColumn = x+i;
						}
					}
//					row = 5;
//					while (row >= 0 && state[row][maybeWinColumn] != 0) {
//							row--;
//					}
//					if (row == y) {
//						canWin = true;
//						winningMove = maybeWinColumn;
//					}
				} else if (testSum == 4) {
					winner = "Player";
					return true;
				}
			}
		}
		//check verticals
		for (int x = 0; x <= 6; x ++) {
			for (int y = 0; y <= 2; y++) {
				//gather test block of size 4
				for (int z = 0; z <= 3; z++) {
					test[z] = state[y+z][x];
				}
				testSum = 0;
				for (int w = 0; w <= 3; w++) {
					testSum = testSum + test[w];
				}
				if (testSum == 15) {
					winningMove = x;
					canWin = true;
				} else if (testSum == 4) {
					winner = "Player";
					return true;
				}
			}
		}
		return canWin;

	}
}
