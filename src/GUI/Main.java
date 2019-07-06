package GUI;


import javax.swing.ImageIcon;
import javax.swing.JFrame;

import GameLoop.GameLoop;
import World.Player;
import World.World;

public class Main {
	
	public static final int LOOP_SPEED_MS = 1;
	public static final int WIDTH = 1020;
	public static final int HEIGHT = 1020;

	public static void main(String args[]) {
		
		GameLoop loop = new GameLoop();
		GUIEventQueue queue = new GUIEventQueue();
		JFrame frame = new JFrame("Cookie Planet");
		GUICanvas canvas = new GUICanvas(WIDTH, HEIGHT);
		GUIFrame uiFrame = new GUIFrame(canvas, frame, loop);
		World world = World.grassWorld(64);
		Player player = new Player("/Assets/Person.png", 7, 2.5);
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
