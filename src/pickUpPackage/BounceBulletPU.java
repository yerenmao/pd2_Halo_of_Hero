package pickUpPackage;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import bulletPackage.Bullet;
import gamePackage.GameObjectController;
import imagePackage.ImageLoader;

public class BounceBulletPU extends PickUp{

	private BufferedImage bounceBulletImage;

	public BounceBulletPU(int x, int y, ImageLoader imageLoader, GameObjectController objectController) {
		super(x, y, imageLoader, objectController);
		bounceBulletImage = imageLoader.getBounceBulletImage();
	}

	public void tick() {
		
	}

	public void render(Graphics g) {
		g.drawImage(bounceBulletImage, x, y, x+Bullet.SIZE, y+Bullet.SIZE, 0, 0, bounceBulletImage.getWidth(), bounceBulletImage.getHeight(), null);
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, Bullet.SIZE, Bullet.SIZE);
	}
}
