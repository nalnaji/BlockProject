import java.util.*;

class Block implements Comparable{
  private Board board;
  private int id, height, width, row, col;

  public Block(Board board, int id, int height, int width, int row, int col){
    this.board = board;
    this.id = id;
    this.height = height;
    this.width = width;
    this.row = row;
    this.col = col;
  }

  static Block deepCopy(Block b){
    Board clone = Board.deepCopy(b.board);
    return new Block (clone, b.id, b.height, b.width, b.row, b.col);
  }
  public boolean matchesWith(Block b){
    if(this.height == b.height &&
        this.width == b.width &&
        this.row == b.row &&
        this.col == b.col)
      return true;
    return false;
  }
  // Attempts to make a move in a direction
  // If the move FAILS, returns null
  // otherwise returns the new state of the game
  public Board move(String direction){
    Block old = new Block(this.board, this. id, this.height,
                          this.width, this.row, this.col);
    this.board.deleteBlock(this);
    switch(direction){
      case "UP":
        this.row--;
        break;
      case "DOWN":
        this.row++;
        break;
      case "LEFT":
        this.col--;
        break;
      case "RIGHT":
        this.col++;
        break;
      default:
        throw new IllegalArgumentException("Tried to move in a direction that is"
                                          + "  not: up, down, left, or right");
    }
    this.board.addBlock(this);
    if(this.board.isValidMove(this.id)){
      return this.board;
    }
    else{
      this.board.deleteBlock(this);
      old.board.addBlock(old);
      return null;
    }
  }
  public boolean overlaps(Block other){
    //establish top-left and bottom-right corners
    int[] l1 = {this.row, this.col};               //0,0
    int[] r1 = {this.row+this.height-1, this.col+this.width-1};  //0,0
    int[] l2 = {other.row, other.col};             //0,1
    int[] r2 = {other.row+other.height-1, other.col+other.width-1};
    if (l1[0] <= l2[0] && l1[1]>=l2[1] && r1[0]>=r2[0] && r1[1]<=r2[1])
      return true;
    if (l2[0] <= l1[0] && l2[1]>=l1[1] && r2[0]>=r1[0] && r2[1]<=r1[1])
      return true;
    if(Arrays.equals(l1, l2) || Arrays.equals(l1, r2) ||
        Arrays.equals(r1, l2) || Arrays.equals(r1, r2))
      return true;
    int b1Top = this.row; //3
    int b1Bottom = this.row + this.height-1; //3
    int b1Left = this.col; //3
    int b1Right = this.col + this.width-1; //3
    int b2Top = other.row; //3
    int b2Bottom = other.row + other.height-1; //4
    int b2Left = other.col; //2
    int b2Right = other.col + other.width-1; //3
    //above or below each other
    if((b1Bottom < b2Top)||(b1Top > b2Bottom)||(b1Left > b2Right)||(b1Right < b2Left))
      return false;
    return true;
  }
  public boolean isWithinDimensions(int height, int width){
    int[] l1 = {this.row, this.col};
    int[] l2 = {this.row+this.height-1, this.col+this.width-1};
    if(l1[0] < 0 || l1[1] < 0)
      return false;
    if(l2[0] > this.board.getHeight()-1 || l2[1] > this.board.getWidth()-1)
      return false;
    return true;
  }
  // Return a list of all the possible board states that
  // created by moving the current block.
  public ArrayList<Board> possibleMoves(){
    String[] moves = {"UP", "DOWN", "LEFT", "RIGHT"};
    ArrayList<Board> result = new ArrayList<Board>();
    for(String move : moves){
      Block possibleBlock = Block.deepCopy(this);
      Board possibleBoard = possibleBlock.move(move);
      if(possibleBoard != null){
        Debugger.log("Block #"+this.id+" can move " + move);
        result.add(possibleBoard);
      }
    }
    return result;
  }
  public int compareTo(Object obj){
    Block o = (Block)(obj);
    if(this.id < o.id)
      return -1;
    if(this.id == o.id)
      return 0;
    return 1;
  }
  public String toString(){
    return "block #"+this.id+" row: "+this.row+" col: "+this.col+
      " height: "+this.height+" width: "+this.width;
  }
  public int getRow(){return this.row;}
  public int getCol(){return this.col;}
  public int getHeight(){return this.height;}
  public int getWidth(){return this.width;}
  public int getId(){return this.id;}
}
