package GUI;


import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import GameLoop.GameLoop;


public class GUIFrame {
	
	private GUICanvas canvas;
	private int width;
	private int height;
	private GameLoop loop;
	private JFrame frame;
	
	public GUIFrame(GUICanvas c, JFrame f, GameLoop loop) {
		this.loop = loop;
		this.canvas = c;
		this.frame = f;
        frame.add(c);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void newFrame(int width, int height, String title, GUICanvas c) {
		JFrame frame = new JFrame();
		frame.setSize(width, height);
		frame.setTitle(title);
		frame.add(c);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
	}
	
	public void setSize(int width, int height) {
		frame.setSize(width, height);
		this.width = width;
		this.height = height;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public void start(int WIDTH, int HEIGHT) {
	    boolean running=true;
	    boolean iterateNext = false;
	    while(running) {
		    BufferStrategy bs = canvas.getBufferStrategy();
		    if(bs==null){
		        canvas.createBufferStrategy(4);
		        iterateNext = false;
		    } else {
		   	 	iterateNext = true;
		    }
		    if(iterateNext) {
		    	loop.update();
			  
		    	Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		    	
			    Main.wait(Main.LOOP_SPEED_MS);	 
			    bs.show();
			    canvas.draw(g);
		    }
	    }
	}
}

