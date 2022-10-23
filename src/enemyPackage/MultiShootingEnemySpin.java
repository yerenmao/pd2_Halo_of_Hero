package enemyPackage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import bulletPackage.Bullet;
import bulletPackage.CurvedBullet;
import bulletPackage.EnemyNormalBullet;
import effectPackage.Animation;
import gamePackage.GameObject;
import gamePackage.GameObjectController;
import gamePackage.GameSetting;
import imagePackage.ImageLoader;
import pickUpPackage.DimondPU;
import playerPackage.Player;

public class MultiShootingEnemySpin extends MultiShootingEnemy {

	private int speed = 3;
	private int count = 0;
	
	protected BufferedImage burnImage;
	protected int showBurnTime = 0;
	protected int burn_mark = 0;
	protected long start_sc;
	protected long time = 0;
	
	private int hp = 2500;
	private int hpOnScreen = hp;
	private final int totalHp = hp;
	
	private int timeToChange;
	private Random r = new Random();
	private Animation animation;
	private boolean attacking = false;
	private int attackingCount = 0;
	private float ix = 0;
	private float iy = 0;
	private float distance = 0;
	private double theta = 0;
	private double delta = 0;
	
	public MultiShootingEnemySpin(float x, float y, ImageLoader imageLoader, GameObjectController objectController, Player player) {
		super(x, y, imageLoader, objectController, player);
		burnImage = imageLoader.getCurvedBulletImage();
		timeToChange = 50 * r.nextInt(2,5);
		setAnimation(imageLoader);
	}
	
	protected void setAnimation(ImageLoader imageLaoder) {
		animation = new Animation(imageLoader.getMultiShootingEnemySpinImages(), 12);
	}
	
	public void tick() {
		tickHpOnScreen();
		if(hpOnScreen <= 0) {
			objectController.enemyList.remove(this);
			dropPU();
		}
		x += velx;
		y += vely;
		count++;
		
		if(attacking) {
			attackingCount++;
			if(attackingCount == 4*25) {
				attackingCount = 0;
				attacking = false;
			}
			if(attacking && attackingCount % 4 == 0) {
				delta++;
				double s = theta + (Math.PI/12 * delta);
				ix = x+Bullet.SIZE/2;
				iy = y+Bullet.SIZE/2;
				shootBullet(ix, iy, distance, s);
			}
		}
		
		if(count == timeToChange) {
			count = 0;
			timeToChange = 50 * r.nextInt(2,5);
			velx = (float)(speed*Math.random()) + 1;
			vely = (float)(speed*Math.random()) + 1;
			if(r.nextInt(2) == 0) velx *= (-1);
			if(r.nextInt(2) == 0) vely *= (-1);
			if(!attacking && r.nextInt(2) == 0) attack();
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
		ix = x+Bullet.SIZE/2;
		iy = y+Bullet.SIZE/2;
		float fx = player.getX()+Bullet.SIZE/2;
		float fy = player.getY()+Bullet.SIZE/2;
		distance = (float) Math.sqrt((ix-fx)*(ix-fx)+(iy-fy)*(iy-fy));
		
		if(r.nextInt(2) == 0) {
			attacking = true;	
			double tan = (iy-fy)/(fx-ix);
			theta = Math.atan(tan);
			if(fx < ix) {
				theta = Math.atan(tan) + Math.PI;
			}
		} else {
			for(int i = 0; i < 12; i++) {
				shootBullet(ix, iy, distance, Math.PI/6f*i);
			}
		}
	}
	
	private void shootBullet(float ix, float iy, double distance, double s) {
		float nfx = ix + (float)(distance*Math.cos(s));
		float nfy = iy - (float)(distance*Math.sin(s));
		objectController.addEnemyBullet(new EnemyNormalBullet(ix, iy, nfx, nfy, imageLoader, objectController));
	}
}
