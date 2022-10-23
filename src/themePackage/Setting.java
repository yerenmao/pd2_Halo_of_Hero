package themePackage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import effectPackage.Transparent;
import gamePackage.GameMain;
import gamePackage.GameSetting;
import imagePackage.ImageLoader;

public class Setting {
	
	private static BufferedImage settingImage;
	private static BufferedImage buttonImage;
	
	private static final int StrGAP = 100;
	private static final int BtnGAP = 100;
	
	private static final int Str1Y = 290, Str2Y = Str1Y+StrGAP, Str3Y = Str2Y+StrGAP, Str4Y = Str3Y+StrGAP;
	
	public static final int onoffBtnW = 120, onoffBtnH = 80;
	
	public static final int backBtnX = 50, backBtnY = 50;
	
	public static final int PlayerHpNumberOnBtnX = 605, PlayerHpNumberOnBtnY = Str1Y - 50;
	public static final int PlayerHpNumberOffBtnX = 745, PlayerHpNumberOffBtnY = Str1Y - 50;
	
	public static final int PlayerTrailOnBtnX = 605, PlayerTrailOnBtnY = PlayerHpNumberOnBtnY + BtnGAP;
	public static final int PlayerTrailOffBtnX = 745, PlayerTrailOffBtnY = PlayerHpNumberOffBtnY + BtnGAP;
	
	public static final int PlayerCollisionBoxOnBtnX = 605, PlayerCollisionBoxOnBtnY = PlayerTrailOnBtnY + BtnGAP;
	public static final int PlayerCollisionBoxOffBtnX = 745, PlayerCollisionBoxOffBtnY = PlayerTrailOffBtnY + BtnGAP;
	
	public static final int EnemyCollisionBoxOnBtnX = 605, EnemyCollisionBoxOnBtnY = PlayerCollisionBoxOnBtnY + BtnGAP;
	public static final int EnemyCollisionBoxOffBtnX = 745, EnemyCollisionBoxOffBtnY = PlayerCollisionBoxOffBtnY + BtnGAP;
	
	
	public Setting(ImageLoader imageLoader) {
		settingImage = imageLoader.getMenuImage();
		buttonImage = imageLoader.getButtonPrewImage();
	}
	
	public void render(Graphics g) {
		
		g.drawImage(settingImage, 0, 0, GameMain.WIDTH, GameMain.HEIGHT, 0, 0, settingImage.getWidth(), settingImage.getHeight(), null);
		g.setColor(Color.white);
		g.setFont(new Font("arial", 3, 100));
		g.drawString("Setting", 320, 180);

		g.setFont(new Font("arial", 1, 55));
		
		g.drawImage(buttonImage, backBtnX, backBtnY, backBtnX+Button.WIDTH, backBtnY+Button.HEIGHT, 0, 0, buttonImage.getWidth(), buttonImage.getHeight(), null);
		
		g.setFont(new Font("monospaced", 0, 30));
		g.drawString("Player HP Number", 200, Str1Y);
		g.drawString("Player Trail", 200, Str2Y);
		g.drawString("Player Collision Box", 200, Str3Y);
		g.drawString("Enemy Collision Box", 200, Str4Y);
		
		g.drawString("ON", 650, Str1Y); g.drawString("OFF", 780, Str1Y);
		g.drawString("ON", 650, Str2Y); g.drawString("OFF", 780, Str2Y);
		g.drawString("ON", 650, Str3Y); g.drawString("OFF", 780, Str3Y);
		g.drawString("ON", 650, Str4Y); g.drawString("OFF", 780, Str4Y);
		
		Graphics2D g2d = (Graphics2D)g;
		g2d.setComposite(Transparent.makeTransparent(0.2f));
		// show Trail
		if(GameSetting.showTrail) {
			g.setColor(Color.green);
			g.fillRoundRect(PlayerTrailOnBtnX, PlayerTrailOnBtnY, onoffBtnW, onoffBtnH, 20, 10);
		} else {
			g.setColor(Color.red);
			g.fillRoundRect(PlayerTrailOffBtnX, PlayerTrailOffBtnY, onoffBtnW, onoffBtnH, 20, 10);
		}
		// show Player HP Number
		if(GameSetting.showPlayerHpNumber) {
			g.setColor(Color.green);
			g.fillRoundRect(PlayerHpNumberOnBtnX, PlayerHpNumberOnBtnY, onoffBtnW, onoffBtnH, 20, 10);
		} else {
			g.setColor(Color.red);
			g.fillRoundRect(PlayerHpNumberOffBtnX, PlayerHpNumberOffBtnY, onoffBtnW, onoffBtnH, 20, 10);
		}
		// show Player Collision Box
		if(GameSetting.showPlayerCollisionBox) {
			g.setColor(Color.green);
			g.fillRoundRect(PlayerCollisionBoxOnBtnX, PlayerCollisionBoxOnBtnY, onoffBtnW, onoffBtnH, 20, 10);
		} else {
			g.setColor(Color.red);
			g.fillRoundRect(PlayerCollisionBoxOffBtnX, PlayerCollisionBoxOffBtnY, onoffBtnW, onoffBtnH, 20, 10);
		}
		// show Enemy Collision Box
		if(GameSetting.showEnemyCollisionBox) {
			g.setColor(Color.green);
			g.fillRoundRect(EnemyCollisionBoxOnBtnX, EnemyCollisionBoxOnBtnY, onoffBtnW, onoffBtnH, 20, 10);
		} else {
			g.setColor(Color.red);
			g.fillRoundRect(EnemyCollisionBoxOffBtnX, EnemyCollisionBoxOffBtnY, onoffBtnW, onoffBtnH, 20, 10);
		}
		g2d.setComposite(Transparent.makeTransparent(1));
	}
}
