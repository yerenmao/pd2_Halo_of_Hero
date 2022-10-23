package playerPackage;

import bulletPackage.BounceBullet;
import bulletPackage.Bullet;
import bulletPackage.NormalBullet;
import gamePackage.GameObjectController;
import imagePackage.ImageLoader;

public class Player1 {
	
	public static final String name = "Joseph";
	
	public static final int[] totalHp = {800, 1200, 1600, 2000};
	public static final int[] totalHpUpgradeCost = {15, 20, 25};
	
	public static final float[] speed = {3f, 3.5f, 4f, 4.5f};
	public static final int[] speedUpgradeCost = {10 , 15, 20};
	
	public static final int leftKeyBulletLevel = 0;
	public static final int[] leftKeyBulletLevelUpgradeCost = {10, 20, 30};
	
	public static final int rightKeyBulletLevel = 0;
	public static final int[] rightKeyBulletLevelUpgradeCost = {10, 20, 30};
	
	public static final int[] maxLeftKeyBulletAmount = {100, 140, 180, 220};
	public static final int[] maxLeftKeyBulletAmountUpgradeCost = {10, 20, 30};
	
	public static final int[] maxRightKeyBulletAmount = {40, 60, 80, 100};
	public static final int[] maxRightKeyBulletAmountUpgradeCost = {10, 20, 30};
	
	public static void shootNormalBullet(float mx, float my, float x, float y, GameObjectController objectController, ImageLoader imageLoader, int level) {
		switch(level) {
		case 0: shootNormalBulletLevel0(mx, my, x+12, y+12, objectController, imageLoader); break;
		case 1: shootNormalBulletLevel1(mx, my, x+12, y+12, objectController, imageLoader); break;
		case 2: shootNormalBulletLevel2(mx, my, x+12, y+12, objectController, imageLoader); break;
		case 3: shootNormalBUlletLevel3(mx, my, x+12, y+12, objectController, imageLoader); break;
		default: break;
		}
			
		
	}
	
	public static void shootBounceBullet(float mx, float my, float x, float y, GameObjectController objectController, ImageLoader imageLoader, int level) {
		objectController.addPlayerBullet(new BounceBullet(x, y, mx, my, imageLoader, objectController, level));
	}
	
	private static void shootNormalBulletLevel0(float mx, float my, float x, float y, GameObjectController objectController, ImageLoader imageLoader) {
		objectController.addPlayerBullet(new NormalBullet(x, y, mx, my, imageLoader, objectController));
	}
	
	private static void shootNormalBulletLevel1(float mx, float my, float x, float y, GameObjectController objectController, ImageLoader imageLoader) {
		double s1 = Math.PI/36;
		double s2 = -Math.PI/36;
		shootWithAngle(x, y, mx, my, s1, objectController, imageLoader);
		shootWithAngle(x, y, mx, my, s2, objectController, imageLoader);
	}
	
	private static void shootNormalBulletLevel2(float mx, float my, float x, float y, GameObjectController objectController, ImageLoader imageLoader) {
		double s1 = Math.PI/36;
		double s2 = -Math.PI/36;
		shootWithAngle(x, y, mx, my, s1, objectController, imageLoader);
		shootWithAngle(x, y, mx, my, s2, objectController, imageLoader);
		float vx = mx - x, vy = my - y;
		objectController.addPlayerBullet(new NormalBullet(x, y, x-vx, y-vy, imageLoader, objectController));
	}
	
	private static void shootNormalBUlletLevel3(float mx, float my, float x, float y, GameObjectController objectController, ImageLoader imageLoader) {
		objectController.addPlayerBullet(new NormalBullet(x, y, mx, my, imageLoader, objectController));
		double s1 = Math.PI/180*8;
		double s2 = -Math.PI/180*8;
		shootWithAngle(x, y, mx, my, s1, objectController, imageLoader);
		shootWithAngle(x, y, mx, my, s2, objectController, imageLoader);
		float vx = mx - x, vy = my - y;
		objectController.addPlayerBullet(new NormalBullet(x, y, x-vx, y-vy, imageLoader, objectController));
	}
	
	private static void shootWithAngle(float ix, float iy, float fx, float fy, double s, GameObjectController objectController, ImageLoader imageLoader) {
		double distance = (float) Math.sqrt((ix-fx)*(ix-fx)+(iy-fy)*(iy-fy));
		double tan = (iy-fy)/(fx-ix);
		double theta = Math.atan(tan);
		if(fx < ix) {
			theta = Math.atan(tan) + Math.PI;
		}
		s += theta;
		float nfx = ix + (float)(distance*Math.cos(s));
		float nfy = iy - (float)(distance*Math.sin(s));
		objectController.addPlayerBullet(new NormalBullet(ix, iy, nfx, nfy, imageLoader, objectController));
	}
}
