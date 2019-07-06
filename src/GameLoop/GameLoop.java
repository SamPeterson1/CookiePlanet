package GameLoop;
import GUI.GUICanvas;
import GUI.GUIEvent;
import GUI.GUIEventQueue;
import GUI.GUIFrame;
import World.Player;



public class GameLoop {

	public GUIEventQueue queue;
	public GUICanvas canvas;
	public GUIEvent event;
	public GUIFrame frame;
	private Player player;

	public GameLoop() {
	}
	
	public void update() {		
		if(queue.isEventToProcess()) {
			event = queue.getEvent();
			if(event.getType() == GUIEvent.EVENT_KEY_PRESS) {
				char key = event.getKeyChar();
				System.out.println(event.getKeyCode());
				if(event.getKeyCode() == 16) {
					player.toggleSprint();
					System.out.println(player.sprint);
				}
				if(key == 'w') {
					canvas.camUp(player.moveSpeed());
					player.up();
				} else if(key == 'a') {
					canvas.camLeft(player.moveSpeed());
					player.left();
				} else if(key == 's') {
					canvas.camDown(player.moveSpeed());
					player.down();
				} else if(key == 'd') {
					canvas.camRight(player.moveSpeed());
					player.right();
				}
			}
		}
	}

	
	public void setElements(GUIEventQueue q, GUICanvas c, GUIFrame f, Player player) {
		this.queue = q;
		this.canvas = c;
		this.frame = f;
		this.player = player;
	}
	
}
