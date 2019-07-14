package World;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.swing.ImageIcon;

public class Building {
	
	private Image img;
	private HashMap <String, Consumable> consumables;
	private ArrayList<Building> connected;
	private boolean active;
	private boolean linked;
	private int ID;
	private int x;
	private int y;
	
	public Building(String img, int ID, boolean isHub) {
		this.img = new ImageIcon(getClass().getResource(img)).getImage();
		this.ID = ID;
		this.connected = new ArrayList<Building>();
		this.consumables = new HashMap<String, Consumable>();
		this.active = true;
		this.linked = isHub;
	}
	
	protected void addConsumable(Consumable c) {
		this.consumables.put(c.getName(), c);
	}
	
	public void tick() {
		if(this.active) {
			for(Consumable c: consumables.values()) {
				this.active = c.tick();
			}
		}
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public boolean linked() {
		return this.linked;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public Image getImage() {
		return this.img;
	}
	
	public int getID() {
		return this.ID;
	}
	
	public void connectTo(Building b) {
		this.connected.add(b);
		if(b.linked) this.linked = true;
	}
	
	public ArrayList<Building> getConnected() {
		return this.connected;
	}
	
	public Consumable getConsumable(String name) {
		return this.consumables.get(name);
	}
	
	public Collection<Consumable> getConsumables() {
		return this.consumables.values();
	}
}
