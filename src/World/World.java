package World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import Consumables.Oxygen;
import GUI.Camera;

public class World {
	
	private int[][] tiles;
	private int[][] resources;
	private int[][] buildingsLayer;
	private int size;
	private HashMap<Integer, Tile> tileTypes;
	private HashMap<Integer, Resource> resourceTypes;
	private HashMap<Integer, Vector<Integer>> tethers;
	private ArrayList<Building> buildings;
	private BuildingFactory buildingFactory;
	private BaseNetwork network;
	private int tetherDist = 320;
	
	public static World grassWorld(int size) {
		int[][] tiles = new int[size][size];
		for(int i = 0; i < size; i ++) {
			for(int j = 0; j < size; j ++) {
				if(i == 5 && j == 62) {
					tiles[i][j] = 2;
				} else {
					if(i == 0 && j == 0) {
						tiles[i][j] = 2;
					} else {
						tiles[i][j] = 1;
					}
				}
			}
		}
		System.out.println("oof");
		int[][] resources = new int[size][size];
		resources[4][4] = 1;
		return new World(tiles, resources);
	}
	
	public World(int[][] tiles, int[][] resources) {
		this.network = new BaseNetwork();
		this.resources = resources;
		this.tiles = tiles;
		this.tethers = new HashMap<Integer, Vector<Integer>>();
		this.buildings = new ArrayList<Building>();
		this.size = tiles.length;
		this.buildingsLayer = new int[this.size][this.size];
		TileFactory tileFactory = new TileFactory("Tiles");
		ResourceFactory resourceFactory = new ResourceFactory("Resources");
		this.buildingFactory = new BuildingFactory("Buildings");
		this.tileTypes = new HashMap<Integer, Tile>();
		this.resourceTypes = new HashMap<Integer, Resource>();
		
		for(int i = 0; i < this.size; i ++) {
			for(int ii = 0; ii < this.size; ii ++) {
				if(!tileTypes.containsKey(Integer.valueOf(tiles[i][ii])))
					tileTypes.put(Integer.valueOf(tiles[i][ii]), tileFactory.getTile(tiles[i][ii]));
			}
		}
		
		for(int i = 0; i < this.size; i ++) {
			for(int ii = 0; ii < this.size; ii ++) {
				if(!resourceTypes.containsKey(Integer.valueOf(resources[i][ii])) && resources[i][ii] != 0)
					resourceTypes.put(Integer.valueOf(resources[i][ii]), resourceFactory.getTile(resources[i][ii]));
			}
		}
		
		System.out.println("HI");
	}

	public boolean buildingExsits(int x, int y) {
		return this.buildingsLayer[y][x] != 0;
	}
	
	public int getResourceID(int i, int j) {
		return this.resources[i][j];
	}
	
	public Building getBuiding(int x, int y) {
		return this.buildings.get(this.buildingsLayer[y][x]-1);
	}
	
	public void distributeResources() {
		this.network.transferResource("Oxygen");
	}
	
	public void placeBuilding(int ID, int x, int y) {
		
		Building b = this.buildingFactory.getBuilding(ID);
		b.setX(x);
		b.setY(y);
		this.buildings.add(b);
		this.buildingsLayer[y][x] = this.buildings.size();
		this.network.addBuilding(b);
	}
	
	public ArrayList<Building> getBuildings() {
		return this.buildings;
	}
	
	public HashMap<Integer, Vector<Integer>> getTethers() {
		return this.tethers;
	}
	
	public void placeTether(int x, int y) {
		Vector<Integer> v = new Vector<Integer>();
		v.add(Integer.valueOf(x));
		v.add(Integer.valueOf(y));
		this.tethers.put(tethers.size(), v);
		this.updateConnections();
	}
	
	private void updateConnections() {
		
		int x = tethers.get(tethers.size()-1).get(0);
		int y = tethers.get(tethers.size()-1).get(1);
		Vector<Integer> connectedIDs = tethers.get(tethers.size()-1);
		
		for(int i = 0; i < this.tethers.size(); i ++) {
			int x2 = this.tethers.get(Integer.valueOf(i)).get(0);
			int y2 = this.tethers.get(Integer.valueOf(i)).get(1);
			int[] pos1 = Camera.worldToScreen(x, y, this.tetherDist, 1);
			int[] pos2 = Camera.worldToScreen(x2, y2, this.tetherDist, 1);
			if(Math.abs(pos2[0]-pos1[0]) <= this.tetherDist && Math.abs(pos2[1]-pos1[1]) <= this.tetherDist) {
				System.out.println("HIII");
				if(dist(pos1[0], pos1[1], pos2[0], pos2[1]) <= this.tetherDist) {
					connectedIDs.add(i);
				}
			}
		}
		
		tethers.put(tethers.size()-1, connectedIDs);
	}
	
	public int getTetherDist() {
		return this.tetherDist;
	}
	
	private float dist(int x1, int y1, int x2, int y2) {
			
		int dx = x1-x2;
		int dy = y1-y2;

		return (float) Math.sqrt(dx*dx + dy*dy);
	}
	
	public void removeResource(int i, int j) {
		this.resources[i][j] = 0;
	}
	
	public Tile getTile(int i, int j) {
		return tileTypes.get(Integer.valueOf(this.getID(i, j)));
	}
	
	public Resource getResource(int i, int j) {
		return resourceTypes.get(Integer.valueOf(this.getID(i, j)));
	}
	
	public int getID(int i, int j) {
		return this.tiles[i][j];
	}
	
	public int getSize() {
		return this.size;
	}
	
}
