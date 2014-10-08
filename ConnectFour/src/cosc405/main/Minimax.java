package cosc405.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import cosc405.bo.Result;
import cosc405.heuristic.HeuristicCalc;

public class Minimax {
	private int MAX_LEVEL = 6;
	private List<Result> results = new ArrayList<Result>(); //for storing mid/bottom level results
	private int[][] testState; //for cloning the current state because 2d arrays are inconvenient
	private ArrayList<Result> endResults = new ArrayList<Result>(); //for storing top level results
	
	public Result minimax(int[][] state, int level, int decision) {

		//if not called by main (decision = -1), place token
		int row = 5;
		if (decision >= 0) {
			boolean placed = false; // should not be relevant. usage should be
									// precluded by validPlay()
			while (row >= 0 && placed == false) {
				if (state[row][decision] == 0) {
					if (level % 2 == 0) {
						state[row][decision] = 1;
						placed = true;
					} else if (level % 2 == 1) {
						state[row][decision] = 5;
						placed = true;
					}
				} else {
					row--;
				}
			}
		}
		
		//collect list of valid moves
		ArrayList<Integer> valid = new ArrayList<Integer>();
		for (int x = 0; x < 7; x++) {
			if (state[0][x] == 0) {
				valid.add(x);
			}
		}

		//determine procedure based on level
		if (level == MAX_LEVEL) {
			//if bottom level, clone array and determine heuristic, returning a Result
			testState = new int[6][7];
			for (int x = 0; x < 6; x++) {
				for (int y = 0; y < 7; y++) {
					testState[x][y] = state[x][y];
				}
			}
			return new Result(HeuristicCalc.calc(testState), decision);

		} else if (level == 0 ){
			//if top level, evaluate and return best decision
			//for every valid option, we clone the state and recursively call minimax
			for (int x: valid) {
				testState = new int[6][7];
				for (int y = 0; y < 6; y++) {
					for (int z = 0; z < 7; z++) {
						testState[y][z] = state[y][z];
					}
				}
				Result r = minimax(testState, level+1, x);
				endResults.add(r);
				//endResults.size();
			}

			Result result = new Result();
			Result storeResult = max(endResults);
			endResults.clear();
			result.setDecision(storeResult.getDecision());
			result.setHeuristic(storeResult.getHeuristic());
			return result;
		} else if (level % 2 == 0) {
			//if even level, recursively call minimax and return maximum
			for (int x : valid) {
				testState = new int[6][7];
				for (int y = 0; y < 6; y++) {
					for (int z = 0; z < 7; z++) {
						testState[y][z] = state[y][z];
					}
				}
				Result r = minimax(testState, level+1,x);
				results.add(r);
			}

			Result result = new Result();
			result.setDecision(decision);
			if (results.size() != 0) {
				result.setHeuristic(max(results).getHeuristic());
			}
			return result;

		} else if (level % 2 == 1) {
			//for odd levels, recursively call minimax and return minimum
			for (int x : valid) {
				testState = new int[6][7];
				for (int y = 0; y < 6; y++) {
					for (int z = 0; z < 7; z++) {
						testState[y][z] = state[y][z];
					}
				}
				results.add(minimax(testState, level + 1, x));
			}
			Result result = new Result();
			result.setDecision(decision);
			if (results.size() != 0) {
				result.setHeuristic(min(results).getHeuristic());
			}
			return result;
		}
		return null;
	}

	private Result max(List<Result> list) {
		//return max value
		
		Result r = list.get(0);
		int high = (int) r.getHeuristic();
		for (int x = 1; x < list.size(); x++) {
			if (list.get(x).getHeuristic() > high) {
				r = list.get(x);
				high = (int) r.getHeuristic();
			}
		}
		results.clear();
		return r;
	}

	private Result min(List<Result> list) {
		//return min value
		
		Result r = list.get(0);
		int low = (int) r.getHeuristic();
		for (int x = 1; x < list.size(); x++) {
			if (list.get(x).getHeuristic() < low) {
				r = list.get(x);
				low = (int) r.getHeuristic();
			}
		}
		results.clear();
		return r;
	}
}
