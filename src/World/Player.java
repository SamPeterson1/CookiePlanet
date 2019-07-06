package World;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Player {
	
	public int x = 0;
	public int y = 0;
	public int worldX = 0;
	public int worldY = 0;
	public int baseSpeed = 0;
	public double sprintMult = 0;
	public boolean sprint = false;
	public Image img;
	
	public Player(String img, int baseSpeed, double sprintMult) {
		this.baseSpeed = baseSpeed;
		this.sprintMult = sprintMult;
		this.img = new ImageIcon(getClass().getResource(img)).getImage();
	}
	
	public void center(int canvasWidth, int canvasHeight) {
		this.x = canvasWidth/2 + img.getWidth(null)/2;
		this.y = canvasHeight/2 + img.getHeight(null)/2;
	}
	
	public void toggleSprint() {
		this.sprint = !this.sprint;
	}
	
	public int moveSpeed() {
		if(sprint) return this.sprintSpeed();
		return this.walkSpeed();
	}
	
	public Image getImage() {
		return this.img;
	}
	
	public int walkSpeed() {
		return this.baseSpeed;
	}
	
	public int sprintSpeed() {
		return (int)(this.baseSpeed*sprintMult);
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getWorldX() {
		return this.worldX;
	}
	
	public int getWorldY() {
		return this.worldY;
	}
	
	public void left() {
		this.worldX -= this.moveSpeed();
	}
	
	public void right() {
		this.worldX += this.moveSpeed();
	}
	
	public void up() {
		this.worldY -= this.moveSpeed();
	}
	
	public void down() {
		this.worldY += this.moveSpeed();
	}
}
