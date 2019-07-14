package GUI;


import java.awt.AlphaComposite;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.ImageIcon;

import World.Building;
import World.Player;
import World.World;

public class GUICanvas extends Canvas {
	//TODO: generate new long for serialVersionUID
	private static final long serialVersionUID = -557652432650828632L;
	private int width;
	private int height;
	private World world;
	private Player player;
	private Image bagBG = new ImageIcon(getClass().getResource("/Assets/BagBG.png")).getImage();
	
	
	public GUICanvas(int width, int height) {
		this.setBackground(Color.WHITE);
		this.setSize(width, height-20);
		this.width = width;
		this.height = height;
	}
	
	
	public void draw(Graphics2D g2) {
		BufferedImage img = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
		BufferedImage tiles = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
		BufferedImage resources = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) img.getGraphics();
		this.clear(g2);
		
		AlphaComposite alcom = AlphaComposite.getInstance(AlphaComposite.SRC_OVER);
        g.setComposite(alcom);
		
		Graphics t = tiles.getGraphics();
		Graphics r = resources.getGraphics();
		for(int i = 0; i < world.getSize(); i ++) {
			for(int ii = 0; ii < world.getSize(); ii ++) {
				int[] screen = Camera.worldToScreen(i, ii, 32, 32);
				t.drawImage(world.getTile(i, ii).getImg(), screen[0], screen[1], null);
				if(world.getResourceID(i, ii) != 0)
					r.drawImage(world.getResource(i, ii).getImage(), screen[0], screen[1], null);
			}
		}
		
		g.drawImage(tiles, 0, 0, null);
		g.drawImage(resources, 0, 0, null);
		
		for(Building b: world.getBuildings()) {
			int[] pos = Camera.worldToScreen(b.getX(), b.getY(), 512, 32);
			//if(pos[0] > -1024 && pos[0] < this.getWidth() + 1024 && pos[1] > -1024 && pos[1] < this.getWidth() + 1024) {
				g.drawImage(b.getImage(), pos[0], pos[1], null);
				for(Building con: b.getConnected()) {
					int[] pos2 = Camera.worldToScreen(con.getX(), con.getY(), 512, 32);
					//if(Math.abs(pos2[0] - pos[0]) < 1024 && Math.abs(pos2[1]- pos[1]) < 1024)
						g.drawLine(pos[0], pos[1], pos2[0], pos2[1]);
	 			}
			//}
		}
		
		Image person = player.getImage();
		g.drawImage(person,	this.getWidth()/2-person.getWidth(null)/2, this.getHeight()/2-person.getWidth(null)/2, null);
		
		if(player.inBag()) {
			g.drawImage(bagBG, 895, 785, this.getWidth()-895, this.getHeight()-763, null);
			g.setColor(Color.BLACK);
			g.fillRect(890, 780, 130, 5);
			g.fillRect(890, 780, 5, 240);
			g.setColor(new Color(0, 0, 0, 100));
			for(int i = 0; i < 8; i ++) {
				int x = i%2;
				int y = i/2;
				g.fillRect(x*55 + 905, y*55 + 795, 50, 50);
				if(i < player.getBag().size()) {
					g.drawImage(player.getBag().get(i).getImage(), x*55 + 905, y*55 + 797, null);
				}
			}
		}
		
		
		HashMap<Integer, Vector<Integer>> tethers = world.getTethers();
		int tetherDist = world.getTetherDist();
		for(Integer key: tethers.keySet()) {
		
			Vector<Integer> tether = tethers.get(key);
			int[] pos = Camera.worldToScreen(tether.get(0), tether.get(1), tetherDist, 1);

			g.drawImage(player.getImage(), pos[0], pos[1], null);
			
			if(pos[0] > -tetherDist && pos[0] < this.getWidth() + tetherDist && pos[1] > -tetherDist && pos[1] < this.getWidth() + tetherDist) {
				int index = 0;
				for(Integer id: tether) {
					if(index > 1) {
						int[] pos2 = Camera.worldToScreen(tethers.get(id).get(0), tethers.get(id).get(1), tetherDist, 1);
						if(Math.abs(pos2[0] - pos[0]) < tetherDist && Math.abs(pos2[1]- pos[1]) < tetherDist)
							g.drawLine(pos[0]+16, pos[1]+16, pos2[0]+16, pos2[1]+16);
					}
					index ++;
				}
			}
		}
		
		
		
		this.render(g2, img);
		
	}
	
	public int[] screenToWorld(int x, int y) {
		
		//a = width/2+(x*32-camY) --> a - width/2 = x*32-camY  --> a - width/2 + camY = x*32 --> x = (a - (width/2) + camY)/32
		
		x = (x - (this.getWidth()/2) + Camera.x)/32;
		y = (y - (this.getWidth()/2) + Camera.y)/32;
		
		int testX = x - world.getSize();
		int testY = y - world.getSize();
		
		if(Math.abs(testX*32-Camera.x) > 16*32 + 100 && testX >= 0) {
			x = testX;
		}
		
		if(Math.abs(testY*32-Camera.y) > 16*32 + 100 && testY >= 0) {
			y = testY;
		}
		
		int[] retVal = {x, y};
		
		return retVal;
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
