class Block{
	private int height, width, row, col;
	private Board board;

	public Block(Board board, int height, int width, int row, int col){
		this.board = board;
		this.height = height;
		this.width = width;
		this.row = row;
		this.col = col;
	}

	static Block deepCopy(Block b){
		return new Block (b.board, b.height, b.width, b.row, b.col);
	}
	
	// Attempts to make a move in a direction
	// If the move FAILS, returns false
	public boolean move(String direction){
		this.board.getBlocks().remove(this);
		Block move =  Block.deepCopy(this);
		switch(direction){
			case "UP":
				move.row--;
				break;
			case "DOWN":
				move.row++;
				break;
			case "LEFT":
				move.col--;
				break;
			case "RIGHT":
				move.col++;
				break;
			default:
				throw new IllegalArgumentException("Tried to move in a direction that is"
																					+ "	not: up, down, left, or right");
		}
		this.board.getBlocks().add(move);
		if(!this.board.isValid()){
			this.board.getBlocks().remove(move);
			this.board.getBlocks().add(this);
			return false;
		}
		else
			return true;
	}
}
