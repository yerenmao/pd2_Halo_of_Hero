package enemyPackage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import assetsPackage.Door;
import assetsPackage.Wall;
import bulletPackage.Bullet;
import bulletPackage.CurvedBullet;
import bulletPackage.EnemyBounceBullet;
import effectPackage.Animation;
import gamePackage.GameObject;
import gamePackage.GameObjectController;
import gamePackage.GameSetting;
import imagePackage.ImageLoader;
import playerPackage.Player;

public class TraceEnemy extends BasicEnemy {

	private int count;
	private int timeToChange = 500;
	
	protected BufferedImage burnImage;
	protected int showBurnTime = 0;
	protected int burn_mark = 0;
	protected long start_sc;
	protected long time = 0;
	
	private int hp = 3000;
	private int hpOnScreen = hp;
	private final int totalHp = hp;
	
	private Animation animation;
	
	public TraceEnemy(float x, float y, ImageLoader imageLoader, GameObjectController objectController, Player player) {
		super(x, y, imageLoader, objectController, player);
		burnImage = imageLoader.getCurvedBulletImage();
		this.player = player;
		this.player = player;
	}
	
	protected void setAnimation(ImageLoader imageLoader) {
		animation = new Animation(imageLoader.getTraceEnemyImages(), 4);
	}

	public void tick() {
		count++;
		if(count == timeToChange) {
			count = 0;
			attack();
		}
		tickHpOnScreen();
		if(hpOnScreen <= 0) {
			objectController.enemyList.remove(this);
			dropPU();
		}
		float diffX = x - (player.getX());
		float diffY = y - (player.getY());
		float distance = (float)Math.sqrt( diffX*diffX + diffY*diffY );
		velx = (float)((-1.0/distance) * diffX) * 2.0f;
		vely = (float)((-1.0/distance) * diffY) * 2.0f;
		
		x += velx;
		y += vely;
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
			if(hpOnScreen - hp > 10) hpOnScreen -= 10;
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
		objectController.addEnemyBullet(new EnemyBounceBullet(x+12, y+12, player.getX()+12, player.getY()+12, imageLoader, objectController));
	}
	
	protected void wallCollision() {
		for(Wall i : objectController.wallList) {
			if(getHorizontalBounds().intersects(i.getBounds())) {
			 	if(velx > 0 && x < i.getX()) {
			 		velx = 0;
			 		x = i.getX() - WIDTH;
			 	} else if(velx < 0 && x > i.getX()) {
			 		velx = 0;
			 		x = i.getX() + Wall.WIDTH;
			 	}
			}
			if(getVerticalBounds().intersects(i.getBounds())) {
				if(vely > 0 && y < i.getY()) {
			 		vely = 0;
			 		y = i.getY() - HEIGHT;
			 	} else if(vely < 0 && y > i.getY()) {
			 		vely = 0;
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
				hp -= 100;
			}
		}
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, Enemy.WIDTH, Enemy.HEIGHT);
	}
}
