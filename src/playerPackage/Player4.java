package playerPackage;

import bulletPackage.BounceBullet;
import bulletPackage.NormalBullet;
import bulletPackage.TrapBullet;
import gamePackage.GameObjectController;
import imagePackage.ImageLoader;

public class Player4 {
	
	public static final String name = "Marcus";
	
	public static final int[] totalHp = {800, 1200, 1600, 2000};
	public static final int[] totalHpUpgradeCost = {15, 20, 25};
	
	public static final float[] speed = {3.5f, 4f, 4.5f, 5f};
	public static final int[] speedUpgradeCost = {10 , 15, 20};	
	
	public static final int leftKeyBulletLevel = 1;
	public static final int[] leftKeyBulletLevelUpgradeCost = {10, 20, 30};
	
	public static final int rightKeyBulletLevel = 1;
	public static final int[] rightKeyBulletLevelUpgradeCost = {10, 20, 30};
	
	public static final int[] maxLeftKeyBulletAmount = {120, 140, 165, 190};
	public static final int[] maxLeftKeyBulletAmountUpgradeCost = {10, 20, 30};
	
	public static final int[] maxRightKeyBulletAmount = {60, 80, 100, 120};
	public static final int[] maxRightKeyBulletAmountUpgradeCost = {10, 20, 30};
	
	public static final int[] bulletDuration = {2000, 3500, 5000, 6500};
	
	public static void shootNormalBullet(float mx, float my, float x, float y, GameObjectController objectController, ImageLoader imageLoader, int level) {
		Player1.shootNormalBullet(mx, my, x, y, objectController, imageLoader, level);
	}
	
	public static void shootTrapBullet(float mx, float my, float x, float y, GameObjectController objectController, ImageLoader imageLoader, long start_sec, int level) {
		objectController.addPlayerBullet(new TrapBullet(x+12, y+12, mx, my, imageLoader, objectController, start_sec, bulletDuration[level]));
	}
}
