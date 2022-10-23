package enemyPackage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import assetsPackage.Door;
import assetsPackage.Wall;
import bulletPackage.Bullet;
import bulletPackage.CurvedBullet;
import bulletPackage.EnemyBounceBullet;
import bulletPackage.EnemyNormalBullet;
import effectPackage.Animation;
import gamePackage.GameObject;
import gamePackage.GameObjectController;
import gamePackage.GameSetting;
import imagePackage.ImageLoader;
import playerPackage.Player;

public class BasicEnemy extends Enemy {
	
	private int speed = 3;
	private int count = 0;
	
	//////////
	protected BufferedImage burnImage;
	protected int showBurnTime = 0;
	protected int burn_mark = 0;
	protected long start_sc;
	protected long time = 0;
	//////////
	
	private int hp = 1000;
	private int hpOnScreen = hp;
	private final int totalHp = hp;
	
	private int timeToChange;
	private Random r = new Random();
	protected Player player;
	
	public BasicEnemy(float x, float y, ImageLoader imageLoader, GameObjectController objectController, Player player) {
		super(x, y, imageLoader, objectController);
		this.player = player;
		velx = (float)(speed*Math.random()) + 1;
		vely = (float)(speed*Math.random()) + 1;
		burnImage = imageLoader.getCurvedBulletImage();
		timeToChange = 50 * r.nextInt(2,5);
		setAnimation(imageLoader);
	}
	
	protected void setAnimation(ImageLoader imageLoader) {
		animation = new Animation(imageLoader.getBasicEnemyImages(), 6);
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
		if(count == timeToChange) {
			count = 0;
			timeToChange = 50 * r.nextInt(2,5);
			velx = (float)(speed*Math.random()) + 1;
			vely = (float)(speed*Math.random()) + 1;
			if(r.nextInt(2) == 0) velx *= (-1);
			if(r.nextInt(2) == 0) vely *= (-1);
			if(r.nextInt(2) == 0) attack();
		}
		animation.tick();
		wallCollision();
		doorCollision();
		playerBulletCollision();
		
		//////////
		if(showBurnTime > 0) showBurnTime--;
		if(burn_mark > 0 && System.currentTimeMillis() >= start_sc +  1000*time) {
			showBurnTime = 20;
			hp -= CurvedBullet.BurnDamage[CurvedBullet.Burnlevel];
			burn_mark -= 1;
			time += 1;
		}
		//////////
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
	
	protected void attack() {
		if(r.nextInt(2) == 0)
			objectController.addEnemyBullet(new EnemyNormalBullet(x+12, y+12, player.getX()+12, player.getY()+12, imageLoader, objectController));
		else
			objectController.addEnemyBullet(new EnemyBounceBullet(x+12, y+12, player.getX()+12, player.getY()+12, imageLoader, objectController));
	}
	
	protected void wallCollision() {
		for(Wall i : objectController.wallList) {
			if(getHorizontalBounds().intersects(i.getBounds())) {
				if(velx > 0 && x < i.getX()) {
					x = i.getX() - Enemy.WIDTH;
					velx *= (-1);
			 	} else if(velx < 0 && x > i.getX()) {
			 		x = i.getX() + Wall.WIDTH;
			 		velx *= (-1);
			 	}
			}
			if(getVerticalBounds().intersects(i.getBounds())) {
				if(vely > 0 && y < i.getY()) {
					y = i.getY() - Enemy.HEIGHT;
			 		vely *= (-1);
			 	} else if(vely < 0 && y > i.getY()) {
			 		vely *= (-1);
			 		y = i.getY() + Wall.HEIGHT;
			 	}
			}
		}
	}
	
	protected void doorCollision() {
		for(Door i : objectController.doorList) {
			if(getVerticalBounds().intersects(i.getBounds())) {
			 	if(vely < 0 && y > i.getY()) {
			 		vely = 0;
			 		y = i.getY() + Door.HEIGHT;
			 	}
			}
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
	
	// for horizontal collision
	protected Rectangle getHorizontalBounds() {
		float bx = x + velx;
		float by = y+5;
		float bw = Enemy.WIDTH; 
		float bh = Enemy.HEIGHT-10;
		return new Rectangle((int)bx, (int)by, (int)bw, (int)bh); 
	}
	
	// for vertical collision
	protected Rectangle getVerticalBounds() {
		float bx = x+5;
		float by = y + vely;
		float bw = Enemy.WIDTH-10; 
		float bh = Enemy.HEIGHT;
		return new Rectangle((int)bx, (int)by, (int)bw, (int)bh);
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, Enemy.WIDTH, Enemy.HEIGHT);
	}

}
