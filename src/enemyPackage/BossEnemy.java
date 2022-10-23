package enemyPackage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import bulletPackage.Bullet;
import bulletPackage.CurvedBullet;
import bulletPackage.EnemyBounceBullet;
import bulletPackage.EnemyChargeBullet;
import bulletPackage.EnemyNormalBullet;
import bulletPackage.EnemyTraceBullet;
import effectPackage.Animation;
import effectPackage.Transparent;
import gamePackage.GameObject;
import gamePackage.GameObjectController;
import gamePackage.GameSetting;
import imagePackage.ImageLoader;
import pickUpPackage.BlueDimondPU;
import pickUpPackage.BulletPU;
import pickUpPackage.HealthPU;
import playerPackage.Player;

public class BossEnemy extends BasicEnemy {
	
	private int speed = 4;
	private int count = 0;
	
	public static final int WIDTH = 90, HEIGHT = 90;
	
	protected BufferedImage burnImage;
	protected BufferedImage angryImage;
	protected int showBurnTime = 0;
	protected int burn_mark = 0;
	protected long start_sc;
	protected long time = 0;
	
	private int hp = 10000;
	private int hpOnScreen = hp;
	private final int totalHp = hp;
	
	private int timeToChange;
	private Random r = new Random();
	
	private boolean attackMode3Attacking = false;
	private int attackMode3Count = 0;
	private double attackMode3Theta = 0;
	private double attackMode3Delta = 0;
	private float attackMode3Distance = 0f;
	
	private boolean attackMode4Attacking = false;
	private int attackMode4Type = 0;
	private int attackMode4Count = 0;
	private double attackMode4Angle = Math.PI/2; 
	public boolean attackMode4Discharge = false;
	private final float attackMode4Radius = 80f;
	private final int attackMode4CD = 100;
	private int attackMode4CDCount = 0;
	
	private boolean invisible = false;
	private int invisibleDuration = 0;
	private long invisibleStart_sec = 0;
	private float bossAlpha = 1f;
	
	private boolean goldenHealth = true;
	private int goldenHealthDuration = r.nextInt(3, 6);
	private long goldenHealthStart_sec = 0;
	
	private static boolean moreDamage = false;
	
	private long secTick_start = System.currentTimeMillis();
	
	public BossEnemy(float x, float y, ImageLoader imageLoader, GameObjectController objectController, Player player) {
		super(x, y, imageLoader, objectController, player);
		burnImage = imageLoader.getCurvedBulletImage();
		angryImage = imageLoader.getAngryImage();
		timeToChange = 50 * r.nextInt(2,4);
		setAnimation(imageLoader);
		goTeleport();
		spawnEnemy(0);
		spawnEnemy(1);
		spawnEnemy(2);
		spawnEnemy(3);
		reset();
	}
	
	private void reset() {
		moreDamage = false;
	}
	
	protected void setAnimation(ImageLoader imageLaoder) {
		animation = new Animation(imageLoader.getBossImages(), 4);
	}
	
	private void secTick() {
		if(!invisible && r.nextInt(10) == 0) goInvisible();
		if(!goldenHealth && r.nextInt(15) == 0) goGoldenHealth();
//		if(r.nextInt(15) == 0) goTeleport();
		if(r.nextInt(20) == 0) spawnEnemy(-1);
	}
	
	public void tick() { 
		
		if(System.currentTimeMillis() - secTick_start >= 1000) {
			secTick(); secTick_start = System.currentTimeMillis();
		}
		
		if(invisible) {			
			if(bossAlpha > 0.025f) bossAlpha -= 0.025f;
			else bossAlpha = 0;
			if(System.currentTimeMillis() - invisibleStart_sec >= 1000 * invisibleDuration) invisible = false;
		} else {
			if(bossAlpha < 0.975f) bossAlpha += 0.025f;
			else bossAlpha = 1;
		}

		if(System.currentTimeMillis() - goldenHealthStart_sec >= 1000 * goldenHealthDuration) goldenHealth = false;
		
		if(hp <= 5000 && !moreDamage) moreDamage = true;
		
		tickHpOnScreen();
		if(hpOnScreen <= 0) {
			objectController.addPickUp(new BlueDimondPU((int)x, (int)y, imageLoader, objectController));
			objectController.removeEnemy(this);
		}
		x += velx;
		y += vely;
		count++;
		if(count == timeToChange) {
			count = 0;
			timeToChange = 50 * r.nextInt(2,4);
			velx = (float)(speed*Math.random()) + 1;
			vely = (float)(speed*Math.random()) + 1;
			if(r.nextInt(2) == 0) velx *= (-1);
			if(r.nextInt(2) == 0) vely *= (-1);
			if(r.nextInt(2) == 0 && (!attackMode3Attacking) && (!attackMode4Attacking)) attack(r.nextInt(5));
		}
		animation.tick();
		wallCollision();
		doorCollision();
		playerBulletCollision();
		
		attackMode3Tick();
		attackMode4Tick();
		
		if(showBurnTime > 0) showBurnTime--;
		if(burn_mark > 0 && System.currentTimeMillis() >= start_sc +  1000*time) {
			showBurnTime = 20;
			hp -= CurvedBullet.BurnDamage[CurvedBullet.Burnlevel];
			burn_mark -= 1;
			time += 1;
		}
	}
	
	private void attackMode3Tick() {
		if(attackMode3Attacking) {
			attackMode3Count++;
			if(attackMode3Count == 4*25) {
				attackMode3Count = 0;
				attackMode3Attacking = false;
			}
			if(attackMode3Attacking && attackMode3Count % 4 == 0) {
				attackMode3Delta++;
				double s = attackMode3Theta + (Math.PI/12 * attackMode3Delta);
				float ix = x+WIDTH/2-Bullet.SIZE/2;
				float iy = y+HEIGHT/2-Bullet.SIZE/2;
				shootBullet(ix, iy, attackMode3Distance, s);
			}
		}
	}
	
	private void attackMode4Tick() {
		if(attackMode4CDCount > 0 && !attackMode4Attacking) attackMode4CDCount--;
		if(attackMode4Attacking) {
			attackMode4Count++;
			if(attackMode4Count == 10*8+9) {
				attackMode4Count = 0;
				attackMode4Angle = Math.PI/2;
				attackMode4Discharge = true;
				attackMode4Attacking = false;
				attackMode4CDCount = attackMode4CD;
			}
			if(attackMode4Attacking && attackMode4Count % 10 == 0) {
				float delX = attackMode4Radius * (float)Math.cos(attackMode4Angle);
				float delY = attackMode4Radius * (float)Math.sin(attackMode4Angle);
				float ix = x + Enemy.WIDTH/2 + delX - Bullet.SIZE/2;
				float iy = y + Enemy.HEIGHT/2 + delY - Bullet.SIZE/2;
				objectController.addEnemyBullet(new EnemyChargeBullet(ix, iy, 0, 0, imageLoader, objectController, player, this, delX, delY, attackMode4Type+1));
				attackMode4Angle += Math.PI/4;
			}
		}
	}
	
	private void tickHpOnScreen() {
		if(hpOnScreen > hp) {
			if(hpOnScreen - hp > 50) hpOnScreen -= 50;
			else if(hpOnScreen - hp > 40) hpOnScreen -= 40;
			else if(hpOnScreen - hp > 30) hpOnScreen -= 30;
			else if(hpOnScreen - hp > 20) hpOnScreen -= 20;
			else if(hpOnScreen - hp > 10) hpOnScreen -= 10;
			else if(hpOnScreen - hp > 5) hpOnScreen -= 5;
			else hpOnScreen--;
		}
	}

	public void render(Graphics g) {
		
		// draw collision box
		if(GameSetting.showEnemyCollisionBox) {
			Rectangle recH = getHorizontalBounds();
			Rectangle recV = getVerticalBounds();
			g.setColor(Color.red);
			g.drawRect(recH.x, recH.y, recH.width, recH.height);
			g.drawRect(recV.x, recV.y, recV.width, recV.height);
		}
		
		Graphics2D g2d = (Graphics2D)g;
		g2d.setComposite(Transparent.makeTransparent(bossAlpha));
		// draw HUD
		drawHUD(g, hpOnScreen, totalHp, WIDTH);		
		// draw boss
		g.drawImage(animation.getImage(), (int)x, (int)y, (int)x+WIDTH, (int)y+HEIGHT, 0, 0, animation.getImage().getWidth(), animation.getImage().getHeight(), null);
		// draw angry
		if(moreDamage) g.drawImage(angryImage, (int)x,	(int)y, 36, 36, null);
		g2d.setComposite(Transparent.makeTransparent(1));
		
		if(showBurnTime > 0) {
			g.drawImage(burnImage, (int)x+15, (int)y+5, Bullet.SIZE+30, Bullet.SIZE+30, null);
		}
	}
	
	protected void drawHUD(Graphics g, int hpOnScreen, int totalHp, int width) {
		g.setColor(Color.black);
		g.drawRect((int)x+3, (int)y-10, width-6, 10);
		if(!goldenHealth && !(objectController.enemyList.size() > 1 && hp <= 3000))
			g.setColor(new Color((int)(255*(1-hpOnScreen/(float)totalHp)), (int)(255*hpOnScreen/(float)totalHp), 0));
		else
			g.setColor(new Color(255, 215, 0));
		g.fillRect((int)x+3, (int)y-10, (int)((width-6)*hpOnScreen/(float)totalHp)+1, 10);
	}
	
	protected void playerBulletCollision() {
		for(int i = 0; i < objectController.playerBulletList.size(); i++) {
			GameObject tempObject = objectController.playerBulletList.get(i);
			if(tempObject.getBounds().intersects(getBounds())) {
				if(!goldenHealth && !(objectController.enemyList.size() > 1 && hp <= 3000)) {
					if(tempObject instanceof CurvedBullet) {
						start_sc = System.currentTimeMillis();
						time = 1;
						burn_mark = CurvedBullet.Burntime[CurvedBullet.Burnlevel];
					}
					hp -= 200;
				}
				objectController.playerBulletList.remove(tempObject);
			}
		}
	}
	
	protected void attack(int attackMode) {
		float ix = x+WIDTH/2-Bullet.SIZE/2;
		float iy = y+HEIGHT/2-Bullet.SIZE/2;
		float fx = player.getX()+Player.WIDTH/2-Bullet.SIZE/2;
		float fy = player.getY()+Player.HEIGHT/2-Bullet.SIZE/2;
		double distance = (float) Math.sqrt((ix-fx)*(ix-fx)+(iy-fy)*(iy-fy));
		double tan = (iy-fy)/(fx-ix);
		double theta = Math.atan(tan);
		if(fx < ix) {
			theta = Math.atan(tan) + Math.PI;
		}
		if(attackMode == 0) {
			objectController.addEnemyBullet(new EnemyTraceBullet(ix, iy, 0, 0, imageLoader, objectController, player));
		} else if(attackMode == 1) {
			int bulletType = r.nextInt(2);
			for(int i = 2; i >= -2; i -= 1) {
				double s = theta + (Math.PI/18 * i);
				float nfx = ix + (float)(distance*Math.cos(s));
				float nfy = iy - (float)(distance*Math.sin(s));
				if(bulletType == 0)
					objectController.addEnemyBullet(new EnemyNormalBullet(ix, iy, nfx, nfy, imageLoader, objectController));
				else 
					objectController.addEnemyBullet(new EnemyBounceBullet(ix, iy, nfx, nfy, imageLoader, objectController));
			}
		} else if(attackMode == 2) {
			for(int i = 0; i < 12; i++) {
				shootBullet(ix, iy, distance, Math.PI/6f*i);
			}
		} else if(attackMode == 3) {
			attackMode3Attacking = true;
			attackMode3Distance = (float)distance;
			attackMode3Theta = theta;
		} else if(attackMode == 4) {
			if(attackMode4CDCount <= 0) {
				attackMode4Attacking = true;
				attackMode4Discharge = false;
				if(attackMode4Type == 0) attackMode4Type = 1;
				else attackMode4Type = 0;
			}
		} 
	}
	
	private void spawnAt(int xx, int yy) {
		switch(r.nextInt(8)) {
		case 0: case 1: case 2: objectController.addEnemy(new BasicEnemy(xx, yy, imageLoader, objectController, player)); break;
		case 3: case 4: objectController.addEnemy(new MultiShootingEnemy(xx, yy, imageLoader, objectController, player)); break;
		case 5: case 6: objectController.addEnemy(new MultiShootingEnemySpin(xx, yy, imageLoader, objectController, player)); break;
		case 7: objectController.addEnemy(new MultiShootingEnemyRevolve(xx, yy, imageLoader, objectController, player)); break;
		}
	}
	
	private void spawnEnemy(int n) {
		if(n == -1) n = r.nextInt(4); 
		switch(n) {
		case 0: spawnAt(13*30, 10*30); break;
		case 1: spawnAt(34*30, 10*30); break;
		case 2: spawnAt(13*30, 27*30); break;
		case 3: spawnAt(34*30, 27*30); break;
		}
	}
	
	private void goInvisible() {
		invisible = true;
		invisibleStart_sec = System.currentTimeMillis();
		invisibleDuration = r.nextInt(5, 11);
	}
	
	private void goGoldenHealth() {
		goldenHealth = true;
		goldenHealthStart_sec = System.currentTimeMillis();
		goldenHealthDuration = r.nextInt(3, 6); 
	}
	
	private void goTeleport() {
		switch(r.nextInt(4)) {
		case 0: x = 13*30; y = 10*30; break;
		case 1: x = 34*30; y = 10*30; break;
		case 2: x = 13*30; y = 27*30; break;
		case 3: x = 34*30; y = 27*30; break;
		}
	}
	
	private void shootBullet(float ix, float iy, double distance, double s) {
		float nfx = ix + (float)(distance*Math.cos(s));
		float nfy = iy - (float)(distance*Math.sin(s));
		objectController.addEnemyBullet(new EnemyNormalBullet(ix, iy, nfx, nfy, imageLoader, objectController));
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x-10, (int)y-10, WIDTH+20, HEIGHT+20);
	}
	
	public static boolean getMoreDamage() {
		return moreDamage;
	}
}
