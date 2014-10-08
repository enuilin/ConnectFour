package cosc405.heuristic;

public class HeuristicCalc {

	//calculates the heuristic value of a bottom level node
	public static double calc(int[][] state) {
		int score = 0;
		int[] test = new int[4]; //will hold sections of the state to be tested
		//count horizontal score
		//don't need to iterate through entire board, as sets of 3 only can't win
		for (int x = 0; x <= 3; x++) {
			for (int y = 0; y <= 5; y++) {
				//gather test block of size 4
				for (int z = 0; z <= 3; z++) {
					test[z] = state[y][x+z];
				}
				//evaluate
				int sum = sum(test);
				if (sum!=0 && (sum%5) == 0) {
					//if 1-3 of ours with others empty
					if (sum/5 == 1) {
						score = score + 1;
					} else if (sum/5 == 2) {
						score = score + 3;
					} else if (sum/5 == 3) {
						score = score + 13;
					}
				}
				if (sum == 4) {
					//mark opponent win condition with extreme score
					score = score - 100;
				}
				if (sum == 20) {
					//mark self win condition with extreme score
					score = score + 100;
				}
			}
		}
		//count vertical score
		for (int x = 0; x <= 6; x ++) {
			for (int y = 0; y <= 2; y++) {
				//gather test block of size 4
				for (int z = 0; z <= 3; z++) {
					test[z] = state[y+z][x];
				}
				//evaluate
				int sum = sum(test);
				if (sum!=0 && (sum%5) == 0) {
					//if 1-3 of our tokens and other space empty
					if (sum/5 == 1) {
						score = score + 1;
					} else if (sum/5 == 2) {
						score = score + 3;
					} else if (sum/5 == 3) {
						score = score + 13;
					}
				}
				if (sum == 4) {
					//mark opponent win condition with extreme score
					score = score - 100;
				}
				if (sum == 20) {
					//mark self win condition with extreme score
					score = score + 100;
				}
			}
		}
		return score;
	}
	
	private static int sum(int[] test) {
		//calculates sum of values in size 4 test array
		int total = 0;
		for (int i = 0; i <= 3; i++) {
			total = total + test[i];
		}
		return total;
	}
}
