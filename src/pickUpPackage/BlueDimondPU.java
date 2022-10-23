package pickUpPackage;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import gamePackage.GameObjectController;
import imagePackage.ImageLoader;

public class BlueDimondPU extends PickUp{
	
	private final int WIDTH = 64, HEIGHT = 64;
	
	private BufferedImage dimondImage;
	
	public BlueDimondPU(int x, int y, ImageLoader imageLoader, GameObjectController objectController) {
		super(x, y, imageLoader, objectController);
		dimondImage = imageLoader.getBlueDimondImage();
	}

	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		g.drawImage(dimondImage, x, y, x+WIDTH, y+HEIGHT, 0, 0, dimondImage.getWidth(), dimondImage.getHeight(), null);
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x+20, y+20, WIDTH-40, HEIGHT-40);
	}
}
