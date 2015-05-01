import java.io.*;
import java.util.*;
class Board{
	private int width, height;
	private TreeSet<Block> blocks = new TreeSet<Block>();

	public Board(int height, int width, TreeSet<Block> blocks){
		this.width = width;
		this.height = height;
		this.blocks = blocks;
	}
	public Board(String file){
		try{
			generateDimensions(file);
			generateBlocks(file, false);
			if(!(this.isValid())){
				throw new IllegalArgumentException("Generation is invalid...");
			} 
		}catch(Exception e){
			System.out.println(e);
		}
	}

	public Board(String file, int height, int width){
		try{
			this.height = height;
                        this.width = width;
			generateBlocks(file, true);
			if(!(this.isValid())){
				throw new IllegalArgumentException("Generation is invalid...");
			} 
		}catch(Exception e){
			System.out.println(e);
		}
	}

	static Board deepCopy(Board b){
		TreeSet<Block> dcBlocks = new TreeSet<Block>();
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
			Debugger.log("Found dimensions of h= " + this.height + " and w= " +this.width);
		}catch(Exception e){
			System.out.println("Something went wrong generating the boards dimensions");
		}
	}
	private void generateBlocks(String file, boolean withoutDimensions){
		try{
			BufferedReader input = openFile(file);
			StringTokenizer read;
                        String line;
                        if(!withoutDimensions)
                            line = input.readLine();
			while ((line = input.readLine()) != null) {
				read = new StringTokenizer(line);
				int height = Integer.parseInt(read.nextToken());
				int width = Integer.parseInt(read.nextToken());
				int row = Integer.parseInt(read.nextToken());
				int col = Integer.parseInt(read.nextToken());
				Block newBlock = new Block(this, this.blocks.size()+1, height, width, row, col);
				blocks.add(newBlock);
			}
			Debugger.log("Generated "+this.blocks.size()+ " blocks");
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
	public boolean isValidMove(int blockID){
                Block currentBlock = this.getBlock(blockID);
                //Make sure block has valid boundaries
                if(!currentBlock.isWithinDimensions(this.height, this.width)){
                          //Debugger.log("ERROR: Block #"+currentBlock.getId()+ " has the wrong dimensions");
                                return false;
                }
                //Make sure block doesn't overlap with other blocks	
                for(Block otherBlock : this.blocks){
                        if(!(currentBlock.equals(otherBlock)) && currentBlock.overlaps(otherBlock)){
                                //Debugger.log("ERROR: Block #"+currentBlock.getId()+ " and #"+otherBlock.getId()+" overlap!");
                                return false;
                        }
                }
		return true;
	}
	public boolean isValid(){
		for(Block currentBlock : this.blocks){
			//Make sure block has valid boundaries
			if(!currentBlock.isWithinDimensions(this.height, this.width)){
				  //Debugger.log("ERROR: Block #"+currentBlock.getId()+ " has the wrong dimensions");
					return false;
			}
			//Make sure block doesn't overlap with other blocks	
			for(Block otherBlock : this.blocks){
				if(!(currentBlock.equals(otherBlock)) && currentBlock.overlaps(otherBlock)){
					//Debugger.log("ERROR: Block #"+currentBlock.getId()+ " and #"+otherBlock.getId()+" overlap!");
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
			blockInfo += b.toString() + "\n";
		}
		return "w: "+this.width + "h: " +this.height+ blockInfo;
	}
	public TreeSet<Block> getBlocks(){
		return blocks;
	}
	static void prettyPrintMove(Board b1, Board b2){
                String msg = "No move made";
		for(Block bl1 : b1.blocks){
			for(Block bl2 : b2.blocks){
				if((bl1.getId() == bl2.getId()) && !(bl1.matchesWith(bl2))){
					if(bl1.getRow() < bl2.getRow()){
					        msg = "Moved block " + bl1.getId() + " down.";
					} else if(bl1.getRow() > bl2.getRow()){
						msg = "Moved block " + bl1.getId() + " up.";
					} else if(bl1.getCol() > bl2.getCol()){
						msg = "Moved block " + bl1.getId() + " to the left.";
					} else if(bl1.getCol() < bl2.getCol()){
						msg = "Moved block " + bl1.getId() + " to the right.";
					}
				}
			}
		}
		System.out.println(msg);
	}
	static void printMove(Board b1, Board b2){
                String msg = "No move made";
		for(Block bl1 : b1.blocks){
			for(Block bl2 : b2.blocks){
				if((bl1.getId() == bl2.getId()) && !(bl1.matchesWith(bl2))){
                                        msg = bl1.getRow()+" "+bl1.getCol()+" ";
                                        msg += bl2.getRow()+" "+bl2.getCol();
					//if(bl1.getRow() < bl2.getRow()){
					//        msg = "Moved block " + bl1.getId() + " down.";
					//} else if(bl1.getRow() > bl2.getRow()){
					//	msg = "Moved block " + bl1.getId() + " up.";
					//} else if(bl1.getCol() > bl2.getCol()){
					//	msg = "Moved block " + bl1.getId() + " to the left.";
					//} else if(bl1.getCol() < bl2.getCol()){
					//	msg = "Moved block " + bl1.getId() + " to the right.";
					//}
				}
			}
		}
		System.out.println(msg);
	}
	public void deleteBlock(Block b){
		Block target = null;
		for( Block block : this.blocks) 
			if(block.getId() == b.getId())
				target = block;
		if(target != null)
			this.blocks.remove(target);
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
        public Block getBlock(int id){
            for(Block b : this.blocks){
                if(b.getId() == id)
                    return b;
            }
            return null;
        }
}
