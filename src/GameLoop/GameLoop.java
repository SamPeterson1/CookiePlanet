package GameLoop;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import GUI.Camera;
import GUI.GUICanvas;
import GUI.GUIEvent;
import GUI.GUIEventQueue;
import GUI.GUIFrame;
import World.Building;
import World.Consumable;
import World.Player;
import World.World;



public class GameLoop {

	public GUIEventQueue queue;
	public GUICanvas canvas;
	public GUIEvent event;
	public GUIFrame frame;
	private Player player;
	private World world;
	private long time;

	public GameLoop() {
		time = System.currentTimeMillis();
	}
	
	public void update() {	
		
		player.updateSprite();
		
		ArrayList<Building> buildings = this.world.getBuildings();
		
		if(System.currentTimeMillis() - time >= 1000) {
			this.world.distributeResources();
			for(Building b: buildings) {
				//b.tick();
				String resources = "";
				for(Consumable c: b.getConsumables()) {
					resources += c.getName() + " Level: " + c.getAmount() + " ";
				}
					
				System.out.println(resources);
			}
			time = System.currentTimeMillis();		
		}

		long time = System.currentTimeMillis();
		
		
		
		if(queue.isEventToProcess()) {
			event = queue.getEvent();
			if(event.getType() == GUIEvent.EVENT_KEY_PRESS) {
				char key = event.getKeyChar();
				System.out.println(event.getKeyCode());
				if(event.getKeyCode() == 16) {
					player.toggleSprint();
				}
				if(key == 'w') {
					Camera.up(player.moveSpeed());
					player.up();
				} else if(key == 'a') {
					Camera.left(player.moveSpeed());
					player.left();
				} else if(key == 's') {
					Camera.down(player.moveSpeed());
					player.down();
				} else if(key == 'd') {
					Camera.right(player.moveSpeed());
					player.right();
				} else if(key == 'b') {
					player.harvestResource();
					player.toggleBag();
				} else if(key == 't') {
					player.placeTether();
					HashMap<Integer, Vector<Integer>> tethers = this.world.getTethers();
					for(Integer key2: tethers.keySet()) {
						System.out.println(key2 + ": " + tethers.get(key2).toString());
					}
				} else if(key == 'p') {
					player.placeBuilding(2);
				} else if(key == 'o') {
					player.placeBuilding(1);
				}
			} else if(event.getType() == GUIEvent.EVENT_KEY_RELEASE) {
				char key = event.getKeyChar();
				if(key == 'w' || key == 'a' || key == 's' || key == 'd') {
					player.setMovement(false);
				}
			} else if(event.getType() == GUIEvent.EVENT_MOUSE_BUTTON_PRESS) {
				if(event.isMouseLeftButton()) {
					int x = event.getMouseX();
					int y = event.getMouseY();
					
					int[] pos = Camera.screenToWorld(x, y);
					
					player.connectBuilding(pos[0], pos[1]);
				} else if(event.isMouseRightButton()) {
					canvas.updateHover(event.getMouseX(), event.getMouseY());
				}
			}
			
			
		}
	}

	
	public void setElements(GUIEventQueue q, GUICanvas c, GUIFrame f, Player player) {
		this.queue = q;
		this.canvas = c;
		this.frame = f;
		this.player = player;
		world = player.getWorld();
	}
	
}
