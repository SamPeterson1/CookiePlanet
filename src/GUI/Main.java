package GUI;


import java.util.HashMap;
import java.util.Vector;

import javax.swing.JFrame;

import GameLoop.GameLoop;
import World.Building;
import World.BuildingFactory;
import World.Player;
import World.World;

public class Main {
	
	public static final int LOOP_SPEED_MS = 500;
	public static final int WIDTH = 1020;
	public static final int HEIGHT = 1020;

	public static void main(String args[]) {
		
		GameLoop loop = new GameLoop();
		GUIEventQueue queue = new GUIEventQueue();
		JFrame frame = new JFrame("SpaceWalk");
		GUICanvas canvas = new GUICanvas(WIDTH, HEIGHT);
		GUIFrame uiFrame = new GUIFrame(canvas, frame, loop);
		World world = World.grassWorld(256);
		HashMap<Integer, Vector<Integer>> tethers = world.getTethers();
		
		world.placeTether(0, 0);
		BuildingFactory factory = new BuildingFactory("Buildings");
		Building hub = factory.getBuilding(1);
		System.out.println(hub.getID());
		
		Player player = new Player(31, 5);
		player.setWorld(world);
		System.out.println("YEET");
		System.out.println(world.getTile(9, 2).getID());
        uiFrame.setSize(Main.WIDTH, Main.HEIGHT);
        canvas.addEventQueue(queue);
        canvas.setWorld(world);
        canvas.setPlayer(player);
        loop.setElements(queue, canvas, uiFrame, player);
        uiFrame.start(uiFrame.getWidth(), uiFrame.getHeight());
        
	}
	
	public static void wait(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
