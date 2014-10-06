package cosc405.heuristic;

public class HeuristicCalc {

	public static double calc(int[][] state) {
		int score = 0;
		int[] test = new int[4];
		//count horizontal score
		for (int x = 0; x <= 3; x++) {
			for (int y = 0; y <= 5; y++) {
				//gather test block of size 4
				for (int z = 0; z <= 3; z++) {
					test[z] = state[y][x+z];
				}
				//evaluate
				int sum = sum(test);
				if (sum!=0 && (sum%5) == 0) {
					score = score + sum/5;
				}
				if (sum == 4) {
					score = score - 200;
				}
				if (sum == 20) {
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
					score = score + sum/5;
				}
				if (sum == 4) {
					score = score - 200;
				}
				if (sum == 20) {
					score = score + 100;
				}
			}
		}
		return score;
	}
	
	private static int sum(int[] test) {
		int total = 0;
		for (int x = 0; x <= 3; x++) {
			total = total + test[x];
		}
		return total;
	}
}
