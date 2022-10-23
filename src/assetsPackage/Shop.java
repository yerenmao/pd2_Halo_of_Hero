package assetsPackage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import effectPackage.Transparent;
import imagePackage.ImageLoader;
import playerPackage.Player;

public class Shop {
	
	public static final int WIDTH = 300/2, HEIGHT = 410/2;
	
	public static int shopMode = 0;
	
	private int x, y;
	private BufferedImage shopImage;
	private BufferedImage shopBoardImage;
	private BufferedImage potionRedImage;
	private BufferedImage potionYellowImage;
	private BufferedImage potionGreenImage;
	private BufferedImage potionBlueImage;
	private BufferedImage potionPurpleImage;
	private BufferedImage leftKeyBulletImage;
	private BufferedImage rightKeyBulletImage;
	
	private BufferedImage dimondImage;
	
	public static final int boardX = 230, boardY = 60, boardW = 600, boardH = 700;
	
	public final int ShopRectR = 20;
	public final int hpShopRectX = 200, hpShopRectY = 400, hpShopRectW = 250, hpShopRectH = 60;
	public final int hpShopStrX = hpShopRectX+80, hpShopStrY = hpShopRectY+38;
	public final int bulletShopRectX = 560, bulletShopRectY = 400, bulletShopRectW = 250, bulletShopRectH = 60;
	public final int bulletShopStrX = bulletShopRectX+50, bulletShopStrY = bulletShopRectY+38;
	
	public final int hpImgW = 80, hpImgH = 80;
	private final int hpImgX = 280, hpImg1Y = 150, hpImg2Y = 260, hpImg3Y = 370, hpImg4Y = 480, hpImg5Y = 590;
	private final int lbImgX = 300, lbImg1Y = 180, lbImg2Y = 300, lbImgW = 60, lbImgH = 60;
	private final int rbImgX = 300, rbImg1Y = 420, rbImg2Y = 540, rbImgW = 60, rbImgH = 60;
	
	private final int dimondImgW = 50, dimondImgH = 50;
	
	private final int hpDimondImgX = 650, hpDimondImg1Y = 185, hpDimondImg2Y = 290, hpDimondImg3Y = 395, hpDimondImg4Y = 500, hpDimondImg5Y = 605;
	
	private final int bulletDimondImgX = 650, bulletDimondImg1Y = 185, bulletDimondImg2Y = 305, bulletDimondImg3Y = 425, bulletDimondImg4Y = 545;
	
	private static int errorLife = 0;
	private static final int errorLifeMax = 200;
	private final int errorLifeDcc = 1; 
	
	public static final int hpBoxX = 645, hpBox1Y = 180, hpBox2Y = 285, hpBox3Y = 390, hpBox4Y = 495, hpBox5Y = 600;
	public static final int hpBoxW = 120, hpBoxH = 60;
	
	public static final int hpDimond1 = 5, hpDimond2 = 10, hpDimond3 = 15, hpDimond4 = 20, hpDimond5 = 20;
	public static final int hp1 = 100, hp2 = 300, hp3 = 500;
	
	public static final int bulletBoxX = 645, bulletBox1Y = 180, bulletBox2Y = 300, bulletBox3Y = 420, bulletBox4Y = 540;
	public static final int bulletBoxW = 120, bulletBoxH = 60;
	
	public static final int bulletDimond1 = 10, bulletDimond2 = 15, bulletDimond3 = 10, bulletDimond4 = 15;
	public static final int bullet1 = 15, bullet2 = 25, bullet3 = 15, bullet4 = 25;
	
	public enum Type {
		bulletShop(),
		hpShop(),
	}
	
	public Type shopType;
	
	public Shop(int x, int y, ImageLoader imageLoader, Player player, Type shopType) {
		this.x = x;
		this.y = y;
		shopImage = imageLoader.getShopImage();
		shopBoardImage = imageLoader.getShopBoardImage();
		potionRedImage = imageLoader.getPotionRedImage();
		potionYellowImage = imageLoader.getPotionYellowImage();
		potionGreenImage = imageLoader.getPotionGreenImage();
		potionBlueImage = imageLoader.getPotionBlueImage();
		potionPurpleImage = imageLoader.getPotionPurpleImage();
		dimondImage = imageLoader.getDimondImage();
		leftKeyBulletImage = player.getLeftKeyBulletImage();
		rightKeyBulletImage = player.getRightKeyBulletImage();
		this.shopType = shopType;
	}
	
	public void tick() {
		if(errorLife != 0) {
			errorLife -= errorLifeDcc;
		}
	}
	
	public void render(Graphics g) {
		
		g.drawImage(shopImage, x, y, x+WIDTH, y+HEIGHT, 0, 0, shopImage.getWidth(), shopImage.getHeight(), null);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setComposite(Transparent.makeTransparent(0.3f));
		g.setColor(Color.gray);
		g.fillRoundRect(hpShopRectX, hpShopRectY, hpShopRectW, hpShopRectH, ShopRectR, ShopRectR);
		g.fillRoundRect(bulletShopRectX, bulletShopRectY, bulletShopRectW, bulletShopRectH, ShopRectR, ShopRectR);
		g.setColor(Color.black);
		g.setFont(new Font("monospaced", 0, 24));
		g.drawString("HP SHOP", hpShopStrX, hpShopStrY);
		g.drawString("BULLET SHOP", bulletShopStrX, bulletShopStrY);
		g2d.setComposite(Transparent.makeTransparent(1));
		
		if(Shop.shopMode != 0) {
			g.drawImage(shopBoardImage, boardX, boardY, boardX+boardW, boardY+boardH, 0, 0, shopBoardImage.getWidth(), shopBoardImage.getHeight(), null);
			g.setColor(Color.black);
			if(shopMode == 1) {
				// render HP shop
				// render picture and paragraph
				g.setFont(new Font("monospaced", 0, 24));
				g.drawImage(potionRedImage, hpImgX, hpImg1Y, hpImgX+hpImgW, hpImg1Y+hpImgH, 0, 0, potionRedImage.getWidth(), potionRedImage.getHeight(), null);
				g.drawString("HP  + "+hp1, 400, 210);
				g.drawImage(potionYellowImage, hpImgX, hpImg2Y, hpImgX+hpImgW, hpImg2Y+hpImgH, 0, 0, potionYellowImage.getWidth(), potionYellowImage.getHeight(), null);
				g.drawString("HP  + "+hp2, 400, 320);
				g.drawImage(potionGreenImage, hpImgX, hpImg3Y, hpImgX+hpImgW, hpImg3Y+hpImgH, 0, 0, potionGreenImage.getWidth(), potionGreenImage.getHeight(), null);
				g.drawString("HP  + "+hp3, 400, 430);
				g.drawImage(potionBlueImage, hpImgX, hpImg4Y, hpImgX+hpImgW, hpImg4Y+hpImgH, 0, 0, potionBlueImage.getWidth(), potionBlueImage.getHeight(), null);
				g.drawString("HP  + 10 / sec", 400, 520);
				g.drawString("for the next min", 400, 550);
				g.drawImage(potionPurpleImage, hpImgX, hpImg5Y, hpImgX+hpImgW, hpImg5Y+hpImgH, 0, 0, potionPurpleImage.getWidth(), potionPurpleImage.getHeight(), null);
				g.drawString("damage 20% off", 400, 630);
				g.drawString("for the next min", 400, 660);
				// render buy area
				g.setColor(new Color(220, 157, 124));
				g2d.setComposite(Transparent.makeTransparent(0.5f));
				g.fillRoundRect(hpBoxX, hpBox1Y, hpBoxW, hpBoxH, 20, 20);
				g.fillRoundRect(hpBoxX, hpBox2Y, hpBoxW, hpBoxH, 20, 20);
				g.fillRoundRect(hpBoxX, hpBox3Y, hpBoxW, hpBoxH, 20, 20);
				g.fillRoundRect(hpBoxX, hpBox4Y, hpBoxW, hpBoxH, 20, 20);
				g.fillRoundRect(hpBoxX, hpBox5Y, hpBoxW, hpBoxH, 20, 20);
				g2d.setComposite(Transparent.makeTransparent(1));
				g.drawImage(dimondImage, hpDimondImgX, hpDimondImg1Y, hpDimondImgX+dimondImgW, hpDimondImg1Y+dimondImgH, 0, 0, dimondImage.getWidth(), dimondImage.getHeight(), null);
				g.drawImage(dimondImage, hpDimondImgX, hpDimondImg2Y, hpDimondImgX+dimondImgW, hpDimondImg2Y+dimondImgH, 0, 0, dimondImage.getWidth(), dimondImage.getHeight(), null);
				g.drawImage(dimondImage, hpDimondImgX, hpDimondImg3Y, hpDimondImgX+dimondImgW, hpDimondImg3Y+dimondImgH, 0, 0, dimondImage.getWidth(), dimondImage.getHeight(), null);
				g.drawImage(dimondImage, hpDimondImgX, hpDimondImg4Y, hpDimondImgX+dimondImgW, hpDimondImg4Y+dimondImgH, 0, 0, dimondImage.getWidth(), dimondImage.getHeight(), null);
				g.drawImage(dimondImage, hpDimondImgX, hpDimondImg5Y, hpDimondImgX+dimondImgW, hpDimondImg5Y+dimondImgH, 0, 0, dimondImage.getWidth(), dimondImage.getHeight(), null);
				g.setColor(Color.white);
				g.drawString("x "+hpDimond1, 700, 218);
				g.drawString("x "+hpDimond2, 700, 323);
				g.drawString("x "+hpDimond3, 700, 428);
				g.drawString("x "+hpDimond4, 700, 533);
				g.drawString("x "+hpDimond5, 700, 638);
			} else if(shopMode == 2) {
				// render bullet shop
				// render picture and paragraph
				g.setFont(new Font("monospaced", 0, 24));
				g.drawImage(leftKeyBulletImage, lbImgX, lbImg1Y, lbImgX+lbImgW, lbImg1Y+lbImgH, 0, 0, leftKeyBulletImage.getWidth(), leftKeyBulletImage.getHeight(), null);
				g.drawString(" + "+bullet1, 400, 225);
				g.drawImage(leftKeyBulletImage, lbImgX, lbImg2Y, lbImgX+lbImgW, lbImg2Y+lbImgH, 0, 0, leftKeyBulletImage.getWidth(), leftKeyBulletImage.getHeight(), null);
				g.drawString(" + "+bullet2, 400, 345);
				g.drawImage(rightKeyBulletImage, rbImgX, rbImg1Y, rbImgX+rbImgW, rbImg1Y+rbImgH, 0, 0, rightKeyBulletImage.getWidth(), rightKeyBulletImage.getHeight(), null);
				g.drawString(" + "+bullet3, 400, 465);
				g.drawImage(rightKeyBulletImage, rbImgX, rbImg2Y, rbImgX+rbImgW, rbImg2Y+rbImgH, 0, 0, rightKeyBulletImage.getWidth(), rightKeyBulletImage.getHeight(), null);
				g.drawString(" + "+bullet4, 400, 585);
				// render buy area
				g.setColor(new Color(220, 157, 124));
				g2d.setComposite(Transparent.makeTransparent(0.5f));
				g.fillRoundRect(bulletBoxX, bulletBox1Y, bulletBoxW, bulletBoxH, 20, 20);
				g.fillRoundRect(bulletBoxX, bulletBox2Y, bulletBoxW, bulletBoxH, 20, 20);
				g.fillRoundRect(bulletBoxX, bulletBox3Y, bulletBoxW, bulletBoxH, 20, 20);
				g.fillRoundRect(bulletBoxX, bulletBox4Y, bulletBoxW, bulletBoxH, 20, 20);
				g2d.setComposite(Transparent.makeTransparent(1));
				g.drawImage(dimondImage, bulletDimondImgX, bulletDimondImg1Y, bulletDimondImgX+dimondImgW, bulletDimondImg1Y+dimondImgH, 0, 0, dimondImage.getWidth(), dimondImage.getHeight(), null);
				g.drawImage(dimondImage, bulletDimondImgX, bulletDimondImg2Y, bulletDimondImgX+dimondImgW, bulletDimondImg2Y+dimondImgH, 0, 0, dimondImage.getWidth(), dimondImage.getHeight(), null);
				g.drawImage(dimondImage, bulletDimondImgX, bulletDimondImg3Y, bulletDimondImgX+dimondImgW, bulletDimondImg3Y+dimondImgH, 0, 0, dimondImage.getWidth(), dimondImage.getHeight(), null);
				g.drawImage(dimondImage, bulletDimondImgX, bulletDimondImg4Y, bulletDimondImgX+dimondImgW, bulletDimondImg4Y+dimondImgH, 0, 0, dimondImage.getWidth(), dimondImage.getHeight(), null);
				g.setColor(Color.white);
				g.drawString("x "+bulletDimond1, 700, bulletBox1Y+38);
				g.drawString("x "+bulletDimond2, 700, bulletBox2Y+38);
				g.drawString("x "+bulletDimond3, 700, bulletBox3Y+38);
				g.drawString("x "+bulletDimond4, 700, bulletBox4Y+38);
				
			}
			if(errorLife != 0) {
				g2d.setComposite(Transparent.makeTransparent(errorLife/(float)errorLifeMax > 0.7f ? 0.7f : errorLife/(float)errorLifeMax));
				g.setColor(new Color(152, 29, 29));
				g.fillRoundRect(360, 90, 300, 50, 20, 20);
				g.setColor(new Color(250, 27, 24));
				g.setFont(new Font("monospaced", 0, 24));
				g.drawString("dimond not enough!", 385, 125);
				g2d.setComposite(Transparent.makeTransparent(1));
			}
		}
	}
	
	public static void showErrorMessage() {
		errorLife = 300;
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Type getType() {
		return shopType;
	}
}
