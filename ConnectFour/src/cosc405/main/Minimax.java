package cosc405.main;

import java.util.ArrayList;
import java.util.List;

import cosc405.bo.Result;
import cosc405.heuristic.HeuristicCalc;

public class Minimax {
	private int MAX_LEVEL = 3;
	private List<Result> results;
	
	
	public Result minimax(int[][] state, int level, int decision) {
		
		results = new ArrayList<Result>();
		if (level==3) {
			return new Result(HeuristicCalc.calc(state), decision);
			
		} else if (level %2 ==0) {
			for (int i = 0; i<7 ; i++) {
				results.add(minimax(state, level, i));
			}
			
			Result result = new Result();
			result.setDecision(decision);
			result.setHeuristic(max(results).getHeuristic());
			return result;
			
			
		} else if (level%2==1) {
			for (int i = 0; i<7 ; i++) {
				results.add(minimax(state, level, i));
			}
			Result result = new Result();
			result.setDecision(decision);
			result.setHeuristic(min(results).getHeuristic());
			return result;
		}
		
		//should not ever hit this
		return null;
	}
	
	private Result max(List<Result> list) {
		
		
		return null;
	
	}
	private Result min(List<Result> list) {
		return null;
		
	}

}
