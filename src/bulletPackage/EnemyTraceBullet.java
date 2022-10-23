package bulletPackage;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import gamePackage.GameObjectController;
import imagePackage.ImageLoader;
import playerPackage.Player;

public class EnemyTraceBullet extends EnemyNormalBullet{

	public static final int DAMAGE = 50;
	
	private Player player;
	private BufferedImage enemyTraceBulletImage;
	
	public EnemyTraceBullet(float x, float y, float mx, float my, ImageLoader imageLoader, GameObjectController objectController, Player player) {
		super(x, y, mx, my, imageLoader, objectController);
		enemyTraceBulletImage = imageLoader.getEnemyTraceBulletImage();
		this.player = player;
	}
	
	public void tick() {
		
		float diffX = x+Bullet.SIZE/2 - (player.getX()+Player.WIDTH/2);
		float diffY = y+Bullet.SIZE/2 - (player.getY()+Player.HEIGHT/2);
		float distance = (float)Math.sqrt( diffX*diffX + diffY*diffY );
		velx = (float)((-1.0/distance) * diffX) * 4.0f;
		vely = (float)((-1.0/distance) * diffY) * 4.0f;
		x += velx;
		y += vely;
		wallCollision();
		doorCollision();
	}
	
	public void render(Graphics g) {
		g.drawImage(enemyTraceBulletImage, (int)x, (int)y, (int)x+Bullet.SIZE, (int)y+Bullet.SIZE, 0, 0, enemyTraceBulletImage.getWidth(), enemyTraceBulletImage.getHeight(), null);
	}
}
