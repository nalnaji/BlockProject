import java.io.*;
import java.util.*;
class Board{
	private int width, height;
	private ArrayList<Block> blocks = new ArrayList<Block>();

	public Board(int height, int width, ArrayList<Block> blocks){
		this.width = width;
		this.height = height;
		this.blocks = blocks;
	}
	public Board(String file){
		try{
			generateDimensions(file);
			generateBlocks(file);
			if(!(this.isValid())){
				throw new IllegalArgumentException("Generation is invalid...");
			} 
		}catch(Exception e){
			System.out.println(e);
		}
	}

	static Board deepCopy(Board b){
		ArrayList<Block> dcBlocks = new ArrayList<Block>();
		Board clone = new Board(b.height, b.width, dcBlocks);
		for(Block bl : b.blocks){
			clone.blocks.add(new Block(clone, bl.getId(), bl.getHeight(), bl.getWidth(), bl.getRow(), bl.getCol()));
		}
		return clone;
	}
  private void generateDimensions(String file){
		try{
			BufferedReader input = openFile(file);
			StringTokenizer read;
			String line = input.readLine();
			read = new StringTokenizer(line);
			this.height = Integer.parseInt(read.nextToken());
			this.width = Integer.parseInt(read.nextToken());
			System.out.println("Found dimensions of h= " + this.height + " and w= " +this.width);
		}catch(Exception e){
			System.out.println("Something went wrong generating the boards dimensions");
		}
	}
	private void generateBlocks(String file){
		try{
			BufferedReader input = openFile(file);
			StringTokenizer read;
			String line = input.readLine();
			while ((line = input.readLine()) != null) {
				read = new StringTokenizer(line);
				int height = Integer.parseInt(read.nextToken());
				int width = Integer.parseInt(read.nextToken());
				int row = Integer.parseInt(read.nextToken());
				int col = Integer.parseInt(read.nextToken());
				Block newBlock = new Block(this, this.blocks.size()+1, height, width, row, col);
				blocks.add(newBlock);
			}
			System.out.println("Generated "+this.blocks.size()+ " blocks");
		}catch(Exception e){
			System.out.println("Something went wrong generating the blocks on the board");
		}
	}
	private BufferedReader openFile(String path){
		try{
			return new BufferedReader(new FileReader(path));
		}catch(Exception e){
			System.out.println("Something went wrong trying to read the input files");
			return null;
		}
	}
	public boolean isValid(){
		for(Block currentBlock : this.blocks){
			//Make sure block has valid boundaries
			if(!currentBlock.isWithinDimensions(this.height, this.width)){
					//System.out.println("ERROR: Block #"+currentBlock.getId()+ " has the wrong dimensions");
					return false;
			}
			//Make sure block doesn't overlap with other blocks	
			for(Block otherBlock : this.blocks){
				if(!(currentBlock.equals(otherBlock)) && currentBlock.overlaps(otherBlock)){
					//System.out.println("ERROR: Block #"+currentBlock.getId()+ " and #"+otherBlock.getId()+" overlap!");
					return false;
				}
			}
		}
		return true;
	}
	public Block getBlockById(int id){
		for(Block b : this.blocks){
			if(b.getId() == id)
				return b;
		}
		return null;
	}
	public ArrayList<Board> getPossibleStates(){
		ArrayList<Board> result = new ArrayList<Board>();
		for(Block b : this.blocks){
			ArrayList<Board> possibleStates = b.possibleMoves();
			for(Board moves : possibleStates)
				result.add(moves);
		}
		return result;
	}
	@Override
	public int hashCode(){
		return this.toString().hashCode();
	}
	public boolean wins(Board goal){
		for(Block b : goal.getBlocks()){
			if(!this.hasBlock(b))
				return false;
		}
		return true;
	}
	public boolean hasBlock(Block b){
		for(Block block : this.blocks){
			if(block.matchesWith(b))
				return true;
		}
		return false;
	}
	@Override
	public boolean equals(Object other){
		if (other == null) return false;
		if (other == this) return true;
		if (!(other instanceof Board)) return false;

		return this.toString().equals(other.toString());
	}
	public String toString(){
		String blockInfo = "";
		for(Block b : this.blocks){
			blockInfo += b.toString();
		}
		return "w: "+this.width + "h: " +this.height+ blockInfo;
	}
	public ArrayList<Block> getBlocks(){
		return blocks;
	}
	static void printMove(Board b1, Board b2){
		String msg = "No move made.";
		for(Block bl1 : b1.blocks){
			for(Block bl2 : b2.blocks){
				if(bl1.getRow() > bl2.getRow()){
					msg = "Moved block " + bl1.getId() + " down.";
				} else if(bl1.getRow() < bl2.getRow()){
					msg = "Moved block " + bl1.getId() + " up.";
				} else if(bl1.getCol() > bl2.getCol()){
					msg = "Moved block " + bl1.getId() + " to the left.";
				} else if(bl1.getCol() < bl2.getCol()){
					msg = "Moved block " + bl1.getId() + " to the right.";
				}
			}
		}
		System.out.println(msg);
	}
	public void deleteBlock(Block b){
		for(int i=0; i < this.blocks.size(); i++) 
			if(this.blocks.get(i).getId() == b.getId())
				this.blocks.remove(i);
	}
	public void addBlock(Block b){
		this.blocks.add(b);
	}
	public int getHeight(){
		return this.height;
	}
	public int getWidth(){
		return this.width;
	}
}
