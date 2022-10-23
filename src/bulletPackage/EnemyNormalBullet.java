package bulletPackage;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import assetsPackage.Door;
import assetsPackage.Wall;
import gamePackage.GameCamera;
import gamePackage.GameObjectController;
import imagePackage.ImageLoader;

public class EnemyNormalBullet extends NormalBullet {

	public static final int DAMAGE = 30;

	private BufferedImage enemyNormalBulletImage;
	
	private float distance;
	
	public EnemyNormalBullet(float x, float y, float mx, float my, ImageLoader imageLoader, GameObjectController objectController) {
		super(x, y, mx, my, imageLoader, objectController);
		enemyNormalBulletImage = imageLoader.getEnemyNormalBulletImage();
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
		g.drawImage(enemyNormalBulletImage, (int)x, (int)y, (int)x+Bullet.SIZE, (int)y+Bullet.SIZE, 0, 0, enemyNormalBulletImage.getWidth(), enemyNormalBulletImage.getHeight(), null);
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, Bullet.SIZE, Bullet.SIZE);
	}

	protected void wallCollision() {
		for(Wall i : objectController.wallList) {
			if(getBounds().intersects(i.getBounds())) {
				objectController.removeEnemyBullet(this);
			}
		}
	}
	
	protected void doorCollision() {
		for(Door i : objectController.doorList) {
			if(getBounds().intersects(i.getBounds())) {
				objectController.removeEnemyBullet(this);
			}
		}
	}
}