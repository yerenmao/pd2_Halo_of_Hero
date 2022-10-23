package assetsPackage;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import gamePackage.GameObject;
import imagePackage.ImageLoader;

public class Wall extends GameObject {
	
	public static final int WIDTH = 30, HEIGHT = 30;
	
	private BufferedImage wallImage;
	
	private int x, y;
	
	public Wall(int x, int y, ImageLoader imageLoader) {
		this.x = x;
		this.y = y;
		wallImage = imageLoader.getWallImage();
	}

	public void tick() {
		
	}
	
	public void render(Graphics g) {
		g.drawImage(wallImage, x, y, x+WIDTH, y+HEIGHT, 0, 0, wallImage.getWidth(), wallImage.getHeight(), null);
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
}
