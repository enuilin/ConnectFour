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
	private List<Result> results = new ArrayList<Result>();
	private int[][] testState;
	private ArrayList<Result> endResults = new ArrayList<Result>();
	
	public Result minimax(int[][] state, int level, int decision) {

//		for (int x = 0; x < 6; x++) {
//			for (int y = 0; y < 7; y++) {
//				testState[x][y] = state[x][y];
//			}
//		}

		int row = 5;
		if (decision >= 0) {
			boolean placed = false; // should not be relevant. usage should be
									// precluded by validPlay()
			while (row >= 0 && placed == false) {
			//	System.out.println("row = " + row + "decision = " + decision);
				//System.out.println("Level = " + level);
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
			if (placed == false) {
				//System.out.println("Something has gone wrong in Minimax.minimax");
			}
			

		}
		
		ArrayList<Integer> valid = new ArrayList<Integer>();
		for (int x = 0; x < 7; x++) {
			if (state[0][x] == 0) {
				valid.add(x);
			}
		}

		//System.out.println("checkpoint");
		if (level == MAX_LEVEL) {
			testState = new int[6][7];
			for (int x = 0; x < 6; x++) {
				for (int y = 0; y < 7; y++) {
					testState[x][y] = state[x][y];
				}
			}
			return new Result(HeuristicCalc.calc(testState), decision);

		} else if (level == 0 ){
			for (int x: valid) {
				testState = new int[6][7];
				for (int y = 0; y < 6; y++) {
					for (int z = 0; z < 7; z++) {
						testState[y][z] = state[y][z];
					}
				}
				Result r = minimax(testState, level+1, x);
				endResults.add(r);
				endResults.size();
			}
//			for (int i = 0; i < endResults.size(); i++) {
//				System.out.print(endResults.get(i).getHeuristic() + " ");
//			}
			Result result = new Result();
			Result storeResult = max(endResults);
			endResults.clear();
			result.setDecision(storeResult.getDecision());
			result.setHeuristic(storeResult.getHeuristic());
			return result;
		} else if (level % 2 == 0) {
		
			for (int x : valid) {
				testState = new int[6][7];
				for (int y = 0; y < 6; y++) {
					for (int z = 0; z < 7; z++) {
						testState[y][z] = state[y][z];
					}
				}
				Result r = minimax(testState, level+1,x);
				//System.out.println("adding result for mod 2 = 0 with decision " + r.getDecision());
				results.add(r);
//				results.add(result);
				//results.size();
			}

			Result result = new Result();
			result.setDecision(decision);
		//	System.out.println("checkpoint3");
			if (results.size() != 0) {
				result.setHeuristic(max(results).getHeuristic());
			}
			//System.out.println("checkpoint 6, heuristic = " + result.getHeuristic());
			return result;

		} else if (level % 2 == 1) {
			
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
		
		// should not ever hit this
		//System.out.println("returning null");
		return null;
	}

	private Result max(List<Result> list) {
//		Collections.sort(list);
//		Result r = list.get(0);
//		results.clear();
//		
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
//		Collections.sort(list);
//		Result r = list.get(list.size()-1);
//		results.clear();
		
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
