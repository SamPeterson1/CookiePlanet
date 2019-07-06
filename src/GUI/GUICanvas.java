package GUI;


import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import World.Player;
import World.World;

public class GUICanvas extends Canvas {
	//TODO: generate new long for serialVersionUID
	private static final long serialVersionUID = -557652432650828632L;
	private int width;
	private int height;
	private int camX;
	private int camY;
	private World world;
	private Player player;
	
	
	public GUICanvas(int width, int height) {
		this.setBackground(Color.WHITE);
		this.setSize(width, height-20);
		this.width = width;
		this.height = height;
	}
	
	
	public void draw(Graphics2D g2) {
		BufferedImage img = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) img.getGraphics();
		this.clear(g2);
		int fineX = camX%32;
		int fineY = camY%32;
		int cx = (int) Math.floor(camX/32);
		int cy = (int) Math.floor(camY/32);
		for(int i = cx-1; i < cx+33; i ++) {
			for(int j = cy-1; j < cy+33; j ++) {
				if(!(i < 0 || i >= 64 || j < 0 || j >= 64)) {
					g.drawImage(world.getTile(i, j).getImg(), (i-cx)*32-fineX, (j-cy)*32-fineY, null);
				} else {
					int x = i;
					int y = j;
					if(x < 0) {
						x += world.getSize();
					}
					if(y < 0) {
						y += world.getSize();
					}
					if(x >= 64) {
						x -= world.getSize();
					}
					if(y >= 64) {
						y -= world.getSize();
					}
					g.drawImage(world.getTile(x, y).getImg(), (i-cx)*32-fineX, (j-cy)*32-fineY, null);
				}
			}
		}
		Image person = player.getImage();
		g.drawImage(person,	this.getWidth()/2-person.getWidth(null)/2, this.getHeight()/2-person.getWidth(null)/2, null);
		
		this.render(g2, img);		
	}

	public void clear(Graphics g) {
		g.setColor(this.getBackground());
		g.fillRect(0, 0, this.width, this.height);
	}
	
	public void render(Graphics g, BufferedImage img) {
		
		img = this.scaleToFrameSize(img);
		g.drawImage(img, 0, 0, null);
		g.dispose();
		
	}
	
	public BufferedImage scaleToFrameSize(BufferedImage img) {
		
		int[] targetRes = new int[2];
		targetRes[0] = this.getWidth();
		targetRes[1] = this.getHeight();
		
		return scale(targetRes, img);
		
	}
	
	public BufferedImage scale(int[] targetRes, BufferedImage img) {
		if(targetRes[1] != Main.WIDTH && targetRes[0] != Main.HEIGHT - 20) {
			Image image = img.getScaledInstance(targetRes[0], targetRes[1], Image.SCALE_SMOOTH);
			BufferedImage resizedImage = new BufferedImage(targetRes[0], targetRes[1], BufferedImage.TYPE_INT_ARGB); 
			Graphics2D g = resizedImage.createGraphics();
			g.drawImage(image, 0, 0, targetRes[0], targetRes[1], null);
			g.dispose();
		    return resizedImage;
		}
		return img;
	}

	public void setWorld(World world) {
		this.world = world;
	}
	
	public void camLeft(int amount) {
		this.camX -= amount;
		if(this.camX <= -16*32) camX = world.getSize()*32 - 16*32;
	}
	
	public void camRight(int amount) {
		this.camX += amount;
		if(this.camX >= world.getSize()*32 - 16*32) camX = -16*32;
	}
	
	public void camUp(int amount) {
		this.camY -= amount;
		if(this.camY <= -16*32) camY = world.getSize()*32 - 16*32;
	}
	
	public void camDown(int amount) {
		this.camY += amount;
		if(this.camY >= world.getSize()*32 - 16*32) camY = -16*32;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public void addEventQueue(GUIEventQueue queue)  {

		this.addKeyListener(queue);
		this.addMouseListener(queue);
		this.addMouseMotionListener(queue);

		return;
	}

}
