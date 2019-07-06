package World;

import java.util.HashMap;

public class World {
	
	private TileFactory factory;
	private int[][] tiles;
	private int size;
	private HashMap<Integer, Tile> tileTypes;
	
	public static World grassWorld(int size) {
		int[][] tiles = new int[size][size];
		for(int i = 0; i < size; i ++) {
			for(int j = 0; j < size; j ++) {
				if(i == 5 && j == 62) {
					tiles[i][j] = 2;
				} else {
					if(i == 10 && j == 10) {
						tiles[i][j] = 2;
					} else {
						tiles[i][j] = 1;
					}
				}
			}
		}
		System.out.println("oof");
		return new World(tiles);
	}
	
	public World(int[][] tiles) {
		this.tiles = tiles;
		this.size = tiles.length;
		this.factory = new TileFactory("Tiles");
		this.tileTypes = new HashMap<Integer, Tile>();
		
		for(int i = 0; i < this.size; i ++) {
			for(int ii = 0; ii < this.size; ii ++) {
				if(!tileTypes.containsKey(Integer.valueOf(tiles[i][ii])))
					tileTypes.put(Integer.valueOf(tiles[i][ii]), factory.getTile(tiles[i][ii]));
			}
		}
		
		System.out.println("HI");
	}
	
	public Tile getTile(int i, int j) {
		return tileTypes.get(Integer.valueOf(this.getID(i, j)));
	}
	
	public int getID(int i, int j) {
		return this.tiles[i][j];
	}
	
	public int getSize() {
		return this.size;
	}
	
}
