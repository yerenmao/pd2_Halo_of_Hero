package bulletPackage;

import assetsPackage.Door;
import assetsPackage.Wall;
import gamePackage.GameCamera;
import gamePackage.GameObjectController;
import imagePackage.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TrapBullet extends NormalBullet {
	
	private int duration;
	
	private static BufferedImage trapBulletImage;
	private long start_sec;

	public TrapBullet(float x, float y, float mx, float my, ImageLoader imageLoader, GameObjectController objectController, long start_sec, int duration) {
		super(x, y, mx, my, imageLoader, objectController);
		trapBulletImage = imageLoader.getTrapBulletImage();
		this.start_sec = start_sec;
		this.duration = duration;
	}

	public void tick() {
		if (System.currentTimeMillis() >= start_sec+duration){
			objectController.removePlayerBullet(this);
		}
	}

	public void render(Graphics g) {
		g.drawImage(trapBulletImage, (int)x, (int)y, (int)x+Bullet.SIZE, (int)y+Bullet.SIZE, 0, 0, trapBulletImage.getWidth(), trapBulletImage.getHeight(), null);
	}
}