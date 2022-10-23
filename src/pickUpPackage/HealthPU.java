package pickUpPackage;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import gamePackage.GameObjectController;
import imagePackage.ImageLoader;

public class HealthPU extends PickUp{
	
	private final int WIDTH = 32, HEIGHT = 32;
	
	private BufferedImage healthImage;
	
	private boolean limitTime = false;
	private boolean spawn = false;
	private long spawnTime;
	private boolean showImage = true;
	private int count = 0;
	
	public HealthPU(int x, int y, ImageLoader imageLoader, GameObjectController objectController) {
		super(x, y, imageLoader, objectController);
		healthImage = imageLoader.getUpgradeHpImage();
	}

	
	public HealthPU(int x, int y, ImageLoader imageLoader, GameObjectController objectController, boolean limitTime) {
		super(x, y, imageLoader, objectController);
		healthImage = imageLoader.getDimondImage();
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
		g.drawImage(healthImage, x, y, x+WIDTH, y+HEIGHT, 0, 0, healthImage.getWidth(), healthImage.getHeight(), null);
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}
}
