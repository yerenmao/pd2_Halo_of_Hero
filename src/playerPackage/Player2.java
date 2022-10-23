package playerPackage;

import bulletPackage.BounceBullet;
import bulletPackage.CurvedBullet;
import bulletPackage.NormalBullet;
import gamePackage.GameObjectController;
import imagePackage.ImageLoader;

public class Player2 {
	
	public static final String name = "Frankie";
	
	public static final int[] totalHp = {900, 1300, 1800, 2300};
	public static final int[] totalHpUpgradeCost = {15, 20, 25};
	
	public static final float[] speed = {2.5f, 3f, 3.5f, 4f};
	public static final int[] speedUpgradeCost = {10 , 15, 20};
	
	public static final int leftKeyBulletLevel = 1;
	public static final int[] leftKeyBulletLevelUpgradeCost = {10, 20, 30};
	
	public static final int rightKeyBulletLevel = 1;
	public static final int[] rightKeyBulletLevelUpgradeCost = {10, 20, 30};
	
	public static final int[] maxLeftKeyBulletAmount = {100, 120, 140, 160};
	public static final int[] maxLeftKeyBulletAmountUpgradeCost = {10, 20, 30};
	
	public static final int[] maxRightKeyBulletAmount = {50, 60, 70, 110};
	public static final int[] maxRightKeyBulletAmountUpgradeCost = {10, 20, 30};
	
	public static void shootNormalBullet(float mx, float my, float x, float y, GameObjectController objectController, ImageLoader imageLoader, int level) {
		Player1.shootNormalBullet(mx, my, x, y, objectController, imageLoader, level);
	}
	
	public static void shootCurveBullet(float mx, float my, float x, float y, GameObjectController objectController, ImageLoader imageLoader, int level) {
		objectController.addPlayerBullet(new CurvedBullet(x+12, y+12, mx, my, imageLoader, objectController, level));
	}
}
