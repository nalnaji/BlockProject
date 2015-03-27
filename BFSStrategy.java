import java.util.*;

class BFSStrategy implements Strategy{
	private Board initBoard, goalBoard;
	//TODO: Change to HashMap that keeps track of moves
	private HashMap<Board, ArrayList<Board>> seenStates;
	public BFSStrategy(Board init, Board goal){
		this.initBoard = init;
		this.goalBoard = goal;
	}
	public void solve(){
		seenStates = new HashMap<Board, ArrayList<Board>>();
		LinkedList<Board> moveQueue = new LinkedList<Board>();
		ArrayList<Board> moveOrder = new ArrayList<Board>();
		moveQueue.add(initBoard);	
		moveOrder.add(initBoard);
		seenStates.put(initBoard, moveOrder);

		while(!(moveQueue.size() == 0)){
			Board current = moveQueue.remove();
			ArrayList<Board> currentMoveOrder = seenStates.get(current);
			if(current.wins(goalBoard)){
				for(int i = 0; i < currentMoveOrder.size()-1; i++){
					Board.printMove(currentMoveOrder.get(i), currentMoveOrder.get(i+1));
				}
				System.out.println("We made ittttt");
				break;
			}
			for(Board possibleState : current.getPossibleStates()){
				if(!seenStates.containsKey(possibleState)){
					ArrayList<Board> newMoveOrder = new ArrayList<Board>();
					for(Board move : currentMoveOrder)
						newMoveOrder.add(Board.deepCopy(move));
					newMoveOrder.add(possibleState);
					seenStates.put(possibleState, newMoveOrder);
					moveQueue.add(possibleState);
				}
				else{
				}
			}
		}
	}
}
