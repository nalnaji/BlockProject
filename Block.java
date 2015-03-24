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
}
