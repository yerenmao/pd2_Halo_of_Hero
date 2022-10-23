package assetsPackage;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import imagePackage.ImageLoader;

public class Door {
	
	public static final int WIDTH = 60, HEIGHT = 60;
	
	private BufferedImage wallImage;
	private BufferedImage doorImage;
	private String doorLevel;
	
	private int x, y;
	
	public Door(int x, int y, ImageLoader imageLoader) {
		this.x = x;
		this.y = y;
		wallImage = imageLoader.getWallImage();
		doorImage = imageLoader.getDoorImage();
	}
	
	public Door(int x, int y, ImageLoader imageLoader, String doorLevel) {
		this(x, y, imageLoader);
		this.doorLevel = doorLevel;
	}

	public void tick() {
		
	}
	
	public void render(Graphics g) {
		g.drawImage(wallImage, x, y, x+Wall.WIDTH, y+Wall.HEIGHT, 0, 0, wallImage.getWidth(), wallImage.getHeight(), null);
		g.drawImage(wallImage, x+Wall.WIDTH, y, x+Wall.WIDTH*2, y+Wall.HEIGHT, 0, 0, wallImage.getWidth(), wallImage.getHeight(), null);
		g.drawImage(doorImage, x, y, x+WIDTH, y+HEIGHT, 0, 0, doorImage.getWidth(), doorImage.getHeight(), null);
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}
	
	public String getDoorLevel() {
		return doorLevel;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
}
