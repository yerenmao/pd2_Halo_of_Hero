package bulletPackage;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import assetsPackage.Door;
import assetsPackage.Wall;
import gamePackage.GameCamera;
import gamePackage.GameObjectController;
import imagePackage.ImageLoader;

public class BounceBullet extends NormalBullet {
	
	private int level;
	private int[] life = {3, 5, 7, 9};
	private static BufferedImage bounceBulletImage;
	
	public BounceBullet(float x, float y, float mx, float my, ImageLoader imageLoader, GameObjectController objectController, int level) {
		super(x, y, mx, my, imageLoader, objectController);
		bounceBulletImage = imageLoader.getBounceBulletImage();
		this.level = level;
	}
	
	public void tick() {
		x += velx;
		y += vely;
		wallCollision();
		doorCollision();
	}

	public void render(Graphics g) {
		g.drawImage(bounceBulletImage, (int)x, (int)y, (int)x+Bullet.SIZE, (int)y+Bullet.SIZE, 0, 0, bounceBulletImage.getWidth(), bounceBulletImage.getHeight(), null);
	}
	
	protected void wallCollision() {
		for(Wall i : objectController.wallList) {
			if(getHorizontalBounds().intersects(i.getBounds())) {
				if(life[level] == 1) objectController.removePlayerBullet(this);
				life[level]--;
				velx *= (-1);
			}
			if(getVerticalBounds().intersects(i.getBounds())) {
				if(life[level] == 1) objectController.removePlayerBullet(this);
				life[level]--;
				vely *= (-1);
			}
		}
	}
	
	protected void doorCollision() {
		for(Door i : objectController.doorList) {
			if(getHorizontalBounds().intersects(i.getBounds())) {
				if(life[level] == 1) objectController.removePlayerBullet(this);
				life[level]--;
				velx *= (-1);
			}
			if(getVerticalBounds().intersects(i.getBounds())) {
				if(life[level] == 1) objectController.removePlayerBullet(this);
				life[level]--;
				vely *= (-1);
			}
		}
	}
	
	// for horizontal collision
	public Rectangle getHorizontalBounds() {
		float bx = x + velx;
		float by = y+5;
		float bw = Bullet.SIZE; 
		float bh = Bullet.SIZE-10;
		return new Rectangle((int)bx, (int)by, (int)bw, (int)bh); 
	}
	
	// for vertical collision
	public Rectangle getVerticalBounds() {
		float bx = x+5;
		float by = y + vely;
		float bw = Bullet.SIZE-10; 
		float bh = Bullet.SIZE;
		return new Rectangle((int)bx, (int)by, (int)bw, (int)bh);
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, Bullet.SIZE, Bullet.SIZE);
	}
}
