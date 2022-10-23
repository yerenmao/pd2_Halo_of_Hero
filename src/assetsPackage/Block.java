package assetsPackage;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import gamePackage.GameObjectController;
import imagePackage.ImageLoader;
import levelPackage.LevelTest;
import playerPackage.Player;

public class Block {
	
	public static final int WIDTH = 50, HEIGHT = 50;
	
	private Player player;
	private BufferedImage blockImage;
	private GameObjectController controller;
	
	private float x, y;
	
	public Block(int x, int y, ImageLoader imageLoader, GameObjectController controller, Player player) {
		this.x = x;
		this.y = y;
		blockImage = imageLoader.getBlockImage();
		this.controller = controller;
		this.player = player;
	}
	
	public void tick() {
		moveByPlayer();
		boundaryCollision();
	}
	
	private void moveByPlayer() {
		if(player.getHorizontalBounds().intersects(getBounds())) {
			if(player.getX() < (int)x+WIDTH/2) {
				x = player.getX()+WIDTH;
			} else if(player.getX() > (int)x+WIDTH/2) {
				x = player.getX()-WIDTH;
			}
		}
		if(player.getVerticalBounds().intersects(getBounds())) {
			if(player.getY() < (int)y+HEIGHT/2) {
				y = player.getY()+HEIGHT;
			} else if(player.getY() > (int)y+HEIGHT/2) {
				y = player.getY()-HEIGHT;
			}
		}
	}
	
	private void boundaryCollision() {
		if(x < LevelTest.firstboundaryX) {
			x = LevelTest.firstboundaryX+2;
			player.setX(x+WIDTH);
		}
		if(x+WIDTH > LevelTest.firstboundaryX+LevelTest.firstboundaryW) {
			x = LevelTest.firstboundaryX+LevelTest.firstboundaryW-WIDTH-2;
			player.setX(x-Player.WIDTH);
		}
		if(y < LevelTest.firstboundaryY) {
			y = LevelTest.firstboundaryY+2;
			player.setY(y+HEIGHT);
		}
		if(y+HEIGHT > LevelTest.firstboundaryY+LevelTest.firstboundaryH) {
			y = LevelTest.firstboundaryY+LevelTest.firstboundaryH-HEIGHT-2;
			player.setY(y-Player.HEIGHT);
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(blockImage, (int)x, (int)y, (int)x+WIDTH, (int)y+HEIGHT, 0, 0, blockImage.getWidth(), blockImage.getHeight(), null);
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, WIDTH, HEIGHT);
	}
	
	// for horizontal collision
	public Rectangle getHorizontalBounds() {
		float bx = x + player.getVelx();
		float by = y+5;
		float bw = WIDTH; 
		float bh = HEIGHT-10;
		return new Rectangle((int)bx, (int)by, (int)bw, (int)bh); 
	}
	
	// for vertical collision
	public Rectangle getVerticalBounds() {
		float bx = x+5;
		float by = y + player.getVely();
		float bw = WIDTH-10; 
		float bh = HEIGHT;
		return new Rectangle((int)bx, (int)by, (int)bw, (int)bh);
	}
}
