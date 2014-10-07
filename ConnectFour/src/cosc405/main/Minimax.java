package cosc405.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import cosc405.bo.Result;
import cosc405.heuristic.HeuristicCalc;

public class Minimax {
	private int MAX_LEVEL = 3;
	private List<Result> results;

	public Result minimax(int[][] state, int level, int decision) {

		int row = 5;
		if (decision >= 0) {
			boolean placed = false; // should not be relevant. usage should be
									// precluded by validPlay()
			while (row >= 0 && placed == false) {
				if (state[row][decision] == 0) {
					if (level % 2 == 0) {
						state[row][decision] = 5;
						placed = true;
					} else if (level % 2 == 1) {
						state[row][decision] = 1;
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
		
		ArrayList<Integer> valid = new ArrayList<Integer>();
		for (int x = 0; x < 7; x++) {
			if (state[0][x] == 0) {
				valid.add(x);
			}
		}

		results = new ArrayList<Result>();
		if (level == MAX_LEVEL) {
			return new Result(HeuristicCalc.calc(state), decision);

		} else if (level % 2 == 0) {
			for (int x : valid) {
				Result r = minimax(state, level+1,x);
				results.add(r);
//				results.add(result);
				results.size();
			}

			Result result = new Result();
			result.setDecision(decision);
			result.setHeuristic(max(results).getHeuristic());
			return result;

		} else if (level % 2 == 1) {
			for (int x : valid) {
				results.add(minimax(state, level + 1, x));
			}
			Result result = new Result();
			result.setDecision(decision);
			result.setHeuristic(min(results).getHeuristic());
			return result;
		}

		// should not ever hit this
		System.out.println("returning null");
		return null;
	}

	private Result max(List<Result> list) {
		list.get(0);
		Collections.sort(list);
		return list.get(0);

	}

	private Result min(List<Result> list) {

		Collections.sort(list);
		return list.get(list.size());
	}

}
