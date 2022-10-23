package themePackage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import effectPackage.Animation;
import gamePackage.GameMain;
import gamePackage.GameSetting;
import imagePackage.ImageLoader;
import playerPackage.Player;

public class Upgrade {

	private Player player;
	
	public static final int buttonBackX = 50, buttonBackY = 50;
	public static final int player1X = 150, player1Y = 160;
	public static final int player2X = 350, player2Y = 160;
	public static final int player3X = 550, player3Y = 160;
	public static final int player4X = 750, player4Y = 160;
	public static final int playerPicW = 70, playerPicH = 70;
	
	public static final int boundaryX = 150, boundaryY = 300;
	public static final int boundaryW = 670, boundaryH = 400;
	
	private BufferedImage buttonBack;
	
	private Animation animation1;
	private Animation animation2;
	private Animation animation3;
	private Animation animation4;
	
	private BufferedImage upgradeImage;
	
	public enum Type {
		type1(),
		type2(),
		type3(),
		type4(),
	}
	
	public static Type type;
	
	public Upgrade(Player player, ImageLoader imageLoader) {
		this.player = player;
		buttonBack = imageLoader.getButtonPrewImage();
		upgradeImage = imageLoader.getMenuImage();
		animation1 = new Animation(imageLoader.getPlayerImages1(), 4);
		animation2 = new Animation(imageLoader.getPlayerImages2(), 4);
		animation3 = new Animation(imageLoader.getPlayerImages3(), 4);
		animation4 = new Animation(imageLoader.getPlayerImages4(), 4);
	}
	
	public static void setType(Player player) {
		switch(player.type) {
		case type1: type = Type.type1; break;
		case type2: type = Type.type2; break;
		case type3: type = Type.type3; break;
		case type4: type = Type.type4; break;
		default: break;
		}
	}
	
	public void tick() {
		animation1.tick();
		animation2.tick();
		animation3.tick();
		animation4.tick();
	}
	
	public void render(Graphics g) {
		g.drawImage(upgradeImage, 0, 0, GameMain.WIDTH, GameMain.HEIGHT, 0, 0, upgradeImage.getWidth(), upgradeImage.getHeight(), null);
		g.drawImage(buttonBack, buttonBackX, buttonBackY, buttonBackX+Button.WIDTH, buttonBackY+Button.HEIGHT, 0, 0, buttonBack.getWidth(), buttonBack.getHeight(), null);
		g.drawImage(animation1.getImage(), player1X, player1Y, player1X+playerPicW, player1Y+playerPicH, 0, 0, animation1.getImage().getWidth(), animation1.getImage().getHeight(), null);
		g.drawImage(animation2.getImage(), player2X, player2Y, player2X+playerPicW, player2Y+playerPicH, 0, 0, animation2.getImage().getWidth(), animation2.getImage().getHeight(), null);
		g.drawImage(animation3.getImage(), player3X, player3Y, player3X+playerPicW, player3Y+playerPicH, 0, 0, animation3.getImage().getWidth(), animation3.getImage().getHeight(), null);
		g.drawImage(animation4.getImage(), player4X, player4Y, player4X+playerPicW, player4Y+playerPicH, 0, 0, animation4.getImage().getWidth(), animation4.getImage().getHeight(), null);
		g.setColor(Color.white);
		switch(type) {
		case type1: drawType1(g); break;
		case type2: drawType2(g); break;
		case type3: drawType3(g); break;
		case type4: drawType4(g); break;
		default: break;
		
		}
	}
	
	private void drawType1(Graphics g) {
		g.drawRoundRect(player1X-10, player1Y, playerPicW+20, playerPicH+15, 10, 10);
		g.setColor(Color.black);
		g.fillRect(100, 250, 100+650, 250+250);
	}
	
	private void drawType2(Graphics g) {
		g.drawRoundRect(player2X-10, player2Y, playerPicW+20, playerPicH+15, 10, 10); 
	}
	
	private void drawType3(Graphics g) {
		g.drawRoundRect(player3X-10, player3Y, playerPicW+20, playerPicH+15, 10, 10); 
	}
	
	private void drawType4(Graphics g) {
		g.drawRoundRect(player4X-10, player4Y, playerPicW+20, playerPicH+15, 10, 10); 
	}
}
