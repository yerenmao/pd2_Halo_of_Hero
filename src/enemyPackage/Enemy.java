package enemyPackage;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import effectPackage.Animation;
import gamePackage.GameObject;
import gamePackage.GameObjectController;
import imagePackage.ImageLoader;
import pickUpPackage.CoinPU;
import pickUpPackage.DimondPU;

public abstract class Enemy extends GameObject {
	
	protected float x, y;
	protected float velx, vely;
	public static final int WIDTH = 48, HEIGHT = 48;
	protected ImageLoader imageLoader;
	protected GameObjectController objectController;
	protected Animation animation;
	private Random r = new Random();
	
	protected Enemy(float x, float y, ImageLoader imageLoader, GameObjectController objectController) {
		this.x = x;
		this.y = y;
		this.imageLoader = imageLoader;
		this.objectController = objectController;
	}
	
	protected void drawHUD(Graphics g, int hpOnScreen, int totalHp) {
		g.setColor(Color.black);
		g.drawRect((int)x+3, (int)y-10, Enemy.WIDTH-6, 10);
		g.setColor(new Color((int)(255*(1-hpOnScreen/(float)totalHp)), (int)(255*hpOnScreen/(float)totalHp), 0));
		g.fillRect((int)x+3, (int)y-10, (int)((Enemy.WIDTH-6)*hpOnScreen/(float)totalHp)+1, 10);
	}
	
	protected void drawHUD(Graphics g, int hpOnScreen, int totalHp, int width) {
		g.setColor(Color.black);
		g.drawRect((int)x+3, (int)y-10, width-6, 10);
		g.setColor(new Color((int)(255*(1-hpOnScreen/(float)totalHp)), (int)(255*hpOnScreen/(float)totalHp), 0));
		g.fillRect((int)x+3, (int)y-10, (int)((width-6)*hpOnScreen/(float)totalHp)+1, 10);
	}
	
	protected void dropPU() {
		int n = r.nextInt(3) + 1;
		for(int i = 0; i < n; i++) { 
			objectController.addPickUp(new DimondPU((int)x - 5 + r.nextInt(-20, 21), (int)y - 5 + r.nextInt(-20, 21), imageLoader, objectController, true));
		}
		objectController.addPickUp(new CoinPU((int)x + 5 + r.nextInt(-20, 21), (int)y + 5 + r.nextInt(-20, 21), imageLoader, objectController, true));
	}
	
	protected abstract void wallCollision();
	protected abstract void playerBulletCollision();
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
}
