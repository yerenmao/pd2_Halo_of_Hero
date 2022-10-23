package pickUpPackage;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import bulletPackage.Bullet;
import gamePackage.GameObjectController;
import imagePackage.ImageLoader;
import playerPackage.Player;

public class BulletPU extends PickUp {

	private Player player;
	
	private BufferedImage leftKeyBulletImage;
	private BufferedImage rightKeyBulletImage;
	
	private boolean limitTime = false;
	private boolean spawn = false;
	private long spawnTime;
	private boolean showImage = true;
	private int count = 0;
	
	public static enum Key {
		Left(),
		Right(),
	}
	
	private Key key;
	
	public BulletPU(int x, int y, ImageLoader imageLoader, GameObjectController objectController, Player player, Key key) {
		super(x, y, imageLoader, objectController);
		this.player = player;
		this.key = key;
		leftKeyBulletImage = player.getLeftKeyBulletImage();
		rightKeyBulletImage = player.getRightKeyBulletImage();
	}
	
	public void addBullet() {
		switch(key) {
		case Left: player.setLeftKeyBulletAmount(player.getLeftKeyBulletAmount()+1); break;
		case Right: player.setRightKeyBulletAmount(player.getRightKeyBulletAmount()+1); break;
		default: break;
		}
	}

	public BulletPU(int x, int y, ImageLoader imageLoader, GameObjectController objectController, Player player, Key key, boolean limitTime) {
		this(x, y, imageLoader, objectController, player, key);
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
		switch(key) {
		case Left: g.drawImage(leftKeyBulletImage, x, y, x+Bullet.SIZE, y+Bullet.SIZE, 0, 0, leftKeyBulletImage.getWidth(), leftKeyBulletImage.getHeight(), null); break;
		case Right: g.drawImage(rightKeyBulletImage, x, y, x+Bullet.SIZE, y+Bullet.SIZE, 0, 0, rightKeyBulletImage.getWidth(), rightKeyBulletImage.getHeight(), null); break;
		default: break;
		}
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, Bullet.SIZE, Bullet.SIZE);
	}
}
