package cosc405.bo;

import java.util.ArrayList;

public class Board {

	private int[][] state;
	private String winner;
	private boolean preventFlag;
	
	public Board() {
		state = new int[6][7];
		//winner and preventFlag are used in conjunction with checkIfWinner()
		winner = "CPU";
		preventFlag = false;
	}

	public void addPiece(int place, boolean ourTurn) {
		// adds token to lowest unoccupied row in given column "place"
		// be sure to utilize validPlay() first
		// due to usage, ourTurn can be passed as level%2 in minmax procedures
		int row = 5;
		boolean placed = false; // should not be relevant. usage should be
								// precluded by validPlay()
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
	}

	public int[][] getState() {
		return state;
	}

	public String getWinner() {
		return winner;
	}

	public ArrayList<Integer> validPlay() {
		// returns a list of valid token placements
		// should be used before requesting input from human.
		// should be used to determine which minmax branches to explore, as
		// there is no point exploring a branch for a decision that is an
		// illegal move
		ArrayList<Integer> valid = new ArrayList<Integer>();
		for (int x = 0; x < 7; x++) {
			if (state[0][x] == 0) {
				valid.add(x);
			}
		}
		return valid;
	}

	public void printTest() {
		for (int i = 0; i < 6; i++) {
			System.out.print("| ");
			for (int j = 0; j < 7; j++) {
				System.out.print(state[i][j] + " ");
			}
			System.out.println("| ");
		}
		
	}
	
	public void print() {
		for (int i = 0; i < 6; i++) {
			System.out.print("| ");
			for (int j = 0; j < 7; j++) {
				if (state[i][j] == 5) {
					System.out.print("C ");
				} else if (state[i][j] == 1) {
					System.out.print("P ");
				} else if (state[i][j] == 0) {
					System.out.print("- ");
				}
			}
			System.out.println("| ");
			
		}
		//column indicator
		System.out.println("-----------------");
		System.out.println("  0 1 2 3 4 5 6  ");
	}

	// replaces all numerical values with 1 and 2 (for player numbers)
	public void printNeutral() {
		// 5s will always represent p1
		// 1 will always represent p2
		for (int i = 0; i < 6; i++) {
			System.out.print("| ");
			for (int j = 0; j < 7; j++) {
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

	// for AI-- flips around values in the board
	public void flip() {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				if (state[i][j] == 5) {
					state[i][j] = 1;
				} else if (state[i][j] == 1) {
					state[i][j] = 5;
				}

			}
		}
	}
	
	// determines multiple end-of-turn situations
	// Priority 1: See if opponent won.
	// Priority 2: See if we can win.
	// Priority 3: See if we can prevent the opponent from winning.
	public boolean checkIfWinner() {
		int winningMove = -1; 	// represents winning column
		int testSum;			// holds sum of test array for testing purposes
		boolean canWin = false;	
		int row;				// row being examined
		boolean canPrevent = false;
		int preventMove = -1;	// represents column for preventing opponent win
		
		int[] test = new int[4];
		// check horizontals
		for (int x = 0; x <= 3; x++) {
			for (int y = 0; y <= 5; y++) {
				// gather test block of size 4
				for (int z = 0; z <= 3; z++) {
					test[z] = state[y][x + z];
				}
				// store sum of test array in testSum
				testSum = 0;
				for (int w = 0; w <= 3; w++) {
					testSum = testSum + test[w];
				}
				// consider notable scenarios
				if (testSum == 15) {
					// if we can win, set canWin flag and record column
					for (int i = 0; i < 4; i++) {
						if (test[i] == 0) {
							if (y == 5 || state[y + 1][x + i] != 0) {
								winningMove = x + i;
								canWin = true;
							}
						}
					}
					
				} else if (testSum == 4) {
					// if player wins, exit function and declare winner immediately
					winner = "Player";
					return true;
				} else if (testSum == 3) {
					// if player is one move from winning, set canPrevent flag and store column
					for (int i = 0; i < 4; i++) {
						if (test[i] == 0) {
							if (y == 5 || state[y + 1][x + i] != 0) {
								preventMove = x + i;
								canPrevent = true;
							}
						}
					}
				}
			}
		}
		// check verticals
		for (int x = 0; x <= 6; x++) {
			for (int y = 0; y <= 2; y++) {
				// gather test block of size 4
				for (int z = 0; z <= 3; z++) {
					test[z] = state[y + z][x];
				}
				// store sum of test array in testSum
				testSum = 0;
				for (int w = 0; w <= 3; w++) {
					testSum = testSum + test[w];
				}
				// consider notable scenarios
				if (testSum == 15) {
					// if we can win, set canWin flag and record column
					winningMove = x;
					canWin = true;
				} else if (testSum == 4) {
					// if player wins, exit function and declare winner immediately
					// changes "winner" variable to reflect the change
					winner = "Player";
					return true;
				} else if (testSum == 3) {
					// if player is one move from winning, set canPrevent flag and store column
					preventMove = x;
					canPrevent = true;
				}
			}
		}
		if (canWin) {
			// if cpu can win, it does so
			// the winner variable is left alone, as the default is "CPU"
			addPiece(winningMove, true);
		} else if (canPrevent) {
			// if opponent win is preventable, do so and set preventFlag to notify function
			addPiece(preventMove, true);
			preventFlag = true;
		}
		return canWin;
	}
	
	public boolean getPreventFlag() {
		return preventFlag;
	}
	
	public void resetPreventFlag() {
		preventFlag = false;
	}
	
	// creates clone of 2d array, because 2d arrays are inconvenient
	public int[][] cloneArray() {
		int[][] clone = new int[6][7];
		for (int x = 0; x < 6; x++) {
			for (int y = 0; y < 7; y++) {
				clone[x][y] = state[x][y];
			}
		}
		return clone;
	}

	// checks if the play board is full
	public boolean checkIfFull() {
		// gathers list of viable moves
		ArrayList<Integer> valid = new ArrayList<Integer>();
		for (int x = 0; x < 7; x++) {
			if (state[0][x] == 0) {
				valid.add(x);
			}
		}
		// if no viable moves, respond accordingly
		if (valid.size() == 0) {
			winner = "It's a tie! Nobody";
			return true;
		}
		return false;
	}
}
