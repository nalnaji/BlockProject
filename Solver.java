public class Solver {
	public static void main(String[] args){
		 String pathToStartFile = args[0];
		 String pathToGoalFile = args[1];

		 Board start = new Board(pathToStartFile);
		 Board goal = new Board(pathToGoalFile);
		 Strategy solver = new BFSStrategy(start, goal);
		 long startTime = System.currentTimeMillis();
		 solver.solve();
		 long endTime = System.currentTimeMillis();
		 long resultTime = endTime - startTime;
		 System.out.println("It took: " + resultTime + " ms");
	}
}
