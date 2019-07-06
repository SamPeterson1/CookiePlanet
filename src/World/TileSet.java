package World;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class TileSet {

	private BufferedImage tileSet;
	private int tileSize;
	
	public TileSet(String setLocation, int tileSize) {
		this.tileSize = tileSize;
		this.tileSet = this.toBufferedImage(new ImageIcon(getClass().getResource(setLocation)).getImage());
	}
	
	public Image getImage(int row, int col) {
		return tileSet.getSubimage((row * this.tileSize), (col * this.tileSize),this.tileSize,this.tileSize);
	}
	
	private BufferedImage toBufferedImage(Image img) {
		
		BufferedImage retVal = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics g = retVal.createGraphics();
		
		g.drawImage(img, 0, 0, null);
		g.dispose();
		
		return retVal;
	}
}
