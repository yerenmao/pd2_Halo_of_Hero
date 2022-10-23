package bulletPackage;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import assetsPackage.Door;
import assetsPackage.Wall;
import gamePackage.GameCamera;
import gamePackage.GameObject;
import gamePackage.GameObjectController;
import imagePackage.ImageLoader;

public class NormalBullet extends Bullet {


	private static BufferedImage normalBulletImage;
	
	private float distance;
	
	public NormalBullet(float x, float y, float mx, float my, ImageLoader imageLoader, GameObjectController objectController) {
		super(x, y, mx, my, imageLoader, objectController);
		normalBulletImage = imageLoader.getNormalBulletImage();
		setSpeed();
	}
	
	public void tick() {
		x += velx;
		y += vely;
		wallCollision();
		doorCollision();
	}
	
	private void setSpeed() {
		distance = (float) Math.sqrt((x-mx)*(x-mx)+(y-my)*(y-my));
		velx = SPEED * (mx-x)/distance;
		vely = SPEED * (my-y)/distance;
	}

	public void render(Graphics g) {
		g.drawImage(normalBulletImage, (int)x, (int)y, (int)x+Bullet.SIZE, (int)y+Bullet.SIZE, 0, 0, normalBulletImage.getWidth(), normalBulletImage.getHeight(), null);
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, Bullet.SIZE, Bullet.SIZE);
	}

	protected void wallCollision() {
		for(Wall i : objectController.wallList) {
			if(getBounds().intersects(i.getBounds())) {
				objectController.removePlayerBullet(this);
			}
		}
	}
	
	protected void doorCollision() {
		for(Door i : objectController.doorList) {
			if(getBounds().intersects(i.getBounds())) {
				objectController.removePlayerBullet(this);
			}
		}
	}
}
