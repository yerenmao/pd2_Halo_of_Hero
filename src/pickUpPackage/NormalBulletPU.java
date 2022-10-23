package pickUpPackage;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import bulletPackage.Bullet;
import gamePackage.GameObjectController;
import imagePackage.ImageLoader;

public class NormalBulletPU extends PickUp {
	
	private BufferedImage normalBulletImage;

	public NormalBulletPU(int x, int y, ImageLoader imageLoader, GameObjectController objectController) {
		super(x, y, imageLoader, objectController);
		normalBulletImage = imageLoader.getNormalBulletImage();
	}

	public void tick() {

	}

	public void render(Graphics g) {
		g.drawImage(normalBulletImage, x, y, x+Bullet.SIZE, y+Bullet.SIZE, 0, 0, normalBulletImage.getWidth(), normalBulletImage.getHeight(), null);
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, Bullet.SIZE, Bullet.SIZE);
	}

}
