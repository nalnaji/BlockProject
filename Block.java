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
	public boolean overlaps(Block other){
		//establish top-left and bottom-right corners
		int[] l1 = {this.row, this.col};
		int[] l2 = {this.row+height, this.col+width};
		int[] r1 = {other.row, other.col};
		int[] r2 = {other.row+height, other.col+width};

		//above or below each other
		if(l1[0] > r2[0] || l2[0] < r1[0])
			return false;
		//side-by-side
		if(l1[1] > r2[1] || l2[1] < r1[1])
			return false;
		return true;
	}
	public boolean isWithinDimensions(int height, int width){
		int[] l1 = {this.row, this.col};
		int[] l2 = {this.row+height, this.col+width};
		
		if(l1[0] < 0 || l1[1] < 0)
			return false;
		if(l2[0] > height-1 || l2[1] > width)
			return false;
		return true;
	}
	public int getRow(){return this.row;}
	public int getCol(){return this.col;}
	public int getHeight(){return this.height;}
	public int getWidth(){return this.width;}
}
