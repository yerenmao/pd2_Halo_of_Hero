package pickUpPackage;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import gamePackage.GameObjectController;
import imagePackage.ImageLoader;

public class DimondPU extends PickUp {
	
	private int WIDTH = 58, HEIGHT = 58;
	
	private boolean limitTime = false;
	private boolean spawn = false;
	private long spawnTime;
	private BufferedImage dimondImage;
	private boolean showImage = true;
	private int count = 0;
	
	public DimondPU(int x, int y, ImageLoader imageLoader, GameObjectController objectController) {
		super(x, y, imageLoader, objectController);
		dimondImage = imageLoader.getDimondImage();
	}
	
	public DimondPU(int x, int y, ImageLoader imageLoader, GameObjectController objectController, boolean limitTime) {
		super(x, y, imageLoader, objectController);
		dimondImage = imageLoader.getDimondImage();
		this.limitTime = limitTime;
	}
	
	public void tick() {
		if(limitTime) {
			if(!spawn) {
				spawnTime = System.currentTimeMillis();
				spawn = true;
			}
			if(System.currentTimeMillis()-spawnTime > 1000 * 5) {
				count++;
				if(count > 5) {
					count = 0;
					showImage = !showImage;
				}
			}
			if(System.currentTimeMillis()-spawnTime > 1000 * 10) {
				objectController.removePickUp(this);
			}
		}
	}
	
	public void render(Graphics g) {
		if(showImage) g.drawImage(dimondImage, x, y, x+WIDTH, y+HEIGHT, 0, 0, dimondImage.getWidth(), dimondImage.getHeight(), null);
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}
	
}
