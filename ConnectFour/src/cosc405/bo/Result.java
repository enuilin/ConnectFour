package cosc405.bo;

public class Result {

	private double heuristic;
	private int decision;
	public Result() {
		
	}
	
	public Result(double heuristic, int decision) {
		this.heuristic = heuristic;
		this.decision = decision;
	}
	public double getHeuristic() {
		return heuristic;
	}
	public void setHeuristic(double heuristic) {
		this.heuristic = heuristic;
	}
	public int getDecision() {
		return decision;
	}
	public void setDecision(int path) {
		this.decision = path;
	}
	
	
}
