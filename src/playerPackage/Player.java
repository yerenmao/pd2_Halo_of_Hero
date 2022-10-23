package playerPackage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import assetsPackage.Door;
import assetsPackage.Shop;
import assetsPackage.Wall;
import bulletPackage.Bullet;
import bulletPackage.EnemyBounceBullet;
import bulletPackage.EnemyChargeBullet;
import bulletPackage.EnemyNormalBullet;
import bulletPackage.EnemyTraceBullet;
import effectPackage.Animation;
import effectPackage.Clamper;
import effectPackage.Trail;
import enemyPackage.BossEnemy;
import enemyPackage.Enemy;
import gamePackage.GameCamera;
import gamePackage.GameMain;
import gamePackage.GameObject;
import gamePackage.GameObjectController;
import gamePackage.GameSetting;
import gamePackage.GameState;
import imagePackage.ImageLoader;
import musicPackage.MusicPlayer;
import pickUpPackage.BlueDimondPU;
import pickUpPackage.BulletPU;
import pickUpPackage.CoinPU;
import pickUpPackage.DimondPU;
import pickUpPackage.HealthPU;
import pickUpPackage.PickUp;
import themePackage.DressingRoom;
import themePackage.Game;

public class Player extends GameObject {
	
	private String name;
	
	// player movement & position
	private boolean up = false, down = false, left = false, right = false;	// controlled by key input
	private boolean jump = false, onGround = false;
	private float playerSpeed = 4;
	private float acc = 1f;		// acceleration
	private float dcc = 0.5f;	// deceleration
	
	private float x, y;
	private float velx, vely;
	private final float g = 0.3f;
	public static final int  WIDTH = 48, HEIGHT = 48;
	
	public int totalHp = 1000;
	public int maxLeftKeyBulletAmount = 100;
	public int maxRightKeyBulletAmount = 30;
	
	// player health point & bullet amount
	private int hp = totalHp;
	private int hpOnScreen = totalHp;
	
	private int leftKeyBulletAmount = maxLeftKeyBulletAmount;
	private int rightKeyBulletAmount = maxRightKeyBulletAmount;
	private int dimondAmount = 0;
	
	// shop effect
	private int lessDamagePotionTime = 60; 
	private boolean lessDamage = false;
	private long lessDamageInit = 0;
	private int lessDamageDelta = 0;
	
	private int recoverPerSec = 10;
	private int recoverHpPotionTime = 60;
	private boolean recoverHp = false;
	private long recoverHpInit = 0;
	private int recoverHpDelta = 0;
	private int lastRecoverHpDelta = 0;
	
	private int leftKeyLevel = 0;
	private int rightKeyLevel = 0;
	
	private boolean freeze = false;
	private long freezeStart = 0;
	
	private ImageLoader imageLoader;
	private GameObjectController objectController;
	private Game gameTheme;
	
	private Animation animation;
	
	public static enum PlayerType {
		type1(), type2(), type3(), type4(),
	}
	
	public PlayerType type;
	
	public Player(float x, float y, ImageLoader imageLoader, GameObjectController objectController) {
		this.x = x;
		this.y = y;
		this.objectController = objectController;
		this.imageLoader = imageLoader;
		switch(GameSetting.playerImage) {
		case 1: changePlayerType(1); break;
		case 2: changePlayerType(2); break;
		case 3: changePlayerType(3); break;
		case 4: changePlayerType(4); break;
		} 
	}
	
	public void getGameControl(Game gameTheme) {
		this.gameTheme = gameTheme;
	}
	
	public void shootBullet(MouseEvent e, GameCamera camera) {
		if(Game.gameMode != Game.GameMode.normalMode) return;
		float mx = e.getX() + camera.getX() - Bullet.SIZE/2;
		float my = e.getY() + camera.getY() - Bullet.SIZE/2;
		if(type == PlayerType.type1) {
			if(e.getButton() == MouseEvent.BUTTON1 && leftKeyBulletAmount > 0) {
				MusicPlayer.playShootSound();
				leftKeyBulletAmount -= 1;
				Player1.shootNormalBullet(mx, my, x, y, objectController, imageLoader, leftKeyLevel);
			} else if(e.getButton() == MouseEvent.BUTTON3 && rightKeyBulletAmount > 0) {
				MusicPlayer.playBounceShootSound();
				rightKeyBulletAmount -= 1;
				Player1.shootBounceBullet(mx, my, x, y, objectController, imageLoader, rightKeyLevel);
			}
		} else if(type == PlayerType.type2) {
			if(e.getButton() == MouseEvent.BUTTON1 && leftKeyBulletAmount > 0) {
				MusicPlayer.playShootSound();
				leftKeyBulletAmount -= 1;
				Player2.shootNormalBullet(mx, my, x, y, objectController, imageLoader, leftKeyLevel);
			}
			else if(e.getButton() == MouseEvent.BUTTON3 && rightKeyBulletAmount > 0) {
				MusicPlayer.playCurveShootSound();
				rightKeyBulletAmount -= 1;
				Player2.shootCurveBullet(mx, my, x, y, objectController, imageLoader, rightKeyLevel);
			}
		} else if(type == PlayerType.type3) {
			if(e.getButton() == MouseEvent.BUTTON1 && leftKeyBulletAmount > 0) {
				MusicPlayer.playShootSound();
				leftKeyBulletAmount -= 1;
				Player3.shootNormalBullet(mx, my, x, y, objectController, imageLoader, leftKeyLevel);
			}
			else if(e.getButton() == MouseEvent.BUTTON3 && rightKeyBulletAmount > 0) {
				MusicPlayer.playSatelliteShootSound();
				rightKeyBulletAmount -= 1;
				Player3.shootSatelliteBullet(mx, my, x, y, objectController, imageLoader, this, System.currentTimeMillis(), rightKeyLevel);
			}
		} else if(type == PlayerType.type4) {
			if(e.getButton() == MouseEvent.BUTTON1 && leftKeyBulletAmount > 0) {
				MusicPlayer.playShootSound();
				leftKeyBulletAmount -= 1;
				Player4.shootNormalBullet(mx, my, x, y, objectController, imageLoader, leftKeyLevel);
			}
			else if(e.getButton() == MouseEvent.BUTTON3 && rightKeyBulletAmount > 0) {
				MusicPlayer.playTrapShootSound();
				rightKeyBulletAmount -= 1;
				Player4.shootTrapBullet(mx, my, x, y, objectController, imageLoader, System.currentTimeMillis(), rightKeyLevel);
			}
		}
	}
	
	private void lostHp(int n) {
		int damage = n;
		if(lessDamage) damage *= 0.8;
		if(BossEnemy.getMoreDamage()) damage *= 1.2;
		
		hp -= damage;
	}

	public void tick() {
		
		if(GameMain.state == GameState.Game) tickHpOnScreen();
		else hp = totalHp;
		
		if(hp <= 0 && hpOnScreen <= 0) gameTheme.lost();
		
		if(GameMain.state == GameState.Game) {
			if(recoverHp || lessDamage) tickShopEffect();
			if(Game.gameMode == Game.GameMode.normalMode) {
				if(Shop.shopMode == 0) {
					animation.tick();
					move();
					wallCollision();
					doorCollision();
					enemyCollision();
					enemyBulletCollision();
					pickUpCollision();
					shopCollision();
				}
			} else if(Game.gameMode == Game.GameMode.platformerMode) {
				animation.tick();
				move();
				doorCollision();
				groundDetection();
				pickUpCollision();
			}
		} else if(GameMain.state == GameState.DressingRoom) {
			move();
			animation.tick();
			dressingRoomBoundaryCollision();
		} else if(GameMain.state == GameState.Menu) {
			animation.tick();
		}
		
		if(GameSetting.showTrail && GameMain.state == GameState.Game) 
			objectController.addTrail(new Trail(0.15f, (int)x, (int)y, (int)x+Player.WIDTH-1, (int)y+Player.HEIGHT-1, 0, 0, animation.getImage().getWidth(), animation.getImage().getHeight(), objectController, animation.getImage()));
	}
	
	private void tickHpOnScreen() {
		if(hpOnScreen > hp) {
			if(hpOnScreen - hp > 10) hpOnScreen -= 10;
			else if(hpOnScreen - hp > 5) hpOnScreen -= 5;
			else hpOnScreen--;
		} else if(hpOnScreen < hp) {
			if(hp - hpOnScreen > 10) {
				if(hpOnScreen + 10 <= totalHp) hpOnScreen += 10;
				else hpOnScreen = totalHp;
			}
			else if(hp - hpOnScreen > 5) {
				if(hpOnScreen + 5 <= totalHp) hpOnScreen += 5;
				else hpOnScreen = totalHp;
			}
			else hpOnScreen++;
		}
	}

	private void tickShopEffect() {
		if(recoverHp) {
			recoverHpDelta = (int)(System.currentTimeMillis() - recoverHpInit)/1000;
			if(recoverHpDelta - lastRecoverHpDelta >= 1) {
				lastRecoverHpDelta = recoverHpDelta;
				hp += recoverPerSec; 
			}
			if(recoverHpDelta >= recoverHpPotionTime) {
				recoverHp = false; lastRecoverHpDelta = 0;
			}
		}
		if(lessDamage) {
			lessDamageDelta = (int)(System.currentTimeMillis() - lessDamageInit)/1000;
			if(lessDamageDelta >= lessDamagePotionTime) lessDamage = false;
		}
	}

	public void render(Graphics g) {
		if(GameSetting.showPlayerCollisionBox) {
			Rectangle recH = getHorizontalBounds();
			Rectangle recV = getVerticalBounds();
			g.setColor(Color.blue);
			g.drawRect(recH.x, recH.y, recH.width, recH.height);
			g.drawRect(recV.x, recV.y, recV.width, recV.height);
		}
		drawHUD(g);
		g.drawImage(animation.getImage(), (int)x, (int)y, (int)x+Player.WIDTH, (int)y+Player.HEIGHT, 0, 0, animation.getImage().getWidth(), animation.getImage().getHeight(), null);
		if(freeze) drawFreeze(g);
	}
	
	private void drawFreeze(Graphics g) {
		g.drawImage(imageLoader.getLightningImage(), (int)x-40, (int)y-40, 128, 128, null);
	}
	
	protected void drawHUD(Graphics g) {
		g.setColor(Color.black);
		g.drawRect((int)x+3, (int)y-10, Enemy.WIDTH-6, 10);
		g.setColor(new Color((int)(255*(1-hpOnScreen/(float)totalHp)), (int)(255*hpOnScreen/(float)totalHp), 0));
		g.fillRect((int)x+3, (int)y-10, (int)((Enemy.WIDTH-6)*hpOnScreen/(float)totalHp)+1, 10);
	}
	
	private void wallCollision() {
		for(int i = 0; i < objectController.wallList.size(); i++) {
			Wall j = objectController.wallList.get(i);
			if(getHorizontalBounds().intersects(j.getBounds())) {
			 	if(velx > 0 && x < j.getX()) {
			 		velx = 0;
			 		x = j.getX() - Player.WIDTH;
			 	} else if(velx < 0 && x > j.getX()) {
			 		velx = 0;
			 		x = j.getX() + Wall.WIDTH;
			 	}
			}
			if(getVerticalBounds().intersects(j.getBounds())) {
				if(vely > 0 && y < j.getY()) {
			 		vely = 0;
			 		y = j.getY() - Player.HEIGHT;
			 	} else if(vely < 0 && y > j.getY()) {
			 		vely = 0;
			 		y = j.getY() + Wall.HEIGHT;
			 	}
			}
		}
	}
	
	private void doorCollision() {
		for(int i = 0; i < objectController.doorList.size(); i++) {
			Door j = objectController.doorList.get(i); 
			if(getVerticalBounds().intersects(j.getBounds()) || getHorizontalBounds().intersects(j.getBounds())) {
				if(Game.gameMode == Game.GameMode.normalMode) {
					if(objectController.enemyList.size() == 0) gameTheme.nextLevel(j.getDoorLevel());
				 	if(vely < 0 && y > j.getY()) {
				 		vely = 0;
				 		y = j.getY() + Door.HEIGHT;
				 	}
				} else {
					gameTheme.nextLevel(j.getDoorLevel());
				}
				
			}
		}
	}
	
	private void enemyCollision() {
		for(int i = 0; i < objectController.enemyList.size(); i++) {
			if(objectController.enemyList.get(i).getBounds().intersects(getBounds())) {
				lostHp(2);
			}
		}
	}
	
	private void enemyBulletCollision() {
		for(int i = 0; i < objectController.enemyBulletList.size(); i++) {
			GameObject tempObject = objectController.enemyBulletList.get(i);
			if(tempObject.getBounds().intersects(getBounds())) {
				if(tempObject instanceof EnemyBounceBullet) lostHp(EnemyBounceBullet.DAMAGE);
				else if(tempObject instanceof EnemyChargeBullet) {lostHp(EnemyChargeBullet.DAMAGE); freeze = true; freezeStart = System.currentTimeMillis();}
				else if(tempObject instanceof EnemyTraceBullet) lostHp(EnemyTraceBullet.DAMAGE);
				else if(tempObject instanceof EnemyNormalBullet) lostHp(EnemyNormalBullet.DAMAGE);
				objectController.enemyBulletList.remove(tempObject);
			}
		}
	}
	
	private void pickUpCollision() {
		for(int i = 0; i < objectController.pickUpList.size(); i++) {
			PickUp temp = objectController.pickUpList.get(i);
			if(temp.getBounds().intersects(getBounds())) {
				if(temp instanceof DimondPU) dimondAmount += 1;
				else if(temp instanceof CoinPU) GameSetting.coinNumber += 1;
				else if(temp instanceof BulletPU) ((BulletPU)temp).addBullet();
				else if(temp instanceof HealthPU) recoverHp(20);
				else if(temp instanceof BlueDimondPU) gameTheme.won();
 				objectController.removePickUp(temp);
				MusicPlayer.playCoinSound();
			}
		}
	}
	
	private void shopCollision() {
		for(int i = 0; i < objectController.shopList.size(); i++) {
			Shop temp = objectController.shopList.get(i);
			if(getHorizontalBounds().intersects(temp.getBounds())) {
				if(temp.shopType == Shop.Type.hpShop) Shop.shopMode = 1;
				else if(temp.shopType == Shop.Type.bulletShop) Shop.shopMode = 2;
			 	if(velx > 0 && x < temp.getX()) {
			 		velx = 0;
			 		x = temp.getX() - Player.WIDTH;
			 	} else if(velx < 0 && x > temp.getX()) {
			 		velx = 0;
			 		x = temp.getX() + Shop.WIDTH;
			 	}
			}
			if(getVerticalBounds().intersects(temp.getBounds())) {
				if(temp.shopType == Shop.Type.hpShop) Shop.shopMode = 1;
				else if(temp.shopType == Shop.Type.bulletShop) Shop.shopMode = 2;
				if(vely > 0 && y < temp.getY()) {
			 		vely = 0;
			 		y = temp.getY() - Player.HEIGHT;
			 	} else if(vely < 0 && y > temp.getY()) {
			 		vely = 0;
			 		y = temp.getY() + Shop.HEIGHT;
			 	}
			}
		}
	}
	
	private void groundDetection() {
		onGround = false;
		for(Wall i : objectController.wallList) {
			if(getVerticalBounds().intersects(i.getBounds())) {
				if(vely > 0 && y+HEIGHT-1 < i.getY()) {
			 		vely = 0;
			 		y = i.getY() - Player.HEIGHT;
			 		onGround = true;
			 	} 
			}	
		}
	}
	
	private void dressingRoomBoundaryCollision() {
		if(x < DressingRoom.boundaryX) {
			x = DressingRoom.boundaryX;
		} else if(x > DressingRoom.boundaryX+DressingRoom.boundaryW-WIDTH) {
			x = DressingRoom.boundaryX+DressingRoom.boundaryW-WIDTH;
		}
		if(y < DressingRoom.boundaryY) {
			y = DressingRoom.boundaryY;
		} else if(y > DressingRoom.boundaryY+DressingRoom.boundaryH-HEIGHT) {
			y = DressingRoom.boundaryY+DressingRoom.boundaryH-HEIGHT;
		}
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, WIDTH, HEIGHT);
	}
	
	// for horizontal collision
	public Rectangle getHorizontalBounds() {
		float bx = x + velx;
		float by = y+5;
		float bw = WIDTH; 
		float bh = HEIGHT-10;
		return new Rectangle((int)bx, (int)by, (int)bw, (int)bh); 
	}
	
	// for vertical collision
	public Rectangle getVerticalBounds() {
		float bx = x+5;
		float by = y + vely;
		float bw = WIDTH-10; 
		float bh = HEIGHT;
		return new Rectangle((int)bx, (int)by, (int)bw, (int)bh);
	}

	private void move() {
		if(Game.gameMode == Game.GameMode.normalMode) {
			if(freeze) {
				if(System.currentTimeMillis() - freezeStart > 1000) freeze = false;
			} else {
				x += velx;
				y += vely;
			}
			boolean horizontalMovement, verticalMovement;
			// horizontal movement
			if(left && !right) {velx -= acc; horizontalMovement = true;}
			else if(right && !left) {velx += acc; horizontalMovement = true;}
			else {
				if(velx > 0) {
					if(velx >= dcc) velx -= dcc;
					else velx = 0;
				}
				else if(velx < 0) {
					if(velx <= -dcc) velx += dcc;
					else velx = 0;
				}
				horizontalMovement = false;
			}
			// vertical movement
			if(up && !down) {vely -= acc; verticalMovement = true;}
			else if(down && !up) {vely += acc; verticalMovement = true;}
			else {
				if(vely > 0) {
					if(vely >= dcc) vely -= dcc;
					else vely = 0;
				}
				else if(vely < 0) {
					if(vely <= dcc) vely += dcc;
					else vely = 0;
				}
				verticalMovement = false;
			}
			if(horizontalMovement && verticalMovement) {
				velx = Clamper.clamp(velx, -playerSpeed/(float)Math.sqrt(2), playerSpeed/(float)Math.sqrt(2));
				vely = Clamper.clamp(vely, -playerSpeed/(float)Math.sqrt(2), playerSpeed/(float)Math.sqrt(2));
			} else {
				velx = Clamper.clamp(velx, -playerSpeed, playerSpeed);
				vely = Clamper.clamp(vely, -playerSpeed, playerSpeed);
			}
		} else if(Game.gameMode == Game.GameMode.platformerMode) {
			x += velx;
			y += vely;
			// horizontal movement
			if(left && !right) velx -= acc;
			else if(right && !left) velx += acc;
			else {
				if(velx > 0) velx -= dcc;
				else if(velx < 0) velx += dcc;
			}
			// vertical movement
			vely += g;
			if(jump && onGround) {
				vely = -9.5f;
			}
			// left right boundary
			if(x < 0) x = 0;
			if(x > 1500-Player.WIDTH) x = 1500-Player.WIDTH;
			velx = Clamper.clamp(velx, -playerSpeed, playerSpeed);
			vely = Clamper.clamp(vely, -100, 100);
		}
	}
	
	public void addLeftKeyBullet(int n) {
		leftKeyBulletAmount += n;
	}
	
	public void addRightKeyBullet(int n) {
		rightKeyBulletAmount += n;
	}
	
	public void spendDimond(int n) {
		dimondAmount -= n;
	}
	
	public void gainDimond(int n) {
		dimondAmount += n;
	}
	
	public void recoverHp(int n) {
		if(hp + n <= totalHp) {
			hp += n;
		} else {
			hp = totalHp;
		}
	}
	
	public int getLeftKeyBulletAmount() {
		return leftKeyBulletAmount;
	}
	
	public void setLeftKeyBulletAmount(int bullet) {
		this.leftKeyBulletAmount = bullet;
	}
	
	public int getRightKeyBulletAmount() {
		return rightKeyBulletAmount;
	}
	
	public void setRightKeyBulletAmount(int bullet) {
		this.rightKeyBulletAmount = bullet;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public float getVelx() {
		return velx;
	}
	
	public float getVely() {
		return vely;
	}
	
	public void setVelx(float velx) {
		this.velx = velx;
	}
	
	public void setVely(float vely) {
		this.vely = vely;
	}

	public void setUp(boolean up) {
		this.up= up;
	}
	
	public void setDown(boolean down) {
		this.down = down;
	}
	
	public void setLeft(boolean left) {
		this.left = left;
	}
	
	public void setRight(boolean right) {
		this.right = right;
	}
	
	public void setJump(boolean jump) {
		this.jump = jump;
	}
	
	public int getDimondAmount() {
		return dimondAmount;
	}
	
	public void setDimondAmount(int dimondAmount) {
		this.dimondAmount = dimondAmount;
	}
	
	public int getHp() {
		return hp;
	}
	
	public int getHpOnScreen() {
		return hpOnScreen;
	}
	
	public int getTotalHp() {
		return totalHp;
	}
	
	public int getLeftBulletLevel() {
		return leftKeyLevel;
	}
	
	public int getRightBulletLevel() {
		return rightKeyLevel;
	}
	
	public String getPlayerSpeedAsString() {
		if(1.9f < playerSpeed && playerSpeed < 2.1f) return "2.0";
		if(2.4f < playerSpeed && playerSpeed < 2.6f) return "2.5";
		if(2.9f < playerSpeed && playerSpeed < 3.1f) return "3.0";
		if(3.4f < playerSpeed && playerSpeed < 3.6f) return "3.5";
		if(3.9f < playerSpeed && playerSpeed < 4.1f) return "4.0";
		if(4.4f < playerSpeed && playerSpeed < 4.6f) return "4.5";
		if(4.9f < playerSpeed && playerSpeed < 5.1f) return "5.0";
		if(5.4f < playerSpeed && playerSpeed < 5.6f) return "5.5";
		if(5.9f < playerSpeed && playerSpeed < 6.1f) return "6.0";
		return "";
	}
	
	public void setHp(int hp) {
		this.hp = hp;
	}
	
	public void setHpOnScreen(int hpOnScreen) {
		this.hpOnScreen = hpOnScreen;
	}
	
	public BufferedImage getPlayerImage() {
		return animation.getImage();
	}
	
	public BufferedImage getPlayerImageNum0() {
		return animation.getImageNum0();
	}
	
	public BufferedImage getLeftKeyBulletImage() {
		switch(type) {
		case type1: return imageLoader.getNormalBulletImage();
		case type2: return imageLoader.getNormalBulletImage();
		case type3: return imageLoader.getNormalBulletImage();
		case type4: return imageLoader.getNormalBulletImage();
		default: break;
		}
		return null;
	}
	
	public BufferedImage getRightKeyBulletImage() {
		switch(type) {
		case type1: return imageLoader.getBounceBulletImage();
		case type2: return imageLoader.getCurvedBulletImage();
		case type3: return imageLoader.getSatelliteBulletImage();
		case type4: return imageLoader.getTrapBulletImage();
		default: break;
		}
		return null;
	}
	
	public void changePlayerType(int n) {
		switch(n) {
		case 1: type = PlayerType.type1; setAttribute(); return;
		case 2: type = PlayerType.type2; setAttribute(); return;
		case 3: type = PlayerType.type3; setAttribute(); return;
		case 4: type = PlayerType.type4; setAttribute(); return;
		}
	}

	public void setAttribute() {
		switch(type) {
		case type1: setAttributeImp(GameSetting.player1Upgrade, Player1.name, Player1.speed, Player1.totalHp, Player1.maxLeftKeyBulletAmount, Player1.maxRightKeyBulletAmount, new Animation(imageLoader.getPlayerImages1(), 4)); break;
		case type2: setAttributeImp(GameSetting.player2Upgrade, Player2.name, Player2.speed, Player2.totalHp, Player2.maxLeftKeyBulletAmount, Player2.maxRightKeyBulletAmount, new Animation(imageLoader.getPlayerImages2(), 4)); break;
		case type3: setAttributeImp(GameSetting.player3Upgrade, Player3.name, Player3.speed, Player3.totalHp, Player3.maxLeftKeyBulletAmount, Player3.maxRightKeyBulletAmount, new Animation(imageLoader.getPlayerImages3(), 4)); break;
		case type4: setAttributeImp(GameSetting.player4Upgrade, Player4.name, Player4.speed, Player4.totalHp, Player4.maxLeftKeyBulletAmount, Player4.maxRightKeyBulletAmount, new Animation(imageLoader.getPlayerImages4(), 4)); break;
		default: break;
		}
	}
	
	private void setAttributeImp(int[] playerUpgrade, String name, float[] speedArr, int[] totalHpArr, int[] maxLeftKeyBulletAmountArr, int[] maxRightKeyBulletAmountArr, Animation animation) {
		this.name = name;
		this.totalHp = totalHpArr[playerUpgrade[0]];
		this.playerSpeed = speedArr[playerUpgrade[1]];
		this.leftKeyLevel = playerUpgrade[2];
		this.maxLeftKeyBulletAmount = maxLeftKeyBulletAmountArr[playerUpgrade[3]];
		this.rightKeyLevel = playerUpgrade[4];
		this.maxRightKeyBulletAmount = maxRightKeyBulletAmountArr[playerUpgrade[5]];
		this.animation = animation;
	}
	
	
	public boolean getRecoverHp() {
		return recoverHp;
	}
	
	public boolean getLessDamage() {
		return lessDamage;
	}
	
	public int getRemainRecoverPotionTime() {
		return recoverHpPotionTime - recoverHpDelta;
	}
	
	public int getRemainLessDamagePotionTime() {
		return lessDamagePotionTime - lessDamageDelta;
	}
	
	public void drinkRecoverPotion() {
		recoverHp = true;
		recoverHpInit = System.currentTimeMillis();
	}
	
	public void drinkLessDamagePotion() {
		lessDamage = true;
		lessDamageInit = System.currentTimeMillis();
	}
	
	public int getUpgradeCost(int n) {
		try {
			switch(type) {
			case type1:
				switch(n) {
				case 1: return Player1.totalHpUpgradeCost[GameSetting.player1Upgrade[0]];
				case 2: return Player1.speedUpgradeCost[GameSetting.player1Upgrade[1]];
				case 3: if(!DressingRoom.getBulletPressed(1)) return Player1.leftKeyBulletLevelUpgradeCost[GameSetting.player1Upgrade[2]];
				else return Player1.maxLeftKeyBulletAmountUpgradeCost[GameSetting.player1Upgrade[3]];
				case 4: if(!DressingRoom.getBulletPressed(2)) return Player1.rightKeyBulletLevelUpgradeCost[GameSetting.player1Upgrade[4]];
				else return Player1.maxRightKeyBulletAmountUpgradeCost[GameSetting.player1Upgrade[5]];
				} break;
			case type2:
				switch(n) {
				case 1: return Player2.totalHpUpgradeCost[GameSetting.player2Upgrade[0]];
				case 2: return Player2.speedUpgradeCost[GameSetting.player2Upgrade[1]];
				case 3: if(!DressingRoom.getBulletPressed(1)) return Player2.leftKeyBulletLevelUpgradeCost[GameSetting.player2Upgrade[2]];
				else return Player2.maxLeftKeyBulletAmountUpgradeCost[GameSetting.player2Upgrade[3]];
				case 4: if(!DressingRoom.getBulletPressed(2)) return Player2.rightKeyBulletLevelUpgradeCost[GameSetting.player2Upgrade[4]];
				else return Player2.maxRightKeyBulletAmountUpgradeCost[GameSetting.player2Upgrade[5]];
				} break;
			case type3:
				switch(n) {
				case 1: return Player3.totalHpUpgradeCost[GameSetting.player3Upgrade[0]];
				case 2: return Player3.speedUpgradeCost[GameSetting.player3Upgrade[1]];
				case 3: if(!DressingRoom.getBulletPressed(1)) return Player3.leftKeyBulletLevelUpgradeCost[GameSetting.player3Upgrade[2]];
				else return Player3.maxLeftKeyBulletAmountUpgradeCost[GameSetting.player3Upgrade[3]];
				case 4: if(!DressingRoom.getBulletPressed(2)) return  Player3.rightKeyBulletLevelUpgradeCost[GameSetting.player3Upgrade[4]];
				else return Player3.maxRightKeyBulletAmountUpgradeCost[GameSetting.player3Upgrade[5]];
				} break;
			case type4:
				switch(n) {
				case 1: return Player4.totalHpUpgradeCost[GameSetting.player4Upgrade[0]];
				case 2: return Player4.speedUpgradeCost[GameSetting.player4Upgrade[1]];
				case 3: if(!DressingRoom.getBulletPressed(1)) return Player4.leftKeyBulletLevelUpgradeCost[GameSetting.player4Upgrade[2]];
				else return Player4.maxLeftKeyBulletAmountUpgradeCost[GameSetting.player4Upgrade[3]];
				case 4: if(!DressingRoom.getBulletPressed(2)) return Player4.rightKeyBulletLevelUpgradeCost[GameSetting.player4Upgrade[4]];
				else return Player4.maxRightKeyBulletAmountUpgradeCost[GameSetting.player4Upgrade[5]];
				} break;
			}
		} catch(Exception e) {
			return -1;
		}
		return 0;
	}
	
	public int getUpgrade(int n) {
		switch(type) {
		case type1: return GameSetting.player1Upgrade[n-1];
		case type2: return GameSetting.player2Upgrade[n-1];
		case type3: return GameSetting.player3Upgrade[n-1];
		case type4: return GameSetting.player4Upgrade[n-1];
		}
		return 0;
	}
	
	public boolean upgrade(int n) {
		switch(type) {
		case type1:
			if(GameSetting.player1Upgrade[n-1] == 3) return true;
			else GameSetting.player1Upgrade[n-1]++; break;
		case type2:
			if(GameSetting.player2Upgrade[n-1] == 3) return true;
			else GameSetting.player2Upgrade[n-1]++; break;
		case type3:
			if(GameSetting.player3Upgrade[n-1] == 3) return true;
			else GameSetting.player3Upgrade[n-1]++; break;
		case type4:
			if(GameSetting.player4Upgrade[n-1] == 3) return true;
			else GameSetting.player4Upgrade[n-1]++; break;
		}
		return true;
	}
	
	
	public void reset() {
		up = false;
		down = false;
		left = false;
		right = false;
		lessDamage = false;
		recoverHp = false;
		lastRecoverHpDelta = 0;
		velx = 0;
		vely = 0;
		hp = totalHp;
		hpOnScreen = totalHp;
		leftKeyBulletAmount = maxLeftKeyBulletAmount;
		rightKeyBulletAmount = maxRightKeyBulletAmount;
		dimondAmount = 0;
	}
	
	public void resetSpeed() {
		up = false;
		down = false;
		left = false;
		right = false;
		velx = 0;
		vely = 0;
	}
}
