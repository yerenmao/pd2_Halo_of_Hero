package enemyPackage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import bulletPackage.Bullet;
import bulletPackage.CurvedBullet;
import bulletPackage.EnemyChargeBullet;
import effectPackage.Animation;
import gamePackage.GameObject;
import gamePackage.GameObjectController;
import gamePackage.GameSetting;
import imagePackage.ImageLoader;
import pickUpPackage.DimondPU;
import playerPackage.Player;

public class MultiShootingEnemyRevolve extends MultiShootingEnemy {
	
	public boolean discharge = false;
	
	private int speed = 3;
	private int count = 0;
	
	protected BufferedImage burnImage;
	protected int showBurnTime = 0;
	protected int burn_mark = 0;
	protected long start_sc;
	protected long time = 0;
	
	private int hp = 1500;
	private int hpOnScreen = hp;
	private final int totalHp = hp;
	
	private int timeToChange;
	private Random r = new Random();
	private Animation animation;
	private boolean attacking = false;
	private int attackingCount = 0;
	private int cd = 100;
	private int cdCount = 0;
	private float radius = 50;
	private double angle = Math.PI/2;
	private int type = 1;
	
	public MultiShootingEnemyRevolve(float x, float y, ImageLoader imageLoader, GameObjectController objectController, Player player) {
		super(x, y, imageLoader, objectController, player);
		burnImage = imageLoader.getCurvedBulletImage();
		timeToChange = 50 * r.nextInt(2,5);
		setAnimation(imageLoader);
	}

	protected void setAnimation(ImageLoader imageLaoder) {
		animation = new Animation(imageLoader.getMultiShootingEnemyRevolveImages(), 12);
	}
	
	public void tick() {
		if(discharge) discharge = false;
		tickHpOnScreen();
		if(hpOnScreen <= 0) {
			objectController.enemyList.remove(this);
			dropPU();
		}
		x += velx;
		y += vely;
		count++;
		
		if(cdCount > 0 && !attacking) cdCount--;
		if(attacking) {
			attackingCount++;
			if(attackingCount == 10*8+9) {
				attackingCount = 0;
				angle = Math.PI/2;
				discharge = true;
				attacking = false;
				if(type == 1) type = 2;
				else type = 1;
				cdCount = cd;
			}
			if(attacking && attackingCount % 10 == 0) {
				float delX = radius * (float)Math.cos(angle);
				float delY = radius * (float)Math.sin(angle);
				float ix = x + Enemy.WIDTH/2 + delX - Bullet.SIZE/2;
				float iy = y + Enemy.HEIGHT/2 + delY - Bullet.SIZE/2;
				objectController.addEnemyBullet(new EnemyChargeBullet(ix, iy, 0, 0, imageLoader, objectController, player, this, delX, delY, type));
				angle += Math.PI/4;
			}
		}
		
		if(count == timeToChange) {
			count = 0;
			timeToChange = 50 * r.nextInt(2,5);
			velx = (float)(speed*Math.random());
			vely = (float)(speed*Math.random());
			if(r.nextInt(2) == 0) velx *= (-1);
			if(r.nextInt(2) == 0) vely *= (-1);
			if(cdCount == 0) {
				if(!attacking && r.nextInt(2) == 0) {
					attack(); attacking = true;
				}
			}
		}
		animation.tick();
		wallCollision();
		doorCollision();
		playerBulletCollision();
		
		if(showBurnTime > 0) showBurnTime--;
		if(burn_mark > 0 && System.currentTimeMillis() >= start_sc +  1000*time) {
			showBurnTime = 20;
			hp -= CurvedBullet.BurnDamage[CurvedBullet.Burnlevel];
			burn_mark -= 1;
			time += 1;
		}
	}
	
	private void tickHpOnScreen() {
		if(hpOnScreen > hp) {
			if(hpOnScreen - hp > 20) hpOnScreen -= 20;
			else if(hpOnScreen - hp > 10) hpOnScreen -= 10;
			else if(hpOnScreen - hp > 5) hpOnScreen -= 5;
			else hpOnScreen--;
		}
	}
	
	public void render(Graphics g) {
		if(GameSetting.showEnemyCollisionBox) {
			Rectangle recH = getHorizontalBounds();
			Rectangle recV = getVerticalBounds();
			g.setColor(Color.red);
			g.drawRect(recH.x, recH.y, recH.width, recH.height);
			g.drawRect(recV.x, recV.y, recV.width, recV.height);
		}
		drawHUD(g, hpOnScreen, totalHp);
		g.drawImage(animation.getImage(), (int)x, (int)y, (int)x+Enemy.WIDTH, (int)y+Enemy.HEIGHT, 0, 0, animation.getImage().getWidth(), animation.getImage().getHeight(), null);
		if(showBurnTime > 0) {
			g.drawImage(burnImage, (int)x+5, (int)y+5, Bullet.SIZE+10, Bullet.SIZE+10, null);
		}
	}
	
	protected void playerBulletCollision() {
		for(int i = 0; i < objectController.playerBulletList.size(); i++) {
			GameObject tempObject = objectController.playerBulletList.get(i);
			if(tempObject.getBounds().intersects(getBounds())) {
				if(tempObject instanceof CurvedBullet) {
					start_sc = System.currentTimeMillis();
					time = 1;
					burn_mark = CurvedBullet.Burntime[CurvedBullet.Burnlevel];
				}
				objectController.playerBulletList.remove(tempObject);
				hp -= 200;
			}
		}
	}
	
	protected void attack() {
		if(cdCount <= 0) {
			attacking = true;
			discharge = false;
		}
			
	}
}
