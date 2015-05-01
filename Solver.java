public class Solver {
  public static void main(String[] args){
     int argIndex = 0;
     if(args[0].equals( "-oinfo" )) {
       Debugger.toggleState();
       argIndex++;
     }
     String pathToStartFile = args[argIndex];
     String pathToGoalFile = args[argIndex+1];

     Board start = new Board(pathToStartFile);
     Board goal = new Board(pathToGoalFile, start.getHeight(), start.getWidth());
     Strategy solver = new BFSStrategy(start, goal);
     if(!solver.solve()){
         System.exit(1);
     }
  }
}
