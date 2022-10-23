package playerPackage;

import bulletPackage.BounceBullet;
import bulletPackage.NormalBullet;
import bulletPackage.SatelliteBullet;
import gamePackage.GameObjectController;
import imagePackage.ImageLoader;

public class Player3 {
	
	public static final String name = "Nathan";
	
	public static final int[] totalHp = {1000, 1500, 2000, 2500};
	public static final int[] totalHpUpgradeCost = {15, 20, 25};
	
	public static final float[] speed = {3f, 3.5f, 4f, 4.5f};
	public static final int[] speedUpgradeCost = {10 , 15, 20};
	
	public static final int leftKeyBulletLevel = 1;
	public static final int[] leftKeyBulletLevelUpgradeCost = {10, 20, 30};
	
	public static final int rightKeyBulletLevel = 1;
	public static final int[] rightKeyBulletLevelUpgradeCost = {10, 20, 30};	

	public static final int[] maxLeftKeyBulletAmount = {100, 115, 130, 150};
	public static final int[] maxLeftKeyBulletAmountUpgradeCost = {10, 20, 30};
	
	public static final int[] maxRightKeyBulletAmount = {35, 50, 70, 90};
	public static final int[] maxRightKeyBulletAmountUpgradeCost = {10, 20, 30};
	
	public static final int[] bulletRadius = {60, 80, 100 ,120};
	
	public static void shootNormalBullet(float mx, float my, float x, float y, GameObjectController objectController, ImageLoader imageLoader, int level) {
		Player1.shootNormalBullet(mx, my, x, y, objectController, imageLoader, level);
	}
	
	public static void shootSatelliteBullet(float mx, float my, float x, float y, GameObjectController objectController, ImageLoader imageLoader, Player player, long start_sec, int level) {
		objectController.addPlayerBullet(new SatelliteBullet(x+12, y+12, mx, my, imageLoader, objectController, player, start_sec, bulletRadius[level]));
	}
}
