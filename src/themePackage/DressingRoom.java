package themePackage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import effectPackage.Animation;
import effectPackage.Transparent;
import gamePackage.GameMain;
import gamePackage.GameSetting;
import imagePackage.ImageLoader;
import pickUpPackage.CoinPU;
import playerPackage.Player;
import playerPackage.Player1;
import playerPackage.Player2;
import playerPackage.Player3;
import playerPackage.Player4;

public class DressingRoom {
	
	private Player player;
	
	public static final int buttonBackX = 50, buttonBackY = 50;
	public static final int buttonUpgradeX = GameMain.WIDTH-50-Player.WIDTH, buttonUpgradeY = 50;
	public static final int player1X = 150, player1Y = 160;
	public static final int player2X = 350, player2Y = 160;
	public static final int player3X = 550, player3Y = 160;
	public static final int player4X = 750, player4Y = 160;
	public static final int playerPicW = 70, playerPicH = 70;
	
	public static final int boundaryX = 150, boundaryY = 300;
	public static final int boundaryW = 670, boundaryH = 400;
	private BufferedImage buttonBack;
	private BufferedImage buttonUpgrade;
	
	private Animation animation1;
	private Animation animation2;
	private Animation animation3;
	private Animation animation4;
	
	private BufferedImage playerImage;
	private BufferedImage dressingRoomImage;
	private BufferedImage upgradeBoardImage;
	private BufferedImage upgradeTableImage;
	private BufferedImage upgradeBarImage;
	private BufferedImage upgradeBarBGImage;
	private BufferedImage upgradeHpImage;
	private BufferedImage upgradeSpeedImage;
	private BufferedImage leftKeyBulletImage;
	private BufferedImage rightKeyBulletImage;
	private BufferedImage coinImage;
	private BufferedImage lockImage;
	
	public static final int buyPlayerBtnW = 40, buyPlayerBtnH = 25;
	public static final int buyPlayerBtnBuy1X = player2X - 6, buyPlayerBtnBuy1Y = player2Y + 50;
	public static final int buyPlayerBtnNah1X = player2X + 36, buyPlayerBtnNah1Y = player2Y + 50;
	public static final int buyPlayerBtnBuy2X = player3X - 6, buyPlayerBtnBuy2Y = player3Y + 50;
	public static final int buyPlayerBtnNah2X = player3X + 36, buyPlayerBtnNah2Y = player3Y + 50;
	public static final int buyPlayerBtnBuy3X = player4X - 6, buyPlayerBtnBuy3Y = player4Y + 50;
	public static final int buyPlayerBtnNah3X = player4X + 36, buyPlayerBtnNah3Y = player4Y + 50;
	
	private static boolean[] playerLockPressed = {false, false, false};
	public static final int[] buyPlayerCoin = {15, 15, 15};
	
	private static boolean[] upgradePressed = {false, false, false, false};
	private static boolean[] bulletPressed = {false, false};
	
	public static final int bulletBtnX = 174, bulletBtn1Y = 530, bulletBtn2Y = 630, bulletBtnW = 60, bulletBtnH = 30;
	
	public static final int upgradeBtnX = 700, upgradeBtn1Y = 320, upgradeBtn2Y = 420, upgradeBtn3Y = 520, upgradeBtn4Y = 620; 
	public static final int upgradeBtnW = 120, upgradeBtnH = 60;
	
	public static final int upgradeYesNahBtnW = 50, upgradeYesNahBtnH = 25;
	public static final int upgradeYesBtnX = upgradeBtnX+8;
	public static final int upgradeYesBtn1Y = upgradeBtn1Y+30, upgradeYesBtn2Y = upgradeBtn2Y+30, upgradeYesBtn3Y = upgradeBtn3Y+30, upgradeYesBtn4Y = upgradeBtn4Y+30;
	public static final int upgradeNahBtnX = upgradeBtnX+61;
	public static final int upgradeNahBtn1Y = upgradeYesBtn1Y, upgradeNahBtn2Y = upgradeYesBtn2Y, upgradeNahBtn3Y = upgradeYesBtn3Y, upgradeNahBtn4Y = upgradeYesBtn4Y;
	
	private static int errorCount;
	private static int errorCountMax = 200;
	
	private static boolean upgradeMode = false;
	
	public DressingRoom(Player player, ImageLoader imageLoader) {
		this.player = player;
		buttonBack = imageLoader.getButtonPrewImage();
		buttonUpgrade = imageLoader.getButtonUpgradeImage();
		dressingRoomImage = imageLoader.getMenuImage();
		upgradeBoardImage = imageLoader.getShopBoardImage();
		upgradeTableImage = imageLoader.getUpgradeTableImage();
		animation1 = new Animation(imageLoader.getPlayerImages1(), 4);
		animation2 = new Animation(imageLoader.getPlayerImages2(), 4);
		animation3 = new Animation(imageLoader.getPlayerImages3(), 4);
		animation4 = new Animation(imageLoader.getPlayerImages4(), 4);
		upgradeBarImage = imageLoader.getLoadBar2Image();
		upgradeBarBGImage = imageLoader.getLoadBarBGImage();
		upgradeHpImage = imageLoader.getUpgradeHpImage();
		upgradeSpeedImage = imageLoader.getUpgradeSpeedImage();
		playerImage = player.getPlayerImageNum0();
		leftKeyBulletImage = player.getLeftKeyBulletImage();
		rightKeyBulletImage = player.getRightKeyBulletImage();
		coinImage = imageLoader.getCoinImage();
		lockImage = imageLoader.getLockImage();
	}
	
	public void tick() {
		animation1.tick();
		animation2.tick();
		animation3.tick();
		animation4.tick();
		player.tick();
		playerImage = player.getPlayerImageNum0();
		leftKeyBulletImage = player.getLeftKeyBulletImage();
		rightKeyBulletImage = player.getRightKeyBulletImage();
		if(errorCount > 0) errorCount--;
	}
	
	public void render(Graphics g) {
		// draw player and box
		drawPlayeAndBox(g);
		// draw board
		g.drawImage(upgradeBoardImage, 100, 280, 100+800, 280+450, 0, 0, upgradeBoardImage.getWidth(), upgradeBoardImage.getHeight(), null);
		// draw coin
		Graphics2D g2d = (Graphics2D)g;
		g2d.setComposite(Transparent.makeTransparent(0.7f));
		g.setColor(Color.gray);
		g.fillRoundRect(400, 40, 200, 70, 20, 20);
		g.setColor(Color.black);
		g.setFont(new Font("monospaced", 0, 48));
		g.drawString(""+GameSetting.coinNumber, 400+20+70, 40+10+42);
		g2d.setComposite(Transparent.makeTransparent(1));
		g.drawImage(coinImage, 400+20, 40+10, CoinPU.WIDTH, CoinPU.HEIGHT, null);
		g2d.setComposite(Transparent.makeTransparent(errorCount/(float)errorCountMax));
		g.setColor(Color.red);
		g.fillRoundRect(400, 40, 200, 70, 20, 20);
		g2d.setComposite(Transparent.makeTransparent(1));
		
		if(!upgradeMode) {
			// draw dressing room
			drawDressingRoom(g);
		} else {
			// draw upgrade mode
			drawUpgradeMode(g);
		}
	}
	
	private void drawPlayeAndBox(Graphics g) {
		g.drawImage(dressingRoomImage, 0, 0, GameMain.WIDTH, GameMain.HEIGHT, 0, 0, dressingRoomImage.getWidth(), dressingRoomImage.getHeight(), null);
		g.drawImage(buttonBack, buttonBackX, buttonBackY, buttonBackX+Button.WIDTH, buttonBackY+Button.HEIGHT, 0, 0, buttonBack.getWidth(), buttonBack.getHeight(), null);
		g.drawImage(buttonUpgrade, buttonUpgradeX, buttonUpgradeY, buttonUpgradeX+Button.WIDTH, buttonUpgradeY+Button.HEIGHT, 0, 0, buttonUpgrade.getWidth(), buttonUpgrade.getHeight(), null);
		g.drawImage(animation1.getImage(), player1X, player1Y, player1X+playerPicW, player1Y+playerPicH, 0, 0, animation1.getImage().getWidth(), animation1.getImage().getHeight(), null);
		g.drawImage(animation2.getImage(), player2X, player2Y, player2X+playerPicW, player2Y+playerPicH, 0, 0, animation2.getImage().getWidth(), animation2.getImage().getHeight(), null);
		g.drawImage(animation3.getImage(), player3X, player3Y, player3X+playerPicW, player3Y+playerPicH, 0, 0, animation3.getImage().getWidth(), animation3.getImage().getHeight(), null);
		g.drawImage(animation4.getImage(), player4X, player4Y, player4X+playerPicW, player4Y+playerPicH, 0, 0, animation4.getImage().getWidth(), animation4.getImage().getHeight(), null);
		if(GameSetting.playerUnlock[0]) {
			if(playerLockPressed[0]) drawLockPressed(g, player2X, player2Y, 0);
			else drawLock(g, player2X, player2Y);
		}
		if(GameSetting.playerUnlock[1]) {
			if(playerLockPressed[1]) drawLockPressed(g, player3X, player3Y, 1);
			else drawLock(g, player3X, player3Y);
		}
		if(GameSetting.playerUnlock[2]) {
			if(playerLockPressed[2]) drawLockPressed(g, player4X, player4Y, 2);
			else drawLock(g, player4X, player4Y);
		}
		g.setColor(Color.white);
		switch(player.type) {
		case type1: g.drawRoundRect(player1X-10, player1Y, playerPicW+20, playerPicH+15, 10, 10); break;
		case type2: g.drawRoundRect(player2X-10, player2Y, playerPicW+20, playerPicH+15, 10, 10); break;
		case type3: g.drawRoundRect(player3X-10, player3Y, playerPicW+20, playerPicH+15, 10, 10); break;
		case type4: g.drawRoundRect(player4X-10, player4Y, playerPicW+20, playerPicH+15, 10, 10); break;
		default: break;
		}
	}
	
	private void drawDressingRoom(Graphics g) {
		g.drawImage(upgradeTableImage, 200, 330, 200+200, 330+200, 0, 0, upgradeTableImage.getWidth(), upgradeTableImage.getHeight(), null);
		g.drawImage(playerImage, 240, 360, 240+120, 360+120, 0, 0, playerImage.getWidth(), playerImage.getHeight(), null);
		g.setColor(Color.black);
		g.drawRect(200, 540, 200, 55);
		g.setFont(new Font("monospaced", 0, 36));
		switch(player.type) {
		case type1: g.drawString("Joseph", 230, 580); break;
		case type2: g.drawString("Frankie", 227, 580); break;
		case type3: g.drawString("Nathan", 240, 580); break;
		case type4: g.drawString("Marcus", 238, 580); break;
		default: break;
		}
		int hh = 310;
		g.drawImage(upgradeHpImage, 450, hh, 58, 58, null);
		g.drawImage(upgradeSpeedImage, 460, hh+65, 48, 48, null);
		g.drawImage(leftKeyBulletImage, 465, hh+65*2, 36, 36, null);
		g.drawImage(leftKeyBulletImage, 465, hh+65*3, 36, 36, null);
		g.drawImage(rightKeyBulletImage, 465, hh+65*4, 36, 36, null);
		g.drawImage(rightKeyBulletImage, 465, hh+65*5, 36, 36, null);
		int x = 453, y = 540;
		drawR(g, "level", x, y-97, 0);
		drawR(g, "max", x, y-33, 12);
		drawR(g, "level", x, y+33, 0);
		drawR(g, "max", x, y+97, 12);
		
		int d = 62;
		g.setColor(Color.black);
		g.setFont(new Font("monospaced", 0, 24));
		g.drawString("total HP : " + player.getTotalHp(), 550, 350);
		g.drawString("speed : " + player.getPlayerSpeedAsString(), 550, 350+1*d);
		g.drawString("bullet level : "+player.getLeftBulletLevel(), 550, 350+2*d);
		g.drawString("max amount : "+player.maxLeftKeyBulletAmount, 550, 350+3*d);
		switch(player.type) {
		case type1: g.drawString("bounce level : "+player.getRightBulletLevel(), 550, 350+4*d); break;
		case type2: g.drawString("fire level : "+player.getRightBulletLevel(), 550, 350+4*d); break;
		case type3: g.drawString("satellite level : "+player.getRightBulletLevel(), 550, 350+4*d); break;
		case type4: g.drawString("trap level : "+player.getRightBulletLevel(), 550, 350+4*d); break;
		default: break;
		}
		g.drawString("max amount : "+player.maxRightKeyBulletAmount, 550, 350+5*d);
	}
	
	private void drawR(Graphics g, String s, int x, int y, int shift) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setComposite(Transparent.makeTransparent(0.5f));
		g.setColor(Color.white);
		g.fillRoundRect(x, y, 60, 30, 15, 15);
		g.setColor(Color.black);
		g.setFont(new Font("monospaced", 0, 20));
		g.drawString(s, x+shift, y+22);
		g2d.setComposite(Transparent.makeTransparent(1));
	}
	
	private void drawLock(Graphics g, int playerX, int playerY) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setComposite(Transparent.makeTransparent(0.5f));
		g.setColor(Color.white);
		g.fillRoundRect(playerX-10, playerY, playerPicW+20, playerPicH+15, 10, 10);
		g2d.setComposite(Transparent.makeTransparent(1));
		g.drawImage(lockImage, playerX+10, playerY+10, 50, 60, null);
	}
	
	private void drawLockPressed(Graphics g, int playerX, int playerY, int n) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setComposite(Transparent.makeTransparent(0.7f));
		g.setColor(Color.black);
		g.fillRoundRect(playerX-10, playerY, playerPicW+20, playerPicH+15, 10, 10);
		g2d.setComposite(Transparent.makeTransparent(1));
		g.drawImage(coinImage, playerX, playerY+16, 22, 22, null);
		g.setColor(Color.white);
		g.setFont(new Font("monospaced", 0, 20));
		g.drawString("X", playerX+29, playerY+35);
		g.drawString(""+buyPlayerCoin[n], playerX+48, playerY+35);
		g.setColor(Color.green);
		g.drawRoundRect(playerX-6, playerY+50, 40, 25, 10, 10);
		g.drawRoundRect(playerX-4, playerY+52, 36, 21, 10, 10);
		g.setColor(Color.white);
		g.setFont(new Font("monospaced", 0, 12));
		g.drawString("buy", playerX+4, playerY+67);
		g.setColor(Color.red);
		g.drawRoundRect(playerX+36, playerY+50, 40, 25, 10, 10);
		g.drawRoundRect(playerX+38, playerY+52, 36, 21, 10, 10);
		g.setColor(Color.white);
		g.setFont(new Font("monospaced", 0, 12));
		g.drawString("nah", playerX+46, playerY+67);
	}
	
	private void drawUpgradeMode(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g.drawImage(upgradeHpImage, 170, 320, 64, 64, null);
		g.drawImage(upgradeSpeedImage, 170, 420, 64, 64, null);
		g.drawImage(leftKeyBulletImage, 180, 520, 48, 48, null);
		g.drawImage(rightKeyBulletImage, 180, 620, 48, 48, null);
		
		g2d.setComposite(Transparent.makeTransparent(0.5f));
		g.setColor(Color.white);
		g.fillRoundRect(bulletBtnX, bulletBtn1Y, bulletBtnW, bulletBtnH, 15, 15);
		g.fillRoundRect(bulletBtnX, bulletBtn2Y, bulletBtnW, bulletBtnH, 15, 15);
		g.setColor(Color.black);
		g.setFont(new Font("monospaced", 0, 20));
		if(!bulletPressed[0]) g.drawString("level", 173, 552);
		else g.drawString("max", 185, 552);
		if(!bulletPressed[1]) g.drawString("level", 173, 652);
		else g.drawString("max", 185, 652);
		g2d.setComposite(Transparent.makeTransparent(1));
		g.drawImage(upgradeBarBGImage, 280, 340, 380, 20, null);
		g.drawImage(upgradeBarImage, 280, 340, (int)(380*player.getUpgrade(1)/3f), 20, null);
		g.drawImage(upgradeBarBGImage, 280, 440, 380, 20, null);
		g.drawImage(upgradeBarImage, 280, 440, (int)(380*player.getUpgrade(2)/3f), 20, null);
		g.drawImage(upgradeBarBGImage, 280, 540, 380, 20, null);
		if(!bulletPressed[0]) g.drawImage(upgradeBarImage, 280, 540, (int)(380*player.getUpgrade(3)/3f), 20, null);
		else g.drawImage(upgradeBarImage, 280, 540, (int)(380*player.getUpgrade(4)/3f), 20, null);
		g.drawImage(upgradeBarBGImage, 280, 640, 380, 20, null);
		if(!bulletPressed[1]) g.drawImage(upgradeBarImage, 280, 640, (int)(380*player.getUpgrade(5)/3f), 20, null);
		else g.drawImage(upgradeBarImage, 280, 640, (int)(380*player.getUpgrade(6)/3f), 20, null);
		drawUpgradeBox(g, upgradeBtnX, upgradeBtn1Y, 1);
		drawUpgradeBox(g, upgradeBtnX, upgradeBtn2Y, 2);
		drawUpgradeBox(g, upgradeBtnX, upgradeBtn3Y, 3);
		drawUpgradeBox(g, upgradeBtnX, upgradeBtn4Y, 4);
	}
	
	private void drawUpgradeBox(Graphics g, int x, int y, int n) {
		Graphics2D g2d = (Graphics2D)g;
		if(!upgradePressed[n-1]) {
			// normal
			g.setColor(new Color(220, 157, 124));
			g2d.setComposite(Transparent.makeTransparent(0.5f));
			g.fillRoundRect(x, y, upgradeBtnW, upgradeBtnH, 20, 20);
			g2d.setComposite(Transparent.makeTransparent(1));
			int nx = x + 50; int ny = y + 40;
			g.drawImage(coinImage, x+15, y+18, 26, 26, null);
			g.setColor(Color.black);
			g.setFont(new Font("monospaced", 0, 24));
			if(player.getUpgradeCost(n) >= 0) g.drawString("X " + player.getUpgradeCost(n), nx, ny);
			else g.drawString("MAX", nx, ny);
		} else {
			// pressed
			g.setColor(Color.black);
			g2d.setComposite(Transparent.makeTransparent(0.5f));
			g.fillRoundRect(x, y, upgradeBtnW, upgradeBtnH, 20, 20);
			g2d.setComposite(Transparent.makeTransparent(1));
			g.setColor(Color.white);
			g.setFont(new Font("monospaced", 0, 16));
			g.drawString("UPGRADE?", x+20, y+20);
			g.setColor(Color.green);
			g.drawRoundRect(x+8, y+30, 50, 25, 10, 10);
			g.drawRoundRect(x+10, y+32, 46, 21, 10, 10);
			g.setColor(Color.white);
			g.setFont(new Font("monospaced", 0, 12));
			g.drawString("yes", x+22, y+47);
			g.setColor(Color.red);
			g.drawRoundRect(x+61, y+30, 50, 25, 10, 10);
			g.drawRoundRect(x+63, y+32, 46, 21, 10, 10);
			g.setColor(Color.white);
			g.setFont(new Font("monospaced", 0, 12));
			g.drawString("nah", x+76, y+47);
		}
	}
	
	public static void pressUpgradeBtn() {
		if(upgradeMode) upgradeMode = false;
		else upgradeMode = true;
	}
	
	public static void resetUpgradeBtn() {
		upgradeMode = false;
	}
	
	public static boolean getUpgradeMode() {
		return upgradeMode;
	}
	
	public static void setPlayerLock(int n) {
		switch(n) {
		case 0: playerLockPressed[0] = false; playerLockPressed[1] = false; playerLockPressed[2] = false; break;
		case 1: playerLockPressed[0] = true; playerLockPressed[1] = false; playerLockPressed[2] = false; break;
		case 2: playerLockPressed[0] = false; playerLockPressed[1] = true; playerLockPressed[2] = false; break;
		case 3: playerLockPressed[0] = false; playerLockPressed[1] = false; playerLockPressed[2] = true; break;
		}
	}
	
	public static boolean getPlayerLockPressed(int n) {
		if(1 <= n && n <= 3) return playerLockPressed[n-1];
		else return false;
	}
	
	public static void setUpgradePressed(int n) {
		switch(n) {
		case 0: upgradePressed[0]=false; upgradePressed[1]=false; upgradePressed[2]=false; upgradePressed[3]=false; break;
		case 1: upgradePressed[0]=true; upgradePressed[1]=false; upgradePressed[2]=false; upgradePressed[3]=false; break;
		case 2: upgradePressed[0]=false; upgradePressed[1]=true; upgradePressed[2]=false; upgradePressed[3]=false; break;
		case 3: upgradePressed[0]=false; upgradePressed[1]=false; upgradePressed[2]=true; upgradePressed[3]=false; break;
		case 4: upgradePressed[0]=false; upgradePressed[1]=false; upgradePressed[2]=false; upgradePressed[3]=true; break;
		}
	}
	
	public static boolean getUpgradePressed(int n) {
		if(1 <= n && n <= 4) return upgradePressed[n-1];
		else return false;
	}
	
	public static void pressBullet(int n) {
		if(bulletPressed[n-1]) bulletPressed[n-1] = false;
		else bulletPressed[n-1] = true;
	}
	
	public static boolean getBulletPressed(int n) {
		return bulletPressed[n-1];
	}
	
	public static void resetPressBullet() {
		bulletPressed[0] = false;
		bulletPressed[1] = false;
	}
	
	public static void showError() {
		errorCount = errorCountMax - 30;
	}
	
	public static void resetError() {
		errorCount = 0;
	}
}
