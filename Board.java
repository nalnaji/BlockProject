import java.io.*;
import java.util.*;

class Board{
	private int width, height;
	private ArrayList<Block> blocks = new ArrayList<Block>();

	public Board(String file){
		try{
			generateDimensions(file);
			generateBlocks(file);
		}catch(Exception e){
			System.out.println("Something went wrong reading input files");
		}
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

				Block newBlock = new Block(this, height, width, row, col);
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
	public ArrayList<Block> getBlocks(){
		return blocks;
	}
}
