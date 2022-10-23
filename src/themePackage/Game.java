package themePackage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import assetsPackage.Floor;
import bulletPackage.Bullet;
import effectPackage.Clamper;
import effectPackage.Transparent;
import gamePackage.GameCamera;
import gamePackage.GameMain;
import gamePackage.GameObjectController;
import gamePackage.GameSetting;
import gamePackage.GameState;
import imagePackage.ImageLoader;
import levelPackage.LevelStarter;
import levelPackage.LevelTest;
import musicPackage.MusicPlayer;
import pickUpPackage.CoinPU;
import playerPackage.Player;

public class Game {
	
	public static boolean paused = false;
	
	private BufferedImage floorImage;
	private BufferedImage dimondImage;
	private BufferedImage coinImage;
	private BufferedImage recoverPotionImage;
	private BufferedImage lessDamagePotionImage;
	
	
	private GameObjectController objectController;
	private Player player;
	private GameCamera camera;
	private LevelStarter levelStarter;
	private boolean initialized = false;
	
	private BufferedImage buttonMenuImage;
	private BufferedImage buttonRestartImage;	
	
	public static final int menuBtnX = 300, menuBtnY = 420;
	public static final int restartBtnX = 460, restartBtnY = 420;
	
	public static final int coinImgX = 700, coinImgY = -70;
	public final int coinRectX = 680, coinRectY = -80, coinRectW = 200, coinRectH = 70, coinRectR = 20;
	public final int coinStrX = 770, coinStrY = -28;
	
	public final int enemyClearedRectX = 200, enemyClearedRectY = 470, enemyClearedRectW = 410, enemyClearedRectH = 70;
	public final int enemyClearedStrX = 220, enemyClearedStrY = 520;
	
	private int countInitialize = 0;
	
	public static int introCount = 0;
	public static final int introCountMax = 30;
	private BufferedImage introImage;
	private BufferedImage introImageBG;
	private BufferedImage introImageText;
	private ImageLoader imageLoader;
	
	private BufferedImage bossGround;
	
	private BufferedImage pausedTextImage;
	
	public enum GameMode {
		intro(),
		normalMode(),
		platformerMode(),
	}
	
	public enum GameLevel {
		Level1(),
		Level2(),
		Level3(),
		Level4(),
		Level5(),
		LevelShop(),
		LevelTest(),
	}
	
	public static GameMode gameMode = GameMode.normalMode;
	public static GameLevel gameLevel = GameLevel.Level1;
	
	
	public Game(ImageLoader imageLoader, GameObjectController objectController, Player player, GameCamera camera, LevelStarter levelStarter) {
		floorImage = imageLoader.getFloorImage();
		dimondImage = imageLoader.getDimondImage();
		coinImage = imageLoader.getCoinImage();
		recoverPotionImage = imageLoader.getPotionBlueImage();
		lessDamagePotionImage = imageLoader.getPotionPurpleImage();
		buttonMenuImage = imageLoader.getButtonMenuImage();
		buttonRestartImage = imageLoader.getButtonRestartImage();
		this.objectController = objectController;
		this.player = player;
		this.camera = camera;
		this.levelStarter = levelStarter;
		this.imageLoader = imageLoader;
		introImage = imageLoader.getLoadBar1Image();
		introImageBG = imageLoader.getLoadBarBGImage();
		introImageText = imageLoader.getLoadBarTextImage();
		bossGround = imageLoader.getBossGroundImage();
		pausedTextImage = imageLoader.getPausedTextImage();
	}
	
	public void tick() {
		
		if(!initialized) initialize();
		
		if(introCount <= introCountMax) {
			introCount++; camera.tick(player); player.resetSpeed();
			if(introCount == introCountMax) {
				if(gameLevel == GameLevel.Level1) MusicPlayer.playGameBGMSound();
				else if(gameLevel == GameLevel.Level5) MusicPlayer.playBossBGMSound();
			}
			return;
		}
		if(!paused) {
			camera.tick(player);
			objectController.tick();			
		}
	}

	public void render(Graphics g) {
		
		if(introCount <= introCountMax) {
			g.setColor(Color.black);
			g.fillRect(0, 0, GameMain.WIDTH, GameMain.HEIGHT);
			switch(gameLevel) {
			case Level1: case Level2: introImage = imageLoader.getLoadBar1Image(); break;
			case Level3: case Level4: case LevelShop: introImage = imageLoader.getLoadBar1Image(); break;
			case Level5: introImage = imageLoader.getLoadBar3Image(); break;
			default: break;
			}
			g.drawImage(introImageBG, GameMain.WIDTH/2-200, GameMain.HEIGHT/2-10, GameMain.WIDTH/2+200, GameMain.HEIGHT/2+10, 0, 0, introImageBG.getWidth(), introImageBG.getHeight(), null);
			g.drawImage(introImage, GameMain.WIDTH/2-200, GameMain.HEIGHT/2-10, GameMain.WIDTH/2-200+(int)(400*introCount/(float)introCountMax), GameMain.HEIGHT/2+10, 0, 0, introImage.getWidth(), introImage.getHeight(), null);
			g.drawImage(introImageText, 390, 250, 390+220, 250+40, 0, 0, introImageText.getWidth(), introImageText.getHeight(), null);
			return;
		}
		
		Graphics2D g2d = (Graphics2D)g;
		
		g2d.translate(-camera.getX(), -camera.getY());
		
		g.setColor(Color.black);
		g.drawRect(0, 0, GameMain.WIDTH, GameMain.HEIGHT);
		
		// draw ground
		if(gameLevel == GameLevel.Level5) {
			for(int y = 0; y < 40 * Floor.HEIGHT; y += Floor.HEIGHT) {
				for(int x = 0; x < 50 * Floor.WIDTH; x += Floor.WIDTH) {
					g.drawImage(bossGround, x, y, x+Floor.WIDTH, y+Floor.HEIGHT, 0, 0, bossGround.getWidth(), bossGround.getHeight(), null);
				}
			}
		} else {
			for(int y = 0; y < 40 * Floor.HEIGHT; y += Floor.HEIGHT) {
				for(int x = 0; x < 50 * Floor.WIDTH; x += Floor.WIDTH) {
					g.drawImage(floorImage, x, y, x+Floor.WIDTH, y+Floor.HEIGHT, 0, 0, floorImage.getWidth(), floorImage.getHeight(), null);
				}
			}
		}
		
		g2d.setComposite(Transparent.makeTransparent(1));
		
		objectController.render(g);
		
		drawBoundary(g);
		
		g2d.translate(camera.getX()+100, camera.getY()+100);
		
		drawHUD(g);
		drawCoin(g);
		if(objectController.enemyList.size() == 0 && gameMode != GameMode.platformerMode && gameLevel != GameLevel.LevelShop) 
			drawEnemyCleared(g);
		
		if(paused) {
			g2d.setComposite(Transparent.makeTransparent(0.5f));
			g.setColor(Color.gray);
			g.fillRect(-90, -90, GameMain.WIDTH-20, GameMain.HEIGHT-50);
			g2d.setComposite(Transparent.makeTransparent(1));
			g.drawImage(pausedTextImage, 260, 200, pausedTextImage.getWidth()/2, pausedTextImage.getHeight()/2, null);
			g.drawImage(buttonMenuImage, menuBtnX, menuBtnY, menuBtnX+Button.WIDTH, menuBtnY+Button.HEIGHT, 0, 0, buttonMenuImage.getWidth(), buttonMenuImage.getHeight(), null);
			g.drawImage(buttonRestartImage, restartBtnX, restartBtnY, restartBtnX+Button.WIDTH, restartBtnY+Button.HEIGHT, 0, 0, buttonRestartImage.getWidth(), buttonRestartImage.getHeight(), null);
		}
	}
	
	private void drawBoundary(Graphics g) {
		if(gameLevel == GameLevel.LevelTest) {
			g.setColor(Color.black);
			g.drawRect(LevelTest.firstboundaryX, LevelTest.firstboundaryY, LevelTest.firstboundaryW, LevelTest.firstboundaryH);
			g.drawRect(LevelTest.secondboundaryX, LevelTest.secondboundaryY, LevelTest.secondboundaryW, LevelTest.secondboundaryH);
		}
	}
	
	private void drawHUD(Graphics g) {
		
		BufferedImage playerImage = player.getPlayerImageNum0();
		BufferedImage leftKeyBulletImage = player.getLeftKeyBulletImage();
		BufferedImage rightKeyBulletImage = player.getRightKeyBulletImage();
		
		Graphics2D g2d = (Graphics2D)g;
		
		g.setColor(Color.gray);
		g2d.setComposite(Transparent.makeTransparent(0.5f));
		g.fillRect(-90, -90, 220, 140);
		g2d.setComposite(Transparent.makeTransparent(1));
		
		g.drawImage(playerImage, -80, -90, -80+32, -90+32, 0, 0, playerImage.getWidth(), playerImage.getHeight(), null);
		g.setColor(Color.black);
		g.drawRect(-45, -85, 150, 30);
		int redValue = (int)Clamper.clamp(255*(1-player.getHpOnScreen()/(float)player.getTotalHp()), 0, 255);
		int greenValue = (int)Clamper.clamp(255*player.getHpOnScreen()/(float)player.getTotalHp(), 0, 255);
		g.setColor(new Color(redValue, greenValue, 0));
		g2d.setComposite(Transparent.makeTransparent(0.5f));
		g.fillRect(-45, -85, (int)(150*player.getHpOnScreen()/(float)player.getTotalHp())+1, 30);
		g2d.setComposite(Transparent.makeTransparent(1));
		
		g.setColor(Color.black);
		g.setFont(new Font("nomospaced", 0, 18));
		
		if(GameSetting.showPlayerHpNumber) 
			g.drawString("" + player.getHpOnScreen(), +5, -63);
		
		g.drawImage(leftKeyBulletImage, -75, -45, -75+Bullet.SIZE, -45+Bullet.SIZE, 0, 0, leftKeyBulletImage.getWidth(), leftKeyBulletImage.getHeight(), null);
		g.drawString("X  " + player.getLeftKeyBulletAmount(), -40, -25);
		
		g.drawImage(rightKeyBulletImage, 35, -45, 35+Bullet.SIZE, -45+Bullet.SIZE, 0, 0, rightKeyBulletImage.getWidth(), rightKeyBulletImage.getHeight(), null);
		g.drawString("X  " + player.getRightKeyBulletAmount(), 70, -25);
		
		g.drawImage(dimondImage, -85, -10, -85+45, -10+45, 0, 0, dimondImage.getWidth(), dimondImage.getHeight(), null);
		g.drawString("X  " + player.getDimondAmount(), -40, 20);
		
		drawShopEffect(g);
	}
	
	private void drawCoin(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setComposite(Transparent.makeTransparent(0.7f));
		g.setColor(Color.gray);
		g.fillRoundRect(coinRectX, coinRectY, coinRectW, coinRectH, coinRectR, coinRectR);
		g.setColor(Color.black);
		g.setFont(new Font("monospaced", 0, 48));
		g.drawString(""+GameSetting.coinNumber, coinStrX, coinStrY);
		g2d.setComposite(Transparent.makeTransparent(1));
		g.drawImage(coinImage, coinImgX, coinImgY, coinImgX+CoinPU.WIDTH, coinImgY+CoinPU.HEIGHT, 0, 0, coinImage.getWidth(), coinImage.getHeight(), null);
	}
	
	private void drawEnemyCleared(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setComposite(Transparent.makeTransparent(0.5f));
		g.setColor(new Color(122, 253, 135));
		g.fillRoundRect(enemyClearedRectX, enemyClearedRectY, enemyClearedRectW, enemyClearedRectH, 20, 20);
		g.setColor(new Color(82, 121, 90));
		g.setFont(new Font("monospaced", 0, 48));
		g.drawString("Enemy Cleared", enemyClearedStrX, enemyClearedStrY);
		g2d.setComposite(Transparent.makeTransparent(1));
	}
	
	private void drawShopEffect(Graphics g) {
		int potionImgSize = 25, potionImgX = 35, potionImgY = 0;
		if(player.getRecoverHp()) {
			g.drawImage(recoverPotionImage, potionImgX, potionImgY, potionImgX+potionImgSize, potionImgY+potionImgSize, 0, 0, recoverPotionImage.getWidth(), recoverPotionImage.getHeight(), null);
			g.drawString(remainTimeToString(player.getRemainRecoverPotionTime()), potionImgX, potionImgY);
		}
		if(player.getLessDamage()) {
			g.drawImage(lessDamagePotionImage, potionImgX+50, potionImgY, potionImgX+50+potionImgSize, potionImgY+potionImgSize, 0, 0, lessDamagePotionImage.getWidth(), lessDamagePotionImage.getHeight(), null);
			g.drawString(remainTimeToString(player.getRemainLessDamagePotionTime()), potionImgX+50, potionImgY);
		}
	}
	
	private String remainTimeToString(int time) {
		String min = "", sec = "";
		min = Integer.toString(time/60);
		if(time % 60 >= 10) sec = Integer.toString(time%60);
		else sec = "0"+ Integer.toString(time%60);
		return min+":"+sec;
	}
	
	private void initialize() {
		if(gameMode == GameMode.platformerMode) {
			player.setY(30*20); player.setX(30*2);
		} else if(gameMode == GameMode.normalMode) {
			if(gameLevel == GameLevel.Level1) {
				player.setY(25*30); player.setX(24*30);
			} else if(gameLevel == GameLevel.Level2) {
				player.setY(25*30); player.setX(24*30);
			} else if(gameLevel == GameLevel.Level3) {
				player.setY(33*30); player.setX(24*30);
			} else if(gameLevel == GameLevel.Level4) {
				player.setY(25*30); player.setX(12*30);
			} else if(gameLevel == GameLevel.Level5) {
				player.setY(30*30); player.setX(24*30);
			} else if(gameLevel == GameLevel.LevelShop) {
				player.setY(13*30); player.setX(17*30);
			}
		}
		countInitialize++; introCount = 0;
		if(countInitialize == 2) {
			initialized = true;
		}
	}
	
	public void start() {
		GameMain.state = GameState.Game;
		MusicPlayer.stopBGMSound();
		initialized = false;
		countInitialize = 0;
		gameLevel = GameLevel.Level1;
		levelStarter.startLevel1();
	}
	
	public void pauseGame() {
		if(paused) paused = false;
		else paused = true;
	}
	
	public void won() {
		GameMain.state = GameState.Won;
		MusicPlayer.playMarioWonSound();
		player.reset();
		objectController.clearAll();
	}
	
	public void lost() {
		GameMain.state = GameState.Lost;
		MusicPlayer.playMarioDieSound();
		player.reset();
		objectController.clearAll();
	}
	
	public void nextLevel(String doorLevel) {
		initialized = false;
		countInitialize = 0;
		objectController.clearAllExceptPlayer();
		switch(doorLevel) {
			case "Level1":
				levelStarter.startNextLevel();
				break;
			case "Level2":
				Game.gameMode = Game.GameMode.platformerMode;
				levelStarter.startLevelPlatformer();
				break;
			case "LevelPlatformer":
				Game.gameMode = Game.GameMode.normalMode;
				levelStarter.startNextLevel();
				break;
			case "Level3":
				levelStarter.startNextLevel();
				break;
			case "Level4":
				levelStarter.startNextLevel();
				break;
			case "LevelShop":
				levelStarter.startNextLevel();
				MusicPlayer.stopBGMSound();
				break;
			case "Level5":
				won();
				break;
			default: break;
		}
		
	}
}