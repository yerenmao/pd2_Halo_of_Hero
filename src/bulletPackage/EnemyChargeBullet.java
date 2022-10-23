package bulletPackage;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import enemyPackage.BasicEnemy;
import enemyPackage.BossEnemy;
import enemyPackage.Enemy;
import enemyPackage.MultiShootingEnemyRevolve;
import gamePackage.GameObjectController;
import imagePackage.ImageLoader;
import playerPackage.Player;

public class EnemyChargeBullet extends EnemyNormalBullet {

	public static final int DAMAGE = 20;
	
	private BufferedImage enemyChargeBulletImage;
	private Player player;
	private MultiShootingEnemyRevolve enemy;
	private BossEnemy boss;
	
	private int dischargeCount = 0;
	private int dischargeNumber = 100;
	private boolean discharge = false;
	private int c = 0;
	
	private int type;
	
	private float delX, delY;
	
	public EnemyChargeBullet(float x, float y, float mx, float my, ImageLoader imageLoader, GameObjectController objectController, Player player, MultiShootingEnemyRevolve enemy, float delX, float delY, int type) {
		super(x, y, mx, my, imageLoader, objectController);
		this.player = player;
		this.enemy = enemy;
		this.delX = delX;
		this.delY = delY;
		this.type = type;
		enemyChargeBulletImage = imageLoader.getEnemyChargeBulletImage();
	}
	
	public EnemyChargeBullet(float x, float y, float mx, float my, ImageLoader imageLoader, GameObjectController objectController, Player player, BossEnemy boss, float delX, float delY, int type) {
		super(x, y, mx, my, imageLoader, objectController);
		this.player = player;
		this.boss = boss;
		this.delX = delX;
		this.delY = delY;
		this.type = type;
		enemyChargeBulletImage = imageLoader.getEnemyChargeBulletImage();
	}
	
	public void tick() {
		for(int i = 0; i < objectController.enemyList.size(); i++) {
			if(objectController.enemyList.get(i).equals(enemy)) {
				break;
			}
			if(i >= objectController.enemyList.size()-1) {
				objectController.enemyBulletList.remove(this);
			}
		}
		
		if(enemy instanceof MultiShootingEnemyRevolve && enemy != null) {
			if(type == 1) {
				if(dischargeCount < dischargeNumber) {
					x = enemy.getX() + Enemy.WIDTH/2 + delX - Bullet.SIZE/2;
					y = enemy.getY() + Enemy.HEIGHT/2 + delY - Bullet.SIZE/2;
					if(dischargeCount == dischargeNumber-1) {
						setSpeed(player);
					}
					dischargeCount++;
				} else {
					x += velx;
					y += vely;
					edgeDetection();
					doorCollision();
				}
			}
			if(type == 2) {
				if(enemy.discharge && c == 0) {
					discharge(); c++;
				}
				if(!discharge) {
					x = enemy.getX() + Enemy.WIDTH/2 + delX - Bullet.SIZE/2;
					y = enemy.getY() + Enemy.HEIGHT/2 + delY - Bullet.SIZE/2;
				} else {
					x += velx;
					y += vely;
					edgeDetection();
					doorCollision();
				}
			}
		}
		if(boss instanceof BossEnemy && boss != null) {
			if(type == 1) {
				if(dischargeCount < dischargeNumber) {
					x = boss.getX() + BossEnemy.WIDTH/2 + delX - Bullet.SIZE/2;
					y = boss.getY() + BossEnemy.HEIGHT/2 + delY - Bullet.SIZE/2;
					if(dischargeCount == dischargeNumber-1) {
						setSpeed(player);
					}
					dischargeCount++;
				} else {
					x += velx;
					y += vely;
					edgeDetection();
					doorCollision();
				}
			}
			if(type == 2) {
				if(boss.attackMode4Discharge && c == 0) {
					discharge(); c++;
				}
				if(!discharge) {
					x = boss.getX() + BossEnemy.WIDTH/2 + delX - Bullet.SIZE/2;
					y = boss.getY() + BossEnemy.HEIGHT/2 + delY - Bullet.SIZE/2;
				} else {
					x += velx;
					y += vely;
					edgeDetection();
					doorCollision();
				}
			}
		}
	}
	
	private void edgeDetection() {
		float deltaX = player.getX() - x; float deltaY = player.getY() - y;
		float d = (float) Math.sqrt(deltaX*deltaX+deltaY*deltaY);
		if(d > 2000) objectController.removeEnemyBullet(this);
	}
	
	public void discharge() {
		setSpeed(player);
		discharge = true;
	}
	
	public void render(Graphics g) {
		g.drawImage(enemyChargeBulletImage, (int)x, (int)y, (int)x+Bullet.SIZE, (int)y+Bullet.SIZE, 0, 0, enemyChargeBulletImage.getWidth(), enemyChargeBulletImage.getHeight(), null);
	}
	
	protected void setSpeed(Player player) {
		float bx = x + Bullet.SIZE;
		float by = y + Bullet.SIZE;
		float px = player.getX() + Player.WIDTH/2;
		float py = player.getY() + Player.HEIGHT/2;
		float distance = (float) Math.sqrt((bx-px)*(bx-px)+(by-py)*(by-py));
		velx = SPEED * (px-bx)/distance;
		vely = SPEED * (py-by)/distance;
	}
}
