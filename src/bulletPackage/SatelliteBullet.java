package bulletPackage;

import assetsPackage.Door;
import assetsPackage.Wall;
import gamePackage.GameCamera;
import gamePackage.GameObjectController;
import imagePackage.ImageLoader;
import playerPackage.Player;

import java.awt.*;
import java.awt.image.BufferedImage;


public class SatelliteBullet extends NormalBullet {

	private int radius;
	
	private static BufferedImage satelliteBulletImage;

	private Player player;
	private long start_sec;

	public SatelliteBullet(float x, float y, float mx, float my, ImageLoader imageLoader, GameObjectController objectController, Player player, long start_sec, int radius) {
		super(x, y, mx, my, imageLoader, objectController);
		satelliteBulletImage = imageLoader.getSatelliteBulletImage();
		this.player = player;
		this.start_sec = start_sec;
		this.radius = radius;
	}


	public void tick() {
		double sec = System.currentTimeMillis() - start_sec;
		x= (float) (player.getX() + Player.WIDTH/2 - Bullet.SIZE/2 + radius * Math.cos(sec/100));
		y= (float) (player.getY() + Player.HEIGHT/2 - Bullet.SIZE/2 + radius * Math.sin(sec/100));
	}

	public void render(Graphics g) {
		g.drawImage(satelliteBulletImage, (int)x, (int)y, (int)x+Bullet.SIZE, (int)y+Bullet.SIZE, 0, 0, satelliteBulletImage.getWidth(), satelliteBulletImage.getHeight(), null);
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