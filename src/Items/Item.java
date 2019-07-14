package Items;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Item {
	
	protected Image img;
	protected int ID;
	
	public Item(String img, int ID) {
		this.img = new ImageIcon(getClass().getResource(img)).getImage();
		this.ID = ID;
	}
	
	public int getID() {
		return this.ID;
	}
	
	public Image getImage() {
		return this.img;
	}
}
