package assetsPackage;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import gamePackage.GameObjectController;
import imagePackage.ImageLoader;
import levelPackage.LevelTest;
import playerPackage.Player;

public class MovingWall {
	public static final int WIDTH = 120, HEIGHT = 120;
	
	private Player player;
	private BufferedImage movingWallImage;
	private GameObjectController controller;
	
	private float x, y;
	private float velx = 1, vely = 1;
	
	public MovingWall(int x, int y, ImageLoader imageLoader, GameObjectController controller, Player player) {
		this.x = x;
		this.y = y;
		movingWallImage = imageLoader.getMovingWallImage();
		this.controller = controller;
		this.player = player;
	}
	
	public MovingWall(int x, int y, float velx, float vely, ImageLoader imageLoader, GameObjectController controller, Player player) {
		this(x, y, imageLoader, controller, player);
		this.velx = velx;
		this.vely = vely;
	}
	
	public void tick() {
		move();
		playerCollision();
		boundaryCollision();
	}
	
	private void move() {
		x += velx;
		y += vely;
	}
	
	private void playerCollision() {
		if(player.getHorizontalBounds().intersects(getBounds())) {
			if(velx < 0 && player.getX() < x) {
				player.setX(x-Player.WIDTH);
			} else if(velx > 0 && player.getX() > x) {
				player.setX(x+WIDTH);
			}
			if(player.getVelx() > 0) {
				player.setVelx(0);
				player.setX(x-Player.WIDTH);
			} else if(player.getVelx() < 0) {
				player.setVelx(0);
				player.setX(x+WIDTH);
			}
		}
		if(player.getVerticalBounds().intersects(getBounds())) {
			if(vely < 0 && player.getY() < y) {
				player.setY(y-Player.HEIGHT);
			} else if(vely > 0 && player.getY() > y) {
				player.setY(y+HEIGHT);
			}
			if(player.getVely() > 0) {
				player.setVely(0);
				player.setY(y-Player.HEIGHT);
			} else if(player.getVely() < 0) {
				player.setVely(0);
				player.setY(y+HEIGHT);
			}
		}

//			if(getHorizontalBounds().intersects(i.getBounds())) {
//				if(i.getVelX() < 0 && x < i.getX()+i.getW()/2) {
//					x = i.getX() - 32;
//				} else if(i.getVelX() > 0 && x > i.getX()+i.getW()/2) {
//					x = i.getX() + i.getW();
//				}
//			 	if(velX > 0) {
//			 		velX = 0;
//			 		x = i.getX() - 32;
//			 	} else if(velX < 0) {
//			 		velX = 0;
//			 		x = i.getX() + i.getW();
//			 	}
//			}
//			if(getVerticalBounds().intersects(i.getBounds())) {
//				if(i.getVelY() < 0 && y < i.getY()+i.getH()/2) {
//					y = i.getY() - 32;
//				} else if(i.getVelY() > 0 && y > i.getY()+i.getH()/2) {
//					y = i.getY() + i.getH();
//				}
//				if(velY > 0) {
//			 		velY = 0;
//			 		y = i.getY() - 32;
//			 	} else if(velY < 0) {
//			 		velY = 0;
//			 		y = i.getY() + i.getH();
//			 	}
//			}
//		}
	}
	
	private void boundaryCollision() {
		if(x < LevelTest.secondboundaryX) {
			velx *= -1;
		}
		if(x+WIDTH > LevelTest.secondboundaryX+LevelTest.secondboundaryW) {
			velx *= -1;
		}
		if(y < LevelTest.secondboundaryY) {
			vely *= -1;
		}
		if(y+HEIGHT > LevelTest.secondboundaryY+LevelTest.secondboundaryH) {
			vely *= -1;
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(movingWallImage, (int)x, (int)y, (int)x+WIDTH, (int)y+HEIGHT, 0, 0, movingWallImage.getWidth(), movingWallImage.getHeight(), null);
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, WIDTH, HEIGHT);
	}
}
