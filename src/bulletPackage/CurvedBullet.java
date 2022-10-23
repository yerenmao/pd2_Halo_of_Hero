package bulletPackage;

import assetsPackage.Door;
import assetsPackage.Wall;
import gamePackage.GameCamera;
import gamePackage.GameObjectController;
import imagePackage.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;


public class CurvedBullet extends NormalBullet {

	private int life =1;

	public static int Burnlevel;
	public static int[] BurnDamage = {20, 35, 50, 65};
	public static int[] Burntime = {3, 4, 5, 6};

	private static BufferedImage curvedBulletImage;
	static float y_v=0;
	
	public CurvedBullet(float x, float y, float mx, float my, ImageLoader imageLoader, GameObjectController objectController, int level) {
		super(x, y, mx, my, imageLoader, objectController);
		curvedBulletImage = imageLoader.getCurvedBulletImage();
		Burnlevel = level;
	}

	public void tick() {

		double theta = Math.atan(vely/velx);
		if (!(velx >0 && vely>0)){
			if (vely>0 && velx<0){
				theta+=Math.PI;
			}
			else if(vely<0 && velx<0){
				theta+= Math.PI;
			}
			else{
				theta+=2*Math.PI;
			}
		}

		double v = Math.sqrt(velx*velx + vely*vely);

		y_v += v;

		x += v * Math.cos(theta) - 5*Math.sin(theta)*Math.sin(y_v/50);
		y += v * Math.sin(theta) + 5*Math.cos(theta)*Math.sin(y_v/50);

		wallCollision();
	}

	public void render(Graphics g) {
		g.drawImage(curvedBulletImage, (int)x, (int)y, (int)x+Bullet.SIZE, (int)y+Bullet.SIZE, 0, 0, curvedBulletImage.getWidth(), curvedBulletImage.getHeight(), null);
	}
	
	protected void wallCollision() {
		for(Wall i : objectController.wallList) {
			if(getHorizontalBounds().intersects(i.getBounds())) {
				if(life == 1) objectController.removePlayerBullet(this);
				life--;
				velx *= (-1);
			}
			if(getVerticalBounds().intersects(i.getBounds())) {
				if(life == 1) objectController.removePlayerBullet(this);
				life--;
				vely *= (-1);
			}
		}
	}
	
	protected void doorCollision() {
		for(Door i : objectController.doorList) {
			if(getHorizontalBounds().intersects(i.getBounds())) {
				if(life == 1) objectController.removePlayerBullet(this);
				life--;
				velx *= (-1);
			}
			if(getVerticalBounds().intersects(i.getBounds())) {
				if(life == 1) objectController.removePlayerBullet(this);
				life--;
				vely *= (-1);
			}
		}
	}
	
	// for horizontal collision
	public Rectangle getHorizontalBounds() {
		float bx = x + velx;
		float by = y+5;
		float bw = Bullet.SIZE; 
		float bh = Bullet.SIZE-10;
		return new Rectangle((int)bx, (int)by, (int)bw, (int)bh); 
	}
	
	// for vertical collision
	public Rectangle getVerticalBounds() {
		float bx = x+5;
		float by = y + vely;
		float bw = Bullet.SIZE-10; 
		float bh = Bullet.SIZE;
		return new Rectangle((int)bx, (int)by, (int)bw, (int)bh);
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, Bullet.SIZE, Bullet.SIZE);
	}
}