package themePackage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import gamePackage.GameMain;
import imagePackage.ImageLoader;

public class Help {
	
	private BufferedImage helpImage;
	private BufferedImage buttonImage;
	
	public static final int backBtnX = 20, backBtnY = 110;
	public static final int backStrX = backBtnX+70, backStrY = backBtnY+60;
	
	public Help(ImageLoader imageLoader) {
		helpImage = imageLoader.getMenuImage();
		buttonImage = imageLoader.getButtonPrewImage();
	}
	
	public void render(Graphics g) {
		g.drawImage(helpImage, 0, 0, GameMain.WIDTH, GameMain.HEIGHT, 0, 0, helpImage.getWidth(), helpImage.getHeight(), null);
		
		g.setColor(Color.white);
		g.setFont(new Font("arial", 3, 100));
		g.drawString("Help", 380, 200);
		
		g.setFont(new Font("monospaced", 0, 20));
		g.drawString("use W A S D to control the player", 300, 350);
		g.drawString("press left key to shoot bullet", 320, 400);
		g.drawString("press right key to shoot special bullet", 280, 450);
		g.drawImage(buttonImage, backBtnX, backBtnY, backBtnX+Button.WIDTH, backBtnY+Button.HEIGHT, 0, 0, buttonImage.getWidth(), buttonImage.getHeight(), null);
	}
}
