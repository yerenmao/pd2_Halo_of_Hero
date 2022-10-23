package themePackage;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import gamePackage.GameMain;
import imagePackage.ImageLoader;

public class Start {
	
	private BufferedImage startImage;
	private BufferedImage basicEnemyImage;
	private BufferedImage traceEnemyImage;
	private BufferedImage mEnemy1;
	private BufferedImage mEnemy2;
	private BufferedImage mEnemy3;
	private BufferedImage player1;
	private BufferedImage player2;
	private BufferedImage player3;
	private BufferedImage player4;
	private BufferedImage bossImage;
	
	private int t = 0, velt = 10, shift = 0;
	private final int d = 120;
	
	public Start(ImageLoader imageLoader) {
		startImage = imageLoader.getStartImage();
		basicEnemyImage = imageLoader.getBasicEnemyImages()[0];
		traceEnemyImage = imageLoader.getTraceEnemyImages()[0];
		mEnemy1 = imageLoader.getMultiShootingEnemyImages()[0];
		mEnemy2 = imageLoader.getMultiShootingEnemyRevolveImages()[0];
		mEnemy3 = imageLoader.getMultiShootingEnemySpinImages()[0];
		player1 = imageLoader.getPlayerImages1()[0];
		player2 = imageLoader.getPlayerImages2()[0];
		player3 = imageLoader.getPlayerImages3()[0];
		player4 = imageLoader.getPlayerImages4()[0];
		bossImage = imageLoader.getBossImages()[0];
	}
	
	public void tick() {
		t += velt;
		shift = (int)(10 * Math.cos(t/100.0));
	}
	
	public void render(Graphics g) {
		g.drawImage(startImage, 0, 0, GameMain.WIDTH, GameMain.HEIGHT, 0, 0, startImage.getWidth(), startImage.getHeight(), null);

		g.drawImage(basicEnemyImage, 1000-t>660?1000-t:660, 370, 64, 64, null);
		g.drawImage(basicEnemyImage, 1000+d-t>700?1000+d-t:700, 200, 64, 64, null);
		g.drawImage(basicEnemyImage, 1000+2*d-t>740?1000+2*d-t:740, 330, 64, 64, null);
		g.drawImage(basicEnemyImage, 1000+3*d-t>800?1000+3*d-t:800, 250, 64, 64, null);
		g.drawImage(basicEnemyImage, 1000+4*d-t>850?1000+4*d-t:850, 350, 64, 64, null);
		g.drawImage(basicEnemyImage, 1000+5*d-t>860?1000+5*d-t:860, 190, 64, 64, null);

		g.drawImage(mEnemy1, -1000-3*d+2*t<200?-1000-3*d+2*t:200, 250, 158, 128, null);
		g.drawImage(mEnemy2, -1000-5*d+2*t<130?-1000-5*d+2*t:130, 360, 158, 128, null);
		g.drawImage(mEnemy3, -1000-7*d+2*t<70?-1000-7*d+2*t:70, 200, 158, 128, null);

		g.drawImage(player1, 190, 2800+1*d-(int)(2.8f*t)>525?2800+1*d-(int)(2.8f*t):525, 84, 84, null);
		g.drawImage(player2, 370, 2800+3*d-(int)(2.8f*t)>500?2800+3*d-(int)(2.8f*t):500, 84, 84, null);
		g.drawImage(player3, 530, 2800+5*d-(int)(2.8f*t)>500?2800+5*d-(int)(2.8f*t):500, 84, 84, null);
		g.drawImage(player4, 710, 2800+7*d-(int)(2.8f*t)>525?2800+7*d-(int)(2.8f*t):525, 84, 84, null);
		
		g.drawImage(bossImage, 320, -2500+2*t<160?-2500+2*t:160+shift, 360, 360, null);
		
		Graphics2D g2d = (Graphics2D)g;
		g2d.setComposite(makeTransparent(0.7f));
		g.setColor(Color.white);
		g.setFont(new Font("monospaced", 0, 50));
		g.drawString("Click to start the game!", 150, 700);
		g2d.setComposite(makeTransparent(1));
	}
	
	private AlphaComposite makeTransparent(float alpha) {
		int type = AlphaComposite.SRC_OVER;
		return (AlphaComposite.getInstance(type, alpha));
	}
	
}
