package themePackage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import gamePackage.GameMain;
import imagePackage.ImageLoader;

public class Lost {

	private BufferedImage lostImage;
	
	private BufferedImage buttonMenuImage;
	private BufferedImage buttonRestartImage;
	
	public static final int menuBtnX = 400, menuBtnY = 450;
	public static final int restartBtnX = 560, restartBtnY = 450;
	
	public Lost(ImageLoader imageLoader) {
		lostImage = imageLoader.getLostImage();
		this.buttonMenuImage = imageLoader.getButtonMenuImage();
		this.buttonRestartImage = imageLoader.getButtonRestartImage();
	}
	
	public void render(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, GameMain.WIDTH, GameMain.HEIGHT);
		g.drawImage(lostImage, GameMain.WIDTH/4, 0, GameMain.WIDTH*3/4, GameMain.HEIGHT/2, 0, 0, lostImage.getWidth(), lostImage.getHeight(), null);
		
		g.drawImage(buttonMenuImage, menuBtnX, menuBtnY, menuBtnX+Button.WIDTH, menuBtnY+Button.HEIGHT, 0, 0, buttonMenuImage.getWidth(), buttonMenuImage.getHeight(), null);
		g.drawImage(buttonRestartImage, restartBtnX, restartBtnY, restartBtnX+Button.WIDTH, restartBtnY+Button.HEIGHT, 0, 0, buttonRestartImage.getWidth(), buttonRestartImage.getHeight(), null);
		
	}
}
