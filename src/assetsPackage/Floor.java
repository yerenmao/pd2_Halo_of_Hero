package assetsPackage;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import imagePackage.ImageLoader;

public class Floor {
	
	public static final int WIDTH = 30, HEIGHT = 30; 
	
	private BufferedImage floorImage;
	
	private int x, y;
	
	public Floor(int x, int y, ImageLoader imageLoader) {
		this.x = x;
		this.y = y;
		floorImage = imageLoader.getBossGroundImage();
	}
	
	public Floor(int x, int y, ImageLoader imageLoader, int n) {
		this.x = x;
		this.y = y;
		floorImage = imageLoader.getSpawnAreaImage();
	}

	public void tick() {
		
	}
	
	public void render(Graphics g) {
		g.drawImage(floorImage, x, y, x+WIDTH, y+HEIGHT, 0, 0, floorImage.getWidth(), floorImage.getHeight(), null);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
}
