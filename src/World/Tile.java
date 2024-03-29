package World;

import java.awt.Image;
import java.util.ArrayList;

public class Tile {
	
	protected boolean habitable;
	protected Image img;
	protected int ID;
	protected boolean animated;
	protected ArrayList<Image> sprites;
	
	public Tile(String tileSet, int imgLoc, int ID, boolean habitable) {
		
		this.habitable = habitable;
		int imgX = imgLoc % 23;
		int imgY = imgLoc / 23; 
		this.img = new TileSet(tileSet, 32).getImage(imgX, imgY);
		this.ID = ID;
		
	}
	
	public Tile(String tileSet, ArrayList<Image> sprites, int ID, boolean habitable) {
		
		this.habitable = habitable;
		this.sprites = sprites;
		this.ID = ID;
		
	}

	public boolean isHabitable() {
		return habitable;
	}
	
	public Image getImg() {
		return img;
	}

	public int getID() {
		return ID;
	}
}
