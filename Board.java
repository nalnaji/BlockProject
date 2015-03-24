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
		}catch(Exception e){
			System.out.println("Something went wrong reading input files");
		}
	}

	static Board deepCopy(Board b){
		ArrayList<Block> dcBlocks = new ArrayList<Block>();
		for(Block block : b.blocks){
			dcBlocks.add(Block.deepCopy(block));
		}
		return new Board(b.height, b.width, dcBlocks);
	}
  private void generateDimensions(String file){
		try{
			BufferedReader input = openFile(file);
			StringTokenizer read;
			String line = input.readLine();
			read = new StringTokenizer(line);
			this.height = Integer.parseInt(read.nextToken());
			this.width = Integer.parseInt(read.nextToken());
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

				Block newBlock = new Block(this, this.blocks.size(), height, width, row, col);
				blocks.add(newBlock);
			}
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
			if(!currentBlock.isWithinDimensions(this.height, this.width))
					return false;
			//Make sure block doesn't overlap with other blocks	
			for(Block otherBlock : this.blocks){
				if(!(currentBlock.equals(otherBlock)) && currentBlock.overlaps(otherBlock))
					return false;	
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
}
