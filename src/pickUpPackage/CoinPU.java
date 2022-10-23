package pickUpPackage;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import gamePackage.GameObjectController;
import imagePackage.ImageLoader;

public class CoinPU extends PickUp {

	public static final int WIDTH = 48, HEIGHT = 48;
	
	public static final int inGameWIDTH = 24, inGameHEIGHT = 24;
	
	private boolean limitTime = false;
	private boolean spawn = false;
	private long spawnTime;
	private BufferedImage coinImage;
	private boolean showImage = true;
	private int count = 0;
	
	public CoinPU(int x, int y, ImageLoader imageLoader, GameObjectController objectController, boolean limitTime) {
		super(x, y, imageLoader, objectController);
		coinImage = imageLoader.getCoinImage();
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
		if(showImage) g.drawImage(coinImage, x, y, x+inGameWIDTH, y+inGameHEIGHT, 0, 0, coinImage.getWidth(), coinImage.getHeight(), null);
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, inGameWIDTH, inGameHEIGHT);
	}
	
	
}
