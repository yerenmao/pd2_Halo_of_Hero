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
import musicPackage.MusicPlayer;
import pickUpPackage.CoinPU;
import playerPackage.Player;

public class Menu {
	
	private Player player;
	private BufferedImage playerImage;
	
	private BufferedImage menuImage;
	
	private BufferedImage buttonAboutImage;
	private BufferedImage buttonPlayImage;
	private BufferedImage buttonSoundONImage;
	private BufferedImage buttonSoundOFFImage;
	private BufferedImage buttonMusicONImage;
	private BufferedImage buttonMusicOFFImage;
	private BufferedImage buttonSettingImage;
	private BufferedImage buttonCommandImage;
	private BufferedImage buttonResetImage;
	private BufferedImage coinImage;
	private BufferedImage questionCloudImage;
	
	public static final int playerImgX = 390, playerImgY = 250;
	public static final int playerImgW = 200, playerImgH = 200;
	
	public static final int coinImgX = 800, coinImgY = 50;
	public static final int coinStrX = 870, coinStrY = 92;
	
	public static final int playBtnX = 450, playBtnY = 540;
	
	public static final int commandBtnX = 30, commandBtnY = 30;
	
	public static final int soundBtnX = 30, soundBtnY = 180;
	
	public static final int musicBtnX = 30, musicBtnY = soundBtnY+100;
	
	public static final int helpBtnX = 30, helpBtnY = musicBtnY+100;
	
	public static final int settingBtnX = 30, settingBtnY = helpBtnY+100;
	
	public static final int resetBtnX = 30, resetBtnY = settingBtnY+100;
	
	public static boolean resetPressed = false;
	private final int cloudW = 210, cloudH = 150;
	private final int cloudResetX = 80, cloudResetY = 410;
	public static final int resetYesX = 110, resetYesY = 470, resetNoX = 200, resetNoY = 470, resetBtnW = 70, resetBtnH = 40;
	
	public Menu(Player player, ImageLoader imageLoader) {
		this.player = player;
		menuImage = imageLoader.getMenuImage();
		buttonAboutImage = imageLoader.getButtonAboutImage();
		buttonPlayImage = imageLoader.getButtonPlayImage();
		buttonSettingImage = imageLoader.getButtonSettingImage();
		buttonCommandImage = imageLoader.getButtonCommandImage();
		buttonResetImage = imageLoader.getButtonRestartImage();
		coinImage = imageLoader.getCoinImage();
		questionCloudImage = imageLoader.getQuestionCloudImage();
		buttonSoundONImage = imageLoader.getButtonSoundONImage();
		buttonSoundOFFImage = imageLoader.getButtonSoundOFFImage();
		buttonMusicONImage = imageLoader.getButtonMusicONImage();
		buttonMusicOFFImage = imageLoader.getButtonMusicOFFImage();
	}
	
	public void tick() {
		player.tick();
	}
	
	public void render(Graphics g) {
		
		g.drawImage(menuImage, 0, 0, GameMain.WIDTH, GameMain.HEIGHT, 0, 0, menuImage.getWidth(), menuImage.getHeight(), null);
		g.setColor(Color.white);
		g.setFont(new Font("arial", 0, 100));
		g.drawString("Menu", 360, 180);
		
		g.drawImage(buttonPlayImage, playBtnX, playBtnY, playBtnX+Button.playButtonWIDTH, playBtnY+Button.playButtonHEIGHT, 0, 0, buttonPlayImage.getWidth(), buttonPlayImage.getHeight(), null);
		
		g.drawImage(buttonCommandImage, commandBtnX, commandBtnY, commandBtnX+Button.WIDTH, commandBtnY+Button.HEIGHT, 0, 0, buttonCommandImage.getWidth(), buttonCommandImage.getHeight(), null);
		
		if(GameSetting.soundOn) g.drawImage(buttonSoundONImage, soundBtnX, soundBtnY, Button.WIDTH, Button.HEIGHT, null);
		else g.drawImage(buttonSoundOFFImage, soundBtnX, soundBtnY, Button.WIDTH, Button.HEIGHT, null);
		
		if(GameSetting.musicOn) g.drawImage(buttonMusicONImage, musicBtnX, musicBtnY, Button.WIDTH, Button.HEIGHT, null);
		else g.drawImage(buttonMusicOFFImage, musicBtnX, musicBtnY, Button.WIDTH, Button.HEIGHT, null);
		
		g.drawImage(buttonSettingImage, settingBtnX, settingBtnY, settingBtnX+Button.WIDTH, settingBtnY+Button.HEIGHT, 0, 0, buttonSettingImage.getWidth(), buttonSettingImage.getHeight(), null);

		g.drawImage(buttonAboutImage, helpBtnX, helpBtnY, helpBtnX+Button.WIDTH, helpBtnY+Button.HEIGHT, 0, 0, buttonAboutImage.getWidth(), buttonAboutImage.getHeight(), null);
		
		g.drawImage(buttonResetImage, resetBtnX, resetBtnY, resetBtnX+Button.WIDTH, resetBtnY+Button.HEIGHT, 0, 0, buttonResetImage.getWidth(), buttonResetImage.getHeight(), null);
		
		Graphics2D g2d = (Graphics2D)g;
		g2d.setComposite(Transparent.makeTransparent(0.7f));
		g.setColor(Color.gray);
		g.fillRoundRect(780, 40, 200, 70, 20, 20);
		g.setColor(Color.black);
		g.setFont(new Font("monospaced", 0, 48));
		g.drawString(""+GameSetting.coinNumber, coinStrX, coinStrY);
		g2d.setComposite(Transparent.makeTransparent(1));
		g.drawImage(coinImage, coinImgX, coinImgY, coinImgX+CoinPU.WIDTH, coinImgY+CoinPU.HEIGHT, 0, 0, coinImage.getWidth(), coinImage.getHeight(), null);
		
		playerImage = player.getPlayerImage();
		g.drawImage(playerImage, playerImgX, playerImgY, playerImgX+playerImgW, playerImgY+playerImgH, 0, 0, playerImage.getWidth(), playerImage.getHeight(), null);
		if(resetPressed) {
			g2d.setComposite(Transparent.makeTransparent(0.3f));
			g.setColor(Color.gray);
			g.fillRect(0, 0, GameMain.WIDTH, GameMain.HEIGHT);
			g2d.setComposite(Transparent.makeTransparent(1));
			g.drawImage(questionCloudImage, cloudResetX, cloudResetY, cloudResetX+cloudW, cloudResetY+cloudH, 0, 0, questionCloudImage.getWidth(), questionCloudImage.getHeight(), null);
			g2d.setComposite(Transparent.makeTransparent(0.7f));
			g.setColor(Color.green);
			g.fillRoundRect(resetYesX, resetYesY, resetBtnW, resetBtnH, 10, 10);
			g.setColor(Color.red);
			g.fillRoundRect(resetNoX, resetNoY, resetBtnW, resetBtnH, 10, 10);
			g2d.setComposite(Transparent.makeTransparent(1));
			g.setColor(Color.black);
			g.setFont(new Font("monospaced", 0, 20));
			g.drawString("RESET THE GAME? ", 100, 450);
			g.drawString("YES", 125, 500);
			g.drawString("NO", 224, 500);
		}
	}
}
