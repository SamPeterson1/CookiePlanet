package World;

import java.awt.Image;

import javax.swing.ImageIcon;

import Items.Item;

public class Resource {
	
	Item drop;
	Image img;
	int ID;
	
	public Resource(Item drop, String img, int ID) {
		this.drop = drop;
		this.img = new ImageIcon(getClass().getResource(img)).getImage();
		this.ID = ID;
	}
	
	public Resource(Item drop, int row, int col, int ID) {
		this.drop = drop;
		this.img = new TileSet("/Assets/TileSet.png", 32).getImage(row, col);
		this.ID = ID;
	}
	
	public Item getDrop() {
		return this.drop;
	}
	
	public int getID() {
		return this.ID;
	}
	
	public Image getImage() {
		return this.img;
	}
}
