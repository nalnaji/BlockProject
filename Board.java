class Board{
	private int width, height;
	private ArrayList<Block> blocks = new ArrayList<Block>();

	public Board(String file){
		generateDimensions(file);
		generateBlocks(file);
	}
  private void generateDimensions(String file){
		BufferedReader input = new BufferedReader(new FileReader(myTextFile));
		StringTokenizer read;
		String line = input.readLine();
		read = new StringTokenizer(line);
		this.height = Integer.parseInt(read.nextToken());
		this.width = Integer.parseInt(read.nextToken());
	}
	private void generateBlocks(String file){
		BufferedReader input = new BufferedReader(new FileReader(myTextFile));
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
	}
}
